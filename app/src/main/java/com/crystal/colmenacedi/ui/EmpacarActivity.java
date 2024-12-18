package com.crystal.colmenacedi.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
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
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EmpacarActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvDynamicItems;
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etUbicacionEmpacar;
    Button btnEmpacar;
    String cedula , equipo , ubicacion , proceso ;
    boolean consumirServicio = true;
    List<List<String>> listaItems1;
    List<List<String>> listaItems2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_empacar);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle("Empacar Bol");
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

        rvDynamicItems = findViewById(R.id.rvDynamicItemsEmpacar);
        rvDynamicItems.setLayoutManager(new LinearLayoutManager(this));

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        etUbicacionEmpacar = findViewById(R.id.etUbicacionEmpacar);
        etUbicacionEmpacar.requestFocus();

        btnEmpacar = findViewById(R.id.btnEmpacar);
        btnEmpacar.setEnabled(false);
    }

    private void eventos() {
        btnEmpacar.setOnClickListener(this);
        etUbicacionEmpacar.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etUbicacionEmpacar.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ubicacion = etUbicacionEmpacar.getText().toString().replaceAll("\\s","");
                    if (!ubicacion.isEmpty()) {
                        etUbicacionEmpacar.setEnabled(false);
                        etUbicacionEmpacar.setTextColor(R.color.opaco);
                        LLenarMenu();
                    }
                    return true;
                }
                return false;
            }
        });

        etUbicacionEmpacar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ubicacion = etUbicacionEmpacar.getText().toString().replaceAll("\\s","");
                    if(!ubicacion.isEmpty()){
                        etUbicacionEmpacar.setEnabled(false);
                        etUbicacionEmpacar.setTextColor(R.color.opaco);
                        LLenarMenu();
                    }
                }
                return handled;
            }
        });
    }

    private void LLenarMenu() {
        if (consumirServicio) {
            consumirServicio=false;
            ocultarTeclado();
            btnEmpacar.setEnabled(true);
            Call<ResponseExtraerGet> responseExtraerGetCall = serviceRetrofit.doExtraerGet(ubicacion, proceso);
            responseExtraerGetCall.enqueue(new Callback<ResponseExtraerGet>() {
                @Override
                public void onResponse(Call<ResponseExtraerGet> call, Response<ResponseExtraerGet> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        LogFile.adjuntarLog(response.body().getErrors().getSource());
                        if (response.body().getErrors().getStatus()) {
                            mensajeDialog("Error", response.body().getErrors().getSource());
                            etUbicacionEmpacar.setText("");
                            etUbicacionEmpacar.requestFocus();
                        } else {
                            mostrarCategorias();
                            listaItems1 = response.body().getData().getItems();
                            listaItems2 = response.body().getData().getItems2();
                            ListaDeItemsRecyclerViewAdapter categoriasAdapter = new ListaDeItemsRecyclerViewAdapter(listaItems1);
                            rvDynamicItems.setAdapter(categoriasAdapter);
                        }
                    } else {
                        mensajeDialog("Error", "Error de conexión con el servicio web base.");
                    }
                    consumirServicio=true;
                }

                @Override
                public void onFailure(Call<ResponseExtraerGet> call, Throwable t) {
                    etUbicacionEmpacar.setText("");
                    etUbicacionEmpacar.requestFocus();
                    LogFile.adjuntarLog("@POST extraer _ cedula=XXX , ean=XXX , ubicacion=XXX , proceso=XXX;", t);
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

    private void mostrarCategorias(){
        rvDynamicItems.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEmpacar:
                irZonaActivity();
                break;
        }
    }

    private void irZonaActivity() {
        Intent i =  new Intent(this, ZonaActivity.class);
        i.putExtra("listaItems2", (Serializable) listaItems2);
        i.putExtra("ubicacion", (Serializable) ubicacion);
        startActivity(i);
        finish();
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
        if(!(EmpacarActivity.this.isFinishing())){
            alerta.show();
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
                        if(titulo.equals("Mensaje") && msj.equals("Empacado")){
                            regresarPrincipal();
                        }
                    }
                });
        AlertDialog alerta = builder.create();
        if(!(EmpacarActivity.this.isFinishing())){
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
}