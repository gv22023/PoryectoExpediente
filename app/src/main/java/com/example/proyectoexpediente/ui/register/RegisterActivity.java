package com.example.proyectoexpediente.ui.register;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.R;
import com.example.proyectoexpediente.database.UESDatabaseHelper;
import com.example.proyectoexpediente.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombre, etEmail, etPassword;
    Button btnCrearCuenta;
    UESDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        dbHelper = new UESDatabaseHelper(this);

        btnCrearCuenta.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            if (usuarioExiste(db, email)) {
                Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
            } else {
                db.execSQL("INSERT INTO usuarios (nombre, email, contrasena, tipo) VALUES (?, ?, ?, ?)",
                        new Object[]{nombre, email, pass, "estandar"});
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean usuarioExiste(SQLiteDatabase db, String email) {
        return db.rawQuery("SELECT id FROM usuarios WHERE email = ?", new String[]{email}).moveToFirst();
    }
}
