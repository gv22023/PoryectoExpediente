package com.example.proyectoexpediente.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

    public static Uri copiarImagenLocal(Context context, Uri uriOrigen) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uriOrigen);
            File archivoDestino = new File(context.getCacheDir(), "perfil.jpg");

            OutputStream outputStream = new FileOutputStream(archivoDestino);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return Uri.fromFile(archivoDestino);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
