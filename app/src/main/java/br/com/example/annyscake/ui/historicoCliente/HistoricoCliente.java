package br.com.example.annyscake.ui.historicoCliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.example.annyscake.R;


public class HistoricoCliente extends Fragment {


    public HistoricoCliente() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historico_cliente, container, false);
    }
}