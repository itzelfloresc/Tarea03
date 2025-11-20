package com.example.tarea2equipo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class CarritoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // --- SETUP DRAWER/TOOLBAR ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ListView listView = findViewById(R.id.listaCarrito);
        TextView textTotal = findViewById(R.id.textTotal);
        MaterialButton btnPagar = findViewById(R.id.btnPagar);
        MaterialButton btnRegresar = findViewById(R.id.btnRegresar);


        // Obtener datos del Singleton
        List<Ingrediente> lista = CarritoManager.getInstance().getProductos();

        // Mostrar lista simple usando un adaptador por defecto de Android
        ArrayAdapter<Ingrediente> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout básico de android
                lista
        );
        listView.setAdapter(adapter);

        // Calcular y mostrar total
        double total = CarritoManager.getInstance().calcularTotal();
        textTotal.setText("Total: $" + total);

        btnPagar.setOnClickListener(v -> {
            if (lista.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "¡Compra realizada con éxito!", Toast.LENGTH_LONG).show();
                CarritoManager.getInstance().vaciarCarrito(); // Limpiar
                finish(); // Cerrar pantalla
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
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
            startActivity(new Intent(this, MainActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}