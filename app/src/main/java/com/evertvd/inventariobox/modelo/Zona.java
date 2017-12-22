package com.evertvd.inventariobox.modelo;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by evertvd on 1/12/2017.
 */

@Entity
public class Zona {

    @Id
    public long id;

    private String nombre;
    private int estado;
    public ToOne<Inventario> inventario;

    @Backlink
    public ToMany<Producto> producto;

    public Zona(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ToOne<Inventario> getInventario() {
        return inventario;
    }

    public void setInventario(ToOne<Inventario> inventario) {
        this.inventario = inventario;
    }

    public ToMany<Producto> getProducto() {
        return producto;
    }

    public void setProducto(ToMany<Producto> producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return nombre.toString();
    }
}
