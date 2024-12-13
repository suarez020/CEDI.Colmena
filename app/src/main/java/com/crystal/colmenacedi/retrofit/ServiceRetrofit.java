package com.crystal.colmenacedi.retrofit;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.retrofit.request.RequestCerradoCarton;
import com.crystal.colmenacedi.retrofit.request.RequestLecturaEan;
import com.crystal.colmenacedi.retrofit.request.RequestPinado;
import com.crystal.colmenacedi.retrofit.request.RequestLogin;
import com.crystal.colmenacedi.retrofit.request.RequestRecepcion;
import com.crystal.colmenacedi.retrofit.response.ResponseFinAuditoria;
import com.crystal.colmenacedi.retrofit.response.auditoria.ResponseAuditoria;
import com.crystal.colmenacedi.retrofit.response.cerradoRFID.ResponseCerradoRFID;
import com.crystal.colmenacedi.retrofit.response.empezarAuditoria.ResponseEmpezarAuditoria;
import com.crystal.colmenacedi.retrofit.response.configuracion.ResponseConfiguracion;
import com.crystal.colmenacedi.retrofit.response.empezarCerrado.ResponseEmpezarCerrado;
import com.crystal.colmenacedi.retrofit.response.finPinado.ResponseFinPinado;
import com.crystal.colmenacedi.retrofit.response.finalizarUbicacionInfo.ResponseFinalizarUbicacionInfo;
import com.crystal.colmenacedi.retrofit.response.iniciaPinado.ResponseIniciaPinado;
import com.crystal.colmenacedi.retrofit.response.inicio.ResponseInicio;
import com.crystal.colmenacedi.retrofit.response.lecturaEan.ResponseLecturaEan;
import com.crystal.colmenacedi.retrofit.response.login.ResponseLogin;
import com.crystal.colmenacedi.retrofit.response.loginGet.ResponseLoginGet;
import com.crystal.colmenacedi.retrofit.response.logout.ResponseLogout;
import com.crystal.colmenacedi.retrofit.response.ubicacion.ResponseUbicacion;
import com.crystal.colmenacedi.retrofit.response.ubicacionGet.ResponseUbicacionGet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceRetrofit {
    @Headers({"entorno: " + Constantes.ENTORNO_API})
    @GET("configuracion")
    Call<ResponseConfiguracion> doConfiguracion(@Query("mac") String mac, @Query("estacion") String estacion);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("inicio")
    Call<ResponseInicio> doInicio(@Query("id") String id, @Query("estacion") String estacion);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("login")
    Call<ResponseLoginGet> doLoginGet(@Query("mac") String mac, @Query("estacion") String estacion, @Query("id") String id);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("login")
    Call<ResponseLogin> doLogin(@Body RequestLogin requestLogin);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("ubicacion")
    Call<ResponseUbicacionGet> doUbicacionGET(@Query("ean") String ean);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("ubicacion")
    Call<ResponseUbicacion> doUbicacion(@Body RequestRecepcion requestRecepcion);





    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("iniciapinado")
    Call<ResponseIniciaPinado> doIniciaPinado(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("finpinado")
    Call<ResponseFinPinado> doFinPinado(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("empezarcerrado")
    Call<ResponseEmpezarCerrado> doEmpezarCerrado(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("lecturaean")
    Call<ResponseLecturaEan> doLecturaEan(@Body RequestLecturaEan requestLecturaEan);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("finalizarubicacioninfo")
    Call<ResponseFinalizarUbicacionInfo> doFinalizarUbicacionInfo(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("logout")
    Call<ResponseLogout> doLogout(@Body RequestLogin requestLogin);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("empezarauditoria")
    Call<ResponseEmpezarAuditoria> doEmpezarAuditoria(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("auditoria")
    Call<ResponseAuditoria> doAuditoria(@Body RequestLecturaEan requestLecturaEan);
    @POST("empezarcerradoRFID")
    Call<ResponseCerradoRFID> doEmpezarCerrdaoRFID(@Body RequestPinado requestEmpezarCerradoRFID);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("cerradoRFID")
    Call<ResponseCerradoRFID> doCerradoRFID(@Body RequestCerradoCarton requestcerradoRFID);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("terminarRFID")
    Call<ResponseFinAuditoria> doTerminarRFID(@Body RequestPinado requestTerminarRFID);
}
