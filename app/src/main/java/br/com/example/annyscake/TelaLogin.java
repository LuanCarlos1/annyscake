package br.com.example.annyscake;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

        setCriarConta(findViewById(R.id.txtTelaCadastro));

        Objects.requireNonNull(getSupportActionBar()).hide();

        getEntrar().setOnClickListener(v -> {
            String email = getEditEmail().getText().toString();
            String senha = getEditSenha().getText().toString();

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
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void autenticarUsuario(View v){
        String email = getEditEmail().getText().toString();
        String senha = getEditSenha().getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                getProgressBar().setVisibility(View.VISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(this::moverParaPrincipal,2000);
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
                snackbar.setAnchorView(getCriarConta());
                snackbar.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null){
            moverParaPrincipal();
        }
    }

    private void moverParaPrincipal(){
        Intent intent = new Intent(TelaLogin.this, TelaUsuario.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes(){
        setEditEmail(findViewById(R.id.editEmail));
        setEditSenha(findViewById(R.id.editSenha));
        setEntrar(findViewById(R.id.btnEntrar));
        setProgressBar(findViewById(R.id.progressbar));
    }

    public EditText getEditEmail() {
        return editEmail;
    }

    public void setEditEmail(EditText editEmail) {
        this.editEmail = editEmail;
    }

    public EditText getEditSenha() {
        return editSenha;
    }

    public void setEditSenha(EditText editSenha) {
        this.editSenha = editSenha;
    }

    public Button getEntrar() {
        return entrar;
    }

    public void setEntrar(Button entrar) {
        this.entrar = entrar;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getCriarConta() {
        return criarConta;
    }

    public void setCriarConta(TextView criarConta) {
        this.criarConta = criarConta;
    }
}