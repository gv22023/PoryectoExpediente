package com.example.proyectoexpediente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyectoexpediente.R;

public class MateriasInscritasFragment extends Fragment {

    public MateriasInscritasFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materias_inscritas, container, false);
    }
}
