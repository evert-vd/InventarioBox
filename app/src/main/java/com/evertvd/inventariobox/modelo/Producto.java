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
public class Producto {

    @Id
    public long id;

    private int codigo;
    private String descripcion;
    private double stock;
    private String tipo;//origen del dato:sistema, app
    private int estado;//-1:diferencia;abierto, 0:sin diferencia:cerrado
    public ToOne<Zona> zona;
    @Backlink
    public ToMany<Conteo> conteo;
    public Producto(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ToOne<Zona> getZona() {
        return zona;
    }

    public void setZona(ToOne<Zona> zona) {
        this.zona = zona;
    }

    public ToMany<Conteo> getConteo() {
        return conteo;
    }

    public void setConteo(ToMany<Conteo> conteo) {
        this.conteo = conteo;
    }

}
