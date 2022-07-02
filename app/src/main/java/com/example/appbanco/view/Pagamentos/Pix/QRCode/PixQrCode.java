package com.example.appbanco.view.Pagamentos.Pix.QRCode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cobranca;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PagarCobranca;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PixQrCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    DatabaseReference dbref;
    Cobranca cobranca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
//        dbref= FirebaseDatabase.getInstance().getReference("cobrancasQrCode");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    public void handleResult(Result rawResult) {
        String data=rawResult.getText().toString();

        recuperarCobranca(data);


//        dbref.push().setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//
//                        onBackPressed();
//                    }
//                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    private void recuperarCobranca(String idCobranca){
        DatabaseReference cobrancaRef = FirebaseHelper.getDatabaseReference()
                .child("cobrancasQrCode")
                .child(idCobranca);

        cobrancaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cobranca = snapshot.getValue(Cobranca.class);
                if(!cobranca.getIdCobrador().equals(FirebaseHelper.getIdFirebase())){
                    cobranca.setIdDestinatario(FirebaseHelper.getIdFirebase());

                    Intent intent = new Intent(PixQrCode.this, PagarCobranca.class);
                    intent.putExtra("cobranca", cobranca);
                    startActivity(intent);

                }else{
                    Toast.makeText(PixQrCode.this, "Você não pode pagar uma cobrança que você criou", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}