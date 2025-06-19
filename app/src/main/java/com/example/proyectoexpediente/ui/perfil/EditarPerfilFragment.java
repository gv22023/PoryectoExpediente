package com.example.proyectoexpediente.ui.perfil;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyectoexpediente.R;

public class EditarPerfilFragment extends Fragment {

    public EditarPerfilFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }
}