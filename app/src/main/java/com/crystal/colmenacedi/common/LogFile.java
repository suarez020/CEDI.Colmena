package com.crystal.colmenacedi.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {

    private LogFile() {}

    @SuppressLint("SimpleDateFormat")
    public static void adjuntarLog(String text) {

        if (ContextCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) MyApp.getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constantes.PERMISO_WRITE_EXTERNAL_STORAGE);
        } else {
            //Obtener fecha y hora
            SimpleDateFormat currentDate = new SimpleDateFormat(Constantes.FORMATO_FECHA_SIMPLE);
            Date todayDate = new Date();

            //Crear ruta "Nombre a elegir (NOMBRE_CARPETA)" si no existe en el almacenamiento interno
            File dirPosMobile = new File(Environment.getExternalStorageDirectory(),Constantes.NOMBRE_CARPETA);
            if (!dirPosMobile.exists())
                if (!dirPosMobile.mkdirs()){
                    android.util.Log.e("LogFile", "No se pudo crear el directorio");
                    return;
                }

            //Creando archivo txt por dia de nombre yyyyMMddlog.txt
            String ruta = dirPosMobile.toString() + "/" + currentDate.format(todayDate) + "log.txt";
            File logFile = new File(ruta);

            //Abriendo el archivo
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                    android.util.Log.e("LogFile","No se pudo crear el log - "+e.getMessage());
                    return;
                }
            }

            //Escriendo en el archivo y despues cerrandolo
            try{
                //Fecha y hora de la escritura
                currentDate = new SimpleDateFormat(Constantes.FORMATO_FECHA_LARGO);
                todayDate = new Date();
                String fechahora = currentDate.format(todayDate);
                text = fechahora +": "+text;

                //BufferedWriter para establecer agregar al indicador de archivo
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.newLine();
                buf.close();
            }catch (IOException e){
                e.printStackTrace();
                android.util.Log.e("LogFile", "No se pudo realizar la escritura en el log - "+e.getMessage());
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static void adjuntarLog(String lugarError, Throwable text) {

        if (ContextCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) MyApp.getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constantes.PERMISO_WRITE_EXTERNAL_STORAGE);
        }else{
            //Obtener fecha y hora
            SimpleDateFormat currentDate = new SimpleDateFormat(Constantes.FORMATO_FECHA_SIMPLE);
            Date todayDate = new Date();

            //Crear ruta "Nombre a elegir (NOMBRE_CARPETA)" si no existe en el almacenamiento interno
            File dirPosMobile = new File(Environment.getExternalStorageDirectory(),Constantes.NOMBRE_CARPETA);
            if (!dirPosMobile.exists())
                if (!dirPosMobile.mkdirs()){
                    android.util.Log.e("LogFile", "No se pudo crear el directorio");
                    return;
                }

            //Creando archivo txt por dia de nombre yyyyMMddlog.txt
            String ruta = dirPosMobile.toString() + "/" + currentDate.format(todayDate) + "log.txt";
            File logFile = new File(ruta);

            //Abriendo el archivo
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                    android.util.Log.e("LogFile","No se pudo crear el log - "+e.getMessage());
                    return;
                }
            }


            //Escriendo en el archivo y despues cerrandolo
            try{
                //Sacar el texto
                StringBuilder sStackTrace = new StringBuilder();
                for(StackTraceElement line : text.getStackTrace()){
                    sStackTrace.append(line.toString()).append("\n");
                }

                //Fecha y hora de la escritura
                currentDate = new SimpleDateFormat(Constantes.FORMATO_FECHA_LARGO);
                todayDate = new Date();
                String fechahora = currentDate.format(todayDate);
                String texto = fechahora +":"+lugarError+":\n"+text.getMessage()+"\n"+sStackTrace;

                //BufferedWriter para establecer agregar al indicador de archivo
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(texto);
                buf.newLine();
                buf.close();
            }catch (IOException e){
                e.printStackTrace();
                android.util.Log.e("LogFile", "No se pudo realizar la escritura en el log - "+e.getMessage());
            }
        }
    }
}
