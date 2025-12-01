package com.example.tarea2equipo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import android.content.Intent;
import android.widget.Toast;

public class InfoIngredienteActivity extends AppCompatActivity {

    // 2. Se eliminan las variables relacionadas con el menú
    private double precioIngrediente = 0;
    private int idUsuario;
    private String nombreUsuario;
    private String casaUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ingrediente);
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
            nombreUsuario = extras.getString(MainActivity.EXTRA_NOMBRE, "Estudiante");
            casaUsuario = extras.getString(MainActivity.EXTRA_CASA, "Hogwarts");

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
}