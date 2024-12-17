package com.crystal.colmenacedi.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;
import com.crystal.colmenacedi.common.LogFile;
import com.crystal.colmenacedi.common.SPM;
import com.crystal.colmenacedi.common.Utilidades;
import com.crystal.colmenacedi.retrofit.ClienteRetrofit;
import com.crystal.colmenacedi.retrofit.ServiceRetrofit;
import com.crystal.colmenacedi.retrofit.request.RequestExtraerPost;
import com.crystal.colmenacedi.retrofit.request.RequestTerminar;
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerGet;
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerPost;
import com.crystal.colmenacedi.retrofit.response.terminar.ResponseTerminar;
import com.crystal.colmenacedi.ui.adapter.ListaDeItemsRecyclerViewAdapter;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SepararActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rvDynamicItems;
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    Button btnTerminarSp;
    EditText etUnidadesLeidasSp , etUbicacionSp, etEanSp;
    TextView etUnidadesLeidas;
    String cedula, equipo, ubicacion, proceso, ean ;
    boolean consumirServicio = true;
    List<List<String>> listaItems1;
    Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separar);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle("Separar UND");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));
        inicioRetrofit();
        findViews();
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        contexto = SepararActivity.this;

        proceso = SPM.getString(Constantes.PROCESO);

        rvDynamicItems = findViewById(R.id.rvDynamicItemsSeparar);
        rvDynamicItems.setLayoutManager(new LinearLayoutManager(this));

        btnTerminarSp=findViewById(R.id.btnTerminarSp);

        etUbicacionSp = findViewById(R.id.etUbicacionSp);

        etEanSp = findViewById(R.id.etEanSp);
        etUnidadesLeidas = findViewById(R.id.tvUnidadesLeidas);
        etUnidadesLeidasSp = findViewById(R.id.etUnidadesLeidasSp);

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        etUbicacionSp.requestFocus(0);
        ocultarTeclado();
    }

    private void eventos() {
        btnTerminarSp.setOnClickListener(this);
        etUbicacionSp.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etUbicacionSp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etUbicacionSp.getText().toString().replaceAll("\\s","");
                    if (!ubicacion.isEmpty()) {
                        getExtraerLLenadoRV();
                    }
                    return true;
                }
                return false;
            }
        });
        etUbicacionSp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etUbicacionSp.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        getExtraerLLenadoRV();
                    }
                }
                return handled;
            }
        });
        etEanSp.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEanSp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ean = etEanSp.getText().toString().replaceAll("\\s","");
                    if (!ean.isEmpty()) {
                        servicioExtraerPostConteo();
                    }
                    return true;
                }
                return false;
            }
        });
        etEanSp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ean = etEanSp.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                        servicioExtraerPostConteo();
                    }
                }
                return handled;
            }
        });
    }

    private void getExtraerLLenadoRV() {
        if (consumirServicio) {
            ocultarTeclado();
            consumirServicio = false;
            Call<ResponseExtraerGet> responseExtraerGetCall = serviceRetrofit.doExtraerGet(ubicacion, proceso);
            responseExtraerGetCall.enqueue(new Callback<ResponseExtraerGet>() {
                @Override
                public void onResponse(Call<ResponseExtraerGet> call, Response<ResponseExtraerGet> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeDialog("Error", response.body().getErrors().getSource());
                            etUbicacionSp.setText("");
                            etUbicacionSp.requestFocus();
                        } else {
                            etUbicacionSp.setEnabled(false);
                            etEanSp.requestFocus();
                            mostrarCategorias();
                            listaItems1 = response.body().getData().getItems();
                            ListaDeItemsRecyclerViewAdapter categoriasAdapter = new ListaDeItemsRecyclerViewAdapter(listaItems1);
                            rvDynamicItems.setAdapter(categoriasAdapter);
                        }
                    } else {
                        mensajeDialog("Error", "Error de conexión con el servicio web base.");
                    }
                    consumirServicio = true;
                }

                @Override
                public void onFailure(Call<ResponseExtraerGet> call, Throwable t) {
                    LogFile.adjuntarLog("Extraer_Get", t);
                    mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    consumirServicio = true;
                }
            });
        }
    }

    private void mostrarCategorias(){
        rvDynamicItems.setVisibility(View.VISIBLE);
    }

    private void servicioExtraerPostConteo(){
            if (consumirServicio){
                ocultarTeclado();
                consumirServicio = false;
                RequestExtraerPost requestExtraerPost=new RequestExtraerPost(cedula,ean,ubicacion,proceso);
                Call<ResponseExtraerPost> call = serviceRetrofit.doExtraerPost(requestExtraerPost);
                call.enqueue(new Callback<ResponseExtraerPost>() {
                    @Override
                    public void onResponse(Call<ResponseExtraerPost> call, Response<ResponseExtraerPost> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            LogFile.adjuntarLog(response.body().getErrors().getSource());
                            if(response.body().getErrors().getStatus()){
                                Utilidades.mensajeDialog("Error", response.body().getErrors().getSource(), contexto);
                                etEanSp.setText("");
                                etEanSp.requestFocus();
                            }else{
                                etUnidadesLeidasSp.setText(response.body().getData().getUnidadesLeidas().toString());
                                etEanSp.setText("");
                                etEanSp.requestFocus();
                            }
                        }else{
                            Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base", contexto);
                        }
                        consumirServicio = true;
                    }

                    @Override
                    public void onFailure(Call<ResponseExtraerPost> call, Throwable t) {
                        LogFile.adjuntarLog("ErrorResponseLecturaEan",t);
                        Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
                        consumirServicio = true;
                    }
                });
            }
    }

    public void mensajeDialog(final String titulo, final String msj){

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
                        if(titulo.equals("Mensaje") && msj.equals("Separar")){
                            regresarPrincipal();
                        }
                    }
                });
        AlertDialog alerta = builder.create();
        if(!(SepararActivity.this.isFinishing())){
            alerta.show();
        }
    }

    @Override
    public void onBackPressed(){
        regresarPrincipal();
    }

    private void regresarPrincipal() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarSp)
        {
            serviceTerminarPost();
        }
    }

    private void serviceTerminarPost() {
        if(consumirServicio) {
            consumirServicio = false;
            cedula = SPM.getString(Constantes.CEDULA_USUARIO);
            RequestTerminar requestTerminar = new RequestTerminar(cedula, ubicacion, proceso);
            Call<ResponseTerminar> call = serviceRetrofit.doTerminar(requestTerminar);
            call.enqueue(new Callback<ResponseTerminar>() {
                @Override
                public void onResponse(Call<ResponseTerminar> call, Response<ResponseTerminar> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeSimpleDialog("Error", response.body().getErrors().getSource());
                        } else {
                            regresarPrincipal();
                        }
                    } else {
                        mensajeSimpleDialog("Error", "Error de conexión con el servicio web base. " + response.message());
                    }
                    consumirServicio=true;
                }

                @Override
                public void onFailure(Call<ResponseTerminar> call, Throwable t) {
                    LogFile.adjuntarLog("ErrorResponse/terminar_Post", t);
                    mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    consumirServicio=true;
                }
            });
        }
    }

    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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
        if(!(SepararActivity.this.isFinishing())){
            alerta.show();
        }
    }
}