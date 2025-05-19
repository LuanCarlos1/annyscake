package br.com.example.annyscake.ui.listapedidos;

import android.annotation.SuppressLint;
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
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

//                // Lógica de clique
//                int finalI = i;
//                btnFinalizar.setOnClickListener(v -> {
//                    moverPedidoParaStatus(pedidosArray, finalI, "finalizado");
//                });
//
//                btnCancelar.setOnClickListener(v -> {
//                    moverPedidoParaStatus(pedidosArray, finalI, "cancelado");
//                });

                layoutPedidos.addView(pedidoLayout);

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

//    private void moverPedidoParaStatus(JSONArray arrayOriginal, int index, String status) {
//        try {
//            JSONObject pedido = arrayOriginal.getJSONObject(index);
//            pedido.put("status", status);
//
//            // Salva em nova lista de status
//            SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
//            String jsonDestino = prefs.getString("pedidos_" + status, "[]");
//            JSONArray arrayDestino = new JSONArray(jsonDestino);
//
//            arrayDestino.put(pedido);
//            prefs.edit().putString("pedidos_" + status, arrayDestino.toString()).apply();
//
//            // Remove da lista original
//            arrayOriginal.remove(index);
//            prefs.edit().putString("lista_pedidos", arrayOriginal.toString()).apply();
//
//            // Recarrega o fragmento
//            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
//            navController.navigate(R.id.listaPedidosFragment); // navega para o próprio fragmento
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}