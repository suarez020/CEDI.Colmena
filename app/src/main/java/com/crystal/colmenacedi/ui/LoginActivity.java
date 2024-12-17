package com.crystal.colmenacedi.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestLogin;
import com.crystal.colmenacedi.retrofit.response.inicio.ResponseInicio;
import com.crystal.colmenacedi.retrofit.response.login.ResponseLogin;
import com.crystal.colmenacedi.retrofit.response.loginGet.ResponseLoginGet;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    Button btnIngresarLogin;
    EditText etCedulaLogin;
    ProgressBar pbLogin;
    TextView tvEquipoLogin;
    String id, estacion, mac;
    boolean consumirServicio = true;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Utilidades.ocultarBarraEstado(getWindow());

        if(SPM.getString(Constantes.SERVIDOR_API) == null){
            irConfiguracion();
        }else{
            inicioRetrofit();
            findViews();
            eventos();
            inicio();
        }
    }

    private void inicio() {
        if (consumirServicio) {
            consumirServicio = false;
            pbLogin.setVisibility(View.VISIBLE);
            mac = SPM.getString(Constantes.MAC_EQUIPO);
            id = SPM.getString(Constantes.CEDULA_USUARIO);

            Call<ResponseInicio> inicio = serviceRetrofit.doInicio(mac,estacion);
            inicio.enqueue(new Callback<ResponseInicio>() {
                @Override
                public void onResponse(Call<ResponseInicio> call, Response<ResponseInicio> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeSimpleDialog("Error", response.body().getErrors().getSource());
                        } else {
                            if (response.body().getInicio().getLogin()) {
                                SPM.setString(Constantes.NOMBRE_USUARIO, response.body().getInicio().getNombre());
                                SPM.setString(Constantes.CEDULA_USUARIO, response.body().getInicio().getId());
                                irPrincipal();
                            }
                        }
                        pbLogin.setVisibility(View.GONE);
                    }
                    consumirServicio = true;
                }

                @Override
                public void onFailure(Call<ResponseInicio> call, Throwable t) {
                    pbLogin.setVisibility(View.GONE);
                    LogFile.adjuntarLog("ErrorResponseGetInicio", t);
                    mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    consumirServicio = true;
                }
            });
        }
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        estacion = SPM.getString(Constantes.EQUIPO_API);
        etCedulaLogin = findViewById(R.id.etCedulaLogin);
        etCedulaLogin.requestFocus();
        btnIngresarLogin = findViewById(R.id.btnIngresarLogin);
        tvEquipoLogin = findViewById(R.id.tvEquipoLogin);
        tvEquipoLogin.setVisibility(View.VISIBLE);
        tvEquipoLogin.setText(SPM.getString(Constantes.EQUIPO_API));
        pbLogin = findViewById(R.id.pbLogin);
        pbLogin.setVisibility(View.GONE);
    }

    private void eventos() {
        btnIngresarLogin.setOnClickListener(this);

        etCedulaLogin.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etCedulaLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    id = etCedulaLogin.getText().toString().replaceAll("\\s","");
                    if (!id.isEmpty()) {
                        validarLogin();
                    }
                    return true;
                }
                return false;
            }
        });

        etCedulaLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    id = etCedulaLogin.getText().toString().replaceAll("\\s","");
                    if(!id.isEmpty()){
                        validarLogin();
                    }
                }
                return handled;
            }
        });
    }

    public void validarLogin(){
        if(id.equals("135798642")){
            irConfiguracion();
        }else{
            iniciarSesion();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnIngresarLogin){
            pbLogin.setVisibility(View.VISIBLE);
            id = etCedulaLogin.getText().toString().replaceAll("\\s","");
            if(id.isEmpty()){
                etCedulaLogin.setError("Ingrese la cedula");
            }else {
                validarLogin();
            }
        }
    }

    private void iniciarSesion() {
        if (consumirServicio) {
            consumirServicio = false;
            pbLogin.setVisibility(View.VISIBLE);
            Call<ResponseLoginGet> loginGet = serviceRetrofit.doLoginGet(mac, estacion, id);
            loginGet.enqueue(new Callback<ResponseLoginGet>() {
                @Override
                public void onResponse(Call<ResponseLoginGet> call, Response<ResponseLoginGet> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeSimpleDialog("Error", response.body().getErrors().getSource());
                            consumirServicio=true;
                        } else {
                            consumirServicio=true;
                            consumirPostLogin();
                        }
                    } else {
                        mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                        SPM.setString(Constantes.CEDULA_USUARIO, "");
                        etCedulaLogin.setText("");
                        etCedulaLogin.requestFocus();
                        pbLogin.setVisibility(View.GONE);
                        consumirServicio=true;
                    }
                }

                @Override
                public void onFailure(Call<ResponseLoginGet> call, Throwable t) {
                    pbLogin.setVisibility(View.GONE);
                    SPM.setString(Constantes.CEDULA_USUARIO, "");
                    LogFile.adjuntarLog("ErrorResponseLogin", t);
                    mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    consumirServicio=true;
                }
            });
        }
    }

    private void consumirPostLogin(){
        if (consumirServicio) {
            consumirServicio=false;
            SPM.setString(Constantes.CEDULA_USUARIO, id);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etCedulaLogin.getWindowToken(), 0);
            RequestLogin requestLogin = new RequestLogin(id, estacion);
            Call<ResponseLogin> call = serviceRetrofit.doLogin(requestLogin);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeSimpleDialog("Error", response.body().getErrors().getSource());
                            pbLogin.setVisibility(View.GONE);
                            etCedulaLogin.setText("");
                            etCedulaLogin.requestFocus();
                        } else {
                            SPM.setString(Constantes.NOMBRE_USUARIO, response.body().getLogin().getNombre());
                            irPrincipal();
                        }
                    } else {
                        mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                        SPM.setString(Constantes.CEDULA_USUARIO, "");
                        etCedulaLogin.setText("");
                        etCedulaLogin.requestFocus();
                        pbLogin.setVisibility(View.GONE);
                    }
                    consumirServicio=true;
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    pbLogin.setVisibility(View.GONE);
                    SPM.setString(Constantes.CEDULA_USUARIO, "");
                    LogFile.adjuntarLog("ErrorResponseLogin", t);
                    mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    consumirServicio=true;
                }
            });
        }
    }


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
        if(!(LoginActivity.this.isFinishing())){
            alerta.show();
        }
    }

    private void irPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    private void irConfiguracion() {
        Intent i = new Intent(this, ConfiguracionActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}