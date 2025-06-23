package com.example.proyectoexpediente.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectoexpediente.models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    Usuario validarUsuario(String correo, String contrasena);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertarUsuario(Usuario usuario);

    @Update
    int actualizarUsuario(Usuario usuario);

    @Query("SELECT tipo_usuario FROM usuarios WHERE correo = :correo LIMIT 1")
    String obtenerTipoUsuario(String correo);

    @Query("SELECT id, nombre, correo, imagen_uri FROM usuarios WHERE correo = :correo")
    Usuario obtenerUsuario(String correo);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerUsuarios();
}
