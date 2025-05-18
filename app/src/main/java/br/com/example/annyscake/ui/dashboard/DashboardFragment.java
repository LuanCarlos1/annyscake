package br.com.example.annyscake.ui.dashboard;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.com.example.annyscake.R;
import br.com.example.annyscake.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        Button botaoAnterior = binding.btnAnterior;
        // Configura o clique do botão para trocar de fragmento
        botaoAnterior.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_dashBoardFragment_to_homeFragment);
        });

        //Spinner com os tamanhos de bolo
        Spinner spinner = binding.spinnerOpcoes;
        String[] opcoes = {"Tamanho do Bolo...","12cm - Rende 13 fatias", "15cm - Rende 15 a 18 fatias",
                "18cm - Rende 20 a 22 fatias", "20cm - Rende 30 a 35 fatias", "25cm - Rende 46 a 50 fatias",
                "30cm - Rende 68 a 72 fatias", "35cm - Rende 92 a 98 fatias", "40cm - Rende 120 a 128 fatias"};


        ArrayAdapter<String> adapter = getStringArrayAdapter(opcoes);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    // Continuação spinner dos tamanhos de bolo
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

    // Continuação spinner dos tipos de massas
    private ArrayAdapter<String> getStringArrayAdapterMassas(String[] opcoesMassas) {
        ArrayAdapter<String> adapterMassas = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcoesMassas) {
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

        adapterMassas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterMassas;
    }


    // Continuação spinner dos Recheios
    private ArrayAdapter<String> getStringArrayAdapterRecheios(String[] opcoesRecheios) {
        ArrayAdapter<String> adapterRecheios = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcoesRecheios) {
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

        adapterRecheios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterRecheios;
    }

    // Continuação spinner dos Recheios Especiais
    private ArrayAdapter<String> getStringArrayAdapterEspeciais(String[] opcoesEspeciais) {
        ArrayAdapter<String> adapterEspeciais = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcoesEspeciais) {
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

        adapterEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterEspeciais;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}