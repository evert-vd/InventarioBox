package com.evertvd.inventariobox.sqlite;

import android.content.Context;

import com.evertvd.inventariobox.Interfaces.IConteo;
import com.evertvd.inventariobox.Interfaces.IProducto;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Producto_;
import com.evertvd.inventariobox.modelo.Zona;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by evertvd on 19/12/2017.
 */

public class SqliteProducto implements IProducto {

    private Box<Producto> productoBox;
    private Query<Producto> productoQuery;
    BoxStore boxStore;
    private Context context;
    // do this once, for example in your Application class

    public SqliteProducto(Context context){
        //App app=new App();
        // boxStore=app.getBoxStore();
        boxStore = ((App)context.getApplicationContext()).getBoxStore();
        this.context=context;
        productoBox = boxStore.boxFor(Producto.class);
    }


    @Override
    public Producto obtenerProducto(long idProducto) {
        QueryBuilder<Producto> builder = productoBox.query().equal(Producto_.id,idProducto);
        Query<Producto> query = builder.build();
        Producto producto = query.findUnique();
        return producto;
    }

    @Override
    public void eliminarProducto(Producto producto) {
        //elimina producto tipo App
        productoBox.remove(producto);
    }

    @Override
    public boolean agregarProducto(Producto producto) {
        productoBox.put(producto);
        return true;
    }

    @Override
    public List<Producto> listarProducto() {
        List<Producto>productoList=productoBox.getAll();
        return productoList;
    }

    @Override
    public List<Producto> listarProductoSistema() {
        QueryBuilder<Producto> builder = productoBox.query();
        builder.notEqual(Producto_.tipo, "App");//solo app
        List<Producto> productoList = builder.build().find();
        return productoList;
    }

    @Override
    public List<Producto> listarProductoApp() {
        QueryBuilder<Producto> builder = productoBox.query();
        builder.equal(Producto_.tipo, "App");//solo app
        List<Producto> productoList = builder.build().find();
        return productoList;
    }

    @Override
    public List<Producto> listarProductoZona(Zona zona) {
        List<Producto> productoList = zona.producto;
        return productoList;
    }

    @Override
    public List<Producto> listarProductoZonaResumen(long idZona) {
        QueryBuilder<Producto> builder = productoBox.query();
        builder.equal(Producto_.zonaId, idZona);//solo app
        List<Producto> productoList = builder.build().find();
        return productoList;
    }

    @Override
    public int totalProductosZona(Zona zona) {
        return listarProductoZona(zona).size();
    }

    @Override
    public List<Producto> listarTotalProductoDiferencia() {
        QueryBuilder<Producto> builder = productoBox.query();
        builder.equal(Producto_.estado, 1);//con diferencia
        List<Producto> productoList = builder.build().find();
        return productoList;
    }

    @Override
    public List<Producto> listarProductoDiferenciaZona(long idZona) {
        QueryBuilder<Producto> builder = productoBox.query();
        builder.notEqual(Producto_.estado,0);
        builder.equal(Producto_.zonaId, idZona);//solo app
        List<Producto> productoList = builder.build().find();
        return productoList;
    }


    @Override
    public void calcularPoductoDiferencia() {
        List<Producto> productoList=listarProducto();
        IConteo iConteo=new SqliteConteo(context);
        for (int i=0;i<productoList.size();i++){
            Producto producto=obtenerProducto(productoList.get(i).getId());
            int totalConteo= iConteo.obtenerTotalConteo(producto);
            //Log.e("CONTEO", String.valueOf(conteo));
            if((producto.getStock()-totalConteo)!=0){
                producto.setEstado(1);//con diferencia
            }else{
                producto.setEstado(0);//sin diferencia
                //actualizacion de validado conteo:
                List<Conteo>conteoList=iConteo.listarConteo(producto);
                for (int j=0;j<conteoList.size();j++){
                    Conteo conteo=conteoList.get(j);
                    conteo.setValidado(1);//validado
                    iConteo.actualizarConteo(conteo);
                }
            }
            productoBox.put(producto);
        }
    }

    @Override
    public int ultimoProducto() {
        List<Producto> productoList=listarProducto();
        return (productoList.size());
    }

    @Override
    public boolean deleteAll() {
        productoBox.removeAll();
        return true;
    }

    public Box<Producto> getProductoBox() {
        return productoBox;
    }
}
