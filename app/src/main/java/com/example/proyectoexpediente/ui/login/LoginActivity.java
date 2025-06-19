package com.example.proyectoexpediente.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.MainActivity;
import com.example.proyectoexpediente.R;
import com.example.proyectoexpediente.database.UESDatabaseHelper;
import com.example.proyectoexpediente.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;
    UESDatabaseHelper dbHelper;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new UESDatabaseHelper(this);
        preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT id, nombre, tipo FROM usuarios WHERE email = ? AND contrasena = ?", new String[]{email, pass});

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String tipo = cursor.getString(2);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id_usuario", id);
                editor.putString("nombre", nombre);
                editor.putString("tipo", tipo);
                editor.apply();

                Toast.makeText(this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
