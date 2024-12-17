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
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerGet;
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
    EditText etUnidadesLeidasSp , etUbicacionSp, etEanAuditoria;
    TextView etUnidadesLeidas;
    String cedula, equipo, ubicacion, proceso, ean , faltantes, sobrantes;
    List<List<String>> listaItems1;
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
        proceso = SPM.getString(Constantes.PROCESO);

        rvDynamicItems = findViewById(R.id.rvDynamicItemsSeparar);
        rvDynamicItems.setLayoutManager(new LinearLayoutManager(this));

        btnTerminarSp=findViewById(R.id.btnTerminarSp);

        etUbicacionSp = findViewById(R.id.etUbicacionSp);

        etEanAuditoria = findViewById(R.id.etEanSp);
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
        etEanAuditoria.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEanAuditoria.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ean = etEanAuditoria.getText().toString().replaceAll("\\s","");
                    if (!ean.isEmpty()) {
                        auditoria();
                    }
                    return true;
                }
                return false;
            }
        });
        etEanAuditoria.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ean = etEanAuditoria.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                        auditoria();
                    }
                }
                return handled;
            }
        });
    }

    private void getExtraerLLenadoRV() {
        ocultarTeclado();
        Call<ResponseExtraerGet> responseExtraerGetCall = serviceRetrofit.doExtraerGet(ubicacion,proceso);
        responseExtraerGetCall.enqueue(new Callback<ResponseExtraerGet>() {
            @Override
            public void onResponse(Call<ResponseExtraerGet> call, Response<ResponseExtraerGet> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    //LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if (response.body().getErrors().getStatus()) {
                        mensajeDialog("Error", response.body().getErrors().getSource());
                        etUbicacionSp.setText("");
                        etUbicacionSp.requestFocus();
                    } else {
                        mostrarCategorias();
                        listaItems1= response.body().getData().getItems();
                        ListaDeItemsRecyclerViewAdapter categoriasAdapter = new ListaDeItemsRecyclerViewAdapter(listaItems1);
                        rvDynamicItems.setAdapter(categoriasAdapter);
                    }
                } else {
                    mensajeDialog("Error", "Error de conexión con el servicio web base.");
                }
            }

            @Override
            public void onFailure(Call<ResponseExtraerGet> call, Throwable t) {
                 //etEanAuditoria.setText("");
                //etEanAuditoria.requestFocus();
                LogFile.adjuntarLog("Extraer_Get",t);
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    private void mostrarCategorias(){
        rvDynamicItems.setVisibility(View.VISIBLE);
    }

    private void auditoria() {
        //Ocultar el teclado de pantalla
        ocultarTeclado();

        /*
        RequestLecturaEan requestLecturaEan = new RequestLecturaEan(cedula, equipo, ean, ubicacion);
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

    private void pasarAuditoriaDoble() {
        /*
        Intent i = new Intent(this, AuditoriaDobleActivity.class);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        startActivity(i);
        finish();
        */
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
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

    }

    private void ocultarTeclado(){
        //Ocultar el teclado de pantalla
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