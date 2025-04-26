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
    
    private int redactorId;
    private String redactorName;
    private double pricePerWord;
    private Region region;
    //private Article article;
    Deque<Article> articleQueue = new ArrayDeque<>();
    
    enum Region{
        NORTE_AMERICA, CENTRO_AMERICA, SUR_AMERICA, EUROPA, ASIA, AFRICA, OCEANIA
    }

    public Redactor(int redactorId, String redactorName, double pricePerWord, Region region /*, Article article*/) {
        this.redactorId = redactorId;
        this.redactorName = redactorName;
        this.pricePerWord = pricePerWord;
        this.region = region;
        //this.article = article;
    }   
  
    

    @Override
    public String toString() {
        return "ID: " + redactorId + ", Nombre: " + redactorName;
    }
    public int getRedactorId() {
        return redactorId;
    }

    public void setRedactorId(int redactorId) {
        this.redactorId = redactorId;
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
/*
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
*/
    
    
  
    @Override
    public void menuOptions() {
        byte optionRedactor = 0;
        System.out.print("""
                            
                            1. Consultar articulos.
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
