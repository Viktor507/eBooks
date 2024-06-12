package com.example.e_books;


import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;


public class FirestoreHelper {
    Context context;

    public FirestoreHelper(Context context) {
        this.context = context;
    }

    private FirebaseFirestore db;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ;
    public CollectionReference itemsCollection;

    public LiveData<List<Datos>> Recomendados() {
        MutableLiveData<List<Datos>> liveData = new MutableLiveData<>();
        List<Datos> itemList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        itemsCollection = db.collection("Recomendados");
        itemsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    String portada = document.getString("Portada");
                    String titulo = document.getString("Titulo");
                    String autor = document.getString("Autor");
                    String fecha = document.getString("Año");
                    String genero = document.getString("Genero");
                    String sinopsis = document.getString("Sinopsis");
                    int likes = document.getLong("Likes").intValue();
                    String pdf = document.getString("Pdf");
                    Datos d = new Datos(id, titulo, autor, fecha, genero, portada, sinopsis, likes, pdf);
                    itemList.add(d);
                }
                liveData.setValue(itemList);
            } else {
                Toast.makeText(context, "Error cargando los libros recomendados", Toast.LENGTH_SHORT).show();
            }
        });

        return liveData;
    }

    public LiveData<List<Datos>> Libros() {
        MutableLiveData<List<Datos>> liveData = new MutableLiveData<>();
        List<Datos> itemList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        itemsCollection = db.collection("Libros");
        itemsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    String portada = document.getString("Portada");
                    String titulo = document.getString("Titulo");
                    String autor = document.getString("Autor");
                    String fecha = document.getString("Año");
                    String genero = document.getString("Genero");
                    String sinopsis = document.getString("Sinopsis");
                    int likes = document.getLong("Likes").intValue();
                    String pdf = document.getString("Pdf");
                    Datos d = new Datos(id, titulo, autor, fecha, genero, portada, sinopsis, likes, pdf);
                    itemList.add(d);
                }
                liveData.setValue(itemList);
            } else {
                Toast.makeText(context, "Error cargando los libros recomendados", Toast.LENGTH_SHORT).show();
            }
        });

        return liveData;
    }

    public LiveData<ArrayList<String>> librosAmados() {
        MutableLiveData<ArrayList<String>> liveData = new MutableLiveData<>();

        db = FirebaseFirestore.getInstance();
        itemsCollection = db.collection("Usuarios");
        itemsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Object likesField = document.get("Likes");
                    ArrayList<String> itemList = (ArrayList<String>) likesField;
                    liveData.setValue(itemList);
                }
            }

        });
        return liveData;
    }

    public void restarLikeAlLibro(String libroId) {
        DocumentReference libroRef1 = db.collection("Libros").document(libroId);
        libroRef1.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Integer likes = documentSnapshot.getLong("Likes").intValue();
                if (likes > 0) {
                    libroRef1.update("Likes", FieldValue.increment(-1));
                }else{
                    libroRef1.update("Likes", 0);
                }
            }
        });

        DocumentReference libroRef2 = db.collection("Recomendados").document(libroId);
        libroRef2.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Integer likes = documentSnapshot.getLong("Likes").intValue();
                if (likes > 0) {
                    libroRef2.update("Likes", FieldValue.increment(-1));
                }else{
                    libroRef2.update("Likes", 0);
                }
            }
        });
    }

    public void sumarLikeAlLibro(String libroId) {
        DocumentReference libroRef1 = db.collection("Libros").document(libroId);
        libroRef1.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Integer likes = documentSnapshot.getLong("Likes").intValue();
                if (likes >= 0) {
                    libroRef1.update("Likes", FieldValue.increment(+1));
                }
            }
        });

        DocumentReference libroRef2 = db.collection("Recomendados").document(libroId);
        libroRef2.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Integer likes = documentSnapshot.getLong("Likes").intValue();
                if (likes >= 0) {
                    libroRef2.update("Likes", FieldValue.increment(+1));
                }
            }
        });
    }

    public String idUser() {
        String uid = "";
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        } else {
            Toast.makeText(context, "No hay un usuario autenticado actualmente", Toast.LENGTH_SHORT).show();
        }
        return uid;
    }
}
