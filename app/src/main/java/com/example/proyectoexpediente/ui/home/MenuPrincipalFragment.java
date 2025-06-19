package com.example.proyectoexpediente.ui.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyectoexpediente.R;

public class MenuPrincipalFragment extends Fragment {

    public MenuPrincipalFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_principal, container, false);
    }
}