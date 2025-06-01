package br.com.example.annyscake;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Objects;

public class TelaLogin extends AppCompatActivity {

    private TextView criarConta;
    private EditText editEmail, editSenha;
    private Button entrar;
    private ProgressBar progressBar;
    String [] mensagens = {"Preencha todos os campos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        iniciarComponentes();


        criarConta = findViewById(R.id.txtTelaCadastro);

        Objects.requireNonNull(getSupportActionBar()).hide();

        entrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.setAnchorView(criarConta);
                snackbar.show();
            }else {
                autenticarUsuario(v);
            }

        });


        criarConta.setOnClickListener(v -> {
            Intent mudarParaCadastro = new Intent(TelaLogin.this, TelaCadastro.class);
            startActivity(mudarParaCadastro);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void autenticarUsuario(View v){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnSuccessListener(authResult -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String uid = user.getUid();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("usuarios").document(uid);

                    docRef.update("logado", true)
                            .addOnSuccessListener(aVoid -> Log.d("Login", "Campo 'logado' atualizado com sucesso"))
                            .addOnFailureListener(e -> Log.e("Login", "Erro ao atualizar 'logado': " + e.getMessage()));
                }else {
                    String erro;
                    try {
                        throw Objects.requireNonNull(task.getException());
                    }catch (Exception e){
                        erro = "Erro ao logar usuário";
                    }
                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.setAnchorView(criarConta);
                    snackbar.show();
                }

                // Continue para mover tela após atualizar o campo
                if (email.equals("suanne@gmail.com")) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(this::moverTelaAdmin, 2000);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(this::moverTelaCliente, 2000);
                }
            });
        });
    }


    private void moverTelaAdmin(){
        Intent intent = new Intent(TelaLogin.this, TelaAdmin.class);
        startActivity(intent);
    }
    private void moverTelaCliente(){
        Intent intent = new Intent(TelaLogin.this, TelaCliente.class);
        startActivity(intent);
    }


    private void iniciarComponentes(){
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        entrar = findViewById(R.id.btnEntrar);
        progressBar = findViewById(R.id.progressbar);
    }
}