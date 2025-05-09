/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;


public class Article {
// Agregar un ID que se autoincremente solo size()+1 
    private static int counter;
    private int id;
    private String keyword;
    private String url;
    private Redactor redactor;
    private int numPalabras;
    private Estado estado;

    public enum Estado {
        POR_ASIGNAR,
        ASIGNADO,
        COMPLETADO,
        CORREGIDO,
        DEVUELTO,
        PUBLICADO
    }

    public Article(String keyword, String url, Redactor redactor, int numPalabras, Estado estado) {
        this.keyword = keyword;
        this.redactor = redactor;
        this.estado = estado;
        this.id = counter;
        if (counter == 0){
            counter = 1;
        } else {
            counter++;
        }
    }
    
    private static void incrementCounter(){
        if (counter == 0){
            counter = 1;
        } else {
            counter++;
        }
    }
    
    
    @Override
    public String toString(){
        return "Keyword del artículo: "+this.keyword + "\n URL: "+ this.url +  "\n Palabras: " + numPalabras +
                "\n Estado: " + this.estado + "\n Redactor: " + (redactor != null ? redactor.getRedactorName() : "Sin asignar");
    }
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
