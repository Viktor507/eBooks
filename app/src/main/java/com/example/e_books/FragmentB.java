package com.example.e_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentB extends Fragment implements SelectedListener{

    private FirestoreHelper fh;
    private RecyclerView recyclerView;

    private Button btn1, btn2, btn3, btn4, btn5, btn6;

    private CustomAdapter adapter;

    private List<Datos> elementosFiltrados = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        fh = new FirestoreHelper(getActivity());
        btn1 = view.findViewById(R.id.btnTodos);
        btn2 = view.findViewById(R.id.btnRomance);
        btn3 = view.findViewById(R.id.btnFantasia);
        btn4 = view.findViewById(R.id.btnTerror);
        btn5 = view.findViewById(R.id.btnDrama);
        btn6 = view.findViewById(R.id.btnScfi);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTodos();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarRomance();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFantasia();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTerror();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDrama();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarScfi();
            }
        });


        recyclerView = view.findViewById(R.id.rcv1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CustomAdapter(new ArrayList<>(), 2,getContext(),this);
        recyclerView.setAdapter(adapter);

        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            Collections.sort(datosList);
            adapter.setItemList(datosList);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
    public void mostrarScfi() {
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(false);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_activo));

        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getGenero().equals("Sci-Fi")) {
                    elementosFiltrados.add(d);
                }
            }
            adapter.setItemList(elementosFiltrados);
            adapter.notifyDataSetChanged();
        });
    }
    public void mostrarDrama() {
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(false);
        btn6.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getGenero().equals("Drama")) {
                    elementosFiltrados.add(d);
                }
            }
            adapter.setItemList(elementosFiltrados);
            adapter.notifyDataSetChanged();
        });
    }
    public void mostrarTerror() {
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(false);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getGenero().equals("Terror")) {
                    elementosFiltrados.add(d);
                }
            }
            adapter.setItemList(elementosFiltrados);
            adapter.notifyDataSetChanged();
        });
    }
    public void mostrarFantasia() {
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(false);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getGenero().equals("Fantasia")) {
                    elementosFiltrados.add(d);
                }
            }
            adapter.setItemList(elementosFiltrados);
            adapter.notifyDataSetChanged();
        });
    }
    public void mostrarRomance() {
        btn1.setClickable(true);
        btn2.setClickable(false);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getGenero().equals("Romance")) {
                    elementosFiltrados.add(d);
                }
            }
            adapter.setItemList(elementosFiltrados);
            adapter.notifyDataSetChanged();
        });
    }
    public void mostrarTodos() {
        btn1.setClickable(false);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
        btn5.setClickable(true);
        btn6.setClickable(true);
        btn1.setBackground(getResources().getDrawable(R.drawable.margen_activo));
        btn2.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn3.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn4.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn5.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));
        btn6.setBackground(getResources().getDrawable(R.drawable.margen_inactivo));

        fh = new FirestoreHelper(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CustomAdapter(new ArrayList<>(), 2,getContext(),this);
        recyclerView.setAdapter(adapter);

        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            Collections.sort(datosList);
            adapter.setItemList(datosList);
            adapter.notifyDataSetChanged();
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
