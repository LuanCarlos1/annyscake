package br.com.example.annyscake.ui.dashboard;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import br.com.example.annyscake.R;
import br.com.example.annyscake.databinding.FragmentDashboardBinding;
import br.com.example.annyscake.ui.pedido.Pedido;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Bundle bundle = getArguments();
        String nome = bundle != null ? bundle.getString("nome") : "";
        String telefone = bundle != null ? bundle.getString("telefone") : "";
        String endereco = bundle != null ? bundle.getString("endereco") : "";
        String dataEntrega = bundle != null ? bundle.getString("dataEntrega") : "";

        Button botaoCadastrar = binding.btnCadastrar;

        botaoCadastrar.setOnClickListener(v -> {
            String tema = binding.txtTemaBolo.getText().toString();
            String tamanho = binding.spinnerOpcoes.getSelectedItem().toString();
            String massa = binding.spinnerMassas.getSelectedItem().toString();
            String recheio = binding.spinnerRecheios.getSelectedItem().toString();
            String especial = binding.spinnerRecheiosEspeciais.getSelectedItem().toString();
            String valor = binding.txtValorBolo.getText().toString();

            if (tema.isEmpty() || valor.isEmpty()
                    || tamanho.equals("Tamanho do Bolo...")
                    || massa.equals("Tipos de Massas...")
                    || recheio.equals("Tipos de Recheios...")
                    || especial.equals("Recheios Especiais...")) {
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar o objeto do pedido
            Pedido pedido = new Pedido(nome, telefone, endereco, dataEntrega, tema, tamanho, massa, recheio, especial, valor, "pendente");

            db.collection("pedidos")
                    .add(pedido)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Pedido salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Erro ao salvar pedido!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        });



        Button botaoAnterior = binding.btnAnterior;
        // Configura o clique do botão para trocar de fragmento
        botaoAnterior.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_dashBoardFragment_to_homeFragment);
        });

        //Spinner com os tamanhos de bolo
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
                // Faça algo com a opção selecionada
            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {
                // Nenhuma opção selecionada
            }
        });

        //Spinner com os tipos de massas
        Spinner spinnerMassas = binding.spinnerMassas;
        String[] opcoesMassas = {"Tipos de Massas...", "Baunilha", "Chocolate", "Branca"};


        ArrayAdapter<String> adapterMassas = getStringArrayAdapter(opcoesMassas);
        spinnerMassas.setAdapter(adapterMassas);

        spinnerMassas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String massaSelecionada = opcoesMassas[position];
                // Faça algo com a opção selecionada
            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {
                // Nenhuma opção selecionada
            }
        });


        //Spinner com os Recheios
        Spinner spinnerRecheios = binding.spinnerRecheios;
        String[] opcoesRecheios = {"Tipos de Recheios...", "Beijinho", "Chocolate", "Ninho"};


        ArrayAdapter<String> adapterRecheios = getStringArrayAdapter(opcoesRecheios);
        spinnerRecheios.setAdapter(adapterRecheios);

        spinnerRecheios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String recheioSelecionado = opcoesRecheios[position];
                // Faça algo com a opção selecionada
            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {
                // Nenhuma opção selecionada
            }
        });


        //Spinner com os Recheios Especiais
        Spinner spinnerEspeciais = binding.spinnerRecheiosEspeciais;
        String[] opcoesEspeciais = {"Recheios Especiais...", "Abacaxi", "Ameixa", "Amendoin", "Maracujá", "Surpresa de Uva", "Doce de Leite", "Sonho de Valsa"};


        ArrayAdapter<String> adapterEspeciais = getStringArrayAdapter(opcoesEspeciais);
        spinnerEspeciais.setAdapter(adapterEspeciais);

        spinnerEspeciais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                if (position == 0) return;

                String especialSelecionado = opcoesEspeciais[position];
                // Faça algo com a opção selecionada
            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {
                // Nenhuma opção selecionada
            }
        });

        return binding.getRoot();
    }

    // Spinner com primeira opção inativa e cinza
    private ArrayAdapter<String> getStringArrayAdapter(String[] opcoes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcoes) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desativa a posição 0
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // cinza para "Selecione..."
                } else {
                    tv.setTextColor(Color.BLACK); // preto para opções válidas
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