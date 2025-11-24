package com.example.tarea2equipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import android.content.Intent;
import android.widget.Toast;

public class InfoIngredienteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private double precioIngrediente = 0;
    private int idUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ingrediente);

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

        TextView textTituloIngrediente = findViewById(R.id.textTituloIngrediente);
        TextView textPropiedadesDescripcion = findViewById(R.id.textPropiedadesDescripcion);
        TextView textPrecio = findViewById(R.id.textPrecio);
        ImageView imageIngrediente = findViewById(R.id.imageIngrediente);
        MaterialButton buttonRegresar = findViewById(R.id.buttonRegresar);
        MaterialButton btnAgregar = findViewById(R.id.buttonAgregarCarrito);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ingredienteTag = extras.getString("INGREDIENTE_TAG", "");
            String ingredienteNombre = extras.getString("INGREDIENTE_NOMBRE", "Ingrediente Desconocido");

            textTituloIngrediente.setText(ingredienteNombre);

            idUsuario = extras.getInt("ID_USUARIO", -1);

            int descripcionResId;
            int imagenResId;

            switch (ingredienteTag) {
                case "gillyweed":
                    descripcionResId = R.string.gillyweed_propiedades_desc;
                    imagenResId = R.drawable.gillyweed;
                    precioIngrediente = 150.00;
                    break;
                case "unicornhair":
                    descripcionResId = R.string.unicornhair_propiedades_desc;
                    imagenResId = R.drawable.hair;
                    precioIngrediente = 500.00;
                    break;
                case "mandragora":
                    descripcionResId = R.string.mandragora_propiedades_desc;
                    imagenResId = R.drawable.mandragora;
                    precioIngrediente = 300.50;
                    break;
                case "tentacula":
                    descripcionResId = R.string.tentacula_propiedades_desc;
                    imagenResId = R.drawable.tentacula;
                    precioIngrediente = 1200.00;
                    break;
                default:
                    descripcionResId = R.string.app_name; // Fallback para la descripción
                    imagenResId = R.drawable.gillyweed; // Fallback para la imagen
                    precioIngrediente = 0;
                    break;
            }

            textPropiedadesDescripcion.setText(getString(descripcionResId));
            imageIngrediente.setImageResource(imagenResId);
            textPrecio.setText("Precio: $" + precioIngrediente);

            btnAgregar.setOnClickListener(v -> {
                Ingrediente nuevoItem = new Ingrediente(ingredienteNombre, precioIngrediente);
                CarritoManager.getInstance().agregarProducto(nuevoItem);
                Toast.makeText(this, "Añadido al carrito", Toast.LENGTH_SHORT).show();
            });
        }

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // --- MENÚS ---
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            CarritoManager.getInstance().vaciarCarrito();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_cart) { // Agregar lógica para el icono del carrito arriba a la derecha
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