package com.crystal.colmenacedi.retrofit;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.retrofit.request.RequestConfiguracionPut;
import com.crystal.colmenacedi.retrofit.request.RequestExtraerPost;
import com.crystal.colmenacedi.retrofit.request.RequestLogin;
import com.crystal.colmenacedi.retrofit.request.RequestTerminar;
import com.crystal.colmenacedi.retrofit.request.RequestUbicacion;
import com.crystal.colmenacedi.retrofit.response.configuracion.ResponseConfiguracion;
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerGet;
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerPost;
import com.crystal.colmenacedi.retrofit.response.inicio.ResponseInicio;
import com.crystal.colmenacedi.retrofit.response.login.ResponseLogin;
import com.crystal.colmenacedi.retrofit.response.loginGet.ResponseLoginGet;
import com.crystal.colmenacedi.retrofit.response.logout.ResponseLogout;
import com.crystal.colmenacedi.retrofit.response.terminar.ResponseTerminar;
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
    @PUT("logout")
    Call<ResponseLogout> doLogout(@Body RequestLogin requestLogin);
//--------------------------------------------------------------------------------------------------
    @Headers({"entorno: " + Constantes.ENTORNO_API})
    @GET("configuracion")
    Call<ResponseConfiguracion> doConfiguracion(@Query("mac") String mac, @Query("estacion") String estacion);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @PUT("configuracion")
    Call<ResponseConfiguracion> doConfiguracionPut(@Body RequestConfiguracionPut requestConfiguracionPut);
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
    Call<ResponseExtraerGet> doExtraerGet(@Query("ubicacion") String ubicacion, @Query("proceso") String proceso);
//5-------------------------------------------------------------------------------------------------
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("extraer")
    Call<ResponseExtraerPost> doExtraerPost(@Body RequestExtraerPost requestExtraerPost);
    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("terminar")
    Call<ResponseTerminar> doTerminar(@Body RequestTerminar requestTerminar);
}
