package com.evertvd.inventariobox.Interfaces;


import com.evertvd.inventariobox.modelo.Empresa;
import com.evertvd.inventariobox.modelo.Inventario;

import java.util.List;

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
