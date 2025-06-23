package com.example.proyectoexpediente.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.proyectoexpediente.models.Materia;
import com.example.proyectoexpediente.models.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataService {

    private final UESDatabase db;

    public DataService(Context context) {
        db = UESDatabase.getDatabase(context);
    }

    public boolean validarUsuario(String correo, String contrasena) {
        this.mostrarUsuarios();
        Usuario usuario = db.usuarioDao().validarUsuario(correo, contrasena);
        return usuario != null;
    }

    public boolean registrarUsuario(String nombre, String correo, String contrasena, String tipoUsuario) {
        Usuario usuarioExistente = db.usuarioDao().obtenerUsuario(correo);
        if (usuarioExistente != null) {
            Log.d("DataService", "El correo ya está registrado: " + correo);
            return false;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setTipoUsuario(tipoUsuario);

        try {
            db.usuarioDao().insertarUsuario(nuevoUsuario);
            Log.d("DataService", "Usuario registrado correctamente: " + correo);
            return true;
        } catch (Exception e) {
            Log.e("DataService", "Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public void mostrarUsuarios() {
        List<Usuario> usuarios = db.usuarioDao().obtenerUsuarios();
        if (usuarios.isEmpty()) {
            Log.d("DataService", "No hay usuarios registrados.");
        } else {
            for (Usuario u : usuarios) {
                Log.d("DataService", "Usuario ID: " + u.getId() + ", Correo: " + u.getCorreo() + ", Contraseña: " + u.getContrasena());
            }
        }
    }

    public boolean actualizarUsuario(String correo, @Nullable String nuevaContrasena, @Nullable String imagenUri) {
        Usuario usuario = db.usuarioDao().obtenerUsuario(correo);
        if (usuario == null) return false;

        boolean hayCambios = false;
        if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
            usuario.setContrasena(nuevaContrasena);
            hayCambios = true;
        }
        if (imagenUri != null && !imagenUri.isEmpty()) {
            usuario.setImagenUri(imagenUri);
            hayCambios = true;
        }
        if (!hayCambios) return false;

        int updated = db.usuarioDao().actualizarUsuario(usuario);
        return updated > 0;
    }

    public String obtenerTipoUsuario(String correo) {
        String tipo = db.usuarioDao().obtenerTipoUsuario(correo);
        return tipo != null ? tipo : "estandar";
    }

    public Map<String, String> obtenerUsuario(String correo) {
        Usuario usuario = db.usuarioDao().obtenerUsuario(correo);
        Map<String, String> map = new HashMap<>();
        if (usuario != null) {
            map.put("nombre", usuario.getNombre());
            map.put("correo", usuario.getCorreo());
            map.put("imagen_uri", usuario.getImagenUri());
        }
        return map;
    }

    public boolean insertarMateria(String nombre, String codigo, int uv) {
        Materia materia = new Materia(nombre, codigo, uv);
        try {
            db.materiaDao().insertarMateria(materia);
            return true;
        } catch (Exception e) {
            Log.e("DataService", "Error al insertar materia: " + e.getMessage());
            return false;
        }
    }

    public List<Materia> obtenerMaterias() {
        return db.materiaDao().obtenerMaterias();
    }
}
