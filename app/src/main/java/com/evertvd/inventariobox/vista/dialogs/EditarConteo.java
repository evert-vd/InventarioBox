package com.evertvd.inventariobox.vista.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Conteo;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Fragmento con un diálogo personalizado
 */
public class EditarConteo extends DialogFragment implements View.OnClickListener {
    private static final String TAG = EditarConteo.class.getSimpleName();
    private int cantidadIngresada;
    private Button btnAceptar, btnCancelar;
    private EditText txtCantidad, txtObservacion;
    private TextInputLayout tilCantidad, tilObservacion;
    private Conteo conteoEdit;
    private long idConteo;
    private  int position;


    //Definimos la interfaz
    public interface OnClickListener {
        void editarConteo(Conteo conteo, int position);
    }

    public EditarConteo() {
        //this.idProducto=idProducto;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return crearAgregarCantidad();
    }

    /**
     * Crea un diÃ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃ¡logo
     */
    public AlertDialog crearAgregarCantidad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_registrar_conteo, null);
        //View v = inflater.inflate(R.layout.dialogo_registrar_conteo, null);
        //idConteo = getArguments().getLong("idConteo");
        //position = getArguments().getInt("position");//posicion del item a editar
        //IConteo iConteo=new SqliteConteo(getActivity());
        //conteoEdit=iConteo.obtenerConteo(idConteo);

        //txtCantidad=(EditText)v.findViewById(R.id.lblCantidadConteo);
        txtObservacion = (EditText) v.findViewById(R.id.txtObservacion);
        //txtObservacion.setText(conteoEdit.getObservacion());
        txtObservacion.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        txtCantidad=(EditText)v.findViewById(R.id.txtCantidad);
        //txtCantidad.setText(String.valueOf(conteoEdit.getCantidad()));

        btnCancelar = (Button) v.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        btnAceptar = (Button) v.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

        tilCantidad = (TextInputLayout) v.findViewById(R.id.tilCantidad);
        tilObservacion = (TextInputLayout) v.findViewById(R.id.tilObservacion);

        builder.setView(v);
        builder.setTitle("Registrar conteo");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAceptar) {
            if (validarCantidad(tilCantidad.getEditText().getText().toString())) {
                EditarConteo.OnClickListener activity = (EditarConteo.OnClickListener) getActivity();

                conteoEdit.setCantidad(Integer.parseInt(tilCantidad.getEditText().getText().toString()));
                conteoEdit.setObservacion( tilObservacion.getEditText().getText().toString());
                conteoEdit.setValidado(0);
                conteoEdit.setEstado(1);//modificación
                activity.editarConteo(conteoEdit, position);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                this.dismiss();
            }
        } else if (v.getId() == R.id.btnCancelar) {

            EditarConteo.OnClickListener activity = (EditarConteo.OnClickListener) getActivity();
            activity.editarConteo(null, position);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            this.dismiss();
        }
        //ocultar teclado
    }

    private boolean validarCantidad(String cantidadIngresada) {
        if (cantidadIngresada.trim().length() == 0) {
            tilCantidad.setError("Ingresar una cantidad válida");
            return false;
        } else {
            tilCantidad.setError(null);
        }
        return true;
    }

}
