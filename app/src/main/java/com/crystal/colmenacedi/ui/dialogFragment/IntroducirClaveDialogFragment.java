package com.crystal.colmenacedi.ui.dialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestFinUbicacion;
import com.crystal.colmenacedi.retrofit.response.ResponseFinAuditoria;
import com.crystal.colmenacedi.retrofit.response.finUbicacion.ResponseFinUbicacion;
import com.crystal.colmenacedi.ui.PrincipalActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroducirClaveDialogFragment extends DialogFragment {

    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private EditText etClaveDF;
    TextToSpeech speech;

    //Variables
    String cedula, equipo, ubicacion, clave;
    Context contexto;
    String claveActivity;

    public IntroducirClaveDialogFragment(String ubicacion, String claveActivity) {
        this.ubicacion = ubicacion;
        this.claveActivity = claveActivity;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.txt_mensajeIntroducirClave)
                .setTitle(R.string.txt_tituloIntroducirClave)
                .setCancelable(false)
                .setPositiveButton(R.string.txt_btnConfirmarClave, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.txt_btnCancelarClave, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_fragment_introducir_clave, null);

        contexto = view.getContext();

        //Iniciar el cliente REST
        inicioRetrofit();
        //Asignar referencias
        findViews();
        //Eventos
        eventos();

        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void eventos() {
        etClaveDF.requestFocus();
        //Eventos sobre el EditText posición
        etClaveDF.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etClaveDF.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    clave = etClaveDF.getText().toString().replaceAll("\\s","");
                    if (!clave.isEmpty()) {
                        mostrarDialog();
                    }
                    return true;
                }
                return false;
            }
        });

        etClaveDF.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    clave = etClaveDF.getText().toString().replaceAll("\\s","");
                    if(!clave.isEmpty()){
                        mostrarDialog();
                    }
                }
                return handled;
            }
        });
    }

    public void mostrarDialog(){
        AlertDialog d = (AlertDialog)getDialog();
        assert d != null;
        Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.callOnClick();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        speech =  new TextToSpeech(contexto, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });

        etClaveDF = view.findViewById(R.id.etClaveDF);
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        d.setCancelable(false);
        {
            assert d != null;
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clave = etClaveDF.getText().toString();
                    if(clave.isEmpty()){
                        mensajeFab("Error", "Introduzca la contraseña", v);
                    }else if(claveActivity.equals(Constantes.ACTIVITY_AUDITORIA)){
                        finAuditoria();
                    }else{
                        finUbicacion();
                    }
                }
            });
        }
    }

    private void finAuditoria(){
        RequestFinUbicacion requestFinAuditoria = new RequestFinUbicacion(cedula, equipo, ubicacion, clave);
        Call<ResponseFinAuditoria> baseCall = serviceRetrofit.doFinAuditoria(requestFinAuditoria);
        baseCall.enqueue(new Callback<ResponseFinAuditoria>() {
            @Override
            public void onResponse(Call<ResponseFinAuditoria> call, Response<ResponseFinAuditoria> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeDialog("Error", response.body().getRespuesta().getMensaje());
                        etClaveDF.setText("");
                        etClaveDF.requestFocus();
                    }else {
                        mensajeSimpleDialog(response.body().getRespuesta().getMensaje());
                    }
                }else{
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseFinAuditoria> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseFinAuditoria: "+call + t);
                mensajeDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    private void finUbicacion(){
        //Ocultar el teclado de pantalla
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etClaveDF.getWindowToken(), 0);
        //Consultar Api finalizarUbicacion
        RequestFinUbicacion requestFinUbicacion = new RequestFinUbicacion(cedula, equipo, ubicacion, clave);
        Call<ResponseFinUbicacion> call = serviceRetrofit.doFinUbicacion(requestFinUbicacion);
        call.enqueue(new Callback<ResponseFinUbicacion>() {
            @Override
            public void onResponse(Call<ResponseFinUbicacion> call, Response<ResponseFinUbicacion> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    toSpeech(response.body().getRespuesta().getVoz());
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeDialog("Error", response.body().getRespuesta().getMensaje());
                        etClaveDF.setText("");
                        etClaveDF.requestFocus();
                    }else {
                        mensajeSimpleDialog(response.body().getRespuesta().getMensaje());
                    }
                }else{
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseFinUbicacion> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseFinUbicacion: "+call + t);
                mensajeDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
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

    private void irPrincipal(){
        Intent i = new Intent(getContext(), PrincipalActivity.class);
        startActivity(i);
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public void mensajeSimpleDialog(String msj){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setMessage(msj)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog d = (AlertDialog)getDialog();
                        assert d != null;
                        d.dismiss();
                        Intent intent = requireActivity().getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        requireActivity().finish();
                        irPrincipal();
                    }
                });
        androidx.appcompat.app.AlertDialog alerta = builder.create();
        alerta.show();
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public void mensajeDialog(String titulo, String msj){

        int icon = R.drawable.vector_alerta;
        if (titulo.equals(getResources().getString(R.string.error))) {
            icon = R.drawable.vector_error;
        } else if(titulo.equals(getResources().getString(R.string.exito))){
            icon = R.drawable.vector_exito;
        } else if(titulo.equals(getResources().getString(R.string.mensaje))){
            icon = R.drawable.vector_mensaje;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle(titulo)
                .setCancelable(false)
                .setMessage(msj)
                .setIcon(icon)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        androidx.appcompat.app.AlertDialog alerta = builder.create();
        alerta.show();
    }

    @SuppressLint("WrongConstant")
    private void mensajeFab(String mode, String msj, View v) {
        if(mode.equals(getResources().getString(R.string.error))){
            Snackbar.make(v, msj, Snackbar.LENGTH_SHORT)
                    .setTextColor(getResources().getColor(R.color.white))
                    .setAction("Action", null).show();
        }else if(mode.equals(getResources().getString(R.string.exito))){
            Snackbar.make(v, msj, Snackbar.LENGTH_SHORT)
                    .setTextColor(getResources().getColor(R.color.verde))
                    .setAction("Action", null).show();
        }
    }
}
