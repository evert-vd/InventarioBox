package com.evertvd.inventariobox.sqlite;

import android.content.Context;

import com.evertvd.inventariobox.Interfaces.IConteo;
import com.evertvd.inventariobox.Interfaces.IProducto;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Conteo_;
import com.evertvd.inventariobox.modelo.Historial;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by evertvd on 19/12/2017.
 */

public class SqliteConteo implements IConteo {
    private Box<Conteo> conteoBox;
    private Query<Conteo> conteoQuery;
    BoxStore boxStore;
    private  Context context;
    //private Activity context;
    // do this once, for example in your Application class

    public SqliteConteo(Context context){
            this.context=context;
        //App app=new App();
        // boxStore=app.getBoxStore();
        boxStore = ((App)context.getApplicationContext()).getBoxStore();
        //this.boxStore=boxStore;
        conteoBox = boxStore.boxFor(Conteo.class);
    }


    @Override
    public Conteo obtenerConteo(long idConteo) {
        QueryBuilder<Conteo> builder = conteoBox.query().equal(Conteo_.id,idConteo);
        Query<Conteo> query = builder.build();
        Conteo conteo = query.findUnique();
        return conteo;
    }

    @Override
    public boolean agregarConteo(Producto producto, Conteo conteo) {
        producto.conteo.add(conteo);
        SqliteProducto sqliteProducto=new SqliteProducto(context);
        sqliteProducto.getProductoBox().put(producto);
        return true;
    }

    @Override
    public List<Conteo> listarConteo(Producto producto) {
        /*List<Conteo>productoList=producto.conteo;*/
        QueryBuilder<Conteo> builder = conteoBox.query();
        builder.notEqual(Conteo_.estado, -1);//excluye eliminados
        builder.equal(Conteo_.productoId,producto.getId());
        List<Conteo> conteoList = builder.build().find();
        return conteoList;
    }

    @Override
    public List<Conteo> listarAllConteo() {
        List<Conteo>conteoList=conteoBox.getAll();
        return conteoList;
    }

    @Override
    public void actualizarConteo(Conteo conteo) {
        conteoBox.put(conteo);
    }

    @Override
    public void eliminarConteo(Conteo conteo) {
        conteoBox.put(conteo);
    }

    @Override
    public int obtenerTotalConteo(Producto producto) {
        List<Conteo>conteoList=listarConteo(producto);
        int conteo=0;
        for (int i=0;i<conteoList.size();i++){
            conteo+=conteoList.get(i).getCantidad();
        }
        return conteo;
    }

    @Override
    public int obtenerTotalConteoZona(Zona zona) {
         int conteoZona=0;
         IProducto iProducto=new SqliteProducto(context);
         List<Producto>productoList=iProducto.listarProductoZona(zona);
         for (int i=0;i<productoList.size();i++){
             conteoZona+=obtenerTotalConteo(productoList.get(i));
         }
        return conteoZona;
    }

    @Override
    public int totalConteo() {
        List<Conteo> conteoList=listarAllConteo();
        int totalConteo=0;
        for (int i=0;i<conteoList.size();i++){
            totalConteo+=conteoList.get(i).getCantidad();
        }
        return totalConteo;
    }

    @Override
    public List<Conteo> listarConteoPorValidar() {
        QueryBuilder<Conteo> builder = conteoBox.query();
        builder.equal(Conteo_.validado, 0);//excluye eliminados
        List<Conteo> conteoList = builder.build().find();
        return conteoList;
    }

    @Override
    public Conteo ultimoConteo(Producto producto){
        List<Conteo>conteoList=listarConteo(producto);
        Conteo conteo=new Conteo();
        if(!conteoList.isEmpty()){
            for (int i=0;i<conteoList.size();i++){
                if(i==conteoList.size()-1){
                    conteo=conteoList.get(i);
                }
            }
        }
        return conteo;
    }

    public void deleteAll(){
        conteoBox.removeAll();
    }

    public Box<Conteo> getConteoBox() {
        return conteoBox;
    }

    public void setConteoBox(Box<Conteo> conteoBox) {
        this.conteoBox = conteoBox;
    }
}
