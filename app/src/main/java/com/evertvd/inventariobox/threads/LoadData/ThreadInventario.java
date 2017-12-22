package com.evertvd.inventariobox.threads.LoadData;

import android.content.Context;
import android.util.Log;
import android.app.Activity;

import com.evertvd.inventariobox.Interfaces.IEmpresa;
import com.evertvd.inventariobox.Interfaces.IInventario;
import com.evertvd.inventariobox.Interfaces.IZona;
import com.evertvd.inventariobox.modelo.Empresa;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.sqlite.SqliteEmpresa;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.utils.Utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class ThreadInventario extends Thread {
    private String path;
    Context context;

    public ThreadInventario(ThreadGroup nombreGrupo, String nombreHilo, String path, Context context) {
        this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();

            leerArchivo();


            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloCC", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    //Thread.sleep(100);
                    //context.getApplicationContext().startActivity();
                    //Toast.makeText(context,total.getTotal()+"registros Cargados",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    return;
                }
            }


    private void leerArchivo() {
        try {
            String file = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
            Log.e("NAMEFILE", file);
            String[] valores = file.split("-");
            int codEmpresa = Integer.parseInt(valores[1]);
            int numInventario = Integer.parseInt(valores[2]);
            String equipo = valores[3].substring(0, valores[3].length());
            //String equipo = valores[3].substring(0, valores[3].length() - 4);
            //4:longitud de ".csv"
            int numEquipo = Integer.parseInt(equipo);
            IEmpresa iEmpresa=new SqliteEmpresa(context);
            Empresa empresa=iEmpresa.obtenerEmpresa(codEmpresa);
            Log.e("EMP", String.valueOf(empresa.getCodigo()));
            Inventario inventario=new Inventario();
            inventario.setNumEquipo(numEquipo);
            inventario.setNumInventario(numInventario);
            inventario.setFechaApertura(Utils.fechaActual());
            inventario.empresa.setTarget(empresa);
            //inventario.setEmpresa_id(iEmpresa.obtenerEmpresa(codEmpresa).getId());
            inventario.setContexto(0);//inventario
            //inventario.setEstado(0);//abierto
            IInventario iInventario=new SqliteInventario(context);
            iInventario.agregarInventario(inventario);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
