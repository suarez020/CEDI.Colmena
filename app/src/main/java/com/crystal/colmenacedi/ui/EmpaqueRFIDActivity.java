package com.crystal.colmenacedi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestCerradoCarton;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.response.ResponseFinAuditoria;
import com.crystal.colmenacedi.retrofit.response.cerradoRFID.GenericosCerradoRFID;
import com.crystal.colmenacedi.retrofit.response.cerradoRFID.ResponseCerradoRFID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpaqueRFIDActivity extends AppCompatActivity implements View.OnClickListener{
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etUbicacionEmpaqueRFID, etGenericoEmpaqueRFID;
    TextView tvTituloEanERFID,tvTituloTamano;
    RecyclerView rvEmpaqueRFID;
    Button btnTerminarEmpaqueRFID;
    GenericosCerradoRFID genericosCerradoRFID;
    String cedula, estacion, ubicacion, generico;
    boolean  consumirServicioapiCerradoRFID;
    Utilidades util;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empaque_rfidactivity);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_empaqueRFID);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));

        //Iniciar el cliente REST
        inicioRetrofit();
        //Asignar referencias
        findViews();
        //Eventos
        eventos();

        if(genericosCerradoRFID != null){
            mostrarConsulta();

        }
        if(consumirServicioapiCerradoRFID){
            generico = getIntent().getExtras().getString("cartonG");
            apiCerradoRFID();
        }
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        contexto = EmpaqueRFIDActivity.this;
        util = new Utilidades(contexto);
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        //todo: extras:
        ubicacion = getIntent().getExtras().getString("ubicacion");
        consumirServicioapiCerradoRFID = getIntent().getExtras().getBoolean("consumirServicioapiCerradoRFID");
        //todo: extras:
        genericosCerradoRFID = (GenericosCerradoRFID) getIntent().getSerializableExtra("genericosRFID");
        etUbicacionEmpaqueRFID = findViewById(R.id.etUbicacionEmpaqueRFID);
        etUbicacionEmpaqueRFID.setText(ubicacion);
        etGenericoEmpaqueRFID = findViewById(R.id.etGenericoEmpaqueRFID);
        etGenericoEmpaqueRFID.requestFocus();

        tvTituloEanERFID = findViewById(R.id.tvTituloEanERFID);
        tvTituloTamano= findViewById(R.id.tvTituloTamano);
        rvEmpaqueRFID = findViewById(R.id.rvEmpaqueRFID);
        rvEmpaqueRFID.setLayoutManager(new LinearLayoutManager(this));
        btnTerminarEmpaqueRFID = findViewById(R.id.btnTerminarEmpaqueRFID);
    }

    private void eventos() {
        btnTerminarEmpaqueRFID.setOnClickListener(this);
        //Eventos sobre el EditText generico
        etGenericoEmpaqueRFID.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etGenericoEmpaqueRFID.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    generico = etGenericoEmpaqueRFID.getText().toString().replaceAll("\\s","");
                    if (!generico.isEmpty()) {
                        irTamanoActivity();
                        //apiCerradoRFID();
                    }
                    return true;
                }
                return false;
            }
        });

        etGenericoEmpaqueRFID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    generico = etGenericoEmpaqueRFID.getText().toString().replaceAll("\\s","");
                    if (!generico.isEmpty()) {
                        irTamanoActivity();//primero elegimos tamaño
                        // apiCerradoRFID();
                    }
                }
                return handled;
            }
        });
    }

    private void irTamanoActivity(){
        //todo: si se lee bien vamos a pasar a la pantalla de tamano
/*        Intent i = new Intent(EmpaqueRFIDActivity.this, TamanoEmpaqueActivity.class);
        i.putExtra("cartonG",generico);
        i.putExtra("ubicacion",ubicacion);
        i.putExtra("activityRFID",true);
        //i.putExtra("genericosRFID", genericosCerradoRFID);
        startActivity(i);
        finish();*/
    }

    private void apiCerradoRFID() {
        RequestCerradoCarton requestCerradoRFID = new RequestCerradoCarton(cedula, estacion, ubicacion, generico);
        Call<ResponseCerradoRFID> call = serviceRetrofit.doCerradoRFID(requestCerradoRFID);
        call.enqueue(new Callback<ResponseCerradoRFID>() {
            @Override
            public void onResponse(Call<ResponseCerradoRFID> call, Response<ResponseCerradoRFID> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    util.toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                    }else{
                        mostrarConsulta();

                    }
                    etGenericoEmpaqueRFID.setText("");
                    etGenericoEmpaqueRFID.requestFocus();
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base.", contexto);
                }
            }

            @Override
            public void onFailure(Call<ResponseCerradoRFID> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseCerradoRFID",t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
            }
        });
    }

    private void mostrarConsulta() {
        tvTituloTamano.setVisibility(View.VISIBLE);
        tvTituloEanERFID.setVisibility(View.VISIBLE);
        rvEmpaqueRFID.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarEmpaqueRFID){
            apiTerminarRFID();
        }
    }

    private void apiTerminarRFID() {
        RequestPinado requestTerminarRFID = new RequestPinado(cedula, estacion, ubicacion);
        Call<ResponseFinAuditoria> call = serviceRetrofit.doTerminarRFID(requestTerminarRFID);
        call.enqueue(new Callback<ResponseFinAuditoria>() {
            @Override
            public void onResponse(Call<ResponseFinAuditoria> call, Response<ResponseFinAuditoria> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    util.toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                    }else{
                        irPrincipal();
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base.", contexto);
                }
            }

            @Override
            public void onFailure(Call<ResponseFinAuditoria> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseTerminarRFID",t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
            }
        });
    }

    private void irPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        irCerrarPosicion();
    }

    private void irCerrarPosicion() {
        Intent i = new Intent(this, EmpacarActivity.class);
        startActivity(i);
        finish();
    }
}