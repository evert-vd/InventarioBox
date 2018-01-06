package com.evertvd.inventariobox.vista.activitys;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.inventariobox.interfaces.IConteo;
import com.evertvd.inventariobox.interfaces.IEmpresa;
import com.evertvd.inventariobox.interfaces.IHistorial;
import com.evertvd.inventariobox.interfaces.IInventario;
import com.evertvd.inventariobox.interfaces.IProducto;
import com.evertvd.inventariobox.interfaces.IZona;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Empresa;
import com.evertvd.inventariobox.modelo.Inventario;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.sqlite.SqliteEmpresa;
import com.evertvd.inventariobox.sqlite.SqliteHistorial;
import com.evertvd.inventariobox.sqlite.SqliteInventario;
import com.evertvd.inventariobox.sqlite.SqliteProducto;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.utils.MainDirectorios;
import com.evertvd.inventariobox.utils.Utils;
import com.evertvd.inventariobox.vista.fragments.FragmentZonas;
import com.evertvd.inventariobox.vista.fragments.FrmContainerResumen;
import com.evertvd.inventariobox.vista.fragments.FrmNuevoProducto;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MenuItem menuDiferencia, menuInventario, menuResumen, menuNuevoProducto;
    private Menu menuNav;
    private Inventario inventario;
    private TextView txtNumEquipo, txtNumInventario;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Explode explode=new Explode();
            explode.setDuration(600);
            Fade fade=new Fade();
            fade.setDuration(600);
            getWindow().setExitTransition(explode);
            getWindow().setReenterTransition(fade);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //cargarEmpresas();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menuNav = navigationView.getMenu();
        menuInventario = menuNav.findItem(R.id.nav_inventario);
        menuDiferencia = menuNav.findItem(R.id.nav_diferencias);
        menuNuevoProducto=menuNav.findItem(R.id.nav_nuevo_Producto);
        menuResumen = menuNav.findItem(R.id.nav_resumen);


        //Forma de acceder al titulo del header
        View header = navigationView.getHeaderView(0);
        txtNumInventario = (TextView) header.findViewById(R.id.txtNumInventario);
        txtNumEquipo = (TextView) header.findViewById(R.id.txtNumEquipo);

        IEmpresa iEmpresa=new SqliteEmpresa(this);
        List<Empresa> empresaList=iEmpresa.listarEmpresa();
        if(empresaList.isEmpty()){
            cargarEmpresas();
        }
        MainDirectorios.crearDirectorioApp(this);
        IInventario iInventario = new SqliteInventario(this);
        inventario = iInventario.obtenerInventario();

        if(inventario!=null){
            txtNumInventario.setText("Inventario Nro "+ String.valueOf(inventario.getNumInventario()));
            if(inventario.getNumEquipo()<10){
                txtNumEquipo.setText("Equipo "+"0"+ String.valueOf(inventario.getNumEquipo()));
            }else{
                txtNumEquipo.setText("Equipo "+String.valueOf(inventario.getNumEquipo()));
            }
            abrirContexto();
        }else {
            finish();
            startActivity(new Intent(this, Login.class));

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_inventario:
                String titulo=item.getTitle().toString();
                abrirContextoInventario(fragmentTransaction);
                break;
            case R.id.nav_diferencias:
                abrirContextoInventario(fragmentTransaction);
                break;
            case R.id.nav_resumen:
                abrirContextoResumen(fragmentTransaction);
                break;
            case R.id.nav_nuevo_Producto:
                abrirContextoNuevoProducto(fragmentTransaction);
                break;

            case R.id.nav_manual:
                abrirPdf();
                break;
            case R.id.nav_cerrarSesion:
                eliminarData();
                break;
            default:
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void abrirContexto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (inventario.getContexto() == 0|| inventario.getContexto()==1) {
            abrirContextoInventario(fragmentTransaction);

        } else if (inventario.getContexto() == 2) {
            menuNav.getItem(2).setEnabled(true);
            menuNav.getItem(0).setEnabled(false);
            menuNav.getItem(1).setEnabled(false);
            menuNav.getItem(3).setEnabled(false);
            abrirContextoResumen(fragmentTransaction);
        }
    }


    private void abrirContextoInventario(FragmentTransaction fragmentTransaction) {

        FragmentZonas fragmentZonas = new FragmentZonas();
        fragmentTransaction.replace(R.id.contenedor, fragmentZonas);
        fragmentTransaction.commit();
    }

    private void abrirContextoResumen(FragmentTransaction fragmentTransaction) {
        FrmContainerResumen frmContainerResumen = new FrmContainerResumen();
        fragmentTransaction.replace(R.id.contenedor, frmContainerResumen);
        setTitle("Resumen Invent. "+inventario.getNumInventario());
        fragmentTransaction.commit();
    }

    private void abrirContextoNuevoProducto(FragmentTransaction fragmentTransaction) {
        FrmNuevoProducto frmNuevoProducto = new FrmNuevoProducto();
        fragmentTransaction.replace(R.id.contenedor, frmNuevoProducto);
        setTitle("Nuevo producto");
        fragmentTransaction.commit();
    }


   private void eliminarData(){
        //mostrar un dialogo
       AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
       confirmar.setMessage("¿Seguro que desea finalizar el inventario "+inventario.getNumInventario()+"?")
               .setTitle("Advertencia")
               .setCancelable(false)
               .setNegativeButton("Cancelar",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               //dialog.cancel();
                               dialog.dismiss();
                           }
                       })
               .setPositiveButton("Aceptar",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {

                               IInventario iInventario=new SqliteInventario(getApplicationContext());
                               iInventario.deleteAll();

                               IZona iZona=new SqliteZona(getApplicationContext());
                               iZona.deleteAll();

                               IProducto iProducto=new SqliteProducto(getApplicationContext());
                               iProducto.deleteAll();

                               IConteo iConteo=new SqliteConteo(getApplicationContext());
                               iConteo.deleteAll();

                               IHistorial iHistorial=new SqliteHistorial(getApplicationContext());
                               iHistorial.deleteAll();

                               startActivity(new Intent(getApplicationContext(), Login.class));
                               finish();//para que no se guarde en la pila
                           }
                       });
       AlertDialog alertDialog = confirmar.create();
       alertDialog.show();

       Button cancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
       if(cancel != null)
           //b.setBackgroundColor(Color.CYAN);
           cancel.setTextColor(getResources().getColor(R.color.grey_darken_2));//color por código al boton cancelar del fialogo
   }


    private void abrirPdf() {
        Utils.copyRawToSDCard(R.raw.manual_usuario, Environment.getExternalStorageDirectory() + "/manual_usuario.pdf", this);
        File pdfFile = new File(Environment.getExternalStorageDirectory(), "/manual_usuario.pdf");//File path
        if (pdfFile.exists()) { //Revisa si el archivo existe!
            Uri path = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //define el tipo de archivo
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Inicia pdf viewer
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
            }
        } else {
            Toast.makeText(getApplicationContext(), "El archivo manual.pdf no existe o tiene otro nombre...! ", Toast.LENGTH_SHORT).show();
        }
    }


    private void cargarEmpresas() {
        Empresa empresa=new Empresa();
        empresa.setNombre("Comercio");
        empresa.setCodigo(2);
        empresa.setEstado(0);

        IEmpresa iEmpresa=new SqliteEmpresa(this);
        iEmpresa.agregarEmpresa(empresa);
        //Query<Empresa> notesQuery;
        //BoxStore boxStore
        //empresaBox =

    }

}
