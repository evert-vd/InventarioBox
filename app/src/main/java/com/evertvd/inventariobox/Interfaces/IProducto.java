package com.evertvd.inventariobox.Interfaces;


import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;

import java.util.List;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IProducto {
    public Producto obtenerProducto(long idProducto);
    public void eliminarProducto(Producto producto);
    public boolean agregarProducto(Producto producto);
    public List<Producto> listarProducto();
    public List<Producto> listarProductoSistema();//el reporte resumido solo incluye los productos del sistema
    public List<Producto> listarProductoApp();
    public List<Producto> listarProductoZona(Zona zona);
    public List<Producto> listarProductoZonaResumen(long idZona);
    public int totalProductosZona(Zona zona);
    public List<Producto> listarTotalProductoDiferencia();
    public List<Producto> listarProductoDiferenciaZona(long idZona);
    public void calcularPoductoDiferencia();
    public int ultimoProducto();
    public boolean deleteAll();
}
