/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author general
 */
public class Redactor implements Consult {

    private static int counter;
    private int redactorId;
    private String redactorName;
    private double pricePerWord;
    private Region region;
    private Article article;
    Deque<Article> articlesQueue = new ArrayDeque<>();

    public enum Region {
        NORTE_AMERICA, CENTRO_AMERICA, SUR_AMERICA, EUROPA, ASIA, AFRICA, OCEANIA
    }

    public Redactor(String redactorName, double pricePerWord, Region region, Article article) {
        this.redactorName = redactorName;
        this.pricePerWord = pricePerWord;
        this.region = region;
        this.article = article;
        this.redactorId = counter;
        if (counter == 0){
            counter = 1;
        } else {
            counter++;
        }
    }

    @Override
    public String toString() {
        return "ID: " + this.redactorId + "\n Nombre: " + this.redactorName + "\n Precio por palabra: " + this.pricePerWord;
    }

    public int getRedactorId() {
        return redactorId;
    }
    
    public String getRedactorName() {
        return redactorName;
    }

    public void setRedactorName(String redactorName) {
        this.redactorName = redactorName;
    }

    public double getPricePerWord() {
        return pricePerWord;
    }

    public void setPricePerWord(double pricePerWord) {
        this.pricePerWord = pricePerWord;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void addArticle(Article article){
        articlesQueue.offer(article);
    }
    
    //Función que cambie el estado a COMPLETADO y envíe el articulo a la lista general del editor
    
    public void removeArticle (Article article){
        articlesQueue.remove(article);
    }

    @Override
    public void menuOptions() {
        byte optionRedactor = 0;
        System.out.print("""
                            
                            1. Consultar articulos pendientes.
                            2. Modificar ESTADO de articulo.
                            3. Ver reporte de mes.
                            4. Salir.
                            Seleccione la accion que quiere hacer: """);
        optionRedactor = GestionEditorial.read.nextByte();
        switch (optionRedactor) {
            case 1 -> {
                break;
            }
            case 2 -> {
                break;
            }
            case 3 -> {
                break;
            }
            case 4 -> {
                break;
            }
            default ->
                System.out.println("Error en la opcion");
        } // end switch for user redactor
    }

}
