package com.example.proyectoexpediente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.database.UESDatabaseHelper;
import com.example.proyectoexpediente.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private UESDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new UESDatabaseHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.validarUsuario(email, password)) {

                String tipoUsuario = dbHelper.obtenerTipoUsuario(email);

                // Guardar datos en SharedPreferences
                SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("correo", email);
                editor.putString("tipo_usuario", tipoUsuario);
                editor.apply();

                Toast.makeText(this, "Bienvenido " + tipoUsuario, Toast.LENGTH_SHORT).show();

                // Redirigir según tipo de usuario
                Intent intent = new Intent(this, MainActivity.class);

                if (email.equals("admin@ues.edu.sv")) {
                    // Enviar extra para que MainActivity abra directamente el fragmento de ingresar materias
                    intent.putExtra("ir_a", "inscribir_materias");
                }

                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
