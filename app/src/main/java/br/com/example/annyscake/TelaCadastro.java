package br.com.example.annyscake;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TelaCadastro extends AppCompatActivity {

    private EditText editNome, editTelefone, editEndereco, editEmail, editSenha;
    private Button btnCadastrar;
    private TextView txtCadastro;
    String [] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);

        inicarComponentes();

        Objects.requireNonNull(getSupportActionBar()).hide();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getBtnCadastrar().setOnClickListener(v -> {
            String nome = getEditNome().getText().toString();
            String telefone = getEditTelefone().getText().toString();
            String endereco = getEditEndereco().getText().toString();
            String email = getEditEmail().getText().toString();
            String senha = getEditSenha().getText().toString();

            if(nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(v, mensagens[0],Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.setAnchorView(txtCadastro);
                snackbar.show();
            }else{
                cadastrarUsuario(v);
            }
        });
    }

    private void inicarComponentes(){
        setEditNome(findViewById(R.id.editNomeCadastro));
        setEditTelefone(findViewById(R.id.editTelefoneCadastro));
        setEditEndereco(findViewById(R.id.editEnderecoCadastro));
        setEditEmail(findViewById(R.id.editEmailCadastro));
        setEditSenha(findViewById(R.id.editSenhaCadastro));
        setBtnCadastrar(findViewById(R.id.btn_Cadastrar));
        setTxtCadastro(findViewById(R.id.txtCadastrese));
    }


    private void cadastrarUsuario(View v){
        String email = getEditEmail().getText().toString();
        String senha = getEditSenha().getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                salvarDadosUsuario();

                Snackbar snackbar = Snackbar.make(v, mensagens[1],Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.setAnchorView(txtCadastro);
                snackbar.show();

                Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
                startActivity(intent);
                finish();

            }else{
                String erro;
                try {
                    throw Objects.requireNonNull(task.getException());

                }catch (FirebaseAuthWeakPasswordException e) {
                    erro = "Digite uma senha com no mínimo 6 caracteres";
                }catch (FirebaseAuthUserCollisionException e) {
                    erro = "Esta conta já foi cadastrada";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    erro = "Email inválido";
                }catch (Exception e){
                    erro = "Erro ao cadastrar usuário";
                }

                Snackbar snackbar = Snackbar.make(v, erro,Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.setAnchorView(txtCadastro);
                snackbar.show();
            }
        });
    }

    private void salvarDadosUsuario(){
        String nome = getEditNome().getText().toString();
        String telefone = getEditTelefone().getText().toString();
        String endereco = getEditEndereco().getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("telefone", telefone);
        usuarios.put("endereco", endereco);

        usuarioId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(unused -> Log.d("db", "Sucesso ao salvar os dados")).addOnFailureListener(e -> Log.d("db_error", "Erro ao salvar os dados" + e));
    }

    public EditText getEditNome() {
        return editNome;
    }

    public void setEditNome(EditText editNome) {
        this.editNome = editNome;
    }

    public EditText getEditTelefone() {
        return editTelefone;
    }

    public void setEditTelefone(EditText editTelefone) {
        this.editTelefone = editTelefone;
    }

    public EditText getEditEndereco() {
        return editEndereco;
    }

    public void setEditEndereco(EditText editEndereco) {
        this.editEndereco = editEndereco;
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

    public Button getBtnCadastrar() {
        return btnCadastrar;
    }

    public void setBtnCadastrar(Button btnCadastrar) {
        this.btnCadastrar = btnCadastrar;
    }

    public TextView getTxtCadastro() {
        return txtCadastro;
    }

    public void setTxtCadastro(TextView txtCadastro) {
        this.txtCadastro = txtCadastro;
    }
}