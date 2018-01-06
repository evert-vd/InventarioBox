package com.evertvd.inventariobox.vista.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.evertvd.inventariobox.interfaces.IConteo;
import com.evertvd.inventariobox.interfaces.IZona;
import com.evertvd.inventariobox.R;
import com.evertvd.inventariobox.modelo.Zona;
import com.evertvd.inventariobox.sqlite.SqliteConteo;
import com.evertvd.inventariobox.sqlite.SqliteZona;
import com.evertvd.inventariobox.utils.Utils;
import com.evertvd.inventariobox.vista.adapters.ResumenZonasAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrmResumenZona extends Fragment {
    private View view;
    private ResumenZonasAdapter adapter;
    private List<Zona> zonaList;
    private RecyclerView recyclerResumenZona;
    private RecyclerView.LayoutManager lManager;
    private TextView txtTotalConteo;

    public FrmResumenZona() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resumen_zona, container, false);

        IZona iZona= new SqliteZona(getActivity());
        zonaList=iZona.listarZona();

        IConteo iConteo=new SqliteConteo(getActivity());
        //iConteo.totalConteo();
        txtTotalConteo=(TextView)view.findViewById(R.id.txtTotalConteo);
        txtTotalConteo.setText(Utils.formatearNumero(iConteo.totalConteo()) + " Und.");

        recyclerResumenZona = (RecyclerView)view. findViewById(R.id.recyclerResumenZona);
        recyclerResumenZona.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recyclerResumenZona.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ResumenZonasAdapter(zonaList, getActivity());
        recyclerResumenZona.setAdapter(adapter);
        recyclerResumenZona.setItemAnimator(new DefaultItemAnimator());
        recyclerResumenZona.setAdapter(adapter);

        return view;
    }
}