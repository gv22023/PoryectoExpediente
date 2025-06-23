package com.example.proyectoexpediente.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectoexpediente.R;
import com.example.proyectoexpediente.VerMateriasActivity;

public class MenuPrincipalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_menu_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnVerExpediente = view.findViewById(R.id.btnVerExpediente);
        Button btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        Button btnInscribirMaterias = view.findViewById(R.id.btnInscribirMaterias);

        // Mostrar botones según el tipo de usuario
        SharedPreferences prefs = requireContext().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        String tipoUsuario = prefs.getString("tipo_usuario", "usuario");

        if ("admin".equals(tipoUsuario)) {
            btnInscribirMaterias.setVisibility(View.VISIBLE);
            btnVerExpediente.setVisibility(View.GONE);
        } else {
            btnInscribirMaterias.setVisibility(View.GONE);
            btnVerExpediente.setVisibility(View.VISIBLE);
        }


        btnVerExpediente.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_ver_expediente);
        });

        btnEditarPerfil.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_editar_perfil);
        });

        btnInscribirMaterias.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VerMateriasActivity.class);
            startActivity(intent);
        });
    }
}
