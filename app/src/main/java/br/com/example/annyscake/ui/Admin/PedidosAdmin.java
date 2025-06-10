package br.com.example.annyscake.ui.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;


public class PedidosAdmin extends Fragment {

    public PedidosAdmin() {

    }

    private FirebaseFirestore db;
    private LinearLayout layoutPedidos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        pedidosRef
                .whereIn("status", Arrays.asList("Pendente", "Aceito")) // ✅ Apenas status, sem filtrar por usuário
                .get()
                .addOnCompleteListener(task -> {
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
                                    + "Valor: R$ " + p.getValor() + ",00" + "\n"
                                    + "Status: " + p.getStatus().toUpperCase());
                            pedidoLayout.addView(txt);

                            String status = doc.getString("status");

                            Button btnFinalizar = new Button(requireContext());
                            Button btnCancelar = new Button(requireContext());

                            switch (status.toLowerCase()) {
                                case "aceito":
                                    btnFinalizar.setText("Finalizar");
                                    btnCancelar.setText("Cancelar");
                                    btnFinalizar.setOnClickListener(v -> confirmarMudancaStatus(docId, "Finalizado"));
                                    btnCancelar.setOnClickListener(v -> confirmarMudancaStatus(docId, "Cancelado"));

                                    pedidoLayout.addView(btnFinalizar);
                                    pedidoLayout.addView(btnCancelar);
                                    break;

                                case "recusado":
                                case "finalizado":
                                case "cancelado":
                                    break;

                                default:
                                    btnFinalizar.setText("Aceitar");
                                    btnCancelar.setText("Recusar");
                                    btnFinalizar.setOnClickListener(v -> confirmarMudancaStatus(docId, "Aceito"));
                                    btnCancelar.setOnClickListener(v -> confirmarMudancaStatus(docId, "Recusado"));

                                    pedidoLayout.addView(btnFinalizar);
                                    pedidoLayout.addView(btnCancelar);
                                    break;
                            }

                            layoutPedidos.addView(pedidoLayout);
                        }
                    }
                });
    }


    private void moverParaHistorico(String docId, Pedido pedido) {

        db.collection("historico_admin")
                .add(pedido);

        db.collection("historico_clientes")
                .document(pedido.getUsuarioId())
                .collection("pedidos")
                .add(pedido);

        db.collection("pedidos").document(docId).delete();
    }


    private void confirmarMudancaStatus(String docId, String novoStatus) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar")
                .setMessage("Tem certeza que deseja marcar como '" + novoStatus + "' este pedido?")
                .setPositiveButton("Sim", (dialog, which) -> atualizarStatus(docId, novoStatus))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void atualizarStatus(String docId, String novoStatus) {
        db.collection("pedidos").document(docId).get().addOnSuccessListener(doc -> {
            Pedido pedido = doc.toObject(Pedido.class);
            if (pedido != null) {
                pedido.setStatus(novoStatus);

                db.collection("pedidos").document(docId).update("status", novoStatus)
                        .addOnSuccessListener(aVoid -> {

                            if (novoStatus.equalsIgnoreCase("Finalizado") || novoStatus.equalsIgnoreCase("Cancelado") || novoStatus.equalsIgnoreCase("Recusado")) {
                                moverParaHistorico(docId, pedido);
                            }
                            carregarPedidos();
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}