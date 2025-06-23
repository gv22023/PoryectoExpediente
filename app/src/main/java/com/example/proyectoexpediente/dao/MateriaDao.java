package com.example.proyectoexpediente.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.proyectoexpediente.models.Materia;

import java.util.List;

@Dao
public interface MateriaDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertarMateria(Materia materia);

    @Query("SELECT * FROM materias")
    List<Materia> obtenerMaterias();

}