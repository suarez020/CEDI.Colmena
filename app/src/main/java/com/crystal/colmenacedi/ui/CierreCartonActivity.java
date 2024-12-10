package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class CierreCartonActivity extends AppCompatActivity implements View.OnClickListener {
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etPosicionCC, etGenericoCC, etActualesCC, etFaltantesCC;
    Button btnConfirmarCC;
    TextToSpeech speech;
    Context contexto;
    String cedula, equipo, ubicacion, cartonG, actuales, faltantes;
    boolean qr;
    boolean  continuarirSplashScreen;
    String tamanoElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cierre_carton);
            Utilidades.ocultarBarraEstado(getWindow());

            this.setTitle(R.string.menu_cierreCarton);
            Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
            inicioRetrofit();
            findViews();
            eventos();
        }catch (Exception e){
            LogFile.adjuntarLog("ErrorCierreCartonActivity", e);
            Log.e("LOGCAT", "ERROR: "+ e);
        }

        if(continuarirSplashScreen){
            btnConfirmarCC.callOnClick();
            irSplashScreen();
        }
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

        //todo: extras: tamano
        continuarirSplashScreen = getIntent().getExtras().getBoolean("continuarirSplashScreen");
        tamanoElegido = getIntent().getExtras().getString("tamanoElegido");
        cartonG=getIntent().getExtras().getString("cartonG");
        contexto = CierreCartonActivity.this;

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        ubicacion = getIntent().getExtras().getString("ubicacion");
        actuales = getIntent().getExtras().getString("leidos");
        faltantes = getIntent().getExtras().getString("faltantes");
        qr = getIntent().getExtras().getBoolean("QR");

        etPosicionCC = findViewById(R.id.etPosicionCC);
        etPosicionCC.setTextColor(R.color.opaco);
        etPosicionCC.setText(ubicacion);

        etGenericoCC = findViewById(R.id.etGenericoCC);
        etGenericoCC.requestFocus();

        etActualesCC = findViewById(R.id.etActualesCC);
        etActualesCC.setTextColor(R.color.opaco);
        etActualesCC.setText(actuales);

        etFaltantesCC = findViewById(R.id.etFaltantesCC);
        etFaltantesCC.setTextColor(R.color.opaco);
        etFaltantesCC.setText(faltantes);

        btnConfirmarCC = findViewById(R.id.btnConfirmarCC);
    }

    private void eventos() {
        etGenericoCC.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etGenericoCC.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cartonG = etGenericoCC.getText().toString().replaceAll("\\s","");
                    if (!cartonG.isEmpty()) {
                        //btnConfirmarCC.callOnClick();
                        irTamanoActivity();
                    }
                    return true;
                }
                return false;
            }
        });

        etGenericoCC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    cartonG = etGenericoCC.getText().toString().replaceAll("\\s","");
                    if(!cartonG.isEmpty()){
                        //btnConfirmarCC.callOnClick();
                        irTamanoActivity();
                    }
                }
                return handled;
            }
        });

        btnConfirmarCC.setOnClickListener(this);
    }

    private void irTamanoActivity(){
        //todo: si se lee bien vamos a pasar a la pantalla de tamano
/*        Intent i = new Intent(CierreCartonActivity.this, TamanoEmpaqueActivity.class);
        i.putExtra("leidos", (Serializable) actuales);
        i.putExtra("faltantes", (Serializable) faltantes);
        i.putExtra("ubicacion",ubicacion);
        i.putExtra("cartonG",cartonG);
        i.putExtra("QR", (Serializable) qr);
        i.putExtra("activityRFID",false);
        startActivity(i);
        finish();*/
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnConfirmarCC){
            if(!etGenericoCC.getText().toString().isEmpty()){
                cartonG = etGenericoCC.getText().toString();
                //irSplashScreen();
            }
        }
    }

    private void irSplashScreen() {
        //Intent i = new Intent(CierreCartonActivity.this, SplashScreenActivity.class);
        //i.putExtra("leidos", (Serializable) actuales);
        //i.putExtra("faltantes", (Serializable) faltantes);
        //i.putExtra("ubicacion", (Serializable) ubicacion);
        //i.putExtra("cartonG", (Serializable) cartonG);
        //i.putExtra("QR", (Serializable) qr);
        //startActivity(i);
        //finish();
    }

    private void irLectura() {
        Intent i;
        if(!qr){
            i = new Intent(this, LecturaDobleActivity.class);
        }else{
            i = new Intent(this, LecturaActivity.class);
        }
        i.putExtra("leidos", (Serializable) actuales);
        i.putExtra("faltantes", (Serializable) faltantes);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        i.putExtra("QR", (Serializable) qr);
        startActivity(i);
        finish();
    }

    private void msjToast(String msj){
        Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        irLectura();
    }
}