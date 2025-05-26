package br.com.example.annyscake.ui.historico;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;

public class Historico extends Fragment {

    private LinearLayout layoutPedidos;
    private Button btnLimpar;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_historico, container, false);

        layoutPedidos = root.findViewById(R.id.layoutPedidosFinalizados);
        btnLimpar = root.findViewById(R.id.btnLimparHistorico);
        db = FirebaseFirestore.getInstance();

        layoutPedidos.removeAllViews();

        carregarPedidos("finalizado");
        carregarPedidos("cancelado");

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
        CollectionReference pedidosRef = db.collection("pedidos");

        pedidosRef.whereEqualTo("status", status).get().addOnSuccessListener(querySnapshot -> {
            if (!querySnapshot.isEmpty()) {
                TextView titulo = new TextView(getContext());
                titulo.setText(status.equals("finalizado") ? "📋 Pedidos Finalizados" : "📋 Pedidos Cancelados");
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
                        + "Cliente: " + pedido.nome + "\n"
                        + "Telefone: " + pedido.telefone + "\n"
                        + "Endereço: " + pedido.endereco + "\n"
                        + "Data de Entrega: " + pedido.data + "\n"
                        + "Tema: " + pedido.tema + "\n"
                        + "Tamanho: " + pedido.tamanho + "\n"
                        + "Massa: " + pedido.massa + "\n"
                        + "Recheio: " + pedido.recheio + "\n"
                        + "Recheio Especial: " + pedido.recheio_especial + "\n"
                        + "Valor: R$ " + pedido.valor + "\n"
                        + "Status: " + (status.equals("finalizado") ? "Finalizado ✔️" : "Cancelado ❌"));

                txtPedido.setPadding(16, 16, 16, 16);
                txtPedido.setBackgroundColor(Color.parseColor("#FAFAFA"));
                layoutPedidos.addView(txtPedido);
            }
        });
    }

    private void limparHistorico() {
        CollectionReference pedidosRef = db.collection("pedidos");

        // Apagar finalizados
        pedidosRef.whereEqualTo("status", "finalizado").get().addOnSuccessListener(querySnapshot -> {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                pedidosRef.document(doc.getId()).delete();
            }
        });

        // Apagar cancelados
        pedidosRef.whereEqualTo("status", "cancelado").get().addOnSuccessListener(querySnapshot -> {
            for (QueryDocumentSnapshot doc : querySnapshot) {
                pedidosRef.document(doc.getId()).delete();
            }

            layoutPedidos.removeAllViews();
            btnLimpar.setVisibility(View.GONE);
        });
    }
}