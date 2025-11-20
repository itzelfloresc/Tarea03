package com.example.tarea2equipo;

import java.util.ArrayList;
import java.util.List;

public class CarritoManager {
    private static CarritoManager instancia;
    private List<Ingrediente> productos;

    // Constructor privado (Singleton)
    private CarritoManager() {
        productos = new ArrayList<>();
    }

    // Método para obtener la única instancia
    public static synchronized CarritoManager getInstance() {
        if (instancia == null) {
            instancia = new CarritoManager();
        }
        return instancia;
    }

    public void agregarProducto(Ingrediente ingrediente) {
        productos.add(ingrediente);
    }

    public List<Ingrediente> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        double total = 0;
        for (Ingrediente i : productos) {
            total += i.getPrecio();
        }
        return total;
    }

    public void vaciarCarrito() {
        productos.clear();
    }
}