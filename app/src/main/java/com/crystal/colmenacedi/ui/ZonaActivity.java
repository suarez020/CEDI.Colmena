package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.crystal.colmenacedi.retrofit.response.lecturaEan.ResponseLecturaEan;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZonaActivity extends AppCompatActivity implements View.OnClickListener {
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etUbicacionLectura, etEanLectura, etActualesLectura, etFaltantesLectura;
    Button btnTerminarCajaLectura;
    String cedula, equipo, ubicacion, cartonG, actuales, faltantes;
    boolean consumirServicio = true;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona);
        Utilidades.ocultarBarraEstado(getWindow());
        this.setTitle("Empacar Bol");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
        inicioRetrofit();
        findViews();
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    @SuppressLint("ResourceAsColor")
    private void findViews() {
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        actuales = getIntent().getExtras().getString("leidos");
        faltantes = getIntent().getExtras().getString("faltantes");
        ubicacion = getIntent().getExtras().getString("ubicacion");

        etUbicacionLectura = findViewById(R.id.etUbicacionLectura);
        etUbicacionLectura.setTextColor(R.color.opaco);
        etUbicacionLectura.setText(ubicacion);

        etEanLectura = findViewById(R.id.etEanLectura);

        etActualesLectura = findViewById(R.id.etActualesLectura);
        etActualesLectura.setTextColor(R.color.opaco);
        etActualesLectura.setText(actuales);

        etFaltantesLectura = findViewById(R.id.etFaltantesLectura);
        etFaltantesLectura.setTextColor(R.color.opaco);
        etFaltantesLectura.setText(faltantes);

        btnTerminarCajaLectura = findViewById(R.id.btnTerminarEmpaque);

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
                        lecturaEan(); //ClickActionDown
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
                        lecturaEan();//enterCode
                    }
                }
                return handled;
            }
        });

        btnTerminarCajaLectura.setOnClickListener(this);
    }

    private void lecturaEan() {
      if (consumirServicio){
          consumirServicio = false;
        //Consultar la Api de lecturaEan
        RequestLecturaEan requestLecturaEan = new RequestLecturaEan(cedula, equipo, cartonG, ubicacion);
        Call<ResponseLecturaEan> call = serviceRetrofit.doLecturaEan(requestLecturaEan);
        call.enqueue(new Callback<ResponseLecturaEan>() {
            @Override
            public void onResponse(Call<ResponseLecturaEan> call, Response<ResponseLecturaEan> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                        etEanLectura.setText("");
                        etEanLectura.requestFocus();
                    }else{
                        etActualesLectura.setText(response.body().getRespuesta().getEan().getLeidos());
                        etFaltantesLectura.setText(response.body().getRespuesta().getEan().getFaltantes());

                        if(response.body().getRespuesta().getEan().getFaltantes().equals("0")){
                            Terminar();
                        }else{
                            etEanLectura.setText("");
                            etEanLectura.requestFocus();
                        }
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base", contexto);
                }
                consumirServicio = true;
            }

            @Override
            public void onFailure(Call<ResponseLecturaEan> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
                consumirServicio = true;
            }
        });
      }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarEmpaque){
            Terminar();
        }
    }

    private void Terminar() {
        Intent i = new Intent(this, EmpacarActivity.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed(){}
}