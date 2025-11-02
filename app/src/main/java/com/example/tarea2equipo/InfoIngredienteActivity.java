package com.example.tarea2equipo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

public class InfoIngredienteActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ingrediente);

        TextView textTituloIngrediente = findViewById(R.id.textTituloIngrediente);
        TextView textPropiedadesDescripcion = findViewById(R.id.textPropiedadesDescripcion);
        ImageView imageIngrediente = findViewById(R.id.imageIngrediente);
        MaterialButton buttonRegresar = findViewById(R.id.buttonRegresar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ingredienteTag = extras.getString("INGREDIENTE_TAG", "");
            String ingredienteNombre = extras.getString("INGREDIENTE_NOMBRE", "Ingrediente Desconocido");

            textTituloIngrediente.setText(ingredienteNombre);

            int descripcionResId;
            int imagenResId;

            switch (ingredienteTag) {
                case "gillyweed":
                    descripcionResId = R.string.gillyweed_propiedades_desc;
                    imagenResId = R.drawable.gillyweed;
                    break;
                case "unicornhair":
                    descripcionResId = R.string.unicornhair_propiedades_desc;
                    imagenResId = R.drawable.hair;
                    break;
                case "mandragora":
                    descripcionResId = R.string.mandragora_propiedades_desc;
                    imagenResId = R.drawable.mandragora;
                    break;
                case "tentacula":
                    descripcionResId = R.string.tentacula_propiedades_desc;
                    imagenResId = R.drawable.tentacula;
                    break;
                default:
                    descripcionResId = R.string.app_name; // Fallback para la descripción
                    imagenResId = R.drawable.gillyweed; // Fallback para la imagen
                    break;
            }

            textPropiedadesDescripcion.setText(getString(descripcionResId));
            imageIngrediente.setImageResource(imagenResId);
        }

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}