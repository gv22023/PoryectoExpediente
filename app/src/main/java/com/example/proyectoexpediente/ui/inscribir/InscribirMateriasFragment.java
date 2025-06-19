package com.example.proyectoexpediente.ui.inscribir;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyectoexpediente.R;

public class InscribirMateriasFragment extends Fragment {

    public InscribirMateriasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inscribir_materias, container, false);
    }
}
