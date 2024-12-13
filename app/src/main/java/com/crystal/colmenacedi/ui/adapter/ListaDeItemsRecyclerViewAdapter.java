package com.crystal.colmenacedi.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.colmenacedi.R;

import java.util.ArrayList;
import java.util.List;

public class ListaDeItemsRecyclerViewAdapter extends RecyclerView.Adapter<ListaDeItemsRecyclerViewAdapter.ViewHolder> {

    private List<List<String>> itemsList;
    private ArrayList<Integer> selectedItems = new ArrayList<>();

    public ListaDeItemsRecyclerViewAdapter(List<List<String>> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> item = itemsList.get(position);
        holder.tvItemKey.setText(item.get(0)); // Primera parte del par clave-valor
        holder.tvItemValue.setText(item.get(1)); // Segunda parte del par clave-valor

        // Actualizar el color del fondo basado en la selección
        if (selectedItems.contains(position)) {
            holder.constraintLayout.setBackgroundColor(Color.GREEN); // Color para elementos seleccionados
        } else {
            holder.constraintLayout.setBackgroundColor(Color.WHITE); // Color para elementos no seleccionados
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.contains(position)) {
                selectedItems.remove(Integer.valueOf(position));
                holder.constraintLayout.setBackgroundColor(Color.WHITE);
            } else {
                selectedItems.add(position);
                holder.constraintLayout.setBackgroundColor(Color.GREEN);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<Integer> selectedItems) {
        this.selectedItems = selectedItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemKey, tvItemValue;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemKey = itemView.findViewById(R.id.tvItemKey);
            tvItemValue = itemView.findViewById(R.id.tvItemValue);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutItem);
        }
    }
}
