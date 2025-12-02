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
    private String nombreUsuario;
    private String casaUsuario;
    private int idUsuario;

    // Variables Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        // --- CONFIGURACIÓN ACTIONBAR Y DRAWER ---
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

        // 1. Recuperar la información pasada desde MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombreUsuario = extras.getString(MainActivity.EXTRA_NOMBRE, "Estudiante");
            casaUsuario = extras.getString(MainActivity.EXTRA_CASA, "Hogwarts");

        } else {
            nombreUsuario = "Estudiante";
            casaUsuario = "Hogwarts";
        }

        // Registramos (o recuperamos) el usuario en la BD para obtener su ID único
        GestorBD gestor = new GestorBD(this);
        gestor.open();
        // Creamos el objeto usuario temporal para buscarlo/crearlo
        Usuario usuarioTemp = new Usuario(nombreUsuario, casaUsuario);
        idUsuario = gestor.registrarUsuario(usuarioTemp);
        gestor.close();
    }

    public void mostrarInfo(View view) {
        // El tag de la CardView (View) contiene el nombre técnico del ingrediente
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
                ingredienteNombre = "Ingrediente Desconocido";
                break;
        }

        // 2. Crear un Intent para la siguiente Activity (detalles)
        Intent intent = new Intent(this, InfoIngredienteActivity.class);

        // 3. Empaquetar el ingrediente seleccionado y el nombre del usuario
        intent.putExtra("INGREDIENTE_TAG", ingredienteTag);
        intent.putExtra("INGREDIENTE_NOMBRE", ingredienteNombre);
        intent.putExtra(MainActivity.EXTRA_NOMBRE, nombreUsuario);
        intent.putExtra("ID_USUARIO", idUsuario);

        // 4. Iniciar la Activity de detalle
        startActivity(intent);
    }

    // --- MENÚS ---
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // Regresar al inicio si se presiona Inicio
            CarritoManager.getInstance().vaciarCarrito();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent intentPerfil = new Intent(this, PerfilActivity.class);
            intentPerfil.putExtra(MainActivity.EXTRA_NOMBRE, nombreUsuario);
            intentPerfil.putExtra(MainActivity.EXTRA_CASA, casaUsuario);
            intentPerfil.putExtra("ID_USUARIO", idUsuario);
            startActivity(intentPerfil);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_cart || id == R.id.nav_carrito) {
            // --- PASAR ID AL CARRITO ---
            Intent intent = new Intent(this, CarritoActivity.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            startActivity(intent);
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
        if (id == R.id.action_cart) {
            // --- PASAR ID AL CARRITO DESDE EL ACTION BAR ---
            Intent intent = new Intent(this, CarritoActivity.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Buscando...", Toast.LENGTH_SHORT).show();
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
}