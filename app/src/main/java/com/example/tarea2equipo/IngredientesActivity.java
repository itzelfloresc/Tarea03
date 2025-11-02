package com.example.tarea2equipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IngredientesActivity extends Activity {
    private String nombreUsuario;
    private String casaUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        // 1. Recuperar la información pasada desde MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombreUsuario = extras.getString(MainActivity.EXTRA_NOMBRE, "Estudiante");
            casaUsuario = extras.getString(MainActivity.EXTRA_CASA, "Hogwarts");

        } else {
            nombreUsuario = "Estudiante";
            casaUsuario = "Hogwarts";
        }
    }

    // Método llamado por el atributo android:onClick="mostrarInfo" en el estilo IngredientCard
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

        // 4. Iniciar la Activity de detalle
        startActivity(intent);
    }
}