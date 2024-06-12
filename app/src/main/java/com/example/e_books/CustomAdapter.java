package com.example.e_books;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<Datos> itemList;
    private int tipoVista;
    private FirestoreHelper fh;

    private FirebaseFirestore db;

    private SelectedListener listener;

    public CustomAdapter(List<Datos> itemList, int Vista, Context context, SelectedListener listener) {

        this.itemList = itemList;
        this.tipoVista = Vista;
        this.context = context;
        this.listener = listener;
    }

    public void setItemList(List<Datos> itemList) {
        this.itemList = itemList;
    }

    private static final int VIEW_TYPE1 = 1;
    private static final int VIEW_TYPE2 = 2;
    private static final int VIEW_TYPE3 = 3;
    private static final int VIEW_TYPE4 = 4;
//    private static final int VIEW_TYPE5 = 5;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgv;
        TextView txtv, likes, txtv2, txtv3;
        ImageButton imgbtn;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            imgv = itemView.findViewById(R.id.imagen);
            txtv = itemView.findViewById(R.id.texto);
            txtv2 = itemView.findViewById(R.id.texto2);
            txtv3 = itemView.findViewById(R.id.texto3);
            imgbtn = itemView.findViewById(R.id.imgbtn);
            likes = itemView.findViewById(R.id.likes);
            view = itemView.findViewById(R.id.lytselect);


        }
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (tipoVista == VIEW_TYPE1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listv1, parent, false);
            return new ViewHolder(view);
        }
        if (tipoVista == VIEW_TYPE2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listv2, parent, false);
            return new ViewHolder(view);
        }

        if (tipoVista == VIEW_TYPE3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listv3, parent, false);
            return new ViewHolder(view);
        }

        if (tipoVista == VIEW_TYPE4) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listv4, parent, false);
            return new ViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listv1, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        fh = new FirestoreHelper(context);
        Datos d = itemList.get(position);
        Glide.with(holder.imgv.getContext())
                .load(d.getPortada())
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgv);
        holder.txtv.setText(d.getTitulo());

        if (tipoVista == VIEW_TYPE2) {
            fh.librosAmados().observe((LifecycleOwner) context, librosPreferidos -> {
                boolean esLibroPreferido = librosPreferidos.contains(d.getId());
                if (esLibroPreferido) {
                    holder.imgbtn.setImageResource(R.drawable.filled_corazon);
                } else {
                    holder.imgbtn.setImageResource(R.drawable.corazon);
                }
            });

            holder.imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseFirestore.getInstance();
                    LiveData<ArrayList<String>> librosAmadosLiveData = fh.librosAmados();
                    librosAmadosLiveData.observe((LifecycleOwner) context, librosPreferidos -> {
                        if (librosPreferidos != null && librosPreferidos.contains(d.getId())) {
                            holder.imgbtn.setImageResource(R.drawable.corazon);
                            librosPreferidos.remove(d.getId());
                            db.collection("Usuarios").document(fh.idUser()).update("Likes", librosPreferidos);
                            fh.restarLikeAlLibro(d.getId());
                            Toast.makeText(context, "Libro eliminado de me gustas", Toast.LENGTH_SHORT).show();
                        } else {
                            holder.imgbtn.setImageResource(R.drawable.filled_corazon);
                            librosPreferidos.add(d.getId());
                            db.collection("Usuarios").document(fh.idUser()).update("Likes", librosPreferidos);
                            fh.sumarLikeAlLibro(d.getId());
                            Toast.makeText(context, "Libro agregado a me gustas", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (tipoVista == VIEW_TYPE3) {
            holder.likes.setText(String.valueOf(d.getLikes()));
        }

        if (tipoVista == VIEW_TYPE4) {
            holder.txtv2.setText(d.getGenero() + ", " + d.getFecha());
            holder.txtv3.setText(d.getAutor());
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(itemList.get(position));

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}

