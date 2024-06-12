package com.example.e_books;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText et1, et2;

    private boolean longPressActivated = false;
    private Handler handler = new Handler();
    private String user, pass;

    private ImageButton imgbtn;

    private TextView txtv;

    private Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et1 = findViewById(R.id.Edit1);
        et2 = findViewById(R.id.Edit2);
        btn1 = findViewById(R.id.Btn1);
        txtv = findViewById(R.id.Txt2);
        imgbtn = findViewById(R.id.off1);

        et1.addTextChangedListener(new TextWatcher() {
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
                    et1.setError("Correo electrónico no válido");
                } else {
                    et1.setError(null);
                }
            }
        });

        imgbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                togglePasswordVisibility(imgbtn, et2);
                                longPressActivated = true;
                            }
                        }, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        handler.removeCallbacksAndMessages(null);
                        if (longPressActivated) {
                            togglePasswordVisibility(imgbtn, et2);
                            longPressActivated = false;
                        }
                        break;
                }
                return false;
            }
        });


        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = et1.getText().toString();
                pass = et2.getText().toString();

                if (!user.equals("") && !pass.equals("")) {
                    if (!isValidEmail(user)) {
                        Toast.makeText(getApplicationContext(), "El correo electrónico ingresado no es válido", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.signInWithEmailAndPassword(user, pass)
                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(Login.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Login.this, Principal.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                        } else {
                                            Toast.makeText(Login.this, "Datos de inicio de sesión incorrectos", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else

                    Toast.makeText(Login.this, "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    private boolean isValidEmail(String email) {

        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


}