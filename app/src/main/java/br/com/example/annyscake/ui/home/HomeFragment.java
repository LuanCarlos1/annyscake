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


import br.com.example.annyscake.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button botaoCadastrar;
    EditText editNome, editTema, editTamanho, editRecheio, editValor, editData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        editNome = binding.txtNomeCliente;
        editTema = binding.txtTemaBolo;
        editTamanho = binding.txtTamanhoBolo;
        editRecheio = binding.txtRecheioBolo;
        editValor = binding.txtValorBolo;
        editData = binding.txtDataEntrega;
        botaoCadastrar = binding.btnCadastrar;

        //BOTÃO PARA CADASTRAR!
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPedido();
                Toast.makeText(getContext(), "Pedido salvo com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void salvarPedido() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("Pedidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("nome", editNome.getText().toString());
        editor.putString("tema", editTema.getText().toString());
        editor.putString("tamanho", editTamanho.getText().toString());
        editor.putString("recheio", editRecheio.getText().toString());
        editor.putString("valor", editValor.getText().toString());
        editor.putString("data", editData.getText().toString());

        editor.apply();
    }

}