package com.evertvd.inventariobox.sqlite;

import com.evertvd.inventariobox.interfaces.IInventario;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Inventario;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by evertvd on 19/12/2017.
 */

public class SqliteInventario implements IInventario {
    private Box<Inventario> inventarioBox;
    private Query<Inventario> inventarioQuery;
    private BoxStore boxStore;
    private Activity activity;
    // do this once, for example in your Application class

    public SqliteInventario(Context context){
        //this.activity=activity;
        boxStore=((App)context.getApplicationContext()).getBoxStore();
        inventarioBox = boxStore.boxFor(Inventario.class);
        inventarioQuery = inventarioBox.query().build();
    }


    @Override
    public Inventario obtenerInventario() {
        Inventario inventario = inventarioQuery.findUnique();
        return inventario;
    }

    @Override
    public boolean agregarInventario(Inventario inventario) {
        inventarioBox.put(inventario);
        return true;
    }

    @Override
    public boolean actualizarInventario(Inventario inventario) {
        inventarioBox.put(inventario);
        Inventario inventario1=obtenerInventario();
        Log.e("CTX SQL", String.valueOf(inventario1.getContexto()));
        return true;
    }

    @Override
    public boolean deleteAll() {
       inventarioBox.removeAll();
        return true;
    }
}
