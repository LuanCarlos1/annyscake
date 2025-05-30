package br.com.example.annyscake;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class TelaUsuario extends AppCompatActivity {

    private TextView emailUsuario, nomeUsuario;
    private Button btnDeslogar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_usuario);

        Objects.requireNonNull(getSupportActionBar()).hide();

        inicializarComponentes();


        getBtnDeslogar().setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(TelaUsuario.this, TelaLogin.class);
            startActivity(intent);
            finish();

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if(documentSnapshot != null){
                getNomeUsuario().setText(documentSnapshot.getString("nome"));
                getEmailUsuario().setText(email);
            }
        });
    }

    private void inicializarComponentes(){
        setNomeUsuario(findViewById(R.id.miniNome));
        setEmailUsuario(findViewById(R.id.miniEmail));
        setBtnDeslogar(findViewById(R.id.btnDeslogar));
    }

    public TextView getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(TextView emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public TextView getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(TextView nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Button getBtnDeslogar() {
        return btnDeslogar;
    }

    public void setBtnDeslogar(Button btnDeslogar) {
        this.btnDeslogar = btnDeslogar;
    }
}