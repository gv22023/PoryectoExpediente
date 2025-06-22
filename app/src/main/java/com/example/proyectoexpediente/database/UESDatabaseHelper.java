package com.example.proyectoexpediente.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UESDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UESExpediente.db";
    public static final int DATABASE_VERSION = 2; // Aumentado para forzar recreación

    public UESDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "correo TEXT UNIQUE, " +
                "contrasena TEXT, " +
                "tipo_usuario TEXT)");

        db.execSQL("CREATE TABLE materias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "descripcion TEXT)");

        db.execSQL("CREATE TABLE inscripciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "materia_id INTEGER)");

        db.execSQL("CREATE TABLE notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "materia_id INTEGER, " +
                "nota REAL)");

        // Insertar usuario admin por defecto
        db.execSQL("INSERT INTO usuarios (nombre, correo, contrasena, tipo_usuario) " +
                "VALUES ('Admin', 'admin@ues.edu.sv', 'admin123', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS materias");
        db.execSQL("DROP TABLE IF EXISTS inscripciones");
        db.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(db);
    }

    public boolean validarUsuario(String correo, String contrasena) {
        this.mostrarUsuarios();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?", new String[]{correo, contrasena});
        boolean existe = (cursor.getCount() > 0);
        cursor.close();
        return existe;
    }

    public boolean registrarUsuario(String nombre, String correo, String contrasena, String tipoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Verificar si ya existe el usuario
        Cursor cursor = db.rawQuery("SELECT id FROM usuarios WHERE correo = ?", new String[]{correo});
        boolean yaExiste = cursor.getCount() > 0;
        cursor.close();

        if (yaExiste) {
            Log.d("UESDatabaseHelper", "El correo ya está registrado: " + correo);
            return false;
        }

        try {
            db.execSQL("INSERT INTO usuarios (nombre, correo, contrasena, tipo_usuario) VALUES (?, ?, ?, ?)",
                    new Object[]{nombre, correo, contrasena, tipoUsuario});
            Log.d("UESDatabaseHelper", "Usuario registrado correctamente: " + correo);
            return true;
        } catch (Exception e) {
            Log.e("UESDatabaseHelper", "Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public void mostrarUsuarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios", null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("MiApp", "assdasdasdasdasdasd");
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));
                String contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena"));

                Log.d("MiApp", "Usuario ID: " + id + ", Correo: " + correo + ", Contraseña: " + contrasena);
            } while (cursor.moveToNext());
        } else {
            Log.d("MiApp", "No hay usuarios registrados.");
        }

        cursor.close();
        db.close();
    }


    // Obtener tipo de usuario (admin o estandar)
    public String obtenerTipoUsuario(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tipo_usuario FROM usuarios WHERE correo = ?", new String[]{correo});
        if (cursor != null && cursor.moveToFirst()) {
            String tipo = cursor.getString(0);
            cursor.close();
            return tipo;
        }
        return "estandar";
    }
}
