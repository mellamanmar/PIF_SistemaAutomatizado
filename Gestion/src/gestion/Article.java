/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;


public class Article {
// Agregar un ID que se autoincremente solo size()+1 
    private static int counter = 0;
    private int id;
    private String titulo;
    private String url;
    private Redactor redactor;
    private int numPalabras;
    private Estado estado;

    enum Estado {
        POR_ASIGNAR,
        ASIGNADO,
        COMPLETADO,
        CORREGIDO,
        DEVUELTO,
        PUBLICADO
    }

    public Article(String titulo, String url, Redactor redactor, int numPalabras, Estado estado) {
        this.id = ++counter;
        this.titulo = titulo;
        this.url = url;
        this.redactor = redactor;
        this.numPalabras = numPalabras;
        this.estado = estado;
    }
    
    public int getId(){
        return id;
    }
    @Override
    public String toString(){
        return "ID: "+this.id + ", titulo: "+this.titulo + ", URL: "+this.url +  ", Palabras: " + numPalabras +
                ", estado: " + estado + ", redactor: " + (redactor != null ? redactor.getRedactorName() : "No asignado"); //Completar el toString con los atributos del articulo. LESLY
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Redactor getRedactor() {
        return redactor;
    }

    public void setRedactor(Redactor redactor) {
        this.redactor = redactor;
    }

    public int getNumPalabras() {
        return numPalabras;
    }

    public void setNumPalabras(int numPalabras) {
        this.numPalabras = numPalabras;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    

}
