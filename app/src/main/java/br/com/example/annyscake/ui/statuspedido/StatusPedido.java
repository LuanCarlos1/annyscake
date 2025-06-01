package br.com.example.annyscake.ui.statuspedido;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.example.annyscake.R;

public class StatusPedido extends Fragment {

    public StatusPedido() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status_pedido, container, false);
    }
}