package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagarCobrancaReciboBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Pagamento;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PagarCobrancaRecibo extends AppCompatActivity {

    ActivityPagarCobrancaReciboBinding binding;
    private Usuario userDestino;
    private Pagamento pagamento;
    private ConstraintLayout linear;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarCobrancaReciboBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        binding.btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size", "" + linear.getWidth() + " " + linear.getWidth());
                bitmap = LoadBitmap(linear, linear.getWidth(), linear.getHeight());
                createPdf();
            }
        });

        linear = findViewById(R.id.lineard);


        recuperaDados();

        binding.buttonRecibo.setOnClickListener(view1 -> {
            Intent intent = new Intent(PagarCobrancaRecibo.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void recuperaDados() {
        String idPagamento = (String) getIntent().getSerializableExtra("idPagamento");

        DatabaseReference pagRef = FirebaseHelper.getDatabaseReference()
                .child("pagamentos")
                .child(idPagamento);
        pagRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pagamento = snapshot.getValue(Pagamento.class);

                getUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private Bitmap LoadBitmap(View v, int width, int height) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);


        return bitmap;


    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(pagamento.getIdUserDestino());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDestino = snapshot.getValue(Usuario.class);
                configDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void configDados() {
        binding.tvCodigo.setText(pagamento.getId());
        binding.tvPessoa.setText(userDestino.getNome());
        binding.tvData.setText(GetMask.getDate(pagamento.getData(), 3));
        binding.tvValor.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(pagamento.getValor())));
    }

    private void createPdf() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        int convertWidth = (int) width, convertHeight = (int) height;


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        String targetPdf = "/sdcard/Recibo.pdf";
        File file;
        file = new File(targetPdf);
        try {


            document.writeTo(new FileOutputStream(file));

            document.close();


            Toast.makeText(this, "baixado com sucesso", Toast.LENGTH_SHORT).show();

            openPdf();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "algo errado tente novamente!" + e.toString(), Toast.LENGTH_SHORT).show();


            //depois de fechar o documento

        }
    }

    private void openPdf() {


        File file = new File("/sdcard/recibo.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);

            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Nenhum aplicativo para visualização em pdf", Toast.LENGTH_SHORT).show();

            }


        }
    }

}