package com.example.manejadordedescarga;

public class FileImagen {
    public int id;
    public String titulo;
    public String name;
    public String file;


    public FileImagen(int id, String name, String file) {
        this.id = id;
        this.name = name;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
