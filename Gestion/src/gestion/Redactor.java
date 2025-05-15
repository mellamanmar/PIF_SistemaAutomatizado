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
        return "ID: " + this.getRedactorId() + " - " + this.redactorName;
    }

    public String redactorInfo() {
        return "ID: " + getRedactorId() + "\nNombre " + getRedactorName() + "\nPrecio por palabra " + getPricePerWord() + "\nVive en: " + getRegion();
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

    public void completedArticles(Article article, int numWords, Editor editor) {
        article.setWordNums(numWords);
        article.setEstado(Article.Estado.COMPLETADO);
        GestionEditorial.editor.listArticles.add(article);
    }

    public void removeArticle(Article article) {
        articlesQueue.remove(article);
    }

    public void showArticlesRedactor() {
        StringBuilder assigned = new StringBuilder("ARTÍCULOS ASIGNADOS:\n");
        StringBuilder completed = new StringBuilder("ARTÍCULOS COMPLETADOS:\n");
        StringBuilder returned = new StringBuilder("ARTÍCULOS DEVUELTOS:\n");
        boolean hasAssigned, hasCompleted, hasReturned = false;
        StringBuilder resultArticles = new StringBuilder("--------------------------\n");

        if (!articlesQueue.isEmpty()) {
            for (Article redactorArticle : articlesQueue) {
                if (null != redactorArticle.getEstado()) {
                    switch (redactorArticle.getEstado()) {
                        case ASIGNADO -> {
                            hasAssigned = true;
                            assigned.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                            if (hasAssigned) {
                                resultArticles.append(assigned).append("\n");
                            }
                        }
                        case COMPLETADO -> {
                            hasCompleted = true;
                            completed.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                            if (hasCompleted) {
                                resultArticles.append(completed);
                            }
                        }
                        case DEVUELTO -> {
                            hasReturned = true;
                            returned.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                            if (hasReturned) {
                                resultArticles.append(returned);
                            }
                        }
                        default -> {
                        }
                    }
                }
            }
            System.out.println(resultArticles.toString());
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
        String optionRedactor = "";
        do {
            optionRedactor = JOptionPane.showInputDialog("""
                            1. Consultar artÍculos.
                            2. Modificar un artículo.
                            3. Ver reporte de mes.
                            4. Salir.
                            Seleccione la accion que quiere hacer: """);
            if (optionRedactor == null || optionRedactor.isBlank()) {
                break;
            }
            switch (optionRedactor) {

                case "1" -> {
                    try {
                        System.out.println("Redactor actual: ID " + this.redactorId + " - Artículos en cola: " + articlesQueue.size());
                        JOptionPane.showMessageDialog(null, "Ingresando a los artículos ASIGNADOS");

                        //¿MOSTRARLOS TODOS?
                        showArticlesRedactor();
                    } catch (Exception e) {
                        System.out.println("No le logró mostrar los artículos asignados" + e);
                    }
                    break;
                }

                case "2" -> {
                    if (!articlesQueue.isEmpty()) {
                        try {
                            Article[] articlesListTmp = articlesQueue.toArray(Article[]::new);
                            Article redactorArticle = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para modificar:", "Artículos",
                                    JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);

                            System.out.println(redactorArticle);

                            int newNumWords = Integer.parseInt(JOptionPane.showInputDialog("¿De cuántas palabras fue su artículo?"));

                            if (redactorArticle != null) {
                                Article.Estado estadoSelect = (Article.Estado) JOptionPane.showInputDialog(null, "Seleccione el estado por cambiar", "Opciones",
                                        JOptionPane.YES_OPTION, null, estado, estado[0]);
                                if (estadoSelect != null) {
                                    completedArticles(redactorArticle, newNumWords, GestionEditorial.editor);
                                    JOptionPane.showMessageDialog(null, "El estado del artículo " + redactorArticle.getArticleId() + " se cambió a " + redactorArticle.getEstado());
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("No le logró modicar el artículo" + e);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Artículo no encontrado.");
                        break;
                    }

                }
                case "3" -> {

                }
                case "4" -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            } // end switch for user redactor
        } while (!"4".equals(optionRedactor));
    }

}
