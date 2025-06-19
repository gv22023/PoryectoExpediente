package com.example.proyectoexpediente.ui.expediente;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyectoexpediente.R;

public class VerExpedienteFragment extends Fragment {

    public VerExpedienteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ver_expediente, container, false);
    }
}
