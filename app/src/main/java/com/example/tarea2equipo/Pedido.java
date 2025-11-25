package com.example.tarea2equipo;

import java.util.List;

public class Pedido {
    private int idUsuario;
    private String fecha;
    private List<Ingrediente> ingredientes;
    private double total;

    public Pedido(int idUsuario, String fecha, List<Ingrediente> ingredientes, double total) {
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.ingredientes = ingredientes;
        this.total = total;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public double getTotal() {
        return total;
    }
}
