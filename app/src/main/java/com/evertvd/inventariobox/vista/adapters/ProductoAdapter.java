package com.evertvd.inventariobox.vista.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.inventariobox.interfaces.IConteo;
import com.evertvd.inventariobox.interfaces.ItemClickListener;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.vista.activitys.ActivityConteo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by evertvd on 30/01/2017.
 */

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> implements ItemClickListener {

    private Context contexto;
    private List<Producto> productoList;
    private Activity activity;

    public ProductoAdapter(List<Producto> productoList, Context contexto, Activity activity) {
        this.contexto = contexto;
        this.activity=activity;
        this.productoList = productoList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                         int viewType) {
        // create a new view

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_producto, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        //ViewHolder vh = new ViewHolder(v);
        //return vh;
        return new ViewHolder(v, this);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            if(productoList.get(position).getTipo().equalsIgnoreCase("App")){
                holder.codigo.setText(String.valueOf("NN"+productoList.get(position).getCodigo()));
            }else{
                holder.codigo.setText(String.valueOf(productoList.get(position).getCodigo()));
            }
            holder.descripcion.setText(productoList.get(position).getDescripcion());
            holder.zona.setText(productoList.get(position).getZona().getTarget().getNombre());
            IConteo iConteo=new SqliteConteo(contexto);
            //holder.cantidad.setText(String.valueOf(iConteo.obtenerTotalConteo(productoList.get(position))));//cambiar por cantidad contada
            holder.cantidad.setText(String.valueOf(iConteo.obtenerTotalConteo(productoList.get(position)))+ "("+productoList.get(position).getStock()+")");//cambiar por cantidad contada
            //holder.cantidad.setText(String.valueOf(productoList.get(position).getStock()));


        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
            Toast.makeText(contexto, "Error al cargar los datos al adaptador", Toast.LENGTH_SHORT).show();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.e(String.valueOf(items.size()),"errpr");
        return productoList.size();

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(contexto, ActivityConteo.class);
        intent.putExtra("id", productoList.get(position).getId());
        contexto.startActivity(intent);
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView codigo, stock, estado;
        TextView descripcion;
        TextView zona;
        TextView cantidad;//se está poniendo la cantidad

        //ImageView img;
        TextView idProducto;
        public ItemClickListener listener;

        public ViewHolder(View view,ItemClickListener listener) {
            super(view);
            this.codigo = (TextView) view.findViewById(R.id.codigo);
            this.descripcion = (TextView) view.findViewById(R.id.descripcion);
            this.cantidad = (TextView) view.findViewById(R.id.cantidad);
            this.zona = (TextView) view.findViewById(R.id.nombreZona);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());

        }
    }

    //metodo que asigna la nueva lista filtrada al recycler
    public void setFilter(List<Producto> listaProducto) {
        this.productoList = new ArrayList<>();
        this.productoList.addAll(listaProducto);
        notifyDataSetChanged();
    }

}
