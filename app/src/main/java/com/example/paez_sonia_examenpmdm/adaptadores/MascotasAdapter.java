package com.example.paez_sonia_examenpmdm.adaptadores;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Adaptador del REcycler view que nos muestr nombre del jinete,FEcha y hora
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.paez_sonia_examenpmdm.R;
import com.example.paez_sonia_examenpmdm.database.Mascota;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.MyViewHolder> {
    private  Context context;
    public List<Mascota> mascotaList;
    public MascotasAdapter(){
        this.mascotaList = new ArrayList<>();
    }
    public MascotasAdapter(Context context){

    }
    public MascotasAdapter(Context context, List<Mascota> reservasPaseos) {
        this.context = context;
        this.mascotaList = reservasPaseos;
    }

    public void setMascotas(List<Mascota> mascotas){
        mascotaList =mascotas;
        notifyDataSetChanged();
    }
    public Mascota getPaseoAt(int position) {
        return mascotaList.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView foto;



        public MyViewHolder(View itemview) {
            super(itemview);
            this.foto = itemview.findViewById(R.id.imageView);
            this.nombre=itemview.findViewById(R.id.nombremascota);



        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota, parent, false);
        return new MyViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(mascotaList !=null) {
            Mascota mascota = mascotaList.get(position);
            String uri= mascota.getImagen().toString();
            Picasso.get().load(uri).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(holder.foto);
            holder.nombre.setText(mascota.getNombre().toString());
            Log.e("adapter", mascota.getNombre());

        }else{
            holder.nombre.setText("No hay ninguna mascota");
        }

    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getItemCount() {
        if(mascotaList != null) {
            return mascotaList.size();
        }else{
            return 0;
        }
    }


}
