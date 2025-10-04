package gestion;

import java.awt.HeadlessException;
import java.time.LocalDate;
import java.util.*;
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
        if (!GestionEditorial.editor.listArticles.contains(article)) {
            GestionEditorial.editor.listArticles.add(article);
        }
    }

    public void removeArticle(Article article) {
        articlesQueue.remove(article);
    }

    public void showArticlesRedactor() {
        StringBuilder assigned = new StringBuilder("ARTICULOS ASIGNADOS:\n");
        StringBuilder completed = new StringBuilder("ARTICULOS COMPLETADOS:\n");
        StringBuilder returned = new StringBuilder("ARTICULOS DEVUELTOS:\n");
        boolean hasAssigned = false;
        boolean hasCompleted = false;
        boolean hasReturned = false;
        StringBuilder resultArticles = new StringBuilder("--------------------------\n");

        if (!articlesQueue.isEmpty()) {
            for (Article redactorArticle : articlesQueue) {
                if (null != redactorArticle.getEstado()) {
                    switch (redactorArticle.getEstado()) {
                        case ASIGNADO -> {
                            hasAssigned = true;
                            assigned.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append("\n");
                        }
                        case COMPLETADO -> {
                            hasCompleted = true;
                            completed.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append(" | Cantidad de palabras: ").append(redactorArticle.getWordNums()).append("\n");
                        }
                        case DEVUELTO -> {
                            hasReturned = true;
                            returned.append("ID: ").append(redactorArticle.getArticleId()).append(" | Palabra clave: ").append(redactorArticle.getKeyword()).append(" | Cantidad de palabras: ").append((redactorArticle.getWordNums())).append("\n");
                        }
                        default -> {
                        }
                    }
                }
            }
            if (hasAssigned) {
                resultArticles.append(assigned).append("-----------------------\n");
            }
            if (hasCompleted) {
                resultArticles.append(completed).append("----------------------\n");
            }
            if (hasReturned) {
                resultArticles.append(returned).append("-----------------------\n");
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

    public void generatePayReport() {
        StringBuilder reporte = new StringBuilder("REPORTE DE PAGO\n\n" + LocalDate.now());
        double totalPago = 0;
        int publishedArticles = 0;

        for (Redactor redactor : GestionEditorial.editor.listRedactors) {

            for (Article articulo : GestionEditorial.editor.listPublishArticles) {
                if (articulo.getRedactor().equals(redactor)) {
                    totalPago += articulo.getWordNums() * redactor.getPricePerWord();
                    publishedArticles++;
                }
            }
        }
        reporte.append("Redactor: ").append(getRedactorName()).append("\n")
                    .append("ID: ").append(getRedactorId()).append("\n")
                    .append("Artículos publicados: ").append(publishedArticles).append("\n")
                    .append("Total a pagar: $").append(String.format("%.2f", totalPago)).append("\n\n");
        
        JOptionPane.showMessageDialog(null, reporte.toString());
    }

    @Override
    public void menuOptions() {
        Article.Estado[] estado = {Article.Estado.COMPLETADO};
        String optionRedactor = "";
        do {
            optionRedactor = JOptionPane.showInputDialog(null, """
                            1. Consultar artÍculos.
                            2. Modificar estado del artículo.
                            3. Ver reporte de mes.
                            4. Volver.
                            Seleccione la accion que quiere hacer: """, "Menú Redactor", JOptionPane.INFORMATION_MESSAGE);
            if (optionRedactor == null || optionRedactor.isBlank()) {
                break;
            }
            switch (optionRedactor) {

                case "1" -> {
                    try {
                        System.out.println("Redactor actual: ID " + this.redactorId + " - Articulos en cola: " + articlesQueue.size());
                        JOptionPane.showMessageDialog(null, "Ingresando a sus articulos");

                        showArticlesRedactor();
                    } catch (HeadlessException e) {
                        System.out.println("No le logró mostrar los articulos asignados" + e.getMessage());
                    }
                    break;
                }

                case "2" -> {
                    if (!articlesQueue.isEmpty()) {
                        try {
                            Article[] articlesListTmp = articlesQueue.toArray(Article[]::new);
                            Article redactorArticle = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para modificar:", "Artículos",
                                    JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);
                            if (redactorArticle == null) {
                                break;
                            }

                            System.out.println("-----------------------\nArticulo actual: \n" + redactorArticle);

                            String input = JOptionPane.showInputDialog("¿De cuántas palabras fue su artículo?");

                            if (input == null || input.isBlank()) {
                                break;
                            }
                            int newNumWords = Integer.parseInt(input);

                            Article.Estado estadoSelect = (Article.Estado) JOptionPane.showInputDialog(null, "Seleccione el estado por cambiar", "Opciones",
                                    JOptionPane.YES_OPTION, null, estado, estado[0]);
                            if (estadoSelect == null) {
                                break;
                            }

                            completedArticles(redactorArticle, newNumWords, GestionEditorial.editor);
                            JOptionPane.showMessageDialog(null, "El estado del artículo " + redactorArticle.getArticleId() + " se cambió a " + redactorArticle.getEstado());
                            break;

                        } catch (HeadlessException | NumberFormatException e) {
                            System.out.println("No le logro modicar el articulo" + e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No tiene artículos por modificar.");
                        break;
                    }

                }
                case "3" -> {
                    generatePayReport();
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
