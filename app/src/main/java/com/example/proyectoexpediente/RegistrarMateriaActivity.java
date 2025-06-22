package com.example.proyectoexpediente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.database.UESDatabaseHelper;

public class RegistrarMateriaActivity extends AppCompatActivity {

    private EditText etNombreMateria;
    private EditText etCodigoMateria;
    private EditText etUV;
    private EditText etClaveAdmin;
    private Button btnCrear;
    private Button btnRegresar;

    private UESDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_materia);

        etNombreMateria = findViewById(R.id.etNombreMateria);
        etCodigoMateria = findViewById(R.id.etCodigoMateria);
        etUV = findViewById(R.id.etUnidadesValorativas);
        etClaveAdmin = findViewById(R.id.etPasswordAdmin);
        btnCrear = findViewById(R.id.btnCrearMateria);
        btnRegresar = findViewById(R.id.btnRegresarMateria);

        dbHelper = new UESDatabaseHelper(this);

        btnCrear.setOnClickListener(v -> crearMateria());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void crearMateria() {
        String nombre = etNombreMateria.getText().toString().trim();
        String codigo = etCodigoMateria.getText().toString().trim();
        String uvStr = etUV.getText().toString().trim();
        String clave = etClaveAdmin.getText().toString().trim();

        if (nombre.isEmpty() || codigo.isEmpty() || uvStr.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!clave.equals("admin123")) {
            Toast.makeText(this, "Contraseña de administrador incorrecta", Toast.LENGTH_SHORT).show();
            return;
        }

        int uv;
        try {
            uv = Integer.parseInt(uvStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "UV debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean resultado = dbHelper.insertarMateria(nombre, codigo, uv);
        if (resultado) {
            Toast.makeText(this, "Materia guardada correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar materia. Verifica si ya existe el código.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombreMateria.setText("");
        etCodigoMateria.setText("");
        etUV.setText("");
        etClaveAdmin.setText("");
    }
}