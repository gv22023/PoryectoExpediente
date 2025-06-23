package com.example.proyectoexpediente.ui.register;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.R;
import com.example.proyectoexpediente.database.DataService;
import com.example.proyectoexpediente.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombre, etEmail, etPassword;
    Button btnCrearCuenta, btnRegresar;
    DataService dbHelper;
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnRegresar = findViewById(R.id.btnRegresar);

        dbHelper = new DataService(this);

        btnCrearCuenta.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String confirmPass = etConfirmPassword.getText().toString().trim();

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean registrado = dbHelper.registrarUsuario(nombre, email, pass, "estandar");

            if (registrado) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "No se pudo registrar, revise la información", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private boolean usuarioExiste(SQLiteDatabase db, String email) {
        return db.rawQuery("SELECT id FROM usuarios WHERE email = ?", new String[]{email}).moveToFirst();
    }
}
