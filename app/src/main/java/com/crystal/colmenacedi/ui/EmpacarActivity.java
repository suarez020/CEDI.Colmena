package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.response.cerradoRFID.GenericosCerradoRFID;
import com.crystal.colmenacedi.retrofit.response.cerradoRFID.ResponseCerradoRFID;
import com.crystal.colmenacedi.retrofit.response.empezarCerrado.ResponseEmpezarCerrado;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpacarActivity extends AppCompatActivity implements View.OnClickListener {
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etPosicionCP;
    Button btnEmpacar;
    String cedula, equipo, ubicacion, leidos, faltantes;
    ArrayList<String> nombresParametros;
    List<List<String>> genericosCerradoRFID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_posicion);
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

    private void findViews() {
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);
        nombresParametros = new ArrayList<>();

        etPosicionCP = findViewById(R.id.etPosicionCP);
        etPosicionCP.requestFocus();

        btnEmpacar = findViewById(R.id.btnEmpacar);
        btnEmpacar.setEnabled(false);
    }

    private void eventos() {
        btnEmpacar.setOnClickListener(this);
        etPosicionCP.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etPosicionCP.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etPosicionCP.getText().toString().replaceAll("\\s","");
                    if (!ubicacion.isEmpty()) {
                        etPosicionCP.setEnabled(false);
                        etPosicionCP.setTextColor(R.color.opaco);
                        habilitarBotones();
                    }
                    return true;
                }
                return false;
            }
        });

        etPosicionCP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etPosicionCP.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        etPosicionCP.setEnabled(false);
                        etPosicionCP.setTextColor(R.color.opaco);
                        habilitarBotones();
                    }
                }
                return handled;
            }
        });
    }

    private void habilitarBotones() {
        btnEmpacar.setEnabled(true);
    }

    private void desabilitarBotones() {
        btnEmpacar.setEnabled(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEmpacar:
                irZonaActivity();
                break;
        }
    }

    private void irZonaActivity() {
        Intent i =  new Intent(this, ZonaActivity.class);
        i.putExtra("leidos", (Serializable) leidos);
        i.putExtra("faltantes", (Serializable) faltantes);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        startActivity(i);
        finish();
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
        if(!(EmpacarActivity.this.isFinishing())){
            alerta.show();
        }
    }

   @Override
    public void onBackPressed(){
       regresarPrincipal();
    }

    private void regresarPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }
}