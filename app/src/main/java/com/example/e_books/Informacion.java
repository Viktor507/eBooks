package com.example.e_books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Informacion extends AppCompatActivity {

    private String autor, titulo, genero, fecha, sinopsis, portada, pdf;
    private int likes;

    private TextView txt1, txt2, txt3, txt4, txt5;
    private ImageView imgv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        if (intent != null) {

            autor = intent.getStringExtra("key1");
            titulo = intent.getStringExtra("key2");
            genero = intent.getStringExtra("key3");
            fecha = intent.getStringExtra("key4");
            sinopsis = intent.getStringExtra("key5");
            likes = intent.getIntExtra("key6", 0);
            portada = intent.getStringExtra("key7");
            pdf = intent.getStringExtra("key8");


            txt1 = findViewById(R.id.autor);
            txt1.setText(autor);

            txt2 = findViewById(R.id.texto);
            txt2.setText(titulo);

            txt3 = findViewById(R.id.generof);
            txt3.setText(genero+", "+fecha);

            txt4 = findViewById(R.id.sinopsis);
            txt4.setText(sinopsis);

            txt5 = findViewById(R.id.likes);
            txt5.setText(String.valueOf(likes));


            imgv = findViewById(R.id.imgv);
            Glide.with(this)
                    .load(portada)
                    .apply(new RequestOptions().centerCrop())
                    .into(imgv);
        }

        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Informacion.this, PdfActivity.class);
                intent.putExtra("key", pdf);
                intent.putExtra("t", titulo);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}