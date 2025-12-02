package com.example.tarea2equipo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// --- Imports de Menú RESTAURADOS ---
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;
import android.content.Intent;
// --- Fin Imports de Menú RESTAURADOS ---

import java.util.List;

// Implementación RESTAURADA
public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // --- Variables de Menú RESTAURADAS ---
    private DrawerLayout drawerLayout;
    private int idUsuario;
    private String nombreUsuario;
    private String casaUsuario;
    // --- Fin Variables de Menú RESTAURADAS ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // 1. Obtener datos pasados por el Intent
        nombreUsuario = getIntent().getStringExtra(MainActivity.EXTRA_NOMBRE);
        casaUsuario = getIntent().getStringExtra(MainActivity.EXTRA_CASA);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

        // --- Configuración de Menú RESTAURADA (Busca los IDs que agregamos al XML) ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // --- Fin Configuración de Menú RESTAURADA ---

        // Componentes de la interfaz de Perfil
        TextView tvNombre = findViewById(R.id.tvNombrePerfil);
        TextView tvCasa = findViewById(R.id.tvCasaPerfil);
        ListView lvHistorial = findViewById(R.id.listaHistorial);
        Button btnVolver = findViewById(R.id.btnVolverPerfil);


        tvNombre.setText("Nombre: " + nombreUsuario);
        tvCasa.setText("Casa: " + (casaUsuario != null ? casaUsuario : "Desconocida"));

        // 2. Obtener historial desde la BD
        GestorBD gestor = new GestorBD(this);
        try {
            gestor.open();
            List<String> historial = gestor.obtenerHistorialPedidos(idUsuario);
            gestor.close();

            if (historial.isEmpty()) {
                historial.add("Aún no has realizado pedidos.");
            }

            // 3. Mostrar en la lista
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    historial
            );
            lvHistorial.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar historial", Toast.LENGTH_SHORT).show();
        }

        btnVolver.setOnClickListener(v -> finish());
    }

    // --- Método de Navegación RESTAURADO (Manejo de clics en el menú lateral) ---
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // Ir a la actividad principal de la Lista de Ingredientes
            CarritoManager.getInstance().vaciarCarrito(); // <-- Esta línea causa el error 97
            Intent intent = new Intent(this, IngredientesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Ya estás en tu Perfil", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Ayuda/Ajustes", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_cart || id == R.id.nav_carrito) {
            // Ir al Carrito
            Intent intent = new Intent(this, CarritoActivity.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            intent.putExtra(MainActivity.EXTRA_NOMBRE, nombreUsuario);
            intent.putExtra(MainActivity.EXTRA_CASA, casaUsuario);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}