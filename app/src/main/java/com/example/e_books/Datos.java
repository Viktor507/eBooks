package com.example.e_books;

public class Datos implements Comparable<Datos> {

    public String id, titulo, autor, fecha, genero, portada, sinopsis, pdf;
    public int likes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Datos(String a, String b, String c, String d, String e, String f, String g, int h, String i) {
        this.id = a;
        this.titulo = b;
        this.autor = c;
        this.fecha = d;
        this.genero = e;
        this.portada = f;
        this.sinopsis = g;
        this.likes = h;
        this.pdf = i;

    }


    @Override
    public int compareTo(Datos datos) {
        return titulo.compareTo(datos.getTitulo());
    }
}