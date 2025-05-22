package br.com.example.annyscake.ui.listapedidos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;

public class ListaPedidosFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listapedidos, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
        String pedidosJson = prefs.getString("lista_pedidos", "[]");

        JSONArray pedidosArray;
        try {
            pedidosArray = new JSONArray(pedidosJson);
        } catch (JSONException e) {
            pedidosArray = new JSONArray();
        }

        LinearLayout layoutPedidos = root.findViewById(R.id.layoutPedidos);
        layoutPedidos.removeAllViews(); // Limpa antes de preencher

        for (int i = 0; i < pedidosArray.length(); i++) {
            try {
                JSONObject obj = pedidosArray.getJSONObject(i);
                Pedido p = Pedido.fromJSON(obj);

                // Cria um container vertical para cada pedido
                LinearLayout pedidoLayout = new LinearLayout(requireContext());
                pedidoLayout.setOrientation(LinearLayout.VERTICAL);
                pedidoLayout.setPadding(16, 16, 16, 16);
                pedidoLayout.setBackgroundColor(Color.parseColor("#FDE4EC"));

                // Cria um TextView com todas as informações
                TextView txt = new TextView(requireContext());
                txt.setText("📦 Pedido " + (i + 1) + ":\n"
                        + "Cliente: " + p.nome + "\n"
                        + "Telefone: " + p.telefone + "\n"
                        + "Endereço: " + p.endereco + "\n"
                        + "Data de Entrega: " + p.data + "\n"
                        + "Tema: " + p.tema + "\n"
                        + "Tamanho: " + p.tamanho + "\n"
                        + "Massa: " + p.massa + "\n"
                        + "Recheio: " + p.recheio + "\n"
                        + "Recheio Especial: " + p.recheio_especial + "\n"
                        + "Valor: R$ " + p.valor);
                pedidoLayout.addView(txt);

                // Botão Finalizar
                Button btnFinalizar = new Button(requireContext());
                btnFinalizar.setText("Finalizar");
                pedidoLayout.addView(btnFinalizar);

                // Botão Cancelar
                Button btnCancelar = new Button(requireContext());
                btnCancelar.setText("Cancelar");
                pedidoLayout.addView(btnCancelar);

                // Lógica de clique
                int finalI = i;
                JSONArray finalPedidosArray = pedidosArray;
                btnFinalizar.setOnClickListener(v -> {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Confirmar Finalização")
                            .setMessage("Tem certeza que deseja finalizar este pedido?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                moverPedidoParaStatus(finalPedidosArray, finalI, "finalizados");
                                layoutPedidos.removeAllViews(); // Limpa antes de preencher
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();

                });

                JSONArray finalPedidosArray1 = pedidosArray;
                btnCancelar.setOnClickListener(v -> {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Cancelar pedido?")
                            .setMessage("Tem certeza que deseja cancelar este pedido?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                moverPedidoParaStatus(finalPedidosArray1, finalI, "cancelados");
                                layoutPedidos.removeAllViews(); // Limpa antes de preencher
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                });

                layoutPedidos.addView(pedidoLayout);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    private void moverPedidoParaStatus(JSONArray arrayOriginal, int index, String status) {
        try {
            JSONObject pedido = arrayOriginal.getJSONObject(index);
            pedido.put("status", status);

            SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
            String jsonDestino = prefs.getString("pedidos_" + status, "[]");
            JSONArray arrayDestino = new JSONArray(jsonDestino);

            arrayDestino.put(pedido);
            prefs.edit().putString("pedidos_" + status, arrayDestino.toString()).apply();

            arrayOriginal.remove(index);
            prefs.edit().putString("lista_pedidos", arrayOriginal.toString()).apply();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
