package com.example.appbanco.view.Pagamentos.Pix.QRCode;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityCriarPixQrCodeBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cobranca;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Locale;

public class CriarPixQrCode extends AppCompatActivity {

    ImageView imageView;
    Button btnQRCode;
    EditText textView;
    private Cobranca cobranca;
    private ActivityCriarPixQrCodeBinding binding;
    private CurrencyEditText edtValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarPixQrCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        imageView = findViewById(R.id.imageView);
//        btnQRCode = findViewById(R.id.generateBtn);


        edtValor = findViewById(R.id.edtValCobrar);
        edtValor.setLocale(new Locale("PT", "br"));

        binding.generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtValor.getText())) {
                    double valorCobrar = (double) edtValor.getRawValue() / 100;

                    criarTransferencia(valorCobrar);
                    enviarTransferencia();

                    Bitmap qrCode = createBitmap(cobranca.getId());

                    binding.imageView.setImageBitmap(qrCode);
                }
            }
        });

    }

    private Bitmap createBitmap(String text) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300,
                    300, null);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int x = 0; x < height; x++) {
            int offset = x * width;
            for (int k = 0; k < width; k++) {
                pixels[offset + k] = result.get(k, x) ? BLACK : WHITE;
            }
        }
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return myBitmap;
    }

    private void criarTransferencia(double valor) {
        cobranca = new Cobranca();
        cobranca.setValor(valor);
        cobranca.setIdCobrador(FirebaseHelper.getIdFirebase());
    }

    private void enviarTransferencia() {
        DatabaseReference cobrancaRef = FirebaseHelper.getDatabaseReference()
                .child("cobrancasQrCode")
                .child(cobranca.getId());

        cobrancaRef.setValue(cobranca).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DatabaseReference updateRef = cobrancaRef
                        .child("data");
                updateRef.setValue(ServerValue.TIMESTAMP);

            } else {
                Toast.makeText(this, "Não foi possível confirmar a cobrança.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}