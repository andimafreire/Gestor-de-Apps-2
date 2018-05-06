package com.andima.gestordeapps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class CapturasRecyclerAdapter extends RecyclerView.Adapter<CapturasRecyclerAdapter.CapturasViewHolder> {

    private AppInfoActivity activity;
    private List<Bitmap> capturas;

    public CapturasRecyclerAdapter(List<Bitmap> pCapturas){
        capturas = pCapturas;
    }

    @NonNull
    @Override
    public CapturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.capturas_cardview,
                parent, false);
        return new CapturasViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturasViewHolder holder, int position) {
        holder.posicion = position;
        holder.captura.setImageBitmap(capturas.get(position));
    }

    @Override
    public int getItemCount() {
        return capturas.size();
    }

    public void setActivity(AppInfoActivity pA){
        activity = pA;
    }

    public class CapturasViewHolder extends RecyclerView.ViewHolder {

        private ImageView captura;
        private int posicion;

        public CapturasViewHolder(View itemView) {
            super(itemView);
            captura = itemView.findViewById(R.id.captura);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(posicion == 0){
                        activity.subirImagen();
                    }else {
                        CapturaFullScreenActivity.captura = capturas.get(posicion);
                        activity.startActivity(new Intent(activity.getBaseContext(), CapturaFullScreenActivity.class));
                    }
                }
            });
        }
    }
}
