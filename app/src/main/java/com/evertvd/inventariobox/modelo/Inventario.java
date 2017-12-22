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
public class Inventario {

    @Id
    public long id;

    private int numInventario;
    private int numEquipo;
    private String fechaApertura;
    private String fechaCierre;
    private int contexto;
    public ToOne<Empresa> empresa;

    @Backlink
    public ToMany<Zona> zona;

    public Inventario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumInventario() {
        return numInventario;
    }

    public void setNumInventario(int numInventario) {
        this.numInventario = numInventario;
    }

    public int getNumEquipo() {
        return numEquipo;
    }

    public void setNumEquipo(int numEquipo) {
        this.numEquipo = numEquipo;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public int getContexto() {
        return contexto;
    }

    public void setContexto(int contexto) {
        this.contexto = contexto;
    }

    public ToOne<Empresa> getEmpresa() {
        return empresa;
    }

    public void setEmpresa(ToOne<Empresa> empresa) {
        this.empresa = empresa;
    }

    public ToMany<Zona> getZona() {
        return zona;
    }

    public void setZona(ToMany<Zona> zona) {
        this.zona = zona;
    }
}
