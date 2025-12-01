package com.example.tarea2equipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Locale;

// La clase ya no implementa NavigationView.OnNavigationItemSelectedListener
public class CarritoActivity extends AppCompatActivity {

    // Se eliminan las variables de DrawerLayout y NavigationView,
    // pero se mantienen las de usuario
    private int idUsuarioActual;
    private int idUsuario;
    private String nombreUsuario;
    private String casaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // --- SE ELIMINA LA CONFIGURACIÓN DEL DRAWER/TOOLBAR ---

        ListView listView = findViewById(R.id.listaCarrito);
        TextView textTotal = findViewById(R.id.textTotal);
        MaterialButton btnPagar = findViewById(R.id.btnPagar);
        MaterialButton btnRegresar = findViewById(R.id.btnRegresar);

        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        nombreUsuario = getIntent().getStringExtra(MainActivity.EXTRA_NOMBRE);
        casaUsuario = getIntent().getStringExtra(MainActivity.EXTRA_CASA);

        // Validar strings por si vienen nulos
        if (nombreUsuario == null) nombreUsuario = "Estudiante";
        if (casaUsuario == null) casaUsuario = "Hogwarts";


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

        idUsuarioActual = getIntent().getIntExtra("ID_USUARIO", -1);

        btnPagar.setOnClickListener(v -> {
            if (lista.isEmpty()) {
                Toast.makeText(this, "Carrito vacío", Toast.LENGTH_SHORT).show();
            } else if (idUsuarioActual == -1) {
                Toast.makeText(this, "Error de usuario", Toast.LENGTH_SHORT).show();
            } else {
                procesarCompra();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // --- MÉTODO PARA GUARDAR EN BASE DE DATOS ---
    private void procesarCompra() {
        // 1. Recopilar datos para el Pedido
        List<Ingrediente> productosCompra = CarritoManager.getInstance().getProductos();
        double totalCompra = CarritoManager.getInstance().calcularTotal();

        // Generar fecha actual (ej: "2023-11-23")
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // 2. Crear el objeto Pedido "Todo en Uno"
        // (Usa el constructor que definimos: idUsuario, fecha, total, listaIngredientes)
        Pedido nuevoPedido = new Pedido(idUsuarioActual, fechaActual, productosCompra, totalCompra);

        // 3. Guardar en Base de Datos usando el Gestor
        GestorBD gestor = new GestorBD(this);
        try {
            gestor.open();
            gestor.registrarPedidoCompleto(nuevoPedido); // ¡Esta función hace toda la magia!
            gestor.close();

            // 4. Feedback y limpieza
            Toast.makeText(this, "¡Compra realizada con éxito!", Toast.LENGTH_LONG).show();

            // Limpiamos el carrito en memoria RAM
            CarritoManager.getInstance().vaciarCarrito();

            // Cerramos la pantalla del carrito
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el pedido", Toast.LENGTH_SHORT).show();
        }
    }

}