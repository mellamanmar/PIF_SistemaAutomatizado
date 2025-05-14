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

    public static int counter;
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
        if (counter == 0) {
            counter = 1;
        } else {
            counter++;
        }
        this.redactorName = redactorName;
        this.pricePerWord = pricePerWord;
        this.region = region;
        this.redactorId = counter;
    }

    public Redactor() {

    }

    @Override
    public String toString() {
        return this.redactorName;
    }
    
    public String redactorInfo(){
        return "ID: " + getRedactorId() + "\nNombre " + getRedactorName() + "\nPrecio por palabra " + getPricePerWord() + "\n Vive en: " + getRegion();
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
        article.setEstado(Article.Estado.ASIGNADO);
        articlesQueue.offerLast(article);
    }

    public void completedArticles(Article article) {
        article.setEstado(Article.Estado.COMPLETADO);
    }

    public void removeArticle(Article article) {
        articlesQueue.remove(article);
    }
// Hay que arreglar cómo se muestran, de esta manera no se muestra nada
    public void showArticlesRedactor() {
        StringBuilder assigned = new StringBuilder ("ARTÍCULOS ASIGNADOS:\n");
        StringBuilder completed = new StringBuilder ("ARTÍCULOS COMPLETADOS:\n");
        boolean hasAssigned = false;
        boolean hasCompleted = false;
        
        if (!articlesQueue.isEmpty()) {
            for (Article redactorArticle : articlesQueue) {
                if (redactorArticle.getEstado() == (Article.Estado.ASIGNADO)) {
                    hasAssigned = true;
                    assigned.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                    
                } else if (redactorArticle.getEstado() == (Article.Estado.COMPLETADO)) {
                    hasCompleted = true;
                    completed.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                }
            }
            StringBuilder resultArticles = new StringBuilder("--------------------------\n");
            
            if (hasAssigned) resultArticles.append(assigned).append("\n");
            if (hasCompleted) resultArticles.append(completed);
                
        } else {
            JOptionPane.showMessageDialog(null, "No tiene artículos en la cola");
        }

    }

    public Article showArticle(int id) {
        for (Article redactorArticle : articlesQueue) {
            if (redactorArticle.getArticleId() == id) {
                System.out.println(redactorArticle);
                return redactorArticle;
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
                            1. Consultar artÍculos.
                            2. Modificar ESTADO de articulo.
                            3. Ver reporte de mes.
                            4. Salir.
                            Seleccione la accion que quiere hacer: """));
            switch (optionRedactor) {

                case 1 -> {
                    System.out.println("Redactor actual: ID " + this.redactorId + " - Artículos en cola: " + articlesQueue.size());
                    showArticlesRedactor();
                }
                case 2 -> {
                    int idTmp = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del artículo que desea consultar"));
                    Article redactorArticle = showArticle(idTmp);
                    if (redactorArticle != null) {
                        Article.Estado estadoSelect = (Article.Estado) JOptionPane.showInputDialog(null, "Seleccione el estado por cambiar", "Opciones",
                                JOptionPane.YES_OPTION, null, estado, estado[0]);
                        int newNumWords = Integer.parseInt(JOptionPane.showInputDialog("¿De cuántas palabras fue su artículo?"));
                        redactorArticle.setNumPalabras(newNumWords);
                        redactorArticle.setEstado(estadoSelect);
                        JOptionPane.showMessageDialog(null, "El estado del artículo " + redactorArticle.getArticleId() + " se cambió a " + redactorArticle.getEstado());
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Artículo no encontrado.");
                        break;
                    }

                }
                case 3 -> {

                }
                case 4 -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            } // end switch for user redactor
        } while (optionRedactor != 4);
    }

}
