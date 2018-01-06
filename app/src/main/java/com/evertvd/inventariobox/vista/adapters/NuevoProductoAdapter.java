package com.evertvd.inventariobox.vista.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.vista.activitys.ActivityConteo;
import com.evertvd.inventariobox.vista.dialogs.DialogHistorialConteo;

import java.util.List;


/**
 * Created by evertvd on 24/11/2017.
 */

public class NuevoProductoAdapter extends RecyclerView.Adapter<NuevoProductoAdapter.MyViewHolder> {
    private Context context;
    private List<Producto> productoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCodigo;
        TextView txtZona;
        TextView txtDescripcion;
        public ImageView img_delete;
        public RelativeLayout view_content, view_delete;


        public MyViewHolder(View view) {
            super(view);
            //views
            txtCodigo = view.findViewById(R.id.txtCodigo);
            txtZona = view.findViewById(R.id.txtZona);
            txtDescripcion = view.findViewById(R.id.txtDescripcion);

            img_delete=(ImageView)view.findViewById(R.id.delete_icon);

            //backgrounds
            //view = view.findViewById(R.id.view_content);
            //view_edit = view.findViewById(R.id.view_edit);
            view_delete = view.findViewById(R.id.view_eliminar);
            view_content=view.findViewById(R.id.view_content);


            /*view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position=getLayoutPosition();
                    Conteo conteo=conteoList.get(position);
                    FragmentManager fragmentManager = ((ActivityConteo)context).getFragmentManager();
                    DialogHistorialConteo historial = new DialogHistorialConteo();
                    Bundle data = new Bundle();
                    data.putLong("idconteo", conteo.getId());
                    historial.setArguments(data);
                    historial.show(fragmentManager, "dialogo conteo");
                    return true;// returning true instead of false, works for me
                }
            });
            */
        }
    }


    public NuevoProductoAdapter(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nuevo_producto, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Producto producto = productoList.get(position);
        if(producto.getTipo().equalsIgnoreCase("App")){
            holder.txtCodigo.setText("NN"+producto.getCodigo());
        }else{
            holder.txtCodigo.setText(String.valueOf(producto.getCodigo()));
        }
        holder.txtZona.setText(producto.getZona().getTarget().getNombre());
        holder.txtDescripcion.setText(producto.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public void removeItem(int position) {
        productoList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Producto item, int position) {
        productoList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void modify(Conteo item, int position){
        notifyItemChanged(position,item);
        notifyItemChanged(position);
    }

    public void addItem(Producto producto){
        productoList.add(producto);
        notifyItemInserted(productoList.size());
    }

    public void restorePosition(int position){
        notifyItemChanged(position);
    }
}


