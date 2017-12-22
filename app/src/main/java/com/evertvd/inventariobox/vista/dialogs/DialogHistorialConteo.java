package com.evertvd.inventariobox.vista.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.evertvd.inventariobox.Interfaces.IConteo;
import com.evertvd.inventariobox.Interfaces.IHistorial;
import com.evertvd.inventariobox.Interfaces.IProducto;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Historial;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.sqlite.SqliteHistorial;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.utils.Utils;
import com.evertvd.inventariobox.vista.adapters.HistorialConteoAdapter;
import com.evertvd.inventariobox.vista.adapters.ZonasAdapter;

import java.util.List;

/**
 * Created by evertvd on 16/08/2017.
 */

public class DialogHistorialConteo extends DialogFragment implements View.OnClickListener {


        //private Button btnAceptar;
        private TextView txtSinHistorial, txtConteoActual;
        private long idconteo;
        private RecyclerView.LayoutManager lManager;
        private HistorialConteoAdapter adapter;

        public DialogHistorialConteo() {
            //this.idProducto=idProducto;
        }

    public static DialogHistorialConteo newInstance(int num) {
        DialogHistorialConteo dialogFragment = new DialogHistorialConteo();
        /*Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);*/

        return dialogFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        idconteo=getArguments().getLong("idconteo");
        return crearModificarCantidad();
    }

    /**
     * Crea un diÃƒÂ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃƒÂ¡logo
     */
    public AlertDialog crearModificarCantidad() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_historial, null);
        txtSinHistorial=(TextView)v.findViewById(R.id.txtSinHistorial);
        txtConteoActual=(TextView)v.findViewById(R.id.txtConteoActual);
        //lista de objetos
        IConteo iConteo=new SqliteConteo(getActivity());
        Conteo conteo=iConteo.obtenerConteo(idconteo);

        txtConteoActual.setText("Conteo actual: "+Utils.formatearNumero(conteo.getCantidad())+" Und.");

        IHistorial iHistorial=new SqliteHistorial(getActivity());
        List<Historial>historialList=iHistorial.listarHisotorial(conteo);
        if(historialList.isEmpty()){
            txtSinHistorial.setVisibility(View.VISIBLE);
        }else{
            txtSinHistorial.setVisibility(View.GONE);
        }
        /*for(int i=0; i<historialList.size(); i++){
            Log.e("hist", String.valueOf(historialList.get(i).getObservacion()));
        }*/

        RecyclerView recycler_historial = (RecyclerView)v.findViewById(R.id.recycler_historial);
        lManager = new LinearLayoutManager(getActivity());
        recycler_historial.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new HistorialConteoAdapter(historialList, getActivity());
        recycler_historial.setItemAnimator(new DefaultItemAnimator());
        recycler_historial.setAdapter(adapter);
        builder.setView(v);

        //btnAceptar = (Button) v.findViewById(R.id.btnAceptar);
        //btnAceptar.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAceptar) {
            this.dismiss();
        }

    }



}
