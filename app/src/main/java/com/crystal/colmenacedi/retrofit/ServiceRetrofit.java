package com.crystal.colmenacedi.retrofit;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.retrofit.request.RequestLecturaEan;
import com.crystal.colmenacedi.retrofit.request.RequestLogin;
import com.crystal.colmenacedi.retrofit.request.RequestUbicacion;
import com.crystal.colmenacedi.retrofit.response.configuracion.ResponseConfiguracion;
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerGet;
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
import retrofit2.http.PUT;
import retrofit2.http.Query;
public interface ServiceRetrofit {
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("inicio")
    Call<ResponseInicio> doInicio(@Query("id") String id, @Query("estacion") String estacion);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("logout")
    Call<ResponseLogout> doLogout(@Body RequestLogin requestLogin);
//--------------------------------------------------------------------------------------------------
    @Headers({"entorno: " + Constantes.ENTORNO_API})
    @GET("configuracion")
    Call<ResponseConfiguracion> doConfiguracion(@Query("mac") String mac, @Query("estacion") String estacion);
//--------------------------------------------------------------------------------------------------
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("login")
    Call<ResponseLoginGet> doLoginGet(@Query("mac") String mac, @Query("estacion") String estacion, @Query("id") String id);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("login")
    Call<ResponseLogin> doLogin(@Body RequestLogin requestLogin);
//--------------------------------------------------------------------------------------------------
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("ubicacion")
    Call<ResponseUbicacionGet> doUbicacionGET(@Query("ean") String ean);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @PUT("ubicacion")
    Call<ResponseUbicacion> doUbicacion(@Body RequestUbicacion requestUbicacion);
//4-------------------------------------------------------------------------------------------------
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("extraer")
    Call<ResponseExtraerGet> doExtraerGet(@Query("ubicacion") String ubicacion, @Query("proceso") String procesoBoton);
//5-------------------------------------------------------------------------------------------------

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("lecturaean")
    Call<ResponseLecturaEan> doLecturaEan(@Body RequestLecturaEan requestLecturaEan);
/*    @POST("empezarcerrado")
    Call<ResponseEmpezarCerrado> doEmpezarCerrado(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("empezarauditoria")
    Call<ResponseEmpezarAuditoria> doEmpezarAuditoria(@Body RequestPinado requestPinado);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("auditoria")
    Call<ResponseAuditoria> doAuditoria(@Body RequestLecturaEan requestLecturaEan);
    @POST("empezarcerradoRFID")
    Call<ResponseCerradoRFID> doEmpezarCerrdaoRFID(@Body RequestPinado requestEmpezarCerradoRFID);*/
}
