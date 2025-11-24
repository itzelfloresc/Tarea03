package com.example.tarea2equipo;

public class Usuario {
    private int id;
    private String nombre;
    private String casa;

    /**
     * Constructor cuando es usuario nuevo. */
    public Usuario(String nombre, String casa) {
        this.nombre = nombre;
        this.casa = casa;
    }

    /**
     * Constructor cuando leemos de la Basa de Datos
     */
    public Usuario(int id, String nombre, String casa) {
        this.id = id;
        this.nombre = nombre;
        this.casa = casa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    @Override
    public String toString() {
        return nombre + " de " + casa;
    }
}
