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
public class Conteo {

    @Id
    public long id;

    private int cantidad;
    private String observacion;
    private String  fechaRegistro;
    private int validado;//1:validado, 0:por validar
    private int estado;//-1:Eliminado, 1:Modificado, 0:inicial
    public ToOne<Producto> producto;

    @Backlink
    public ToMany<Historial> historial;

    public Conteo(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getValidado() {
        return validado;
    }

    public void setValidado(int validado) {
        this.validado = validado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ToOne<Producto> getProducto() {
        return producto;
    }

    public void setProducto(ToOne<Producto> producto) {
        this.producto = producto;
    }

    public ToMany<Historial> getHistorial() {
        return historial;
    }

    public void setHistorial(ToMany<Historial> historial) {
        this.historial = historial;
    }
}
