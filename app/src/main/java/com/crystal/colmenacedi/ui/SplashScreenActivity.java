package com.crystal.colmenacedi.ui;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestCerradoCarton;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.response.empezarCerrado.ResponseEmpezarCerrado;
import com.crystal.colmenacedi.retrofit.response.login.ResponseLogin;

import java.io.Serializable;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    Animation animacionArriba, animacionAbajo;
    ImageView ivSplashCaja;
    ProgressBar pbSplash;
    TextView tvEstadoSplash;
    TextToSpeech speech;
    Context contexto;

    //Variables
    String cedula, equipo, ubicacion, cartonG, actuales, faltantes, mensaje;
    boolean qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Utilidades.ocultarBarraEstado(getWindow());

        //Agregar animaciones
        animaciones();
        //Iniciar el cliente REST
        InicioRetrofit();
        //Inicializar objetos
        inicializar();

        cerradoCarton();
    }

    private void irCierreCarton() {
        Intent i = new Intent(this, CierreCartonActivity.class);
        i.putExtra("leidos", (Serializable) actuales);
        i.putExtra("faltantes", (Serializable) faltantes);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        i.putExtra("QR", (Serializable) qr);
        startActivity(i);
        finish();
    }

    private void animaciones() {
        animacionArriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        animacionAbajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
    }

    private void InicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void inicializar() {
        contexto = SplashScreenActivity.this;

        speech =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });

        ivSplashCaja = findViewById(R.id.ivSplashCaja);
        ivSplashCaja.setAnimation(animacionArriba);

        pbSplash = findViewById(R.id.pbSplash);
        pbSplash.setAnimation(animacionAbajo);

        tvEstadoSplash = findViewById(R.id.tvEstadoSplash);
        tvEstadoSplash.setAnimation(animacionAbajo);

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);
        ubicacion = getIntent().getExtras().getString("ubicacion");
        actuales = getIntent().getExtras().getString("leidos");
        faltantes = getIntent().getExtras().getString("faltantes");
        cartonG = getIntent().getExtras().getString("cartonG");
        qr = getIntent().getExtras().getBoolean("QR");
    }

    private void cerradoCarton() {
        //Consultar la Api de cerradoCarton
        RequestCerradoCarton requestCerradoCarton = new RequestCerradoCarton(cedula, equipo, ubicacion, cartonG);
        Call<ResponseLogin> call = serviceRetrofit.doCerradoCarton(requestCerradoCarton);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                        toast(response.body().getRespuesta().getMensaje());
                        irCierreCarton();
                    }else{
                        mensaje = response.body().getRespuesta().getMensaje();
                        consultarFaltantesAndLeidos(mensaje);
                    }
                }else{
                    setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                    toast("Error de conexión con el servicio web base.");
                    irCierreCarton();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
                setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                toast("Error de conexión: " + t.getMessage());
                irCierreCarton();
            }
        });
    }

    private void consultarFaltantesAndLeidos(String mensaje) {
        setEstatus(getResources().getString(R.string.titulo_esperaSplash), getResources().getColor(R.color.azul_claro));
        //Consultar la Api de empezar cerrado
        RequestPinado requestPinado = new RequestPinado(cedula, equipo, ubicacion);
        Call<ResponseEmpezarCerrado> call = serviceRetrofit.doEmpezarCerrado(requestPinado);
        call.enqueue(new Callback<ResponseEmpezarCerrado>() {
            @Override
            public void onResponse(Call<ResponseEmpezarCerrado> call, Response<ResponseEmpezarCerrado> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                        mensajeDialogError(response.body().getRespuesta().getMensaje());
                    }else{
                        faltantes = response.body().getRespuesta().getEmpezarCerrado().getFaltantes();
                        actuales = response.body().getRespuesta().getEmpezarCerrado().getLeidos();
                        qr = response.body().getRespuesta().isQr();

                        if(faltantes.equals("-1") && actuales.equals("-1")){
                            setEstatus(getResources().getString(R.string.titulo_estadoSplash), getResources().getColor(R.color.amarillo));
                            consultarFaltantesAndLeidos(mensaje);
                        }else if(faltantes.equals("0") && actuales.equals("0")){
                            setEstatus(getResources().getString(R.string.titulo_cerradoSplash), getResources().getColor(R.color.verde_chillon));
                            irConfirmarCerrado();
                        }else if(!faltantes.equals("0") && !actuales.equals("-1")){
                            setEstatus(getResources().getString(R.string.titulo_cerradoSplash), getResources().getColor(R.color.verde_chillon));
                            pbSplash.setVisibility(View.GONE);
                            toast(mensaje);
                            irLectura();
                        }
                    }
                }else{
                    setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                    mensajeDialogError("Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEmpezarCerrado> call, Throwable t) {
                setEstatus(getResources().getString(R.string.titulo_errorSplash), getResources().getColor(R.color.rojo));
                LogFile.adjuntarLog("ErrorResponseEmpezarCerrado",t);
                mensajeDialogError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void irConfirmarCerrado() {
        Intent i = new Intent(this, ConfirmarCerrarPosicionActivity.class);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        i.putExtra("faltantes", (Serializable) faltantes);
        startActivity(i);
        finish();
    }

    private void irLectura() {
        Intent i;
        i = new Intent(this, LecturaActivity.class);
        i.putExtra("leidos", (Serializable) actuales);
        i.putExtra("faltantes", (Serializable) faltantes);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        i.putExtra("QR", (Serializable) qr);
        startActivity(i);
        finish();
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
    public void onBackPressed(){
    }

    //Mostrar un en barra inferior el estatus de la conexion al dispositivo
    private void setEstatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                tvEstadoSplash.setBackgroundColor(color);
                tvEstadoSplash.setText(statusMessage);
            }
        });
    }

    public void mensajeDialogError(String mensaje){
        int icon = R.drawable.vector_error;

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle(R.string.error)
                .setCancelable(false)
                .setMessage(mensaje)
                .setIcon(icon)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        irCierreCarton();
                        dialog.dismiss();
                    }
                });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void toast(String msj){
        Toast.makeText(SplashScreenActivity.this, msj, Toast.LENGTH_LONG).show();
    }
}