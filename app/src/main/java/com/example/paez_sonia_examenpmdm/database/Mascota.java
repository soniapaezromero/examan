package com.example.paez_sonia_examenpmdm.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mascotas")
public class Mascota {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "raza")
    private String raza;
    @ColumnInfo(name = "edad")
    private Integer edad;
    @ColumnInfo(name = "imagen")
    private String imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
