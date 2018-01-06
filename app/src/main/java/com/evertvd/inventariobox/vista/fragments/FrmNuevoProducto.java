package com.evertvd.inventariobox.vista.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.inventariobox.interfaces.IProducto;
import com.evertvd.inventariobox.interfaces.IZona;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.vista.adapters.NuevoProductoAdapter;
import com.evertvd.inventariobox.vista.adapters.RecyclerItemTouchHelperProducto;
import com.evertvd.inventariobox.vista.dialogs.DialogNuevoProducto;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrmNuevoProducto extends Fragment implements View.OnClickListener, DialogNuevoProducto.InterfaceDialogNuevoProducto, RecyclerItemTouchHelperProducto.RecyclerItemTouchHelperListener {
    private List<Producto> productoList;
    View view;
    private TextView txtSinRegistros;
    private RecyclerView recyclerView;
    FragmentManager fragmentManager;
    private NuevoProductoAdapter adapter;

    public FrmNuevoProducto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nuevo_producto, container, false);

        agregarFab();

        IProducto iProducto = new SqliteProducto(getActivity());
        productoList = iProducto.listarProductoApp();

        // Inflate the layout for this fragment
        txtSinRegistros = (TextView) view.findViewById(R.id.txtSinRegistros);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_nuevo_producto);

        // Layout Managers:
        adapter = new NuevoProductoAdapter(getActivity(), productoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        if (!productoList.isEmpty()) {
            txtSinRegistros.setVisibility(View.GONE);
        } else {
            txtSinRegistros.setVisibility(View.VISIBLE);
        }

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param

        //swipe solo a la izquierda
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperProducto(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNuevoProducto) {
            FragmentManager fm = getFragmentManager();
            try {
                DialogNuevoProducto registrarNuevoProducto = new DialogNuevoProducto();
                registrarNuevoProducto.setTargetFragment(this, 0);
                registrarNuevoProducto.setCancelable(false);
                registrarNuevoProducto.show(fm, "nuevo producto");
            } catch (Exception e) {
                Log.e("error", e.toString());
            }
        }
    }

    public void agregarFab() {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btnNuevoProducto);
        fab.setOnClickListener(this);
    }

    @Override
    public void addNuevoProducto(Producto producto) {
        if (producto != null) {
            IProducto iProducto = new SqliteProducto(getActivity());
            iProducto.agregarProducto(producto);

            IZona iZona = new SqliteZona(getActivity());
            iZona.calcularDiferenciaZona();//vuelve a calcular la diferencia para que aparezca el nuevo producto en caso la zona ya esté sin diferencia

            adapter.addItem(producto);
            txtSinRegistros.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Producto Agregado", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NuevoProductoAdapter.MyViewHolder) {
            final Producto productoTemp = productoList.get(viewHolder.getAdapterPosition());
            final IProducto iProducto = new SqliteProducto(getActivity());
            iProducto.eliminarProducto(productoTemp);
            productoList=iProducto.listarProductoApp();
            if(productoList.isEmpty()){
                txtSinRegistros.setVisibility(View.VISIBLE);
            }else{
                txtSinRegistros.setVisibility(View.GONE);
            }
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(view, "Producto eliminado", Snackbar.LENGTH_SHORT);
            //snackbar.setAction("UNDO", new View.OnClickListener() {

            //snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            adapter.removeItem(viewHolder.getAdapterPosition());
        }
    }
}

