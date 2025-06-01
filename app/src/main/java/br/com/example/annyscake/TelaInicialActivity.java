package br.com.example.annyscake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual == null){

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent moverParaAMain = new Intent(TelaInicialActivity.this, TelaLogin.class);
                startActivity(moverParaAMain);
                // Adicionar transição suave opcional
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }, 3000);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null) {
            String usuarioAtualId = usuarioAtual.getUid();

            DocumentReference docRef = FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document(usuarioAtualId);

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot != null) {
                    Boolean logado = documentSnapshot.getBoolean("logado");
                    if (Boolean.TRUE.equals(logado)) {
                        String email = usuarioAtual.getEmail();
                        if (!"suanne@gmail.com".equals(email)) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                moverTelaCliente();
                                // Adicionar transição suave opcional
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }, 3000);

                        } else {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                moverTelaAdmin();
                                // Adicionar transição suave opcional
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }, 3000);
                        }
                    }
                }
            });
        }
    }

    private void moverTelaAdmin(){
        Intent intent = new Intent(this, TelaAdmin.class);
        startActivity(intent);
    }
    private void moverTelaCliente(){
        Intent intent = new Intent(this, TelaCliente.class);
        startActivity(intent);
    }
}