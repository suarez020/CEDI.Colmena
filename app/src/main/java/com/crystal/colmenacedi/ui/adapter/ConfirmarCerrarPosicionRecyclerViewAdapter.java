package com.crystal.colmenacedi.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.colmenacedi.R;
import com.crystal.colmenacedi.common.Constantes;

import java.util.List;

public class ConfirmarCerrarPosicionRecyclerViewAdapter extends RecyclerView.Adapter<ConfirmarCerrarPosicionRecyclerViewAdapter.ViewHolder> {
    List<List<String>> ListInformacion;
    List<List<String>> listaEmpaqueRFID;
    int code;

    public  ConfirmarCerrarPosicionRecyclerViewAdapter(List<List<String>> listInformacion, List<List<String>> listaEmpaqueRFID, int code){
        this.ListInformacion = listInformacion;
        this.listaEmpaqueRFID = listaEmpaqueRFID;
        this.code = code;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public ConfirmarCerrarPosicionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_confirmar_cerrar_posicion, parent, false);
        return new ConfirmarCerrarPosicionRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ConfirmarCerrarPosicionRecyclerViewAdapter.ViewHolder holder, int position) {
        if(code == Constantes.CODE_EMPAQUE_RFID){
            holder.tvNumeroConfirmarCerrarP.setText(Integer.toString(position+1));
            holder.tvEanConfirmarCerrarP.setText(". "+listaEmpaqueRFID.get(position).get(0));//aunque sale en el centro.
            holder.tvTamano.setText(listaEmpaqueRFID.get(position).get(1));
        }else if(code == Constantes.CODE_CON_CERRAR_P){// en finalizar ubicacion se quiere mostrar
            holder.tvNumeroConfirmarCerrarP.setText(Integer.toString(position+1));
            holder.tvEanConfirmarCerrarP.setText(". "+ListInformacion.get(position).get(0));
            holder.tvCantidadConfirmarCerrarP.setText(ListInformacion.get(position).get(1));
            holder.tvTamano.setText(ListInformacion.get(position).get(2));
        }else {// Auditoria esto se muestra en pantalla no se desea mostrar tamaño en auditoria.
            holder.tvNumeroConfirmarCerrarP.setText(Integer.toString(position+1));
            holder.tvEanConfirmarCerrarP.setText(". "+ListInformacion.get(position).get(0));
            holder.tvCantidadConfirmarCerrarP.setText(ListInformacion.get(position).get(1));
        }

        if ((position % 2) == 0) {
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumeroConfirmarCerrarP, tvEanConfirmarCerrarP, tvCantidadConfirmarCerrarP, tvTamano;
        Context context;
        ConstraintLayout constraintLayout;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            tvNumeroConfirmarCerrarP = itemView.findViewById(R.id.tvNumeroConfirmarCerrarP);
            tvEanConfirmarCerrarP = itemView.findViewById(R.id.tvEanConfirmarCerrarP);
            tvCantidadConfirmarCerrarP = itemView.findViewById(R.id.tvCantidadConfirmarCerrarP);
            tvTamano = itemView.findViewById(R.id.tvTamano);
            constraintLayout = itemView.findViewById(R.id.constConfirmarCerrado);

            if(code == Constantes.CODE_EMPAQUE_RFID){
                tvCantidadConfirmarCerrarP.setVisibility(View.GONE);
            }
            if(code == Constantes.CODE_AUDITORIA){
                tvTamano.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(code == Constantes.CODE_EMPAQUE_RFID){
           return listaEmpaqueRFID.size();
        }else{
            return ListInformacion.size();
        }
    }
}
