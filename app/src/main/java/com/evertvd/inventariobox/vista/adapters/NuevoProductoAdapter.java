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
import com.evertvd.inventariobox.vista.activitys.ActivityConteo;
import com.evertvd.inventariobox.vista.dialogs.DialogHistorialConteo;

import java.util.List;


/**
 * Created by evertvd on 24/11/2017.
 */

public class NuevoProductoAdapter extends RecyclerView.Adapter<NuevoProductoAdapter.MyViewHolder> {
    private Context context;
    private List<Conteo> conteoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCantidad;
        TextView txtFechaRegistro;
        TextView txtEstado;
        Context context1;
        public ImageView img_delete;
        public RelativeLayout view_content, view_edit, view_delete, view_validate;
        private LinearLayout view_estado;


        public MyViewHolder(View view) {
            super(view);
            context1=context;
            //views
            txtCantidad = view.findViewById(R.id.txtCantidad);
            txtFechaRegistro = view.findViewById(R.id.txtFechaRegistro);
            txtEstado = view.findViewById(R.id.txtEstado);
            view_estado=(LinearLayout)view.findViewById(R.id.view_estado);
            img_delete=(ImageView)view.findViewById(R.id.delete_icon);

            //backgrounds
            //view = view.findViewById(R.id.view_content);
            view_edit = view.findViewById(R.id.view_edit);
            view_delete = view.findViewById(R.id.view_eliminar);
            view_validate = view.findViewById(R.id.view_validate);
            view_content=view.findViewById(R.id.view_content);


            view.setOnLongClickListener(new View.OnLongClickListener() {
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

        }
    }


    public NuevoProductoAdapter(Context context, List<Conteo> cartList) {
        this.context = context;
        this.conteoList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conteo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Conteo conteo = conteoList.get(position);

        holder.txtCantidad.setText(String.valueOf(conteo.getCantidad()));
        holder.txtFechaRegistro.setText(conteo.getFechaRegistro());
        if(conteo.getProducto().getTarget().getZona().getTarget().getInventario().getTarget().getContexto()==0){
            holder.view_estado.setVisibility(View.GONE);
        }else{
            holder.view_estado.setVisibility(View.VISIBLE);
            if(conteo.getValidado()==0){
                holder.txtEstado.setText("Por validar");
            }else{
                holder.txtEstado.setText("Validado");
            }
        }
    }

    @Override
    public int getItemCount() {
        return conteoList.size();
    }

    public void removeItem(int position) {
        conteoList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Conteo item, int position) {
        conteoList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void modify(Conteo item, int position){
        notifyItemChanged(position,item);
        notifyItemChanged(position);
    }

    public void addItem(Conteo conteo){
        conteoList.add(conteo);
        notifyItemInserted(conteoList.size());
    }

    public void restorePosition(int position){
        notifyItemChanged(position);
    }
}


