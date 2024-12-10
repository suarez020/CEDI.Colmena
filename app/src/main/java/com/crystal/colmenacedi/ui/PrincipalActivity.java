package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestLogin;
import com.crystal.colmenacedi.retrofit.response.logout.ResponseLogout;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    Button btnUbicarUnd, btnEmpacarBol, btnSepararUnd;
    String cedula, estacion;
    TextToSpeech speech;
    Animation animacionArriba, animacionAbajo;
    CardView cvPrincipalE;
    TextView tvEquipoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_principal);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));

        //Agregar animaciones
        animaciones();
        //Iniciar el cliente REST
        inicioRetrofit();
        //Asignar referencias
        findViews();
        //Eventos
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        tvEquipoPrincipal = findViewById(R.id.tvEquipoPrincipal);
        tvEquipoPrincipal.setText(SPM.getString(Constantes.EQUIPO_API));
        speech =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });

        cvPrincipalE = findViewById(R.id.cvPrincipalE);
        btnUbicarUnd = findViewById(R.id.btnUbicarUnd);
        btnEmpacarBol = findViewById(R.id.btnEmpacarBol);
        btnSepararUnd = findViewById(R.id.btnSepararUnd);
    }

    private void eventos() {
        btnUbicarUnd.setOnClickListener(this);
        btnEmpacarBol.setOnClickListener(this);
        btnSepararUnd.setOnClickListener(this);
    }

    private void animaciones() {
        animacionArriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        animacionAbajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
    }

    //Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    //Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.mItemCerrarSesion) {
            cerrarSesion();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUbicarUnd:
                irUbicar(v);
                break;
            case R.id.btnEmpacarBol:
                irEmpacar();
                break;
            case R.id.btnSepararUnd:
                irSeparar();
                break;
        }
    }

    private void irUbicar(View view) {
        Intent i = new Intent(this, UbicarActivity.class);
        startActivity(i);
        finish();
    }

    private void irEmpacar() {
        Intent i = new Intent(this, EmpacarActivity.class);
        startActivity(i);
        finish();
    }

    private void irSeparar() {
        Intent i = new Intent(this, SepararActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        //Regresar al login
        cerrarSesion();
    }

    private void cerrarSesion() {
        int icon = R.drawable.vector_cerrar_sesion;
        //Alerta para confirmar el cierre de sesion
        AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
        builder.setTitle(getResources().getString(R.string.cerrar_sesion))
                .setCancelable(false)
                .setIcon(icon)
                .setMessage(getResources().getString(R.string.cerrar_sesion_confirmar))
                .setPositiveButton(R.string.confirmar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                apiCerrarSesion();
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void apiCerrarSesion() {
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        //Consumir servicio para cerrar sesion
        RequestLogin requestLogout = new RequestLogin(cedula, estacion);
        Call<ResponseLogout> call = serviceRetrofit.doLogout(requestLogout);
        call.enqueue(new Callback<ResponseLogout>() {
            @Override
            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                    }else{
                        if(response.body().getRespuesta().getLogout().isDesmatriculado()){
                            //Cerrar sesión
                            irLogin();
                        }else{
                            mensajeSimpleDialog("Alerta", "No puede cerrar sesión, el dispositivo esta sin desmatricular");
                        }
                    }
                }else{
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base. "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseLogout> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseLogout",t);
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
        if(!(PrincipalActivity.this.isFinishing())){
            alerta.show();
        }
    }

    private void irLogin() {
        Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

}