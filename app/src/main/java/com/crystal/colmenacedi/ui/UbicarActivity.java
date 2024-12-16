package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.crystal.colmenacedi.retrofit.request.RequestUbicacion;
import com.crystal.colmenacedi.retrofit.response.ubicacion.ResponseUbicacion;
import com.crystal.colmenacedi.retrofit.response.ubicacionGet.ResponseUbicacionGet;
import com.crystal.colmenacedi.ui.adapter.ListaDeItemsRecyclerViewAdapter;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicarActivity extends AppCompatActivity{
    RecyclerView rvDynamicItems;
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etEan, etUbicacion;
    ProgressBar pbRecibirCaja;
    String id, estacion, ean, ubicacion, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicar);
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

        id = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        etEan = findViewById(R.id.etEan);
        etEan.requestFocus();
        etUbicacion = findViewById(R.id.etPosicionRC);
        pbRecibirCaja = findViewById(R.id.pbRecibirCaja);
        pbRecibirCaja.setVisibility(View.GONE);
    }

    private void eventos() {
        etUbicacion.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etUbicacion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etUbicacion.getText().toString().trim().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        pbRecibirCaja.setVisibility(View.VISIBLE);
                        ubicacionPut();
                    }
                    return true;
                }
                return false;
            }
        });

        etUbicacion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etUbicacion.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        pbRecibirCaja.setVisibility(View.VISIBLE);
                        ubicacionPut();
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
                        etUbicacion.setEnabled(true);
                        etUbicacion.requestFocus();
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

    private void mostrarCategorias(){
        rvDynamicItems.setVisibility(View.VISIBLE);
    }

    private void ubicacionPut() {
        RequestUbicacion requestUbicacion=new RequestUbicacion(id,ean,estacion);
        Call<ResponseUbicacion> call =serviceRetrofit.doUbicacion(requestUbicacion);
        call.enqueue(new Callback<ResponseUbicacion>() {
            @Override
            public void onResponse(Call<ResponseUbicacion> call, Response<ResponseUbicacion> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    //LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getErrors().getStatus()){
                        mensajeDialog("Error", response.body().getErrors().getSource());
                    }else{
                        Log.e("LOGCAT", "ReponseUbicacionPut: " +response.body());
                        //mensaje = response.body().getErrors().getSource();
                        //mensajeSimpleDialog(response.body().getErrors().getSource());
                        regresarPrincipal();
                    }
                }else{
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseUbicacion> call, Throwable t) {
                pbRecibirCaja.setVisibility(View.GONE);
                LogFile.adjuntarLog("ErrorResponseFinPinado",t);
                mensajeDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

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