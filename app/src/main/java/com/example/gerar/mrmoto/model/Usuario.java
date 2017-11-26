package com.example.gerar.mrmoto.model;

/**
 * Created by gerar on 02/11/2017.
 */

public class Usuario {
    private String email;
    private String contrasena;
    private String nombre;
    private String celular;
    private String fechaNacimiento;
    private String direccion;
    String tipo;

    //constructor
    public Usuario(String email, String contrasena, String nombre, String celular, String fechaNacimiento, String direccion, String tipo){
        this.email = email;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.tipo = tipo;
    }

    //getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
