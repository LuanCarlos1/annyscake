package br.com.example.annyscake.ui.listapedidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.example.annyscake.R;
import br.com.example.annyscake.ui.pedido.Pedido;

public class ListaPedidosFragment extends Fragment {

    private TextView textPedidos;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_listapedidos, container, false);

        Button btnMostrarPedidos = root.findViewById(R.id.btnMostrarPedidos);
        textPedidos = root.findViewById(R.id.textPedidos);

        btnMostrarPedidos.setOnClickListener(v -> mostrarPedidos());

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

                sb.append("📦 Pedido ").append(i + 1).append(":\n")
                        .append("Cliente: ").append(pedido.nome).append("\n")
                        .append("Tema: ").append(pedido.tema).append("\n")
                        .append("Tamanho: ").append(pedido.tamanho).append("\n")
                        .append("Recheio: ").append(pedido.recheio).append("\n")
                        .append("Valor: R$").append(pedido.valor).append("\n")
                        .append("Entrega: ").append(pedido.data).append("\n\n");
            }

            textPedidos.setText(sb.toString());

        } catch (JSONException e) {
            textPedidos.setText("Erro ao carregar pedidos.");
            Toast.makeText(getContext(), "Erro ao carregar pedidos!", Toast.LENGTH_SHORT).show();
        }
    }
}