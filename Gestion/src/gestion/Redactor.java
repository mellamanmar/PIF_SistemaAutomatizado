/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JOptionPane;

/**
 *
 * @author general
 */
public class Redactor implements Consult {

    public static int counter = 1;
    private int redactorId;
    private String redactorName;
    private double pricePerWord;
    private Region region;
    private Article article;
    Deque<Article> articlesQueue = new ArrayDeque<>();

    public enum Region {
        NORTE_AMERICA, CENTRO_AMERICA, SUR_AMERICA, EUROPA, ASIA, AFRICA, OCEANIA
    }

    public Redactor(String redactorName, double pricePerWord, Region region) {
        this.redactorName = redactorName;
        this.pricePerWord = pricePerWord;
        this.region = region;
        this.redactorId = counter++;
    }

    public Redactor() {

    }

    @Override
    public String toString() {
        return "ID: " + this.redactorId + "\n Nombre: " + this.redactorName + "\n Precio por palabra: " + this.pricePerWord + "\n Vive en: " + this.region;
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

    public void addArticle(Article article) {
        articlesQueue.offer(article);
    }

    public void completedArticles(Article article) {
        article.setEstado(Article.Estado.COMPLETADO);
    }

    public void removeArticle(Article article) {
        articlesQueue.remove(article);
    }

    public void showArticlesRedactor() {
        if (!articlesQueue.isEmpty()) {
            for (Article redactorArticle : articlesQueue) {
                if (redactorArticle.getEstado().equals(Article.Estado.ASIGNADO)) {
                    System.out.println("ID: " + redactorArticle.getArticleId() + "\nPalabra clave: " + redactorArticle.getKeyword() + "\nEstado: " + redactorArticle.getEstado());
                } else if (redactorArticle.getEstado().equals(Article.Estado.COMPLETADO)) {
                    System.out.println("ID: " + redactorArticle.getArticleId() + "\nPalabra clave: " + redactorArticle.getKeyword() + "\nEstado: " + redactorArticle.getEstado());
                }
            }
        }
        JOptionPane.showMessageDialog(null, "No tiene artículos asignados");
    }

    public Article showArticle(int id) {
        for (Article redactorArticle : articlesQueue) {
            if (redactorArticle.getArticleId() == id) {
                System.out.println(article);
                return article;
            }
        }
        System.out.println("No existe el articulo con el id " + id);
        return null;
    }

    @Override
    public void menuOptions() {
        Article.Estado[] estado = {Article.Estado.COMPLETADO};
        byte optionRedactor = 0;
        do {
            optionRedactor = Byte.parseByte(JOptionPane.showInputDialog("""
                            1. Consultar articulos pendientes.
                            2. Consultar artículos completados.
                            3. Modificar ESTADO de articulo.
                            4. Ver reporte de mes.
                            5. Salir.
                            Seleccione la accion que quiere hacer: """));
            switch (optionRedactor) {
                case 1 -> {
                    showArticlesRedactor();
                }
                case 2 -> {
                    showArticlesRedactor();
                }
                case 3 -> {
                    int idTmp = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del artículo que desea consultar"));
                    Article redactorArticle = showArticle(idTmp);
                    Article.Estado estadoSelect = (Article.Estado) JOptionPane.showInputDialog(null, "Seleccione el estado por cambiar", "Opciones",
                            JOptionPane.OK_OPTION, null, estado, estado[0]);
                    redactorArticle.setEstado(estadoSelect);
                    JOptionPane.showMessageDialog(null, "El estado del artículo " + redactorArticle.getArticleId() + " se cambió a " + redactorArticle.getEstado());
                }
                case 4 -> {

                }
                case 5 -> {
                }
                default ->
                    System.out.println("Error en la opcion");

            } // end switch for user redactor
        } while (optionRedactor != 5);
    }

}
