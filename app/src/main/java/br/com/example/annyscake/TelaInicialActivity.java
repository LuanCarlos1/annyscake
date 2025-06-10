package br.com.example.annyscake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class TelaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(16441125);
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();


            if (auth.getCurrentUser() == null) {

                startActivity(new Intent(this, TelaLogin.class));
                finish();
                return;
            }

            String idUsuario = auth.getUid();
            String email = auth.getCurrentUser().getEmail();

            assert idUsuario != null;
            DocumentReference docRef = db.collection("usuarios").document(idUsuario);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Boolean logado = documentSnapshot.getBoolean("logado");
                    if (logado != null && logado) {
                        if ("suanne@gmail.com".equals(email)) {
                            moverTelaAdmin();
                        } else {
                            moverTelaCliente();
                        }
                        finish();
                    }
                }
            });

            // Adicionar transição suave opcional
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 3000);


    }

    private void moverTelaAdmin(){
        startActivity(new Intent(this, TelaAdmin.class));
    }
    private void moverTelaCliente(){
        startActivity(new Intent(this, TelaCliente.class));
    }
}

