package com.example.proyectoexpediente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.proyectoexpediente.database.UESDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoexpediente.databinding.ActivityMainBinding;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Acción rápida", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Registrar los destinos principales en la navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_editar_perfil,
                R.id.nav_ver_expediente,
                R.id.nav_inscribir
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //cargar nombre del usuario
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        TextView tvBienvenida = binding.appBarMain.getRoot().findViewById(R.id.welcome);
        String correoUsuario = prefs.getString("correo", null);

        if (correoUsuario != null) {
            UESDatabaseHelper dbHelper = new UESDatabaseHelper(this);
            Map<String, String> usuario = dbHelper.obtenerUsuario(correoUsuario);

            String nombreUsuario = usuario.get("nombre");
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                tvBienvenida.setText("Bienvenido, " + nombreUsuario);
            } else {
                tvBienvenida.setText("Bienvenido, usuario");
            }
        } else {
            tvBienvenida.setText("Bienvenido, usuario");
        }

        // Redirección si el usuario es admin
        String destino = getIntent().getStringExtra("ir_a");
        if ("inscribir_materias".equals(destino)) {
            navController.navigate(R.id.nav_inscribir);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // log out
            SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
            prefs.edit().clear().apply(); // Limpia sesión

            // redirect to login
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
