package com.example.tarea2equipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

// 1. Implementar la interfaz para manejar el menú lateral
public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int idUsuario;
    private String nombreUsuario;
    private String casaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // --- CONFIGURACIÓN DE TOOLBAR Y DRAWER (Añadido) ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // ---------------------------------------------------

        TextView tvNombre = findViewById(R.id.tvNombrePerfil);
        TextView tvCasa = findViewById(R.id.tvCasaPerfil);
        ListView lvHistorial = findViewById(R.id.listaHistorial);
        Button btnVolver = findViewById(R.id.btnVolverPerfil);

        // 1. Obtener datos pasados por el Intent
        nombreUsuario = getIntent().getStringExtra(MainActivity.EXTRA_NOMBRE);
        casaUsuario = getIntent().getStringExtra(MainActivity.EXTRA_CASA);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

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

    // --- MENÚS ---

    // 2. Inflar el menú de opciones (iconos de la Toolbar)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actbar, menu);
        return true;
    }

    // 3. Controlar los clics en los ítems de la Toolbar (Buscar, Carrito, Ayuda)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Toast.makeText(this, "Buscando...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_cart) {
            // Ir al carrito
            Intent intent = new Intent(this, CarritoActivity.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_notifications) {
            Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_help) {
            Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 4. Controlar los clics en el menú lateral (Drawer Layout)
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Regresar al inicio
            CarritoManager.getInstance().vaciarCarrito();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_carrito) {
            // Ir al carrito
            Intent intent = new Intent(this, CarritoActivity.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
        }
        // Si selecciona nav_profile, ya estamos aquí.

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}