package com.evertvd.inventariobox.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.evertvd.inventariobox.interfaces.IInventario;
import com.evertvd.inventariobox.interfaces.IProducto;
import com.evertvd.inventariobox.interfaces.IZona;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.utils.Utils;
import com.evertvd.inventariobox.vista.fragments.FragmentZonas;
import com.evertvd.inventariobox.vista.fragments.FrmContainerResumen;


/**
 * Created by evertvd on 18/09/2017.
 */

public class ThreadCierreInventario extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;
    private Context context;
    private int contexto;
    //private IInventario iInventario;
    //private Inventario inventario;
    private FragmentManager fragmentManager;

    //para progresDialog
    public ThreadCierreInventario(ProgressDialog dialog, Context context, FragmentManager fragmentManager, int contexto) {
        //this.activity=activity;
        this.dialog=dialog;
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.contexto=contexto;
    }

    /*
    //para dialog fragment
    public TareaCarga(DialogFragment progress, Context context, String path) {
        this.dialogTask = progress;
        this.context = context;
        this.path=path;
    }
    */

    public void onPreExecute() {
        //aquí se puede colocar código a ejecutarse previo
        //a la operación
        //iInventario=new SqliteInventario(context);
        //inventario=iInventario.obtenerInventario();
        //Log.e("contextThr", String.valueOf(inventario.getContexto()));
        dialog.show();
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        dialog.dismiss();
        abrirFragment();

        }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background
        try{
        calcularDiferencias();
        }catch (Exception e){
        }
        return null;
    }

    private void calcularDiferencias(){
        IProducto iProducto=new SqliteProducto(context);
        iProducto.calcularPoductoDiferencia();//calcula las diferencias en el producto
        IZona iZona=new SqliteZona(context);
        iZona.calcularDiferenciaZona();//calcula las diferencias en la zona
        IInventario iInventario=new SqliteInventario(context);
        Inventario inventario=iInventario.obtenerInventario();

        if(contexto==0){
            inventario.setContexto(1);
        }else if(contexto==1){
            inventario.setContexto(2);
            inventario.setFechaCierre(Utils.fechaActual());
            //inventario.setEstado(1);//cerrado
        }
        iInventario.actualizarInventario(inventario);
    }

    private void abrirFragment(){
        if (contexto==0){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentZonas fragment = new FragmentZonas();
            fragmentTransaction.replace(R.id.contenedor, fragment);
            //setTitle("Inventario "+inventario.getNuminventario());
            fragmentTransaction.commit();
        }else{
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FrmContainerResumen fragment = new FrmContainerResumen();
            fragmentTransaction.replace(R.id.contenedor, fragment);
            //setTitle("Inventario "+inventario.getNuminventario());
            fragmentTransaction.commit();
        }
    }

}