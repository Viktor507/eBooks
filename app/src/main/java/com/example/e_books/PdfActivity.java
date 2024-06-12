package com.example.e_books;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;


public class PdfActivity extends AppCompatActivity {

    private PDFView pdfView;
    ProgressBar progressBar;

    String urlPdf, titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView = findViewById(R.id.pdf_viewer);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        urlPdf = intent.getStringExtra("key");
        titulo = intent.getStringExtra("t");

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View customActionBarView = inflator.inflate(R.layout.actionbar, null);

        TextView customTitle = customActionBarView.findViewById(R.id.custom_title);
        customTitle.setText(titulo);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                android.view.Gravity.CENTER
        );
        actionBar.setCustomView(customActionBarView, layoutParams);
        actionBar.setDisplayShowCustomEnabled(true);


        

        new RecibirPDFStream(pdfView, progressBar).execute(urlPdf);
    }

}
