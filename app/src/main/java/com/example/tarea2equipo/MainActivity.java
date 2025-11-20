package com.example.tarea2equipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends Activity {
    // Claves para pasar datos a otras Activities
    public static final String EXTRA_NOMBRE = "com.example.tarea2equipo.NOMBRE";
    public static final String EXTRA_CASA = "com.example.tarea2equipo.CASA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        final TextInputEditText inputApodo = findViewById(R.id.inputApodo);
        // El ID 'inputEmail' en activity_inicio.xml se usa para la casa
        final TextInputEditText inputCasa = findViewById(R.id.inputEmail);
        MaterialButton buttonContinuar = findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Obtener la información del usuario
                String nombre = inputApodo.getText().toString();
                String casa = inputCasa.getText().toString();

                // Validar inputs
                if (nombre.isEmpty()) {
                    inputApodo.setError("Por favor ingresa tu nombre");
                    return;
                }

                if (casa.isEmpty()) {
                    inputCasa.setError("Por favor ingresa tu casa");
                    return;
                }

                // 2. Crear el Intent para iniciar IngredientesActivity
                Intent intent = new Intent(MainActivity.this, IngredientesActivity.class);

                // 3. Empaquetar la información en el Intent para pasarla a la siguiente Activity
                intent.putExtra(EXTRA_NOMBRE, nombre);
                intent.putExtra(EXTRA_CASA, casa);

                // 4. Iniciar la nueva Activity
                startActivity(intent);
            }
        });
    }
}