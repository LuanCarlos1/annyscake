package br.com.example.annyscake.ui.listapedidos;

import android.annotation.SuppressLint;
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

import java.util.HashMap;
import java.util.Map;

import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;

public class ListaPedidosFragment extends Fragment {

    private FirebaseFirestore db;
    private LinearLayout layoutPedidos;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listapedidos, container, false);
        layoutPedidos = root.findViewById(R.id.layoutPedidos);

        db = FirebaseFirestore.getInstance();
        carregarPedidos();

        return root;
    }

    private void carregarPedidos() {
        layoutPedidos.removeAllViews();

        CollectionReference pedidosRef = db.collection("pedidos");
        pedidosRef.whereEqualTo("status", "pendente").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Pedido p = doc.toObject(Pedido.class);
                    String docId = doc.getId();

                    LinearLayout pedidoLayout = new LinearLayout(requireContext());
                    pedidoLayout.setOrientation(LinearLayout.VERTICAL);
                    pedidoLayout.setPadding(16, 16, 16, 16);


                    TextView txt = new TextView(requireContext());
                    txt.setText("📦 Pedido:\n"
                            + "Cliente: " + p.getNome() + "\n"
                            + "Telefone: " + p.getTelefone() + "\n"
                            + "Endereço: " + p.getEndereco() + "\n"
                            + "Data de Entrega: " + p.getDataEntrega() + "\n"
                            + "Tema: " + p.getTema() + "\n"
                            + "Tamanho: " + p.getTamanho() + "\n"
                            + "Massa: " + p.getMassa() + "\n"
                            + "Recheio: " + p.getRecheio() + "\n"
                            + "Recheio Especial: " + p.getRecheioEspecial() + "\n"
                            + "Valor: R$ " + p.getValor()
                            + "Status: " + p.getStatus());
                    pedidoLayout.addView(txt);


                    Button btnFinalizar = new Button(requireContext());
                    btnFinalizar.setText("Finalizar");
                    pedidoLayout.addView(btnFinalizar);

                    Button btnCancelar = new Button(requireContext());
                    btnCancelar.setText("Cancelar");
                    pedidoLayout.addView(btnCancelar);

                    btnFinalizar.setOnClickListener(v -> confirmarMudancaStatus(docId, "finalizado"));
                    btnCancelar.setOnClickListener(v -> confirmarMudancaStatus(docId, "cancelado"));

                    layoutPedidos.addView(pedidoLayout);
                }
            }
        });
    }

    private void confirmarMudancaStatus(String docId, String novoStatus) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar")
                .setMessage("Tem certeza que deseja marcar como '" + novoStatus + "' este pedido?")
                .setPositiveButton("Sim", (dialog, which) -> atualizarStatus(docId, novoStatus))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void atualizarStatus(String docId, String status) {
        Map<String, Object> atualizacao = new HashMap<>();
        atualizacao.put("status", status);

        db.collection("pedidos").document(docId).update(atualizacao)
                .addOnSuccessListener(aVoid -> carregarPedidos());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}