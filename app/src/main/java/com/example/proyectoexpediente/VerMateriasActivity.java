package com.example.proyectoexpediente;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoexpediente.database.UESDatabaseHelper;

import java.util.List;

public class VerMateriasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MateriaAdapter adapter;
    private UESDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_materias);

        recyclerView = findViewById(R.id.recyclerMaterias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new UESDatabaseHelper(this);
        List<Materia> lista = dbHelper.obtenerMaterias();

        adapter = new MateriaAdapter(lista);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnIrRegistrarMateria).setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrarMateriaActivity.class));
        });

        findViewById(R.id.btnRegresar).setOnClickListener(v -> {
            finish();
        });
    }
}