package com.crystal.colmenacedi.common;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.crystal.colmenacedi.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Utilidades {
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
}
