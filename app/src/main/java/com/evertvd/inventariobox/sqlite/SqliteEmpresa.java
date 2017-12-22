package com.evertvd.inventariobox.sqlite;

import android.content.Context;

import com.evertvd.inventariobox.Interfaces.IEmpresa;
import com.evertvd.inventariobox.controller.App;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Conteo_;
import com.evertvd.inventariobox.modelo.Empresa;
import com.evertvd.inventariobox.modelo.Empresa_;

import android.app.Activity;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by evertvd on 19/12/2017.
 */

public class SqliteEmpresa implements IEmpresa {
    private Box<Empresa> empresaBox;
    private Query<Empresa> notesQuery;
    BoxStore boxStore;
    //private Activity context;
    // do this once, for example in your Application class

    public SqliteEmpresa(Context context){

        //App app=new App();
        // boxStore=app.getBoxStore();
        boxStore = ((App)context.getApplicationContext()).getBoxStore();
        //this.boxStore=boxStore;
        empresaBox = boxStore.boxFor(Empresa.class);
    }

    @Override
    public List<Empresa> listarEmpresa() {
        List<Empresa> empresaList = empresaBox.getAll();
        return empresaList;
    }

    @Override
    public Empresa obtenerEmpresa(int codigo) {
        QueryBuilder<Empresa> builder = empresaBox.query().equal(Empresa_.codigo,codigo);
        Query<Empresa> query = builder.build();
        Empresa empresa = query.findUnique();
        return empresa;
    }

    @Override
    public boolean agregarEmpresa(Empresa empresa) {
        empresaBox.put(empresa);
        return true;
    }
}
