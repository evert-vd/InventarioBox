package com.evertvd.inventariobox.sqlite;

import android.app.Activity;
import android.content.Context;

import com.evertvd.inventariobox.Interfaces.IProducto;
import com.evertvd.inventariobox.Interfaces.IZona;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Conteo_;
import com.evertvd.inventariobox.modelo.Empresa;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;
import com.evertvd.inventariobox.modelo.Zona_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by evertvd on 19/12/2017.
 */

public class SqliteZona implements IZona {

    private Box<Zona> zonaBox;
    private Query<Zona> zonaQuery;
    private BoxStore boxStore;
    private Context context;


    public SqliteZona(Context context){
        this.context=context;
        boxStore=((App)context.getApplicationContext()).getBoxStore();
        zonaBox = boxStore.boxFor(Zona.class);
        zonaQuery = zonaBox.query().build();
    }


    @Override
    public List<Zona> listarZona() {
        List<Zona> zonaList = zonaBox.getAll();
        return zonaList;
    }

    @Override
    public Zona buscarZonaNombre(String nombre) {
        QueryBuilder<Zona> builder = zonaBox.query().equal(Zona_.nombre,nombre);
        Query<Zona> query = builder.build();
        Zona zona = query.findUnique();
        return zona;
    }

    @Override
    public Zona buscarZonaId(long id) {
        QueryBuilder<Zona> builder = zonaBox.query().equal(Zona_.id,id);
        Query<Zona> query = builder.build();
        Zona zona = query.findUnique();

        return zona;
    }

    @Override
    public Zona obtenerZona(Producto producto) {
        Zona zona=producto.zona.getTarget();
        return zona;
    }


    @Override
    public boolean agregarZona(Zona zona) {
        zonaBox.put(zona);
        return true;
    }

    @Override
    public boolean actualizarZona(Zona zona) {
        zonaBox.put(zona);
        return true;
    }

    @Override
    public List<Zona> listarZonaDiferencia() {
        QueryBuilder<Zona> builder = zonaBox.query();
        builder.notEqual(Zona_.estado, 0);//excluye eliminados
        List<Zona> zonaList = builder.build().find();
        return zonaList;
    }



    @Override
    public void calcularDiferenciaZona() {
        List<Zona>zonaList=listarZona();
        for (int i=0;i<zonaList.size();i++){
            Zona zona=buscarZonaId(zonaList.get(i).getId());
            IProducto iProducto=new SqliteProducto(context);
            List<Producto>productoList=iProducto.listarProductoDiferenciaZona(zona.getId());
            if(productoList.isEmpty()){
                zona.setEstado(0);//zona sin diferencia
            }else{
                zona.setEstado(1);
            }
            actualizarZona(zona);
        }
    }

    @Override
    public boolean deleteAll() {
        zonaBox.removeAll();
        return true;
    }
}
