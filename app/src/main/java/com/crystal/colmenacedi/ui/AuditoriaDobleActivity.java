package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.crystal.colmenacedi.retrofit.response.auditoria.ResponseAuditoria;
import com.crystal.colmenacedi.retrofit.response.auditoria.RespuestaAuditoria;
import com.crystal.colmenacedi.retrofit.response.empezarAuditoria.ResponseEmpezarAuditoria;
import com.crystal.colmenacedi.retrofit.response.empezarAuditoria.RespuestaEmpezarAuditoria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuditoriaDobleActivity extends AppCompatActivity implements View.OnClickListener{
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etPosicionAuditoria, etEanAuditoria, etFaltantesAuditoria, etSobrantesAuditoria;
    TextView tvTituloSinRegistros, tvTituloRFIDAuditoria;
    String cedula, equipo, ubicacion, faltantes, ean, sobrantes;
    RecyclerView rvAuditoria;
    RespuestaEmpezarAuditoria respuestaEmpezarAuditoria;
    RespuestaAuditoria respuestaAuditoria;
    FloatingActionButton fabFinUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separar);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_auditoria);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
        inicioRetrofit();
        findViews();
        eventos();
        empezarAuditoria();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        ubicacion = getIntent().getExtras().getString("ubicacion");

        etPosicionAuditoria = findViewById(R.id.etUbicacion);
        etPosicionAuditoria.setText(ubicacion);
        etPosicionAuditoria.callOnClick();

        etEanAuditoria = findViewById(R.id.etEanAuditoria);

        etSobrantesAuditoria = findViewById(R.id.etSobrantesAuditoria);
        tvTituloSinRegistros = findViewById(R.id.tvTituloSinRegistros);
        tvTituloRFIDAuditoria = findViewById(R.id.tvTituloSeparar);
        tvTituloRFIDAuditoria.setVisibility(View.VISIBLE);

        rvAuditoria = findViewById(R.id.rvAuditoria);
        rvAuditoria.setLayoutManager(new LinearLayoutManager(this));

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        etEanAuditoria.requestFocus(0);
        //fabFinUbicacion = findViewById(R.id.fabFinUbicacion);
        fabFinUbicacion.setEnabled(false);
        //Ocultar el teclado de pantalla
        ocultarTeclado();
    }

    private void eventos() {
        //Eventos sobre el EditText Posicion
        etPosicionAuditoria.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etPosicionAuditoria.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etPosicionAuditoria.getText().toString().replaceAll("\\s","");
                    if (!ubicacion.isEmpty()) {
                        //Consultar Api
                        fabFinUbicacion.setEnabled(true);
                        empezarAuditoria();
                    }
                    return true;
                }
                return false;
            }
        });

        etPosicionAuditoria.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etPosicionAuditoria.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        //Consultar Api
                        fabFinUbicacion.setEnabled(true);
                        empezarAuditoria();
                    }
                }
                return handled;
            }
        });

        etEanAuditoria.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEanAuditoria.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ean = etEanAuditoria.getText().toString().replaceAll("\\s","");
                    if (!ean.isEmpty()) {
                        //Consultar Api
                        auditoria();
                    }
                    return true;
                }
                return false;
            }
        });

        etEanAuditoria.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ean = etEanAuditoria.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                        auditoria();
                    }
                }
                return handled;
            }
        });

        fabFinUbicacion.setOnClickListener(this);
    }

    private void empezarAuditoria() {

        //Ocultar el teclado de pantalla
        ocultarTeclado();

        RequestPinado requestPinado = new RequestPinado(cedula, equipo, ubicacion);
        Call<ResponseEmpezarAuditoria> call = serviceRetrofit.doEmpezarAuditoria(requestPinado);
        call.enqueue(new Callback<ResponseEmpezarAuditoria>() {
            @Override
            public void onResponse(Call<ResponseEmpezarAuditoria> call, Response<ResponseEmpezarAuditoria> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        etPosicionAuditoria.setText("");
                        etPosicionAuditoria.requestFocus();
                    }else{
                        respuestaEmpezarAuditoria = response.body().getRespuesta();

                        faltantes = respuestaEmpezarAuditoria.getFaltantes();
                        sobrantes = respuestaEmpezarAuditoria.getSobrantes();
                        if(faltantes.equals("0")){
                            regresarPrincipal();
                        }
                    }
                }else{
                    etPosicionAuditoria.setText("");
                    etPosicionAuditoria.requestFocus();
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseEmpezarAuditoria> call, Throwable t) {
                etPosicionAuditoria.setText("");
                etPosicionAuditoria.requestFocus();
                LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    private void auditoria() {

        //Ocultar el teclado de pantalla
        ocultarTeclado();

        RequestLecturaEan requestLecturaEan = new RequestLecturaEan(cedula, equipo, ean, ubicacion);
        Call<ResponseAuditoria> call = serviceRetrofit.doAuditoria(requestLecturaEan);
        call.enqueue(new Callback<ResponseAuditoria>() {
            @Override
            public void onResponse(Call<ResponseAuditoria> call, Response<ResponseAuditoria> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        etEanAuditoria.setText("");
                        etEanAuditoria.requestFocus();
                    }else{
                        respuestaAuditoria = response.body().getRespuesta();

                        faltantes = respuestaAuditoria.getFaltantes();
                        sobrantes = respuestaAuditoria.getSobrantes();

                        if(faltantes.equals("0")){
                            regresarPrincipal();
                        }else{
                            etEanAuditoria.setText("");
                            etEanAuditoria.requestFocus();

                            etFaltantesAuditoria.setText(faltantes);
                            etSobrantesAuditoria.setText(sobrantes);
                        }
                    }
                }else{
                    etEanAuditoria.setText("");
                    etEanAuditoria.requestFocus();
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseAuditoria> call, Throwable t) {
                etEanAuditoria.setText("");
                etEanAuditoria.requestFocus();
                LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
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
        if(!(AuditoriaDobleActivity.this.isFinishing())){
            alerta.show();
        }
    }

    private void regresarPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        //if(v.getId() == R.id.fabFinUbicacion){ introducirClave(); }
    }

    private void ocultarTeclado(){
        //Ocultar el teclado de pantalla
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed(){}
}