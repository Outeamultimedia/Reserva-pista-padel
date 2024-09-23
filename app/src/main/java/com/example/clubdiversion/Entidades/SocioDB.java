package com.example.clubdiversion.Entidades;

public class SocioDB {
    private Integer id;
    private String Nombre;
    private String Direccion;
    private String Tlf;
    private String Correo;
    private Boolean SocioAdmin;


    public SocioDB(Integer id, String nombre, String direccion, String tlf, String correo, Boolean socioAdmin) {
        this.id = id;
        Nombre = nombre;
        Direccion = direccion;
        Tlf = tlf;
        Correo = correo;
        SocioAdmin = socioAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTlf() {
        return Tlf;
    }

    public void setTlf(String tlf) {
        Tlf = tlf;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public Boolean getSocioAdmin() {
        return SocioAdmin;
    }

    public void setSocioAdmin(Boolean socioAdmin) {
        SocioAdmin = socioAdmin;
    }
}
