package br.com.example.annyscake.ui.perfilCliente;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import br.com.example.annyscake.R;
import br.com.example.annyscake.TelaLogin;


public class PerfilCliente extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        

        return inflater.inflate(R.layout.fragment_perfil_cliente, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnDeslogar = view.findViewById(R.id.btnDeslogarCliente);

        TextView emailUsuario = view.findViewById(R.id.miniEmail);
        TextView nomeUsuario = view.findViewById(R.id.miniNome);


        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("usuarios").document(usuarioID);
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if(documentSnapshot != null){
                nomeUsuario.setText(documentSnapshot.getString("nome"));
                emailUsuario.setText(email);
            }
        });

        btnDeslogar.setOnClickListener(v -> {

            usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("usuarios").document(uid);

                docRef.update("logado", false)
                        .addOnSuccessListener(aVoid -> Log.d("Login", "Campo 'logado' atualizado com sucesso"))
                        .addOnFailureListener(e -> Log.e("Login", "Erro ao atualizar 'logado': " + e.getMessage()));
            }
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), TelaLogin.class);
            startActivity(intent);
            requireActivity().finish();

        });

    }
}