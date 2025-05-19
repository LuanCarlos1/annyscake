package br.com.example.annyscake.ui.listapedidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;

public class ListaPedidosFragment extends Fragment {

    private TextView textPedidos;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listapedidos, container, false);
        LinearLayout layoutPedidos = root.findViewById(R.id.layoutPedidos);

        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
        String pedidosJson = prefs.getString("lista_pedidos", "[]");

        try {
            JSONArray array = new JSONArray(pedidosJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject pedidoObj = array.getJSONObject(i);
                Pedido pedido = Pedido.fromJSON(pedidoObj);

                // Cria um TextView com os detalhes do pedido
                TextView txt = new TextView(getContext());
                txt.setText(pedido.nome + " - " + pedido.tema + " - " + pedido.valor);

                // Cria o botão FINALIZAR
                Button btnFinalizar = new Button(getContext());
                btnFinalizar.setText("Finalizar");

                // Cria o botão CANCELAR
                Button btnCancelar = new Button(getContext());
                btnCancelar.setText("Cancelar");

                // Container horizontal para o texto e botões
                LinearLayout containerItem = new LinearLayout(getContext());
                containerItem.setOrientation(LinearLayout.VERTICAL);
                containerItem.setPadding(16, 16, 16, 16);

                containerItem.addView(txt);
                containerItem.addView(btnFinalizar);
                containerItem.addView(btnCancelar);

                layoutPedidos.addView(containerItem);

                // Ação ao clicar em FINALIZAR
                btnFinalizar.setOnClickListener(v -> {
                    try {
                        pedidoObj.put("status", "finalizado");
                        moverPedido(pedidoObj, "lista_pedidos", "lista_pedidos_finalizados");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                // Ação ao clicar em CANCELAR
                btnCancelar.setOnClickListener(v -> {
                    try {
                        pedidoObj.put("status", "cancelado");
                        moverPedido(pedidoObj, "lista_pedidos", "lista_pedidos_cancelados");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @SuppressLint("SetTextI18n")
    private void mostrarPedidos() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
        String pedidosStr = prefs.getString("lista_pedidos", "[]");

        try {
            JSONArray array = new JSONArray(pedidosStr);
            if (array.length() == 0) {
                textPedidos.setText("Nenhum pedido encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Pedido pedido = Pedido.fromJSON(obj);

                NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String valorFormatado = formatador.format(Double.parseDouble(pedido.valor));

                sb.append("📦 Pedido ").append(i + 1).append(":\n")
                        .append("Cliente: ").append(pedido.nome).append("\n")
                        .append("Telefone: ").append(pedido.telefone).append("\n")
                        .append("Endereço: ").append(pedido.endereco).append("\n")
                        .append("Tema: ").append(pedido.tema).append("\n")
                        .append("Tamanho: ").append(pedido.tamanho).append("\n")
                        .append("Massa: ").append(pedido.massa).append("\n")
                        .append("Recheio: ").append(pedido.recheio).append("\n")
                        .append("Recheio Especial: ").append(pedido.recheio_especial).append("\n")
                        .append("Valor: ").append(valorFormatado).append("\n")
                        .append("Entrega: ").append(pedido.data).append("\n\n");
            }

            textPedidos.setText(sb.toString());

        } catch (JSONException e) {
            textPedidos.setText("Erro ao carregar pedidos.");
            Toast.makeText(getContext(), "Erro ao carregar pedidos!", Toast.LENGTH_SHORT).show();
        }
    }

    private void moverPedido(JSONObject pedido, String deLista, String paraLista) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);

        try {
            JSONArray origem = new JSONArray(prefs.getString(deLista, "[]"));
            JSONArray destino = new JSONArray(prefs.getString(paraLista, "[]"));

            // Remove o pedido da lista de origem
            JSONArray novaOrigem = new JSONArray();
            for (int i = 0; i < origem.length(); i++) {
                JSONObject obj = origem.getJSONObject(i);
                if (!obj.toString().equals(pedido.toString())) {
                    novaOrigem.put(obj);
                }
            }

            // Adiciona na lista de destino
            destino.put(pedido);

            // Salva
            prefs.edit()
                    .putString(deLista, novaOrigem.toString())
                    .putString(paraLista, destino.toString())
                    .apply();

            Toast.makeText(getContext(), "Pedido movido com sucesso!", Toast.LENGTH_SHORT).show();

            // Atualiza a tela
            requireActivity().recreate();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}