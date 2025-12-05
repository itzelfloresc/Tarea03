package com.example.tarea2equipo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class PerfilActivity extends AppCompatActivity {
    private int idUsuario;
    private String nombreUsuario;
    private String casaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // 1. Configura la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Establece el título desde el código
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Mi Perfil");
        }

        // 1. Obtener datos pasados por el Intent
        nombreUsuario = getIntent().getStringExtra(MainActivity.EXTRA_NOMBRE);
        casaUsuario = getIntent().getStringExtra(MainActivity.EXTRA_CASA);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

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

}