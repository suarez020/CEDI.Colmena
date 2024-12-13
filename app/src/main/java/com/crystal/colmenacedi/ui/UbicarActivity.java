package com.crystal.colmenacedi.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.request.RequestRecepcion;
import com.crystal.colmenacedi.retrofit.response.finPinado.ResponseFinPinado;
import com.crystal.colmenacedi.retrofit.response.iniciaPinado.ResponseIniciaPinado;
import com.crystal.colmenacedi.retrofit.response.loginGet.ResponseLoginGet;
import com.crystal.colmenacedi.retrofit.response.ubicacion.ResponseUbicacion;
import com.crystal.colmenacedi.retrofit.response.ubicacionGet.ResponseUbicacionGet;
import com.crystal.colmenacedi.ui.adapter.ListaDeItemsRecyclerViewAdapter;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicarActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rvDynamicItems;
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etEan, etPosicionRC;
    TextView tvTituloPedidoRC, tvPedidoRCV, tvTituloTiendaRC, tvTiendaRCV, tvTituloPosicionRC, tvPosicionRC, tvTituloTotalesRC, tvTotalesRCV;
    Button btnTerminarCarton;
    ProgressBar pbRecibirCaja;
    String cedula, estacion, ean, ubicacion, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibir_cajas);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle("Ubicar UND");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
        inicioRetrofit();
        findViews();
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        rvDynamicItems = findViewById(R.id.rvDynamicItems);
        rvDynamicItems.setLayoutManager(new LinearLayoutManager(this));

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        etEan = findViewById(R.id.etEan);
        etEan.requestFocus();
        etPosicionRC = findViewById(R.id.etPosicionRC);
        btnTerminarCarton = findViewById(R.id.btnTerminarCarton);
        btnTerminarCarton.setEnabled(false);
        pbRecibirCaja = findViewById(R.id.pbRecibirCaja);
        pbRecibirCaja.setVisibility(View.GONE);
    }

    private void eventos() {
        btnTerminarCarton.setOnClickListener(this);

        //Eventos sobre el EditText posición
        etPosicionRC.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etPosicionRC.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etPosicionRC.getText().toString().trim().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        pbRecibirCaja.setVisibility(View.VISIBLE);
                        iniciaPinado();
                    }
                    return true;
                }
                return false;
            }
        });

        etPosicionRC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etPosicionRC.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        pbRecibirCaja.setVisibility(View.VISIBLE);
                        iniciaPinado();
                    }
                }
                return handled;
            }
        });

        //Evento sobre el EditText cartón genérico
        etEan.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ean = etEan.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                            pbRecibirCaja.setVisibility(View.VISIBLE);
                            LLenarMenu();
                        }
                        return true;
                    }
                return false;
            }
        });

        etEan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ean = etEan.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                        pbRecibirCaja.setVisibility(View.VISIBLE);
                        LLenarMenu();
                    }
                }
                return handled;
            }
        });
    }

    private void LLenarMenu() {
        //ean = SPM.getString(Constantes.EQUIPO_API);
        Call<ResponseUbicacionGet> ubicacionGet = serviceRetrofit.doUbicacionGET(ean);
        ubicacionGet.enqueue(new Callback<ResponseUbicacionGet>() {
            @Override
            public void onResponse(Call<ResponseUbicacionGet> call, Response<ResponseUbicacionGet> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    //LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if (response.body().getErrors().getStatus()) {//            .getRespuesta().getError().getStatus()){
                        mensajeDialog("Error", response.body().getErrors().getSource());//.getRespuesta().getMensaje());
                        etEan.setText("");
                        etEan.requestFocus();
                    } else {
                        mostrarCategorias();
                        List<List<String>> lista = response.body().getData().getItems();//getRespuesta().getSku().getItems();
                        ListaDeItemsRecyclerViewAdapter categoriasAdapter = new ListaDeItemsRecyclerViewAdapter(lista);

                        rvDynamicItems.setAdapter(categoriasAdapter);
//                        UnidadesFaltantes=response.body().getRespuesta().getCampos().getUnidadesFaltantes();
//                        UnidadesLeidas=response.body().getRespuesta().getCampos().getUnidadesLeidas();
//                        isConteo = response.body().getRespuesta().getCampos().getContar();
                    }
                } else {
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
                pbRecibirCaja.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseUbicacionGet> call, Throwable t) {

            }
        });
    }
        private void UbicacionPostTodoSalioOK (){

            RequestRecepcion requestRecepcion = new RequestRecepcion(cedula, estacion, ean);
            Call<ResponseUbicacion> call = serviceRetrofit.doUbicacion(requestRecepcion);
            call.enqueue(new Callback<ResponseUbicacion>() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(Call<ResponseUbicacion> call, Response<ResponseUbicacion> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        //LogFile.adjuntarLog(response.body().getRespuesta().toString());
                        if (response.body().getErrors().getStatus()) {//            .getRespuesta().getError().getStatus()){
                            mensajeDialog("Error", response.body().getErrors().getSource());//.getRespuesta().getMensaje());
                            etEan.setText("");
                            etEan.requestFocus();
                        } else {
                            // tvPedidoRCV.setText(response.body().getRespuesta().getRecepcion().getPedido());
                            //  tvTiendaRCV.setText(response.body().getRespuesta().getRecepcion().getTienda());
                            //  tvPosicionRC.setText(response.body().getRespuesta().getRecepcion().getUbicacion());
                            //  tvTotalesRCV.setText(response.body().getRespuesta().getRecepcion().getCantidad());
                            mostrarCategorias();
                            etEan.setEnabled(false);
                            etEan.setTextColor(R.color.opaco);
                            etPosicionRC.setEnabled(true);
                            etPosicionRC.requestFocus();
                        }
                    } else {
                        mensajeDialog("Error", "Error de conexión con el servicio web base.");
                    }
                    pbRecibirCaja.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ResponseUbicacion> call, Throwable t) {
                    pbRecibirCaja.setVisibility(View.GONE);
                    LogFile.adjuntarLog("ErrorResponseRecepcion", t);
                    mensajeDialog("Error", "Error de conexión: " + t.getMessage());
                }
            });

    }

    private void iniciaPinado() {
        //Consultar la Api de iniciaPinado
        RequestPinado requestPinado = new RequestPinado(cedula, estacion, ubicacion);
        Call<ResponseIniciaPinado> call = serviceRetrofit.doIniciaPinado(requestPinado);
        call.enqueue(new Callback<ResponseIniciaPinado>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<ResponseIniciaPinado> call, Response<ResponseIniciaPinado> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeDialog("Error", response.body().getRespuesta().getMensaje());
                    }else{
                        Log.e("LOGCAT", "ResponseIniciaPinado: "+response.body());
                        mensajeSimpleDialog(response.body().getRespuesta().getMensaje());
                        btnTerminarCarton.setEnabled(true);
                        etPosicionRC.setEnabled(false);

                        etPosicionRC.setTextColor(R.color.opaco);
                    }
                }else{
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
                pbRecibirCaja.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseIniciaPinado> call, Throwable t) {
                pbRecibirCaja.setVisibility(View.GONE);
                LogFile.adjuntarLog("ErrorResponseIniciaPinado",t);
                mensajeDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarCarton){
            finalizarPinado();
        }
    }

    private void finalizarPinado() {
        
        //Consultar la Api de finPinado
        RequestPinado requestPinado = new RequestPinado(cedula, estacion, ubicacion);
        Call<ResponseFinPinado> call = serviceRetrofit.doFinPinado(requestPinado);
        call.enqueue(new Callback<ResponseFinPinado>() {
            @Override
            public void onResponse(Call<ResponseFinPinado> call, Response<ResponseFinPinado> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeDialog("Error", response.body().getRespuesta().getMensaje());
                    }else{
                        Log.e("LOGCAT", "ReponseFinPinado: " +response.body());
                        mensaje = response.body().getRespuesta().getMensaje();
                        mensajeSimpleDialog(response.body().getRespuesta().getMensaje());
                    }
                }else{
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseFinPinado> call, Throwable t) {
                pbRecibirCaja.setVisibility(View.GONE);
                LogFile.adjuntarLog("ErrorResponseFinPinado",t);
                mensajeDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    private void mostrarCategorias(){
        rvDynamicItems.setVisibility(View.VISIBLE);
    }

    //Alert Dialog para mostrar mensajes simples
    public void mensajeSimpleDialog(final String msj){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msj)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(msj.equals(mensaje)){
                            regresarPrincipal();
                        }
                    }
                });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void mensajeDialog(final String titulo, final String msj){

        int icon = R.drawable.vector_alerta;
        if (titulo.equals(getResources().getString(R.string.error))) {
            icon = R.drawable.vector_error;
        } else if(titulo.equals(getResources().getString(R.string.exito))){
            icon = R.drawable.vector_exito;
        } else if(titulo.equals(getResources().getString(R.string.mensaje))){
            icon = R.drawable.vector_mensaje;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo)
                .setCancelable(false)
                .setMessage(msj)
                .setIcon(icon)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(titulo.equals("Mensaje") && msj.equals("Pinado finalizado")){
                            regresarPrincipal();
                        }
                    }
                });
        AlertDialog alerta = builder.create();
        if(!(UbicarActivity.this.isFinishing())){
            alerta.show();
        }
    }

    private void regresarPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        regresarPrincipal();
    }
}