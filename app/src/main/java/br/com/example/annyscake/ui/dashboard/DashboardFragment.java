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

                String opcaoSelecionada = opcoes[position];
                // Faça algo com a opção selecionada
            }

            @Override
            public void onNothingSelected(@NonNull AdapterView<?> parent) {
                // Nenhuma opção selecionada
            }
        });

        return binding.getRoot();
    }

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