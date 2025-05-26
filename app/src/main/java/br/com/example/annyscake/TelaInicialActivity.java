package br.com.example.annyscake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class TelaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(16441125);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent moverParaAMain = new Intent(TelaInicialActivity.this, MainActivity.class);
            startActivity(moverParaAMain);
            // Adicionar transição suave opcional
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3000);
    }
}