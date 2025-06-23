package com.example.proyectoexpediente.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proyectoexpediente.dao.MateriaDao;
import com.example.proyectoexpediente.dao.UsuarioDao;
import com.example.proyectoexpediente.models.Materia;
import com.example.proyectoexpediente.models.Usuario;

import java.util.concurrent.Executors;

@Database(entities = {Usuario.class, Materia.class}, version = 1)
public abstract class UESDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract MateriaDao materiaDao();

    private static volatile UESDatabase INSTANCE;

    public static UESDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UESDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UESDatabase.class, "ues_expediente_db_v2") // nuevo nombre
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        Usuario admin = new Usuario();
                                        admin.setNombre("Admin");
                                        admin.setCorreo("admin@ues.edu.sv");
                                        admin.setContrasena("admin123");
                                        admin.setTipoUsuario("admin");
                                        admin.setImagenUri(null);

                                        getDatabase(context).usuarioDao().insertarUsuario(admin);
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
