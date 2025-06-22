package com.example.proyectoexpediente;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectoexpediente.MateriaAdapter;
import com.example.proyectoexpediente.database.UESDatabaseHelper;

import java.util.List;

public class VerMateriasActivity extends AppCompatActivity {

    private RecyclerView rvMaterias;
    private MateriaAdapter materiaAdapter;
    private UESDatabaseHelper dbHelper;
    private Button btnRegresar, btnCrearOtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ver_materias);

        rvMaterias = findViewById(R.id.rvMaterias);
        btnRegresar = findViewById(R.id.btnRegresar2);
        btnCrearOtra = findViewById(R.id.btnCrearOtraMateria);

        rvMaterias.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new UESDatabaseHelper(this);
        List<Materia> listaMaterias = dbHelper.obtenerMaterias();
        materiaAdapter = new MateriaAdapter(listaMaterias);
        rvMaterias.setAdapter(materiaAdapter);

        btnRegresar.setOnClickListener(v -> finish());

        btnCrearOtra.setOnClickListener(v -> {
            Intent intent = new Intent(VerMateriasActivity.this, RegistrarMateriaActivity.class);
            startActivity(intent);
        });
    }
}
