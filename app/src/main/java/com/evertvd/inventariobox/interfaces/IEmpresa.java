package com.evertvd.inventariobox.interfaces;


import com.evertvd.inventariobox.modelo.Empresa;
import java.util.List;

/**
 * Created by evertvd on 06/12/2017.
 */

public interface IEmpresa {
    public List<Empresa> listarEmpresa();
    public Empresa obtenerEmpresa(int codigo);
    public boolean agregarEmpresa(Empresa empresa);
}
