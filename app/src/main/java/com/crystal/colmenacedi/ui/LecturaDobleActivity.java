package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestLecturaEan;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.response.cerrarCarton.ResponseCerrarCarton;
import com.crystal.colmenacedi.retrofit.response.lecturaEan.ResponseLecturaEan;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturaDobleActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    EditText etUbicacionLectura, etEanLectura, etActualesLectura, etFaltantesLectura;
    Button btnTerminarCajaLectura;
    TextToSpeech speech;
    TextView tvTituloRFIDLectura;
    ConstraintLayout cvPantallaLectura;

    //Variables
    String cedula, equipo, ubicacion, cartonG, actuales, faltantes;
    boolean qr;
    boolean consumirServicioLectura = true;;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle("naranja");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));

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

    @SuppressLint("ResourceAsColor")
    private void findViews() {
        contexto = LecturaDobleActivity.this;
        cvPantallaLectura = findViewById(R.id.cvPantallaLectura);
        cvPantallaLectura.setBackgroundColor(Color.parseColor("#FFA500"));
        speech =  new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        actuales = getIntent().getExtras().getString("leidos");
        faltantes = getIntent().getExtras().getString("faltantes");
        ubicacion = getIntent().getExtras().getString("ubicacion");
        qr = getIntent().getExtras().getBoolean("QR");

        etUbicacionLectura = findViewById(R.id.etUbicacionLectura);
        etUbicacionLectura.setTextColor(R.color.opaco);
        etUbicacionLectura.setText(ubicacion);
        tvTituloRFIDLectura = findViewById(R.id.tvTituloRFIDLectura);
        tvTituloRFIDLectura.setVisibility(View.VISIBLE);

        etEanLectura = findViewById(R.id.etEanLectura);

        etActualesLectura = findViewById(R.id.etActualesLectura);
        etActualesLectura.setTextColor(R.color.opaco);
        etActualesLectura.setText(actuales);

        etFaltantesLectura = findViewById(R.id.etFaltantesLectura);
        etFaltantesLectura.setTextColor(R.color.opaco);
        etFaltantesLectura.setText(faltantes);

        btnTerminarCajaLectura = findViewById(R.id.btnTerminarEmpacar);

        etEanLectura.requestFocus(0);
    }

    private void eventos() {
        etEanLectura.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEanLectura.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cartonG = etEanLectura.getText().toString().replaceAll("\\s","");
                    if (!cartonG.isEmpty()) {
                        lecturaEan();
                    }
                    return true;
                }
                return false;
            }
        });

        etEanLectura.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    cartonG = etEanLectura.getText().toString().replaceAll("\\s","");
                    if(!cartonG.isEmpty()){
                        //Consultar Api
                        lecturaEan();
                    }
                }
                return handled;
            }
        });
        btnTerminarCajaLectura.setOnClickListener(this);
    }

    private void lecturaEan() {
        if (consumirServicioLectura){
            consumirServicioLectura = false;
        //Consultar la Api de lecturaEan
        RequestLecturaEan requestLecturaEan = new RequestLecturaEan(cedula, equipo, cartonG, ubicacion);
        Call<ResponseLecturaEan> call = serviceRetrofit.doLecturaEan(requestLecturaEan);
        call.enqueue(new Callback<ResponseLecturaEan>() {
            @Override
            public void onResponse(Call<ResponseLecturaEan> call, Response<ResponseLecturaEan> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                        etEanLectura.setText("");
                        etEanLectura.requestFocus();
                    }else{
                        etActualesLectura.setText(response.body().getRespuesta().getEan().getLeidos());
                        etFaltantesLectura.setText(response.body().getRespuesta().getEan().getFaltantes());

                        if(response.body().getRespuesta().getEan().getFaltantes().equals("0")){
                            cerrarCarton();
                        }else{
                            etEanLectura.setText("");
                            etEanLectura.requestFocus();
                        }
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base", contexto);
                }
                consumirServicioLectura=true;
            }

            @Override
            public void onFailure(Call<ResponseLecturaEan> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
                consumirServicioLectura=true;
            }
        });
      }
    }

    private void cerrarCarton() {
        //Consultar la Api de cerrarCarton
        RequestPinado requestPinado = new RequestPinado(cedula, equipo, ubicacion);
        Call<ResponseCerrarCarton> call = serviceRetrofit.doCerrarCarton(requestPinado);
        call.enqueue(new Callback<ResponseCerrarCarton>() {
            @Override
            public void onResponse(Call<ResponseCerrarCarton> call, Response<ResponseCerrarCarton> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                        etEanLectura.setText("");
                        etEanLectura.requestFocus();
                    }else{
                        actuales = response.body().getRespuesta().getCerrarCarton().getLeidos();
                        faltantes = response.body().getRespuesta().getCerrarCarton().getFaltantes();

                        irCierreCarton();
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base", contexto);
                }
            }

            @Override
            public void onFailure(Call<ResponseCerrarCarton> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseCerrarCarton",t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
            }
        });
    }

    private void regresarCerrarPosicion() {
        Intent i = new Intent(this, EmpacarActivity.class);
        startActivity(i);
        finish();
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
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarEmpacar){
            cerrarCarton();
        }
    }

    @Override
    public void onBackPressed(){
        regresarCerrarPosicion();
    }
}