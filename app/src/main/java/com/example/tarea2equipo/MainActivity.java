package com.example.tarea2equipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Claves para pasar datos a otras Activities
    public static final String EXTRA_NOMBRE = "com.example.tarea2equipo.NOMBRE";
    public static final String EXTRA_CASA = "com.example.tarea2equipo.CASA";

    // Variables para el Drawer
    private DrawerLayout drawerLayout; // Se mantiene como campo de clase

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        // --- A. CONFIGURACIÓN DE (ACTION BAR) ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- B. CONFIGURACIÓN DEL DRAWER ---
        drawerLayout = findViewById(R.id.drawer_layout);
        // navigationView convertida a variable local
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Configura el icono de "hamburguesa" que abre/cierra el menú
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Asigna el listener para los clicks del menú lateral
        navigationView.setNavigationItemSelectedListener(this);

        final TextInputEditText inputApodo = findViewById(R.id.inputApodo);
        final TextInputEditText inputCasa = findViewById(R.id.inputEmail);
        MaterialButton buttonContinuar = findViewById(R.id.buttonContinuar);

        // CORRECCIÓN LAMBDA
        buttonContinuar.setOnClickListener(v -> {
            // 1. Obtener la información del usuario de forma segura
            String nombre = inputApodo.getText() != null ? inputApodo.getText().toString() : "";
            String casa = inputCasa.getText() != null ? inputCasa.getText().toString() : "";

            // Validar inputs (CORRECCIÓN HARDCODING DE STRING EN ERRORES)
            if (nombre.isEmpty()) {
                inputApodo.setError(getString(R.string.error_input_name));
                return;
            }

            if (casa.isEmpty()) {
                inputCasa.setError(getString(R.string.error_input_house));
                return;
            }

            // 2. Crear el Intent para iniciar IngredientesActivity
            Intent intent = new Intent(MainActivity.this, IngredientesActivity.class);

            // 3. Empaquetar la información
            intent.putExtra(EXTRA_NOMBRE, nombre);
            intent.putExtra(EXTRA_CASA, casa);

            // 4. Iniciar la nueva Activity
            startActivity(intent);
        });
    }

    // --- D. CONTROLAR CLICKS DEL MENÚ DRAWER (LATERAL) ---
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // CORRECCIÓN HARDCODING DE STRING EN TOASTS
        if (id == R.id.nav_home) {
            Toast.makeText(this, getString(R.string.toast_home), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, getString(R.string.toast_profile), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, getString(R.string.toast_settings), Toast.LENGTH_SHORT).show();
        }

        // Cierra el drawer después de seleccionar
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // --- E. INFLAR MENÚ DE LA ACTIONBAR (SUPERIOR) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actbar, menu);
        return true;
    }

    // --- F. CONTROLAR CLICKS DE LA ACTIONBAR (SUPERIOR) ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // CORRECCIÓN HARDCODING DE STRING EN TOASTS
        if (id == R.id.action_search) {
            Toast.makeText(this, getString(R.string.toast_search), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_notifications) {
            Toast.makeText(this, getString(R.string.toast_notifications), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_help) {
            Toast.makeText(this, getString(R.string.toast_help), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}