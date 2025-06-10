package br.com.example.annyscake.ui.Admin;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;


public class HistoricoAdmin extends Fragment {

    public HistoricoAdmin() {

    }

    private LinearLayout layoutPedidos;
    private Button btnLimpar;
    private FirebaseFirestore db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_historico_admin, container, false);

        layoutPedidos = root.findViewById(R.id.layoutPedidosFinalizados);
        btnLimpar = root.findViewById(R.id.btnLimparHistorico);
        db = FirebaseFirestore.getInstance();

        layoutPedidos.removeAllViews();

        carregarPedidos("Finalizado");
        carregarPedidos("Cancelado");
        carregarPedidos("Recusado");


        btnLimpar.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Limpar histórico?")
                    .setMessage("Tem certeza que deseja apagar todos os pedidos finalizados e cancelados?")
                    .setPositiveButton("Sim", (dialog, which) -> limparHistorico())
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        return root;
    }

    private void carregarPedidos(String status) {
        CollectionReference pedidosRef = db.collection("historico_admin");

        pedidosRef.whereEqualTo("status", status)
                .get().addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        TextView titulo = new TextView(getContext());
                        titulo.setText(status.equals("Finalizado") ? "📋 Pedidos Finalizados" :
                                status.equals("Cancelado") ? "📋 Pedidos Cancelados" :
                                        "📋 Pedidos Recusados");
                        titulo.setTextSize(18);
                        titulo.setPadding(16, 32, 16, 8);
                        layoutPedidos.addView(titulo);
                        btnLimpar.setVisibility(View.VISIBLE);
                    }

                    int contador = 1;
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Pedido pedido = doc.toObject(Pedido.class);

                        TextView txtPedido = new TextView(getContext());
                        txtPedido.setText("📦 Pedido " + contador++ + ":\n"
                                + "Cliente: " + pedido.getNome() + "\n"
                                + "Telefone: " + pedido.getTelefone() + "\n"
                                + "Endereço: " + pedido.getEndereco() + "\n"
                                + "Data de Entrega: " + pedido.getDataEntrega() + "\n"
                                + "Tema: " + pedido.getTema() + "\n"
                                + "Tamanho: " + pedido.getTamanho() + "\n"
                                + "Massa: " + pedido.getMassa() + "\n"
                                + "Recheio: " + pedido.getRecheio() + "\n"
                                + "Recheio Especial: " + pedido.getRecheioEspecial() + "\n"
                                + "Valor: R$ " + pedido.getValor() + "\n"
                                + "Status: " + (status.equals("Finalizado") ? "Finalizado ✔️" :
                                status.equals("Cancelado") ? "Cancelado ❌" :
                                        "Recusado ❌"));

                        txtPedido.setPadding(16, 16, 16, 16);
                        txtPedido.setBackgroundColor(Color.parseColor("#FAFAFA"));
                        layoutPedidos.addView(txtPedido);
                    }
                });
    }


    private void limparHistorico() {
        CollectionReference historicoRef = db.collection("historico_admin");

        // Apagar finalizados
        historicoRef.whereEqualTo("status", "Finalizado").get().addOnSuccessListener(querySnapshot -> {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                historicoRef.document(doc.getId()).delete();
            }
        });

        // Apagar recusados
        historicoRef.whereEqualTo("status", "Recusado").get().addOnSuccessListener(querySnapshot -> {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                historicoRef.document(doc.getId()).delete();
            }
        });

        // Apagar cancelados
        historicoRef.whereEqualTo("status", "Cancelado").get().addOnSuccessListener(querySnapshot -> {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                historicoRef.document(doc.getId()).delete();
            }

            layoutPedidos.removeAllViews();
            btnLimpar.setVisibility(View.GONE);
        });
    }
}