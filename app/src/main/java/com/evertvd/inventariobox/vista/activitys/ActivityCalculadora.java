package com.evertvd.inventariobox.vista.activitys;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ActivityCalculadora extends AppCompatActivity {
    /***** Declaring Variable *****/
    //Button btnClear;
    TextView tvProcessor, tvResult;
    ArrayList<Historial> arrayList = new ArrayList<Historial>();
    ArrayList<String> arrayList2 = new ArrayList<String>();
    private int posicion;
    private long idConteo, idProducto;
    private Conteo conteo;
    private Producto producto;
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnSaveNone, btnSaveDone;
    String processor;
    Boolean isSmallBracketOpen;
    Button btnMultiply, btnMinus, btnPlus, btnDivide, btnDecimal, btnBack, btnEqual;
    int developedCounter;
    static String developedBy = "Atif Naseem";
    static String deveopedNote = "developed in IU, Tue Sep 26, 2017";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        //arrayList=new ArrayList<>();
        setTitle("Registrar Conteo");
        int cantidad = getIntent().getExtras().getInt("cantidad");
        posicion = getIntent().getExtras().getInt("posicion");
        idProducto = getIntent().getExtras().getLong("idProducto");
        if (posicion != -1) {
            idConteo = getIntent().getExtras().getLong("idConteo");
            IConteo iConteo = new SqliteConteo(this);
            conteo = iConteo.obtenerConteo(idConteo);
        } else {
            IProducto iProducto = new SqliteProducto(this);
            producto = iProducto.obtenerProducto(idProducto);
        }


        //Toast.makeText(this,"posicion:" + posicion, Toast.LENGTH_SHORT).show();
        //prueba historial conteo
        /*
        IHistorial iHistorial=new SqliteHistorial(this);
        List<Historial>historialList=iHistorial.listarHisotorial(conteo);
        if(!historialList.isEmpty()){
            for (int i=0;i<historialList.size();i++){
                Log.e("HIST", historialList.get(i).getObservacion());
            }
        }else{
            Log.e("HIST", "No existe historial");
        }
        */


        isSmallBracketOpen = false;
        developedCounter = 0;

        /***** Assigning Variable *****/
        //btnClear = (Button) findViewById(R.id.btn_delete);
        tvProcessor = (TextView) findViewById(R.id.tv_process);
        tvResult = (TextView) findViewById(R.id.tv_result);

        if (conteo != null) {
            tvProcessor.setText(String.valueOf(cantidad+".0"));
            tvResult.setText(String.valueOf(conteo.getCantidad()+".0"));
        } else {
            tvProcessor.setText("");
            tvResult.setText(Utils.formatearNumero(cantidad));
        }

        btnOne = (Button) findViewById(R.id.btn_one);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnThree = (Button) findViewById(R.id.btn_three);
        btnFour = (Button) findViewById(R.id.btn_four);
        btnFive = (Button) findViewById(R.id.btn_five);
        btnSix = (Button) findViewById(R.id.btn_six);
        btnSeven = (Button) findViewById(R.id.btn_seven);
        btnEight = (Button) findViewById(R.id.btn_eight);
        btnNine = (Button) findViewById(R.id.btn_nine);
        btnZero = (Button) findViewById(R.id.btn_zero);

        btnSaveNone = (Button) findViewById(R.id.btnSaveNone);
        btnSaveDone = (Button) findViewById(R.id.btnSaveDone);

        btnMultiply = (Button) findViewById(R.id.btn_multiply);
        btnMinus = (Button) findViewById(R.id.btn_minus);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnDivide = (Button) findViewById(R.id.btn_divide);

        btnDecimal = (Button) findViewById(R.id.btn_dot);
        btnBack = (Button) findViewById(R.id.btn_back);

        //btnSmallBracket = (Button) findViewById(R.id.btn_small_bracket);
        btnEqual = (Button) findViewById(R.id.btn_equal);
        //btnPercentage = (Button) findViewById(R.id.btn_percentage);

        /*
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developedCounter++;
                if (developedCounter >= 5) {
                    developedCounter = 0;
                    tvProcessor.setText(developedBy);
                    tvResult.setText(deveopedNote);
                } else {
                    tvProcessor.setText("");
                    tvResult.setText("");
                }
            }
        });*/


        /******************************************************
         * Number Buttons on-Click
         ******************************************************/
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "1");
                deshabilitarBotonGuardar();
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "2");
                deshabilitarBotonGuardar();
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "3");
                deshabilitarBotonGuardar();
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "4");
                deshabilitarBotonGuardar();
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "5");
                deshabilitarBotonGuardar();
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "6");
                deshabilitarBotonGuardar();
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "7");
                deshabilitarBotonGuardar();
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "8");
                deshabilitarBotonGuardar();
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "9");
                deshabilitarBotonGuardar();
            }
        });
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "0");
                deshabilitarBotonGuardar();
            }
        });


        /******************************************************
         * Functional Buttons on-Click
         ******************************************************/
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "x");
                deshabilitarBotonGuardar();
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "-");
                deshabilitarBotonGuardar();
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "+");
                deshabilitarBotonGuardar();
            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "/");
                deshabilitarBotonGuardar();
            }
        });
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + ".");
                deshabilitarBotonGuardar();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                if (processor.length() > 0) {
                    processor = processor.substring(0, processor.length() - 1);
                    tvProcessor.setText(processor);
                } else {
                    tvResult.setText("0");
                }
                deshabilitarBotonGuardar();
            }
        });

        btnBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                developedCounter++;
                if (developedCounter >= 5) {
                    developedCounter = 0;
                    tvProcessor.setText(developedBy);
                    tvResult.setText(deveopedNote);
                } else {
                    tvProcessor.setText("");
                    tvResult.setText("0");
                }
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                deshabilitarBotonGuardar();
                return true;
            }
        });


        /*
        btnSmallBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                if (isSmallBracketOpen) {
                    processor = tvProcessor.getText().toString();
                    tvProcessor.setText(processor + ")");
                    isSmallBracketOpen = false;
                } else {
                    processor = tvProcessor.getText().toString();
                    tvProcessor.setText(processor + "(");
                    isSmallBracketOpen = true;
                }
            }
        });

        btnPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
                processor = tvProcessor.getText().toString();
                tvProcessor.setText(processor + "%");
            }
        });*/


        /******************************************************
         * Equal Buttons on-Click
         ******************************************************/
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearScreen();
                processor = tvProcessor.getText().toString();
                processor = processor.replaceAll("x", "*");
                processor = processor.replaceAll("%", "/100");

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);
                String result = "";

                try {

                    Scriptable scope = rhino.initStandardObjects();
                    result = rhino.evaluateString(scope, processor, "JavaScript", 1, null).toString();
                    tvProcessor.setText(result);
                    String cadena = processor + "=" + result;
                    //tvResult.setVisibility(View.GONE);
                    crearHistorial(cadena);
                    Log.e("RES", cadena);
                    if(!result.contains("Error") || !result.contains("JavaScript")|| processor.contains("mozila")){
                        habilitarBotonGuardar();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                    deshabilitarBotonGuardar();
                    result = "Error. Verificar datos ingresados";
                }
                tvResult.setText(result);
            }
        });

        btnSaveDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                try{
                    String cantidad = tvResult.getText().toString();
                    //intent.putExtra("cantidad", cantidad);
                    intent.putExtra("posicion", posicion);
                    if (posicion != -1) {
                        actualizarConteo(cantidad);
                    } else {
                        guardarConteo(cantidad);
                    }
                    setResult(RESULT_OK, intent);
                    //guardarHistorial();
                    finish();
                }catch (Exception e){
                    intent.putExtra("posicion", -1);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("posicion", posicion);
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
        finish();
    }

    public void clearScreen() {
        processor = tvProcessor.getText().toString();
        if (processor.contains(developedBy)) {
            tvProcessor.setText("");
            tvResult.setText("");
        }
        developedCounter = 0;
    }

    public void habilitarBotonGuardar() {
        btnSaveNone.setVisibility(View.GONE);
        btnSaveDone.setVisibility(View.VISIBLE);
    }

    public void deshabilitarBotonGuardar() {
        btnSaveNone.setVisibility(View.VISIBLE);
        btnSaveDone.setVisibility(View.GONE);
    }


    private boolean actualizarConteo(String cantidad) {
        boolean estado = false;

        BigDecimal number = new BigDecimal(cantidad);
        long iPart = number.longValue();
        String numer = String.valueOf(iPart);
        //BigDecimal fraccion = number.remainder(BigDecimal.ONE);
        if (iPart != 0) {
            conteo.setCantidad(Integer.parseInt(numer));
            conteo.setFechaRegistro(Utils.fechaActual());
            conteo.setEstado(1);//modificacion
            IConteo iConteo = new SqliteConteo(this);
            iConteo.actualizarConteo(conteo);

            IHistorial iHistorial = new SqliteHistorial(this);
            for (int i = 0; i < arrayList2.size(); i++) {
                Historial historial = new Historial();
                historial.setObservacion(arrayList2.get(i));
                historial.setTipo(2);
                iHistorial.agregarHistorial(conteo,historial);
            }
            estado = true;
        }
        return estado;
    }

    private void guardarConteo(String cantidad) {
        BigDecimal number = new BigDecimal(cantidad);
        long iPart = number.longValue();
        String numer = String.valueOf(iPart);
        //BigDecimal fraccion = number.remainder(BigDecimal.ONE);
        if (iPart != 0) {
            Conteo conteo = new Conteo();
            conteo.setCantidad(Integer.parseInt(numer));
            conteo.setValidado(0);//por validar
            conteo.setEstado(0);//estado inicial
            conteo.setFechaRegistro(Utils.fechaActual());
            IProducto iProducto = new SqliteProducto(this);
            IConteo iConteo = new SqliteConteo(this);
            iConteo.agregarConteo(iProducto.obtenerProducto(idProducto), conteo);
            guardarHistorial();
        }
    }


    public void crearHistorial(String cadena) {
        if(arrayList2.size()>=1){
            //valida que no se guarde la misma cantidad en el historial
            String item0=arrayList2.get(arrayList2.size()-1);
            if(!item0.contains(cadena)){
                arrayList2.add(cadena);
            }

        }else{
            arrayList2.add(cadena);
        }


    }

    public void guardarHistorial() {
        IHistorial iHistorial = new SqliteHistorial(this);
        IConteo iConteo=new SqliteConteo(this);
        Conteo conteo2=iConteo.ultimoConteo(producto);
        for (int i = 0; i < arrayList2.size(); i++) {
            Historial historial = new Historial();
            historial.setObservacion(arrayList2.get(i));
            historial.setTipo(1);
            iHistorial.agregarHistorial(conteo2,historial);
        }
    }
}
