package com.example.tarea2equipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;
import android.view.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IngredientesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Solo se mantiene 'nombreUsuario' porque se usa en onNavigationItemSelected
    private String nombreUsuario;

    // Solo se mantiene 'drawerLayout' porque se usa en onNavigationItemSelected
    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        // --- CONFIGURACIÓN ACTIONBAR Y DRAWER ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        // navigationView es ahora local
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // 1. Recuperar la información pasada desde MainActivity
        Bundle extras = getIntent().getExtras();

        // CORRECCIÓN HARDCODING DE STRING
        String defaultUserName = getString(R.string.default_user_name);
        String defaultHouseName = getString(R.string.default_house_name);

        if (extras != null) {
            nombreUsuario = extras.getString(MainActivity.EXTRA_NOMBRE, defaultUserName);
            // La variable casaUsuario ya no es un campo de clase y se convierte a local si se necesita.
            // Aquí se recupera si se necesita para alguna lógica futura. Si no se usa, la omitimos.
            // String casaUsuario = extras.getString(MainActivity.EXTRA_CASA, defaultHouseName);
        } else {
            nombreUsuario = defaultUserName;
            // casaUsuario = defaultHouseName;
        }
    }

    public void mostrarInfo(View view) {
        String ingredienteTag = (String) view.getTag();
        String ingredienteNombre;

        // Mapear el tag al nombre legible desde strings.xml
        switch (ingredienteTag) {
            case "gillyweed":
                ingredienteNombre = getString(R.string.ingrediente1_nombre);
                break;
            case "unicornhair":
                ingredienteNombre = getString(R.string.ingrediente2_nombre);
                break;
            case "mandragora":
                ingredienteNombre = getString(R.string.ingrediente3_nombre);
                break;
            case "tentacula":
                ingredienteNombre = getString(R.string.ingrediente4_nombre);
                break;
            default:
                ingredienteNombre = getString(R.string.default_ingredient_name);
                break;
        }

        // 2. Crear un Intent para la siguiente Activity (detalles)
        Intent intent = new Intent(this, InfoIngredienteActivity.class);

        // 3. Empaquetar el ingrediente seleccionado y el nombre del usuario
        intent.putExtra("INGREDIENTE_TAG", ingredienteTag);
        intent.putExtra("INGREDIENTE_NOMBRE", ingredienteNombre);
        intent.putExtra(MainActivity.EXTRA_NOMBRE, nombreUsuario);

        // 4. Iniciar la Activity de detalle
        startActivity(intent);
    }

    // --- LÓGICA DE MENÚS ---
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            String profileMessage = getString(R.string.profile_message, nombreUsuario);
            Toast.makeText(this, profileMessage, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, getString(R.string.settings_message), Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(this, getString(R.string.search_message), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_notifications) {
            Toast.makeText(this, getString(R.string.notifications_message), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_help) {
            Toast.makeText(this, getString(R.string.help_message), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}