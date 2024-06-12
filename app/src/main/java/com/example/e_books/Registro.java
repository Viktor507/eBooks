package com.example.e_books;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registro extends AppCompatActivity {

    private TextView textView;

    private boolean longPressActivated = false;
    private Handler handler = new Handler();

    private EditText etcorreo, etpass1, etpass2;

    private ImageButton btn1, btn2;

    private String a, b, c;

    private String text;

    private String url = "https://firebasestorage.googleapis.com/v0/b/ebooks-9562b.appspot.com/o/T%C3%89RMINOS%20Y%20CONDICIONES%20DE%20USO%20DE%20E.pdf?alt=media&token=8515353b-7351-4757-b558-b6ce4965fbb3";

    private Spannable spannableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etcorreo = findViewById(R.id.Email);
        etpass1 = findViewById(R.id.Pass);
        etpass2 = findViewById(R.id.RePass);

        btn1 = findViewById(R.id.off1);
        btn2 = findViewById(R.id.off2);


        textView = findViewById(R.id.txt2);

        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                togglePasswordVisibility(btn1,etpass1);
                                longPressActivated = true;
                            }
                        }, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        handler.removeCallbacksAndMessages(null);
                        if (longPressActivated) {
                            togglePasswordVisibility(btn1,etpass1);
                            longPressActivated = false;
                        }
                        break;
                }
                return false;
            }
        });


        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                togglePasswordVisibility(btn2,etpass2);
                                longPressActivated = true;
                            }
                        }, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        handler.removeCallbacksAndMessages(null);
                        if (longPressActivated) {
                            togglePasswordVisibility(btn2,etpass2);
                            longPressActivated = false;
                        }
                        break;
                }
                return false;
            }
        });


        text = "Al crear una cuenta en E-BOOKS automáticamente estás aceptando nuestros términos y condiciones de uso.";

        spannableString = new SpannableString(text);
        int startIndexOfColoredText = text.indexOf("términos y condiciones");
        int endIndexOfColoredText = startIndexOfColoredText + "términos y condiciones".length();

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        StyleSpan boldSpan = new StyleSpan(Typeface.ITALIC);

        spannableString.setSpan(colorSpan, startIndexOfColoredText, endIndexOfColoredText, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(boldSpan, startIndexOfColoredText, endIndexOfColoredText, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        URLSpan urlSpan = new URLSpan(url) {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Registro.this, PdfActivity.class);
                intent.putExtra("key", url);
                intent.putExtra("t", "E-BOOKS");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        };

        spannableString.setSpan(urlSpan, startIndexOfColoredText, endIndexOfColoredText, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);

        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Button RegBtn = findViewById(R.id.BtnReg);

        etcorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString().trim();
                if (!isValidEmail(email)) {
                    etcorreo.setError("Correo electrónico no válido");
                } else {
                    etcorreo.setError(null);
                }
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = etcorreo.getText().toString();
                b = etpass1.getText().toString();
                c = etpass2.getText().toString();


                if (!(a.isEmpty() || b.isEmpty() || c.isEmpty())) {

                    if (!isValidEmail(a)) {
                        Toast.makeText(getApplicationContext(), "El correo electrónico ingresado no es válido", Toast.LENGTH_SHORT).show();
                    } else {
                        if ((b.equals(c)) == true) {
                            Intent intent = new Intent(Registro.this, Suscripcion.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else {
                            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Existen campos vacíos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void togglePasswordVisibility(ImageButton b, EditText e) {
        if (e.getTransformationMethod() instanceof PasswordTransformationMethod) {
            e.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            b.setImageResource(R.drawable.on);
        } else {
            e.setTransformationMethod(PasswordTransformationMethod.getInstance());
            b.setImageResource(R.drawable.off);
        }

        e.setSelection(e.getText().length());
    }

    private boolean isValidEmail(String email) {

        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}