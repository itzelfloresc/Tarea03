package com.example.tarea2equipo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        TextView tvNombre = findViewById(R.id.tvNombrePerfil);
        TextView tvCasa = findViewById(R.id.tvCasaPerfil);
        ListView lvHistorial = findViewById(R.id.listaHistorial);
        Button btnVolver = findViewById(R.id.btnVolverPerfil);

        // 1. Obtener datos pasados por el Intent
        String nombre = getIntent().getStringExtra(MainActivity.EXTRA_NOMBRE);
        String casa = getIntent().getStringExtra(MainActivity.EXTRA_CASA);
        int idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

        tvNombre.setText("Nombre: " + nombre);
        tvCasa.setText("Casa: " + (casa != null ? casa : "Desconocida"));

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