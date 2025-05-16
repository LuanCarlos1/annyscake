package br.com.example.annyscake.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.example.annyscake.R;
import br.com.example.annyscake.databinding.FragmentHomeBinding;
import br.com.example.annyscake.ui.pedido.Pedido;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button botaoCadastrar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        botaoCadastrar = root.findViewById(R.id.btnCadastrar);
        EditText editNome = root.findViewById(R.id.txtNomeCliente);
        EditText editTema = root.findViewById(R.id.txtTemaBolo);
        EditText editTamanho = root.findViewById(R.id.txtTamanhoBolo);
        EditText editRecheio = root.findViewById(R.id.txtRecheioBolo);
        EditText editValor = root.findViewById(R.id.txtValorBolo);
        EditText editData = root.findViewById(R.id.txtDataEntrega);



        botaoCadastrar.setOnClickListener(v -> {
            Pedido pedido = new Pedido(
                    editNome.getText().toString(),
                    editTema.getText().toString(),
                    editTamanho.getText().toString(),
                    editRecheio.getText().toString(),
                    editValor.getText().toString(),
                    editData.getText().toString()
            );

            SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            String pedidosStr = prefs.getString("lista_pedidos", "[]");
            try {
                JSONArray listaPedidos = new JSONArray(pedidosStr);
                listaPedidos.put(pedido.toJSON());
                editor.putString("lista_pedidos", listaPedidos.toString());
                editor.apply();
                Toast.makeText(getContext(), "Pedido cadastrado!", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Limpar os campos
            editNome.setText("");
            editTema.setText("");
            editTamanho.setText("");
            editRecheio.setText("");
            editValor.setText("");
            editData.setText("");
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}