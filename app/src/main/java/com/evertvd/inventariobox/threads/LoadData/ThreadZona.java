package com.evertvd.inventariobox.threads.LoadData;

import android.content.Context;
import android.util.Log;

import com.csvreader.CsvReader;
import com.evertvd.inventariobox.Interfaces.IInventario;
import com.evertvd.inventariobox.Interfaces.IZona;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.modelo.Zona;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteZona;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class ThreadZona extends Thread {
    private String path;
    Context context;

    public ThreadZona(ThreadGroup nombreGrupo, String nombreHilo, String path, Context context) {
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

            CsvReader activos = new CsvReader(path);
            //activos.readHeaders();
            int i=0;
            while (activos.readRecord()) {
                IZona iZona=new SqliteZona(context);
                if(iZona.buscarZonaNombre(activos.get(0))==null){
                    IInventario iInventario=new SqliteInventario(context);
                    Inventario inventario=iInventario.obtenerInventario();

                    Zona zona=new Zona();
                    zona.setNombre(activos.get(0));
                    zona.inventario.setTarget(inventario);
                    zona.setEstado(1);
                    //Log.e("HILO INV", String.valueOf(inventario.getId()));
                    //zona.setInventario_id(inventario.getId());
                   //con direncia
                    iZona.agregarZona(zona);
                }
            }
            activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
