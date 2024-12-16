package com.crystal.colmenacedi.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import java.util.Objects;
public class AuditoriaDobleActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separar);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_auditoria);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
    }

    private void empezarAuditoria() {

        //Ocultar el teclado de pantalla
        ocultarTeclado();

/*        RequestPinado requestPinado = new RequestPinado(cedula, equipo, ubicacion);
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
        */
     }

    private void auditoria() {

        //Ocultar el teclado de pantalla
        ocultarTeclado();

/*        RequestLecturaEan requestLecturaEan = new RequestLecturaEan(cedula, equipo, ean, ubicacion);
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
        });*/
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