package com.example.tarea2equipo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Para ver la interfaz de 'activity_inicio.xml'
        //setContentView(R.layout.activity_inicio);

        // Para ver la interfaz de 'activity_ingredientes.xml' (la segunda pantalla):
        setContentView(R.layout.activity_ingredientes);
    }
}