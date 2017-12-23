package com.evertvd.inventariobox.vista.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.evertvd.inventariobox.Interfaces.IInventario;
import com.evertvd.inventariobox.Interfaces.IProducto;
import com.evertvd.inventariobox.Interfaces.IZona;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.modelo.Zona;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.vista.adapters.ProductoAdapter;
import com.evertvd.inventariobox.vista.adapters.ZonasAdapter;

import java.util.ArrayList;
import java.util.List;


public class ActivityProducto extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ProductoAdapter adapter;
    private Zona zona;
    List<Producto> productoList;
    private RecyclerView recycler;
    private long idZona;
    private RecyclerView.LayoutManager lManager;
    private Inventario inventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Explode explode=new Explode();
            explode.setDuration(1000);
            Fade fade=new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(explode);
            getWindow().setReturnTransition(fade);
        }

        super.onCreate(savedInstanceState);
        //animaciones
        /*Slide slideLeft=new Slide(Gravity.LEFT);
        slideLeft.setDuration(500);
        slideLeft.setInterpolator(new DecelerateInterpolator());

        Slide slideRight=new Slide(Gravity.RIGHT);
        slideRight.setDuration(500);
        slideRight.setInterpolator(new DecelerateInterpolator());

        getWindow().setEnterTransition(slideRight);
        //getWindow().setReenterTransition(slideRight);
        getWindow().setReturnTransition(slideRight);
        getWindow().setAllowEnterTransitionOverlap(false);*/
        setContentView(R.layout.activity_producto);

        idZona=getIntent().getExtras().getLong("id");
        //Toast.makeText(this,"Id:"+idZona, Toast.LENGTH_SHORT).show();

        IInventario iInventario=new SqliteInventario(this);
        inventario=iInventario.obtenerInventario();

        IZona iZona=new SqliteZona(this);
        zona=iZona.buscarZonaId(idZona);

        Log.e("Zona", zona.getNombre());

        IProducto iProducto=new SqliteProducto(this);
        //if(inventario.getContexto()!=2){
            productoList=iProducto.listarProductoZona(zona);
            Log.e("SIZEP", String.valueOf(productoList.size()));
            List<Producto>productoListAll=iProducto.listarProducto();
            for (int i=0;i<productoListAll.size();i++){
                Log.e("PROD ALL", String.valueOf(productoListAll.get(i).getDescripcion()));
            }

        //}else{
           //productoList=iProducto.listarProductoZonaResumen(idZona);
        //}
        setTitle("Prod. en la zona " + zona.getNombre());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); flecha regresar

        recycler = (RecyclerView) findViewById(R.id.listadoProductos);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ProductoAdapter(productoList, this, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());

        if (savedInstanceState != null) {
            Log.e("saved instance", savedInstanceState.toString());
        }

        ((ProductoAdapter) adapter).setOnItemClickListener(new ProductoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ActivityConteo.class);
                intent.putExtra("id", productoList.get(position).getId());
                    startActivity(intent);



            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_productos, menu);
        MenuItem item = menu.findItem(R.id.action_buscar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilter(productoList);
                return true;
            }
        });

        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            List<Producto> listaFiltrada = filter(productoList, newText);
            //aca se envía la nueva lista al recycler
            adapter.setFilter(listaFiltrada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Producto> filter(List<Producto> lista, String query) {
        List<Producto> listaFiltrada = new ArrayList<Producto>();
        try {

            //aca se recibe el parametro de busqueda
            query = query.toLowerCase();
            for (Producto producto : lista) {
                //busqueda por descripcion
                String queryDescripcion = producto.getDescripcion().toLowerCase();
                //busqueda por codigo
                String queryCodigo = String.valueOf(producto.getCodigo()).toLowerCase();
                if (queryDescripcion.contains(query)) {
                    listaFiltrada.add(producto);
                } else if (queryCodigo.contains(query)) {
                    listaFiltrada.add(producto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFiltrada;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IProducto iProducto=new SqliteProducto(this);
        if(inventario.getContexto()!=2){
            productoList=iProducto.listarProductoZona(zona);
        }else{
            productoList=iProducto.listarProductoZonaResumen(idZona);
        }
        adapter = new ProductoAdapter(productoList, this,this);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        //Intent intent = new Intent(this, MainActivity.class);
        //Intent intent=new Intent(view.getContext(), PruebaDetalle.class);
        //intent.putExtra("zona",zona );
        //startActivity(intent);
    }


}
