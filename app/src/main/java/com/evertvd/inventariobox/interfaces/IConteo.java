package com.evertvd.inventariobox.interfaces;


import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;

import java.util.List;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IConteo {
    public Conteo obtenerConteo(long idConteo);
    public boolean agregarConteo(Producto producto, Conteo conteo);
    public List<Conteo> listarConteo(Producto producto);
    public List<Conteo> listarAllConteo();
    public void actualizarConteo(Conteo conteo);
    public void eliminarConteo(Conteo conteo);
    public int obtenerTotalConteo(Producto producto);
    public int obtenerTotalConteoZona(Zona zona);
    public int totalConteo();
    public List<Conteo> listarConteoPorValidar();
    public Conteo ultimoConteo(Producto producto);
    public void deleteAll();

}
