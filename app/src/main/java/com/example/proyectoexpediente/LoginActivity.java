package com.example.proyectoexpediente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.proyectoexpediente.database.DataService;
import com.example.proyectoexpediente.ui.materia.VerMateriasActivity;
import com.example.proyectoexpediente.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private DataService dbHelper;
    private Switch selectModo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Verificar si ya hay sesión activa
        SharedPreferences session = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        if (session.contains("correo")) {
            // Ya hay sesión, ir directo a MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra LoginActivity
            return;
        }

        // Aplicar tema antes de cargar la UI
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean nightMode = preferences.getBoolean("night_mode", false);
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        dbHelper = new DataService(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        selectModo = findViewById(R.id.selectModo);

        // Establecer estado inicial del switch
        selectModo.setChecked(nightMode);

        // Escuchar cambios en el switch
        selectModo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night_mode", true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night_mode", false);
            }
            editor.apply();
            recreate(); // Reiniciar la actividad para aplicar el nuevo tema
        });

        // Lógica del botón Login
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

                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

                // Redirigir según tipo de usuario
                Intent intent = new Intent(this, MainActivity.class);

                if (email.equals("admin@ues.edu.sv")) {
                    // Enviar extra para que MainActivity abra directamente el fragmento de ingresar materias
                    intent.putExtra("ir_a", "fragment_menu_principal");
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
