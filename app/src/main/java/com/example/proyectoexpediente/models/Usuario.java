package com.example.proyectoexpediente.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Nullable
    public String getImagenUri() {
        return imagenUri;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String nombre;

    @ColumnInfo(name = "correo")
    public String correo;

    public String contrasena;

    @Nullable
    @ColumnInfo(name = "imagen_uri")
    public String imagenUri;

    @ColumnInfo(name = "tipo_usuario")
    public String tipoUsuario;

    public void setContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
