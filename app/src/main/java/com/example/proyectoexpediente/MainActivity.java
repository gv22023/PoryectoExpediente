package com.example.proyectoexpediente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import com.example.proyectoexpediente.database.DataService;
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

        // Registrar destinos principales
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

        // Cargar nombre del usuario
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        TextView tvBienvenida = binding.appBarMain.getRoot().findViewById(R.id.welcome);
        String correoUsuario = prefs.getString("correo", null);

        if (correoUsuario != null) {
            DataService dbHelper = new DataService(this);
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

        // Manejador personalizado para navegación + logout
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_logout) {
                mostrarDialogoCerrarSesion();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawer(GravityCompat.START);
            return handled;
        });
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
            // Ya no se usa logout desde aquí, pero se mantiene por si acaso
            mostrarDialogoCerrarSesion();
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

    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                    prefs.edit().clear().apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}

