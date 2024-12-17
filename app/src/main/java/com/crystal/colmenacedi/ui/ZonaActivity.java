package com.crystal.colmenacedi.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
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
import com.crystal.colmenacedi.retrofit.response.extraer.ResponseExtraerPost;
import com.crystal.colmenacedi.retrofit.response.terminar.ResponseTerminar;
import com.crystal.colmenacedi.ui.adapter.ListaDeItemsRecyclerViewAdapter;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ZonaActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvDynamicItems;
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    EditText etUbicacionLectura, etEanLectura, etUnidades, etPaquetes;
    Button btnTerminarCajaLectura;
    String cedula, equipo, ubicacion, ean, actuales, faltantes,proceso;
    boolean consumirServicio = true;
    Context contexto;
    List<List<String>> listaItems2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_empacar_fin);
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

    @SuppressLint("ResourceAsColor")
    private void findViews() {
        contexto = ZonaActivity.this;

        rvDynamicItems = findViewById(R.id.rvDynamicItemsZonaEmpacar);
        rvDynamicItems.setLayoutManager(new LinearLayoutManager(this));

        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        equipo = SPM.getString(Constantes.EQUIPO_API);

        listaItems2 = (List<List<String>>) getIntent().getSerializableExtra("listaItems2");
        actuales = getIntent().getExtras().getString("leidos");
        faltantes = getIntent().getExtras().getString("faltantes");
        ubicacion = getIntent().getExtras().getString("ubicacion");

        ListaDeItemsRecyclerViewAdapter categoriasAdapter = new ListaDeItemsRecyclerViewAdapter(listaItems2);
        rvDynamicItems.setAdapter(categoriasAdapter);

        etUbicacionLectura = findViewById(R.id.etUbicacionLectura);
        etUbicacionLectura.setTextColor(R.color.opaco);
        etUbicacionLectura.setText(ubicacion);

        etEanLectura = findViewById(R.id.etEanLectura);

        etUnidades = findViewById(R.id.etActualesLectura);
        etUnidades.setTextColor(R.color.opaco);
        etUnidades.setText(actuales);

        etPaquetes = findViewById(R.id.etUnidadesLeidasSp);
        etPaquetes.setTextColor(R.color.opaco);
        etPaquetes.setText(faltantes);

        btnTerminarCajaLectura = findViewById(R.id.btnTerminarEmpaque);

        etEanLectura.requestFocus(0);
    }

    private void eventos() {
        btnTerminarCajaLectura.setOnClickListener(this);
        etEanLectura.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEanLectura.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ean = etEanLectura.getText().toString().replaceAll("\\s","");
                    if (!ean.isEmpty()) {
                        lecturaEan(); //ClickActionDown
                    }
                    return true;
                }
                return false;
            }
        });

        etEanLectura.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ean = etEanLectura.getText().toString().replaceAll("\\s","");
                    if(!ean.isEmpty()){
                        lecturaEan();//enterCode
                    }
                }
                return handled;
            }
        });
    }

    private void lecturaEan() {
      if (consumirServicio){
        ocultarTeclado();
        consumirServicio = false;
        RequestExtraerPost requestExtraerPost=new RequestExtraerPost(cedula,ean,"83429","43232");
        Call<ResponseExtraerPost> call = serviceRetrofit.doExtraerPost(requestExtraerPost);
        call.enqueue(new Callback<ResponseExtraerPost>() {
            @Override
            public void onResponse(Call<ResponseExtraerPost> call, Response<ResponseExtraerPost> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getErrors().getSource());
                    if(response.body().getErrors().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getErrors().getSource(), contexto);
                        etEanLectura.setText("");
                        etEanLectura.requestFocus();
                    }else{
                        etUnidades.setText(response.body().getData().getUnidadesLeidas().toString());
                        etPaquetes.setText(response.body().getData().getPaquetesLeidos().toString());
                        etEanLectura.setText("");
                        etEanLectura.requestFocus();
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnTerminarEmpaque){
            Terminar();
        }
    }

    private void Terminar() {
        proceso = SPM.getString(Constantes.PROCESO);
        cedula=SPM.getString(Constantes.CEDULA_USUARIO);

        RequestTerminar requestTerminar =new RequestTerminar(cedula,ubicacion,proceso);
        Call<ResponseTerminar> call = serviceRetrofit.doTerminar(requestTerminar);
        call.enqueue(new Callback<ResponseTerminar>() {
            @Override
            public void onResponse(Call<ResponseTerminar> call, Response<ResponseTerminar> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getErrors().getSource());
                    if(response.body().getErrors().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getErrors().getSource(), contexto);
                    }else{
                        //egresarPrincipal();
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base. "+ response.message(), contexto);
                }
            }

            @Override
            public void onFailure(Call<ResponseTerminar> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponse/terminar_Post",t);
                Utilidades.mensajeDialog("Error", "Error de conexión. "+ t.getMessage(), contexto);
            }
        });

        Intent i = new Intent(this, EmpacarActivity.class);
        startActivity(i);
        finish();
    }

    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onBackPressed(){}
}