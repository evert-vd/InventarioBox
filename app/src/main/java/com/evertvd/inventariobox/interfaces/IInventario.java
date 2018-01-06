package com.evertvd.inventariobox.interfaces;


import com.evertvd.inventariobox.modelo.Inventario;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IInventario {
    public Inventario obtenerInventario();
    //public Inventario obtenerInventarioCerrado();
    public boolean agregarInventario(Inventario inventario);
    public boolean actualizarInventario(Inventario inventario);
    public boolean deleteAll();
}
