package br.com.example.annyscake.ui.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import br.com.example.annyscake.R;


public class InicioAdmin extends Fragment {

    public InicioAdmin() {
    }


    Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        TextView textSaudacao = root.findViewById(R.id.olaAdmin);

        TextView textRelogio = root.findViewById(R.id.textRelogio);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo")); // Fuso horário de Brasília
                String dataHora = sdf.format(new Date());
                textRelogio.setText(dataHora);
                handler.postDelayed(this, 1000); // atualiza a cada 1 segundo
            }
        };
        handler.post(runnable);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("pedidos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalPedidos = queryDocumentSnapshots.size(); // Total de documentos
                    if(totalPedidos > 1){
                        textSaudacao.setText("Você tem " + totalPedidos + " pedidos!");
                    }else{
                        textSaudacao.setText("Você tem " + totalPedidos + " pedido!");

                    }
                });

        return root;
    }
}