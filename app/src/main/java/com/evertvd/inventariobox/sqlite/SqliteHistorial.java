package com.evertvd.inventariobox.sqlite;

import android.content.Context;

import com.evertvd.inventariobox.interfaces.IHistorial;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Historial;
import com.evertvd.inventariobox.modelo.Historial_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by evertvd on 20/12/2017.
 */

public class SqliteHistorial implements IHistorial {
    private Box<Historial> historialBox;
    private Query<Historial> historialQuery;
    BoxStore boxStore;
    private Context context;
    //private Activity context;
    // do this once, for example in your Application class

    public SqliteHistorial(Context context){
        this.context=context;
        //App app=new App();
        // boxStore=app.getBoxStore();
        boxStore = ((App)context.getApplicationContext()).getBoxStore();
        //this.boxStore=boxStore;
        historialBox = boxStore.boxFor(Historial.class);
    }


    @Override
    public Historial obtenerHisotorial(long idHistorial) {
        QueryBuilder<Historial> builder = historialBox.query().equal(Historial_.id,idHistorial);
        Query<Historial> query = builder.build();
        Historial historial = query.findUnique();
        return historial;
    }

    @Override
    public boolean agregarHistorial(Conteo conteo, Historial historial) {
        conteo.historial.add(historial);
        SqliteConteo sqliteConteo=new SqliteConteo(context);
        sqliteConteo.getConteoBox().put(conteo);
        return true;
    }

    @Override
    public List<Historial> listarHisotorial(Conteo conteo) {
         /*List<Conteo>productoList=producto.conteo;*/
       // List<Historial>historialList=conteo.historial;
        //Log.e("ZIE H", String.valueOf(historialList.size()));
        QueryBuilder<Historial> builder = historialBox.query();
        builder.equal(Historial_.conteoId, conteo.getId());//excluye eliminados
        List<Historial> historialList = builder.build().find();

        return historialList;

    }

    @Override
    public void eliminarHistorial(Historial historial) {
        historialBox.remove(historial);
    }

    @Override
    public Historial ultimoHistorial(Conteo conteo) {
        Historial historial=new Historial();
        List<Historial>historialList=listarHisotorial(conteo);
        if(!historialList.isEmpty()){
            for (int i=0;i<historialList.size();i++){
                if(i==historialList.size()-1){
                    historial=historialList.get(i);
                }
            }
        }else{
            historial=null;
        }
        return historial;
    }

    public void deleteAll(){
        historialBox.removeAll();
    }
}
