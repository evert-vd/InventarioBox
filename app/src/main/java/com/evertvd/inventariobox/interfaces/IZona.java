package com.evertvd.inventariobox.interfaces;



import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;

import java.util.List;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IZona {
    public List<Zona> listarZona();
    public Zona buscarZonaNombre(String nombre);
    public Zona buscarZonaId(long id);
    public Zona obtenerZona(Producto producto);
    public boolean agregarZona(Zona zona);
    public boolean actualizarZona(Zona zona);
    public List<Zona> listarZonaDiferencia();
    public void calcularDiferenciaZona();
    public boolean deleteAll();
}
