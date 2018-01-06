package com.evertvd.inventariobox.vista.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Historial;

import java.util.List;

/**
 * Created by evertvd on 16/08/2017.
 */

public class HistorialConteoAdapter extends RecyclerView.Adapter<HistorialConteoAdapter.ViewHolder> {

    private Context contexto;
    private List<Historial> historialList;
    //private Activity activity;

    public HistorialConteoAdapter(List<Historial> historialList, Context contexto) {
        this.contexto = contexto;
        this.historialList = historialList;
        //this.activity=activity;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        TextView txtTipo, txtOperacion;


        public ViewHolder(View v) {
            super(v);

            this.txtOperacion = (TextView) v.findViewById(R.id.txtOperacion);
            this.txtTipo = (TextView) v.findViewById(R.id.txtTipo);
            //this.txtConteo=(TextView)v.findViewById(R.id.txtConteo);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public HistorialConteoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                      int viewType) {
        // create a new view

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_historial, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        //return vh;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HistorialConteoAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Tener cuidado con los tipos de datos que se almaccena, debemos parsear todo a Sring

        try {
            holder.txtOperacion.setText(historialList.get(position).getObservacion());
            if(historialList.get(position).getTipo()==1){
                holder.txtTipo.setText("I");
            }else{
                holder.txtTipo.setText("M");
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
            Toast.makeText(contexto, "Error al cargar los datos al adaptador historial", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        //Log.e(String.valueOf(items.size()),"errpr");
        return historialList.size();

    }
}
