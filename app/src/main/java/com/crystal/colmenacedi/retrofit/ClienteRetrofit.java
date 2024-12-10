package com.crystal.colmenacedi.retrofit;

import android.util.Log;

import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.SPM;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static ClienteRetrofit instancia = null;
    private Retrofit retrofit;
    private ServiceRetrofit servicios;
    private String URLBase;

    public ClienteRetrofit(){
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        URLBase = SPM.getString(Constantes.API_POSSERVICE_URL);
        if (URLBase == null){
            URLBase = Constantes.API_POSSERVICE_URL;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(URLBase)
                .client(ok)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicios = retrofit.create(ServiceRetrofit.class);
    }

    public static ClienteRetrofit obtenerInstancia(){
        if (instancia == null){
            instancia = new ClienteRetrofit();
        }else{
            String andURL = SPM.getString(Constantes.API_POSSERVICE_URL);
            String insURL = instancia.retrofit.baseUrl().toString();
            if(!insURL.equals(andURL)){
                instancia = new ClienteRetrofit();
                Log.i("logcat", "new: "+instancia.retrofit.baseUrl()+ " - " + SPM.getString(Constantes.API_POSSERVICE_URL));
            }
        }
        return instancia;
    }

    public ServiceRetrofit obtenerServicios(){
        return servicios;
    }
}
