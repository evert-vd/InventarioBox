package com.evertvd.inventariobox.modelo;

import android.os.Parcelable;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by evertvd on 1/12/2017.
 */

@Entity
public class Historial {

    @Id
    public long id;
    private int cantidad;
    private String observacion;
    private String fechaRegistro;
    private String fechaModificacion;
    private int tipo;//1:inicial, 2: modificacion, -1:eliminacion

    public ToOne<Conteo> conteo;

    public Historial(){

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

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public ToOne<Conteo> getConteo() {
        return conteo;
    }

    public void setConteo(ToOne<Conteo> conteo) {
        this.conteo = conteo;
    }
}
