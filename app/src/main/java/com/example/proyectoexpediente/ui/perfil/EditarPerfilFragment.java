package com.example.proyectoexpediente.ui.perfil;

import static com.example.proyectoexpediente.utils.Utils.copiarImagenLocal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectoexpediente.R;
import com.example.proyectoexpediente.database.DataService;
import com.example.proyectoexpediente.database.DataService;
import com.example.proyectoexpediente.utils.Utils;

import java.util.Map;

public class EditarPerfilFragment extends Fragment {

    private static final int REQUEST_CODE_SELECT_IMAGE = 100;

    private ImageView imgPerfil;
    private EditText etNombre, etCorreo, etPassword, etConfirmPassword;
    private Button btnGuardar, btnSeleccionarImagen;
    private Uri selectedImageUri = null;

    private DataService dbHelper;
    private String correoUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgPerfil = view.findViewById(R.id.imgPerfil);
        etNombre = view.findViewById(R.id.etNombre);
        etCorreo = view.findViewById(R.id.etCorreo);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword2);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnSeleccionarImagen = view.findViewById(R.id.btnSeleccionarImagen);

        dbHelper = new DataService(requireContext());

        // obtener correo del usuario desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        correoUsuario = prefs.getString("correo", "");

        cargarDatosUsuario();

        btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            seleccionarImagenLauncher.launch(intent);
        });

        btnGuardar.setOnClickListener(v -> {
            String nuevaContrasena = etPassword.getText().toString().trim();
            String confirmar = etConfirmPassword.getText().toString().trim();

            if (!nuevaContrasena.isEmpty() && !nuevaContrasena.equals(confirmar)) {
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            String imagenUri = selectedImageUri != null ? selectedImageUri.toString() : null;

            boolean updated = dbHelper.actualizarUsuario(
                    correoUsuario,
                    nuevaContrasena.isEmpty() ? null : nuevaContrasena,
                    imagenUri
            );

            if (updated) {
                Toast.makeText(getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No se modificó ningún dato", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosUsuario() {
        Map<String, String> usuario = dbHelper.obtenerUsuario(correoUsuario);

        if (!usuario.isEmpty()) {
            etNombre.setText(usuario.get("nombre"));
            etCorreo.setText(usuario.get("correo"));
            etCorreo.setEnabled(false);

            String imagenUriStr = usuario.get("imagen_uri");
            if (imagenUriStr != null) {
                Uri uri = Uri.parse(imagenUriStr);
                imgPerfil.setImageURI(uri);
            }
        }
    }


    private final ActivityResultLauncher<Intent> seleccionarImagenLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri originalUri = result.getData().getData();
                selectedImageUri = Utils.copiarImagenLocal(requireContext(), originalUri);

                if (selectedImageUri != null) {
                    imgPerfil.setImageURI(selectedImageUri);
                }
            }
        });

}
