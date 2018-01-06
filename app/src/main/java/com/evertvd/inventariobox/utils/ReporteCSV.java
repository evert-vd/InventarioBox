package com.evertvd.inventariobox.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.csvreader.CsvWriter;
import com.evertvd.inventariobox.interfaces.IConteo;
import com.evertvd.inventariobox.interfaces.IHistorial;
import com.evertvd.inventariobox.interfaces.IInventario;
import com.evertvd.inventariobox.interfaces.IProducto;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Historial;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.sqlite.SqliteHistorial;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteProducto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by evertvd on 17/08/2017.
 */

public class ReporteCSV {


    private String nombreArchivo(Context context, int numReporte, String tipoReporte) {
        if (numReporte == 1 && tipoReporte.equalsIgnoreCase("resumido")) {
            return context.getString(R.string.file_resumido) + "(1)" + context.getString(R.string.extension_file);
        } else if (numReporte == 1 && tipoReporte.equalsIgnoreCase("detallado")) {
            return context.getString(R.string.file_detallado) + "(1)" + context.getString(R.string.extension_file);
        } else if (numReporte == 0 && tipoReporte.equalsIgnoreCase("resumido")) {
            return context.getString(R.string.file_resumido) + context.getString(R.string.extension_file);
        } else {
            return context.getString(R.string.file_detallado) + context.getString(R.string.extension_file);
        }
    }

    public void Resumido(Context context, int numReporte) {
        if (MainDirectorios.comprobarMemoriaSD()) {
            try {
                IProducto iProducto = new SqliteProducto(context);
                List<Producto> productoList = iProducto.listarProductoSistema();
                MainDirectorios.crearDirectorioApp(context);
                String nombreReporte = "";
                if (numReporte == 0) {
                    nombreReporte = nombreArchivo(context, 0, "resumido");
                    MainDirectorios.eliminarFichero(context, nombreReporte);
                } else {
                    nombreReporte = nombreArchivo(context, 1, "resumido");
                    MainDirectorios.eliminarFichero(context, nombreReporte);

                }

                CsvWriter csvOutput = new CsvWriter(new FileWriter(MainDirectorios.obtenerDirectorioApp(context) + "/" + nombreReporte, true), ',');
                for (int i = 0; i < productoList.size(); i++) {
                    csvOutput.write(productoList.get(i).getZona().getTarget().getNombre());
                    csvOutput.write(String.valueOf(productoList.get(i).getCodigo()));
                    csvOutput.write(productoList.get(i).getDescripcion());
                    IConteo iConteo = new SqliteConteo(context);
                    csvOutput.write(String.valueOf(iConteo.obtenerTotalConteo(productoList.get(i))));
                    csvOutput.endRecord();
                }
                csvOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
                //Log.e("ERROR", "error");
            }
        } else {
            Toast.makeText(context, "La memoria SD no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

    public void Detallado(Context context, int numReporte) {
        if (MainDirectorios.comprobarMemoriaSD()) {
            try {
                IInventario iInventario = new SqliteInventario(context);
                Inventario inventario = iInventario.obtenerInventario();

                IHistorial iHistorial = new SqliteHistorial(context);

                IConteo iConteo = new SqliteConteo(context);
                List<Conteo> conteoList = iConteo.listarAllConteo();
                Log.e("SIZE", String.valueOf(conteoList.size()));
                String nombreReporte;
                if (numReporte == 0) {
                    //reemplazar reporte
                    nombreReporte = nombreArchivo(context, 0, "detallado");
                    MainDirectorios.eliminarFichero(context, nombreReporte);
                } else {
                    //nuevo reporte
                    nombreReporte = nombreArchivo(context, 1, "detallado");
                    MainDirectorios.eliminarFichero(context, nombreReporte);
                }

                CsvWriter csvOutput = new CsvWriter(new FileWriter(MainDirectorios.obtenerDirectorioApp(context) + "/" + nombreReporte, true), ',');

                String numeroEquipo = "";
                if (inventario.getNumEquipo() < 10) {
                    numeroEquipo = "0" + String.valueOf(inventario.getNumEquipo());
                } else {
                    numeroEquipo = String.valueOf(inventario.getNumEquipo());
                }

                /*csvOutput.write("REPORTE DETALLADO "+Operaciones.fechaActual().toUpperCase());
                csvOutput.write("\n");
                //empresa.getCodempresa()
                csvOutput.write("Archivo origen: INV-"+String.valueOf(inventario.getEmpresa().getCodempresa())+"-"+inventario.getNuminventario()+"-"+numeroEquipo);
                csvOutput.write("\n");
                csvOutput.write("Equipo Inventariador: "+numeroEquipo);
                csvOutput.write("\n");*/
                //titulos
                csvOutput.write("ZONA");
                csvOutput.write("CODIGO");
                csvOutput.write("DESCRIPCION");
                csvOutput.write("CANTIDAD");
                //csvOutput.write("OBSERVACION");
                csvOutput.write("HISTORIAL");
                csvOutput.write("ESTADO");
                csvOutput.endRecord();

                for (int i = 0; i < conteoList.size(); i++) {
                    csvOutput.write(conteoList.get(i).getProducto().getTarget().getZona().getTarget().getNombre());
                    csvOutput.write(String.valueOf(conteoList.get(i).getProducto().getTarget().getCodigo()));
                    csvOutput.write(conteoList.get(i).getProducto().getTarget().getDescripcion());
                    csvOutput.write(String.valueOf(conteoList.get(i).getCantidad()));
                    //csvOutput.write(String.valueOf(conteoList.get(i).getObservacion()));

                    List<Historial> historialList = iHistorial.listarHisotorial(conteoList.get(i));
                    String historial = "";
                    for (int h = 0; h < historialList.size(); h++) {
                        historial += "[" + historialList.get(h).getObservacion() + "] ";
                    }
                    csvOutput.write(historial);

                    if (conteoList.get(i).getEstado() == -1) {
                        csvOutput.write("Eliminado");
                    } else {
                        if (conteoList.get(i).getProducto().getTarget().getTipo().equalsIgnoreCase("App")) {
                            csvOutput.write("Reg. Aplicacion");
                        } else {
                            csvOutput.write("");
                        }
                    }
                    csvOutput.endRecord();
                }
                csvOutput.close();
                //Toast.makeText(context, "Reporte Detallado exportado correctamente", Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {
                Toast.makeText(context, "Error al generar reporte detallado", Toast.LENGTH_SHORT).show();
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }
        } else {
            Toast.makeText(context, "La memoria SD no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

}





