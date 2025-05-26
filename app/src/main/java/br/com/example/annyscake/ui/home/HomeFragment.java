package br.com.example.annyscake.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import br.com.example.annyscake.R;
import br.com.example.annyscake.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Acessa os campos com o binding
        Button botaoProximaPag = binding.btnProx;

        // Navega para o DashboardFragment, passando o bundle
        botaoProximaPag.setOnClickListener(v -> {

            EditText editNome = binding.txtNomeCliente;
            EditText editTelefone = binding.txtTelefone;
            EditText editEndereco = binding.txtEndereco;
            EditText editDataEntrega = binding.txtDataEntrega;

            Bundle bundle = new Bundle();
            bundle.putString("nome", editNome.getText().toString());
            bundle.putString("telefone", editTelefone.getText().toString());
            bundle.putString("endereco", editEndereco.getText().toString());
            bundle.putString("dataEntrega", editDataEntrega.getText().toString());

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_homeFragment_to_dashboardFragment, bundle);

        });


        return binding.getRoot(); // retorna o layout corretamente associado ao binding
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
