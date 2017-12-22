package com.evertvd.inventariobox.Interfaces;

import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Historial;

import java.util.List;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IHistorial {
    public Historial obtenerHisotorial(long idHistorial);
    public boolean agregarHistorial(Conteo conteo,Historial historial);
    public List<Historial> listarHisotorial(Conteo conteo);
    public void eliminarHistorial(Historial historial);
    public Historial ultimoHistorial(Conteo conteo);
    public void deleteAll();
}
