package br.com.example.annyscake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class TelaAdmin extends AppCompatActivity {

    private TextView txtRelogio;
    private final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnTelaSair = findViewById(R.id.btnIrTelaDeslogar);

        btnTelaSair.setOnClickListener(v -> {
            Intent intent = new Intent(this, TelaUsuario.class);
            startActivity(intent);
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

        txtRelogio = findViewById(R.id.txtRelogio);
        handler.post(atualizarRelogio);
    }
    private final Runnable atualizarRelogio = new Runnable() {
        @Override
        public void run() {
            String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            txtRelogio.setText(dataHora);
            handler.postDelayed(this, 1000);
        }
    };
}