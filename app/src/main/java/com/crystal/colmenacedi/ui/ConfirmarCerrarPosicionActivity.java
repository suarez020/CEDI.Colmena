package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.response.finalizarUbicacionInfo.ResponseFinalizarUbicacionInfo;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmarCerrarPosicionActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    EditText etPosicionConfirmarCerrado, etFaltantesConfirmarCerrado;
    RecyclerView recyclerViewConfirmarCerrado;
    Button btnConfirmarCerradoPosicion;
    ArrayList<String> listInformacion;
    TextToSpeech speech;

    //Variables
    String activity, cedula, estacion, ubicacion, faltantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_cerrar_posicion);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_confirmarCerrarPosicion);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));

        //Iniciar el cliente REST
        inicioRetrofit();
        //Asignar referencias
        findViews();
        //Eventos
        eventos();
        //Llenar ListWiew
        finalizarUbicacionInfo();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    @SuppressLint("ResourceAsColor")
    private void findViews() {
        speech =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });

        activity = SPM.getString(Constantes.ACTIVITY_NAVEGAR);
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        //ubicacion = getIntent().getExtras().getString("ubicacion");
        //faltantes = getIntent().getExtras().getString("faltantes");

        recyclerViewConfirmarCerrado = findViewById(R.id.rvConfirmarCerradoPosicion);
        recyclerViewConfirmarCerrado.setLayoutManager(new LinearLayoutManager(this));
        etPosicionConfirmarCerrado = findViewById(R.id.etPosicionConfirmarCerrado);
        etPosicionConfirmarCerrado.setTextColor(R.color.opaco);
        etPosicionConfirmarCerrado.setText(ubicacion);

        etFaltantesConfirmarCerrado = findViewById(R.id.etFaltantesConfirmarCerrado);
        etFaltantesConfirmarCerrado.setTextColor(R.color.opaco);
        etFaltantesConfirmarCerrado.setText(faltantes);

        btnConfirmarCerradoPosicion = findViewById(R.id.btnConfirmarCerradoPosicion);
        listInformacion = new ArrayList<>();
    }

    private void eventos() {
        btnConfirmarCerradoPosicion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnConfirmarCerradoPosicion) {
        }
    }



    private void toSpeech(final String msj) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(speech != null){
                    speech.speak(msj, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }, 100);
    }

    private void finalizarUbicacionInfo() {
        //Consultar la Api de finalizarUbicaciónInfo
        RequestPinado requestPinado = new RequestPinado(cedula, estacion, ubicacion);
        Call<ResponseFinalizarUbicacionInfo> call = serviceRetrofit.doFinalizarUbicacionInfo(requestPinado);
        call.enqueue(new Callback<ResponseFinalizarUbicacionInfo>() {
            @Override
            public void onResponse(Call<ResponseFinalizarUbicacionInfo> call, Response<ResponseFinalizarUbicacionInfo> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                    }
                }else{
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseFinalizarUbicacionInfo> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseFinalizarUbicacionInfo",t);
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public void mensajeSimpleDialog(String titulo, String msj){

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

                    }
                });
        AlertDialog alerta = builder.create();
        if(!(ConfirmarCerrarPosicionActivity.this.isFinishing())){
            alerta.show();
        }
    }

/*
    @Override
    public void onBackPressed(){
        //Regresar a la pantalla principal
        regresarPrincipal();
    }
*/

    private void regresarPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }
}