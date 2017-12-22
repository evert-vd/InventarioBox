package com.evertvd.inventariobox.modelo;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by evertvd on 1/12/2017.
 */

@Entity
public class Empresa {

    @Id
    public long id;
    private String nombre;
    private int codigo;
    private int estado;//1:archivado, 0:desarchivado

    @Backlink
    public   ToMany<Inventario> inventario;

    public Empresa(){

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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ToMany<Inventario> getInventario() {
        return inventario;
    }

    public void setInventario(ToMany<Inventario> inventario) {
        this.inventario = inventario;
    }

    //override methods


    @Override
    public String toString() {
        return nombre.toString();
    }
}
