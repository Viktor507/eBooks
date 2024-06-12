package com.example.e_books;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class FragmentA extends Fragment implements SelectedListener{
    private SliderLayout sliderLayout;
    private PagerIndicator customIndicator;
    private HashMap<String, Integer> sliderImages;
    private List<Datos> elementosFiltrados = new ArrayList<>();
    private FirestoreHelper fh;
    private CustomAdapter adapter1, adapter2;
    private RecyclerView recyclerView1, recyclerView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        fh = new FirestoreHelper(getActivity());
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                alertDialog();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        recyclerView1 = view.findViewById(R.id.rcv1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter1 = new CustomAdapter(new ArrayList<>(), 1, getContext(),this);
        recyclerView1.setAdapter(adapter1);

        fh.Recomendados().observe(getViewLifecycleOwner(), datosList -> {
            adapter1.setItemList(datosList);
            adapter1.notifyDataSetChanged();
        });

        mostrarTop();

        recyclerView2 = view.findViewById(R.id.rcv2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        adapter2 = new CustomAdapter(new ArrayList<>(), 3, getContext(),this);
        recyclerView2.setAdapter(adapter2);


        sliderLayout = view.findViewById(R.id.sliderLayout);
        customIndicator = view.findViewById(R.id.custom_indicator);
        sliderImages = new HashMap<>();
        sliderImages.put("img1", R.drawable.noticia1);
        sliderImages.put("img2", R.drawable.noticia2);
        sliderImages.put("img3", R.drawable.noticia3);

        setupSliderLayout();

        return view;
    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();


    }

    @Override
    public void onResume() {
        sliderLayout.startAutoCycle();
        super.onResume();

    }

    public void mostrarTop() {
        elementosFiltrados.clear();
        fh.Libros().observe(getViewLifecycleOwner(), datosList -> {
            for (Datos d : datosList) {
                if (d.getLikes()>0) {
                    elementosFiltrados.add(d);
                }
            }
            Collections.sort(elementosFiltrados, new Comparator<Datos>() {
                @Override
                public int compare(Datos o1, Datos o2) {
                    return Integer.compare(o2.getLikes(), o1.getLikes());
                }
            });

            List<Datos> topTresElementos = new ArrayList<>();
            for (int i = 0; i < Math.min(3, elementosFiltrados.size()); i++) {
                topTresElementos.add(elementosFiltrados.get(i));
            }
            adapter2.setItemList(topTresElementos);
            adapter2.notifyDataSetChanged();
        });

    }


    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Desea salir de la seasión actual?");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setupSliderLayout() {


        for (String name : sliderImages.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView
                    .image(sliderImages.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.setCustomIndicator(customIndicator);
            sliderLayout.addSlider(textSliderView);

        }
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