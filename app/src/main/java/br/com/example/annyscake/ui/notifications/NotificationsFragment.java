package br.com.example.annyscake.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.example.annyscake.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    TextView textPedidoSalvo;
    Button botaoMostrar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        botaoMostrar = binding.btnMostrar;
        textPedidoSalvo = binding.txtMostrar;


        botaoMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPedido();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void mostrarPedido() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);

        String nome = prefs.getString("nome", "Sem nome");
        String tema = prefs.getString("tema", "Sem tema");
        String tamanho = prefs.getString("tamanho", "Sem tamanho");
        String recheio = prefs.getString("recheio", "Sem recheio");
        String valor = prefs.getString("valor", "Sem valor");
        String data = prefs.getString("data", "Sem data");

        String pedido = "📦 Pedido Salvo:\n\n"
                + "Cliente: " + nome + "\n"
                + "Tema: " + tema + "\n"
                + "Tamanho: " + tamanho + "cm" + "\n"
                + "Recheio: " + recheio + "\n"
                + "Valor: R$" + valor + "\n"
                + "Entrega: " + data;

        textPedidoSalvo.setText(pedido);
    }
}