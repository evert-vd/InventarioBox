package com.evertvd.inventariobox.vista.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.inventariobox.interfaces.IConteo;
import com.evertvd.inventariobox.interfaces.IHistorial;
import com.evertvd.inventariobox.interfaces.IProducto;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Conteo;
import com.evertvd.inventariobox.modelo.Historial;
import com.evertvd.inventariobox.modelo.Producto;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.sqlite.SqliteHistorial;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.utils.Utils;
import com.evertvd.inventariobox.vista.adapters.ConteoAdapter;
import com.evertvd.inventariobox.vista.adapters.RecyclerItemTouchHelperConteo;

import java.util.List;


public class ActivityConteo extends AppCompatActivity implements View.OnClickListener,RecyclerItemTouchHelperConteo.RecyclerItemTouchHelperListener{

    int request_code = 1;
    private FloatingActionButton btnAgregar;
    private RecyclerView recyclerView;
    private View view;
    private TextView abTitulo, abCodigo, abZona,abCantidad;

    private List<Conteo> conteoList;
    private Producto producto;
    private ConteoAdapter mAdapter;
    private long id;

    //dialog view
    TextView txtSinRegistros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteos);
        getSupportActionBar().setElevation(0);
        view=(View)findViewById(R.id.activity_conteos);
        recyclerView =findViewById(R.id.recycler_view_detalle);

        id=getIntent().getExtras().getLong("id");
        IProducto iProducto=new SqliteProducto(this);
        producto=iProducto.obtenerProducto(id);

        IConteo iConteo=new SqliteConteo(this);
        conteoList=iConteo.listarConteo(producto);

        setTitle("Registro de conteos");

        btnAgregar=(FloatingActionButton)findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        if(producto.getZona().getTarget().getInventario().getTarget().getContexto()==2){
            btnAgregar.setVisibility(View.GONE);
        }


        abCodigo = (TextView) findViewById(R.id.txtAbCodigo);
        if(producto.getTipo().equalsIgnoreCase("App")){
            abCodigo.setText(String.valueOf("NN"+producto.getCodigo()));
        }else{
            abCodigo.setText(String.valueOf(producto.getCodigo()));
        }
        abZona = (TextView) findViewById(R.id.txtAbZona);
        abZona.setText(producto.getZona().getTarget().getNombre());
        //titulo del actionBar
        abTitulo = (TextView) findViewById(R.id.txtAbDescripcion);
        abTitulo.setText(producto.getDescripcion());

        abCantidad = (TextView) findViewById(R.id.txtAbCantidad);
        abCantidad.setText(Utils.formatearNumero(iConteo.obtenerTotalConteo(producto)));

        txtSinRegistros=(TextView)findViewById(R.id.txtSinRegistros);

        mAdapter = new ConteoAdapter(this, conteoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        if(!conteoList.isEmpty()){
            txtSinRegistros.setVisibility(View.GONE);
        }else{
            txtSinRegistros.setVisibility(View.VISIBLE);
        }

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        if(producto.getZona().getTarget().getInventario().getTarget().getContexto()!=2){
            //deslizar solo en inventario y diferencia
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperConteo(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        if (viewHolder instanceof ConteoAdapter.MyViewHolder) {
            Log.e("direccion", String.valueOf(direction));
            final Conteo conteoTemp = conteoList.get(viewHolder.getAdapterPosition());
            if (conteoList.get(position).getValidado() != 1) {
                if (direction <= 4) {
                    //eliminar conteo
                    if (((ConteoAdapter.MyViewHolder) viewHolder).view_delete.getVisibility() == View.VISIBLE) {
                        //eliminacion
                        final IConteo iConteo = new SqliteConteo(getApplicationContext());
                        // backup of removed item for undo purpose
                        //final Conteo deletedItem = conteoList.get(viewHolder.getAdapterPosition());

                        final int deletedIndex = viewHolder.getAdapterPosition();
                        final int cantidadTemp = conteoTemp.getCantidad();
                        final int cantidadAbTemp = Integer.parseInt(abCantidad.getText().toString());
                        conteoTemp.setCantidad(0);
                        conteoTemp.setValidado(1);
                        conteoTemp.setEstado(-1);//eliminado
                        //guardar historial
                        guardarHistorial(conteoTemp);
                        iConteo.eliminarConteo(conteoTemp);
                        actualizarTotalAb(iConteo.obtenerTotalConteo(producto));
                        mAdapter.removeItem(viewHolder.getAdapterPosition());

                        Snackbar snackbar = Snackbar.make(view, "Conteo eliminado", Snackbar.LENGTH_LONG);
                        snackbar.setAction("DESHACER", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // undo is selected, restore the deleted item
                                conteoTemp.setEstado(0);
                                conteoTemp.setValidado(0);
                                conteoTemp.setCantidad(cantidadTemp);
                                iConteo.actualizarConteo(conteoTemp);
                                //eliminar historial
                                eliminarHistorial(conteoTemp);
                                //Log.e("cantidad", String.valueOf(iConteo.obtenerTotalConteo(producto)));
                                actualizarTotalAb(cantidadAbTemp);
                                mAdapter.restoreItem(conteoTemp, deletedIndex);

                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();

                    } else if (((ConteoAdapter.MyViewHolder) viewHolder).view_validate.getVisibility() == View.VISIBLE) {
                        //validar
                        final IConteo iConteo = new SqliteConteo(getApplicationContext());
                        conteoTemp.setValidado(1);//validado
                        iConteo.actualizarConteo(conteoTemp);
                        Snackbar snackbar = Snackbar.make(view, "Conteo validado", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.YELLOW);
                        mAdapter.notifyItemChanged(position);
                        snackbar.show();
                    }


                } else {
                    Intent i = new Intent(this, ActivityCalculadora.class);
                    i.putExtra("cantidad", conteoTemp.getCantidad());
                    i.putExtra("posicion", position);
                    i.putExtra("idConteo", conteoTemp.getId());
                    i.putExtra("idProducto", producto.getId());
                    startActivityForResult(i, request_code);

                }
            } else {
                mAdapter.restorePosition(position);
                Snackbar snackbar = Snackbar.make(view, "Este conteo ya está validado", Snackbar.LENGTH_LONG);
                snackbar.setAction("Volver a validar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item
                        conteoTemp.setValidado(0);
                        IConteo iConteo = new SqliteConteo(getApplicationContext());
                        iConteo.actualizarConteo(conteoTemp);
                        mAdapter.notifyItemChanged(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnAgregar){
            /*DialogFragment dialogFragment = new RegistrarConteo();
            dialogFragment.show(getFragmentManager(), "dialogoRegistrar");*/
            Intent i = new Intent(this, ActivityCalculadora.class);
            i.putExtra("cantidad", 0);
            i.putExtra("posicion", -1);//posicion del item del adapter(nuevo:-1, Modificacion:posicion)
            i.putExtra("idProducto", producto.getId());//nuevo producto
            //i.putExtra("idConteo", 0);//idConteo Null
            startActivityForResult(i, request_code);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //poner dentro de un try catch
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            IConteo iConteo = new SqliteConteo(this);
            int posicion = intent.getIntExtra("posicion",-1);
            if(posicion==-1) {
                //NUEVO CONTEO
                    actualizarTotalAb(iConteo.obtenerTotalConteo(producto));
                    //actualizacion del adapter
                    actualizarAdapter();
                    txtSinRegistros.setVisibility(View.GONE);
                    //mAdapter.addItem(conteo);
                    Toast.makeText(this, "Conteo Registrado", Toast.LENGTH_SHORT).show();
            }else{
                actualizarTotalAb(iConteo.obtenerTotalConteo(producto));
                actualizarAdapter();
                Toast.makeText(this, "Conteo Editado"+ posicion, Toast.LENGTH_SHORT).show();
            }
        }

        if (resultCode == RESULT_CANCELED) {
            int posicion = intent.getIntExtra("posicion",-1);
            if(posicion!=-1){
                mAdapter.restorePosition(posicion);
                //Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarHistorial(Conteo conteoTemp){
        IHistorial iHistorial=new SqliteHistorial(this);
        Historial historial=new Historial();
        historial.setCantidad(conteoTemp.getCantidad());
        historial.setFechaRegistro(conteoTemp.getFechaRegistro());
        historial.setFechaModificacion(Utils.fechaActual());
        historial.setObservacion(conteoTemp.getObservacion());
        List<Historial> historialList = iHistorial.listarHisotorial(conteoTemp);
        if (historialList.isEmpty()) {
            historial.setTipo(1);//una modificacion
        } else {
            historial.setTipo(2);//mas de una modificacion
        }
        iHistorial.agregarHistorial(conteoTemp,historial);
    }

    private void eliminarHistorial(Conteo conteoTemp){
        IHistorial iHistorial=new SqliteHistorial(this);
        if(iHistorial.ultimoHistorial(conteoTemp)!=null){
            iHistorial.eliminarHistorial(iHistorial.ultimoHistorial(conteoTemp));
        }
    }



    private void actualizarTotalAb(int totalConteo){
        abCantidad.setText(String.valueOf(totalConteo));
    }

    private void actualizarAdapter(){
        IConteo iConteo=new SqliteConteo(this);
        conteoList=iConteo.listarConteo(producto);
        mAdapter = new ConteoAdapter(this, conteoList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


   }
