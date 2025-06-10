
package br.com.example.annyscake.ui.pedidocliente;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import java.util.HashMap;
import java.util.Map;

import br.com.example.annyscake.databinding.FragmentPedidoClienteBinding;
import br.com.example.annyscake.ui.pedido.Pedido;

public class pedidoCliente extends Fragment {

    private String nome, endereco, telefone;
    private FragmentPedidoClienteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        Map<String, String> precosTamanhos = new HashMap<>();
        precosTamanhos.put("12cm - Rende 13 fatias", "60,00");
        precosTamanhos.put("15cm - Rende 15 a 18 fatias", "80,00");
        precosTamanhos.put("18cm - Rende 20 a 22 fatias", "120,00");
        precosTamanhos.put("20cm - Rende 30 a 35 fatias", "140,00");
        precosTamanhos.put("25cm - Rende 46 a 50 fatias", "190,00");
        precosTamanhos.put("30cm - Rende 68 a 72 fatias", "310,00");
        precosTamanhos.put("35cm - Rende 92 a 98 fatias", "450,00");
        precosTamanhos.put("40cm - Rende 120 a 128 fatias", "550,00");


        binding = FragmentPedidoClienteBinding.inflate(inflater, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String idUsuario = auth.getUid();

        assert idUsuario != null;
        DocumentReference docRef = db.collection("usuarios").document(idUsuario);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                nome = documentSnapshot.getString("nome");
                endereco = documentSnapshot.getString("endereco");
                telefone = documentSnapshot.getString("telefone");
            }
        });

        Button botaoCadastrar = binding.btnCadastrar;

        botaoCadastrar.setOnClickListener(v -> {
            String tema = binding.txtTemaBolo.getText().toString();
            String tamanho = binding.spinnerOpcoes.getSelectedItem().toString();
            String massa = binding.spinnerMassas.getSelectedItem().toString();
            String recheio = binding.spinnerRecheios.getSelectedItem().toString();
            String especial = binding.spinnerRecheiosEspeciais.getSelectedItem().toString();
            String valor = binding.txtValorBolo.getText().toString();
            String data = binding.txtDataEntrega.getText().toString();



            if (tema.isEmpty() || valor.isEmpty()
                    || tamanho.equals("Tamanho do Bolo...")
                    || massa.equals("Tipos de Massas...")
                    || recheio.equals("Tipos de Recheios...")
                    || especial.equals("Recheios Especiais...")) {
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }


            Pedido pedido = new Pedido(nome, telefone, endereco, data, tema, tamanho, massa, recheio, especial, valor, "Pendente", idUsuario);

            db.collection("pedidos")
                    .add(pedido)
                    .addOnSuccessListener(documentReference -> Toast.makeText(getContext(), "Pedido salvo com sucesso!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Erro ao salvar pedido!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        });



        Spinner spinnerTamanhos = binding.spinnerOpcoes;
        String[] opcoes = {"Tamanho do Bolo...","12cm - Rende 13 fatias", "15cm - Rende 15 a 18 fatias",
                "18cm - Rende 20 a 22 fatias", "20cm - Rende 30 a 35 fatias", "25cm - Rende 46 a 50 fatias",
                "30cm - Rende 68 a 72 fatias", "35cm - Rende 92 a 98 fatias", "40cm - Rende 120 a 128 fatias"};


        ArrayAdapter<String> adapter = getStringArrayAdapter(opcoes);
        spinnerTamanhos.setAdapter(adapter);

        spinnerTamanhos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String tamanhoSelecionado = opcoes[position];
                String valor = precosTamanhos.get(tamanhoSelecionado);
                binding.txtValorBolo.setText(valor);

            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {

            }
        });


        Spinner spinnerMassas = binding.spinnerMassas;
        String[] opcoesMassas = {"Tipos de Massas...", "Baunilha", "Chocolate", "Branca"};


        ArrayAdapter<String> adapterMassas = getStringArrayAdapter(opcoesMassas);
        spinnerMassas.setAdapter(adapterMassas);

        spinnerMassas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String massaSelecionada = opcoesMassas[position];

            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {

            }
        });



        Spinner spinnerRecheios = binding.spinnerRecheios;
        String[] opcoesRecheios = {"Tipos de Recheios...", "Beijinho", "Chocolate", "Ninho"};


        ArrayAdapter<String> adapterRecheios = getStringArrayAdapter(opcoesRecheios);
        spinnerRecheios.setAdapter(adapterRecheios);

        spinnerRecheios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String recheioSelecionado = opcoesRecheios[position];

            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {

            }
        });



        Spinner spinnerEspeciais = binding.spinnerRecheiosEspeciais;
        String[] opcoesEspeciais = {"Recheios Especiais...", "Abacaxi", "Ameixa", "Amendoin", "Maracujá", "Surpresa de Uva", "Doce de Leite", "Sonho de Valsa"};


        ArrayAdapter<String> adapterEspeciais = getStringArrayAdapter(opcoesEspeciais);
        spinnerEspeciais.setAdapter(adapterEspeciais);

        spinnerEspeciais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String especialSelecionado = opcoesEspeciais[position];

            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }


    private ArrayAdapter<String> getStringArrayAdapter(String[] opcoes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcoes) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
