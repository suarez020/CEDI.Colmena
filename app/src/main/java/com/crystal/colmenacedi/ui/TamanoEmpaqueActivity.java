package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestTamano;
import com.crystal.colmenacedi.retrofit.response.tamano.ResponseTamano;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TamanoEmpaqueActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAB,btnB,btnExp,btnBc,btnC,btnD,btnBolsa;
    ClienteRetrofit appCliente;
    ServiceRetrofit apiService;
    String cedula;
    String estacion;
    String ubicacion;
    String generico;
    String leidos;
    String faltantes;
    boolean qr;
    boolean activityRFID;
    TextToSpeech speech;
    boolean consumirServicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamano_empaque);
        inicioRetrofit();
        findViews();
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        apiService = appCliente.obtenerServicios();
    }

    private void findViews() {
        consumirServicio = true;
        btnAB=findViewById(R.id.btnAB);
        btnB=findViewById(R.id.btnB);
        btnExp=findViewById(R.id.btnExp);
        btnBc=findViewById(R.id.btnBc);
        btnC=findViewById(R.id.btnC);
        btnD=findViewById(R.id.btnD);
        btnBolsa=findViewById(R.id.btnBolsa);

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion =   SPM.getString(Constantes.EQUIPO_API);

        speech =  new TextToSpeech(getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR){
                speech.setLanguage(new Locale("spa", "ESP"));
            }
        });
        //Validar si se recibe una matricula
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ubicacion = getIntent().getStringExtra("ubicacion");
            generico = getIntent().getStringExtra("cartonG");
            activityRFID=getIntent().getBooleanExtra("activityRFID",false);

            leidos=getIntent().getStringExtra("leidos");
            faltantes=getIntent().getStringExtra("faltantes");
            qr = getIntent().getExtras().getBoolean("QR");
        }
    }

    private void eventos() {
        btnAB.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnExp.setOnClickListener(this);
        btnBc.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnBolsa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tamano;

        switch (v.getId()){
            case R.id.btnAB:
                tamano="AB";
                break;
            case R.id.btnC:
                tamano="C";
                break;
            case R.id.btnD:
                tamano="D";
                break;
            case R.id.btnBc:
                tamano="BC";
                break;
            case R.id.btnBolsa:
                tamano="E";
                break;
            default:
                tamano="B";
                break;
        }
        consumirServicio(tamano);
    }

    private void consumirServicio(String tamanoParametro){
        if(consumirServicio){
            consumirServicio = false;
            String tamanoElegido=tamanoParametro;
            RequestTamano rTamano = new RequestTamano(cedula,estacion,ubicacion,generico,tamanoElegido);
            Call<ResponseTamano> callTamano = apiService.doTamano(rTamano);
            callTamano.enqueue(new Callback<ResponseTamano>() {
                @Override
                public void onResponse(Call<ResponseTamano> call, Response<ResponseTamano> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                           consumirServicio = true;
                        if (response.body().getRespuesta().getError().getStatus()){
                            LogFile.adjuntarLog(response.body().getRespuesta().getError().getSource());
                            mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        }else{
                           if(activityRFID){
                               boolean consumirServicioapiCerradoRFID=true;
                               Intent intent = new Intent(TamanoEmpaqueActivity.this, EmpaqueRFIDActivity.class);
                               intent.putExtra("ubicacion",ubicacion);
                               intent.putExtra("consumirServicioapiCerradoRFID",consumirServicioapiCerradoRFID);
                               intent.putExtra("cartonG",generico);//generico = etGenericoEmpaqueRFID.getText().toString().replaceAll("\\s","");
                               intent.putExtra("tamano",tamanoElegido);

                               startActivity(intent);
                           }else{
                               boolean continuarirSplashScreen=true;
                               Intent intent = new Intent(TamanoEmpaqueActivity.this, CierreCartonActivity.class);
                               intent.putExtra("ubicacion",ubicacion);
                               intent.putExtra("continuarirSplashScreen",continuarirSplashScreen);
                               intent.putExtra("cartonG",generico);//generico = etGenericoEmpaqueRFID.getText().toString().replaceAll("\\s","");
                               intent.putExtra("tamano",tamanoElegido);
                               //-----------------------------------------
                               intent.putExtra("leidos", leidos);
                               intent.putExtra("faltantes",  faltantes);
                               intent.putExtra("QR", qr);
                               //-----------------------------------------
                               startActivity(intent);
                           }
                        }
                    }else{
                        Utilidades.mensajeDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_conexion_sb) + response.message(),TamanoEmpaqueActivity.this);
                        consumirServicio = true;
                    }
                }
                @Override
                public void onFailure(Call<ResponseTamano> call, Throwable t) {
                    LogFile.adjuntarLog("ErrorResponseMatricula", t);
                    Utilidades.mensajeDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_conexion) + t.getMessage(),TamanoEmpaqueActivity.this);
                    consumirServicio = true;
                }
            });
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

    @Override
    public void onBackPressed() {}

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
        if(!(TamanoEmpaqueActivity.this.isFinishing())){
            alerta.show();
        }
    }
}