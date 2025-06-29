package com.example.proyectoexpediente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.database.DataService;
import com.example.proyectoexpediente.ui.materia.VerMateriasActivity;

public class RegistrarMateriaActivity extends AppCompatActivity {

    private EditText etNombreMateria, etCodigoMateria, etUnidadesValorativas;
    private Button btnCrearMateria, btnRegresarMateria;
    private DataService dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_materia);

        etNombreMateria = findViewById(R.id.etNombreMateria);
        etCodigoMateria = findViewById(R.id.etCodigoMateria);
        etUnidadesValorativas = findViewById(R.id.etUnidadesValorativas);
        btnCrearMateria = findViewById(R.id.btnCrearMateria);
        btnRegresarMateria = findViewById(R.id.btnRegresarMateria);

        dbHelper = new DataService(this);

        btnCrearMateria.setOnClickListener(v -> mostrarDialogoConfirmacion());
        btnRegresarMateria.setOnClickListener(v -> finish());
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Está seguro que desea crear esta materia?")
                .setPositiveButton("Sí", (dialog, which) -> registrarMateria())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void registrarMateria() {
        Toast.makeText(this, "Función registrarMateria() llamada", Toast.LENGTH_SHORT).show();
        String nombre = etNombreMateria.getText().toString().trim();
        String codigo = etCodigoMateria.getText().toString().trim();
        String uvStr = etUnidadesValorativas.getText().toString().trim();

        if (nombre.isEmpty() || codigo.isEmpty() || uvStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int uv;
        try {
            uv = Integer.parseInt(uvStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "UV debe ser numérico", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean insertado = dbHelper.insertarMateria(nombre, codigo, uv);

        if (insertado) {
            Toast.makeText(this, "Materia registrada", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, VerMateriasActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Error: código ya existe", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Llegó al final de registrarMateria (temporal)", Toast.LENGTH_LONG).show();
    }
}
