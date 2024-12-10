package com.crystal.colmenacedi.common;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.crystal.colmenacedi.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Utilidades {
    private Context contexto;
    private TextToSpeech speech;

    public Utilidades(){
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.e("LOGCAT", ex.getMessage());
        }
        return "02:00:00:00:00:00";
    }

    public Utilidades(Context context) {
        this.contexto = context;
        speech =  new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });
    }

    public boolean valConstVaciasConf(){
        return SPM.getString(Constantes.SERVIDOR_API) != null && SPM.getString(Constantes.PUERTO_API) != null && SPM.getString(Constantes.EQUIPO_API) !=null;
    }

    public static boolean validarCamposVacios(String... dato) {
        int i = 0;
        while (i<dato.length && !dato[i].isEmpty()){
            i++;
        }
        return i==dato.length;
    }

    public static boolean validarLength(String dato, Integer min, Integer max){
        return dato.length() >= min && dato.length() <= max;
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public static void mensajeDialog(String titulo, String msj, Context context){

        int icon = R.drawable.vector_alerta;
        if (titulo.equals("Error")){
            icon = R.drawable.vector_error;
        } else if(titulo.equals("Éxito")){
            icon = R.drawable.vector_exito;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        alerta.show();
    }

    public static void ocultarBarraEstado(Window window){
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void toSpeech(final String msj) {
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
