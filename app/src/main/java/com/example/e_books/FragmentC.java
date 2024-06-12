package com.example.e_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FragmentC extends Fragment implements  SelectedListener{

    private Button btn1, btn2, btn3;

    private FirestoreHelper fh;


    private RecyclerView recyclerView;

    private CustomAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_libros, container, false);

        fh = new FirestoreHelper(getActivity());
        btn1 = view.findViewById(R.id.btnAmados);
        btn2 = view.findViewById(R.id.btnLeidos);
        btn3 = view.findViewById(R.id.btnLeyendo);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAmados();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarLeidos();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarLeyendo();
            }
        });

        recyclerView = view.findViewById(R.id.rcv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        adapter = new CustomAdapter(new ArrayList<>(), 4,getContext(), this);
        recyclerView.setAdapter(adapter);

        mostrarAmados();

        return view;
    }

    public void mostrarLeyendo() {
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(false);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_activo));


    }
    public void mostrarLeidos() {
        btn1.setClickable(true);
        btn2.setClickable(false);
        btn3.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

    }
    public void mostrarAmados() {
        btn1.setClickable(false);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        LiveData<ArrayList<String>> librosAmadosLiveData = fh.librosAmados();
        librosAmadosLiveData.observe((LifecycleOwner) getContext(), librosPreferidos -> {
            fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
                HashSet<String> librosPreferidosSet = new HashSet<>(librosPreferidos);
                ArrayList<Datos> elementosFiltrados = new ArrayList<>();
                for (Datos d : datosList) {
                    if (librosPreferidosSet.contains(d.getId())) {
                        elementosFiltrados.add(d);
                    }
                }
                adapter.setItemList(elementosFiltrados);
                adapter.notifyDataSetChanged();
            });
        });

    }

    @Override
    public void onItemClicked(Datos d) {
        Intent intent = new Intent(getContext(), Informacion.class);
        intent.putExtra("key1", d.getAutor());
        intent.putExtra("key2", d.getTitulo());
        intent.putExtra("key3", d.getGenero());
        intent.putExtra("key4", d.getFecha());
        intent.putExtra("key5", d.getSinopsis());
        intent.putExtra("key6", d.getLikes());
        intent.putExtra("key7", d.getPortada());
        intent.putExtra("key8", d.getPdf());
        getContext().startActivity(intent);
    }
}
