package gestion;

import java.io.*;
import java.util.*;

/**
 *
 * @author marim
 */
public class PersistenciaDatos {

    public static void saveData(ArrayList<Redactor> redactores, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Redactor redactor : redactores) {
                writer.write(redactor.getRedactorId() + "," + redactor.getRedactorName() + ","
                        + redactor.getPricePerWord() + "," + redactor.getRegion());
                writer.newLine();
                for (Article article : redactor.articlesQueue) {
                    writer.write("ARTICLE," + article.getArticleId() + "," + article.getKeyword() + ","
                            + article.getWordNums() + "," + article.getEstado());
                    writer.newLine();
                }
                writer.write(("END REDACTOR"));
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar los datos" + e.getMessage());
        }
    }

    public static ArrayList<Redactor> loadData(String filePath) {
        ArrayList<Redactor> redactores = new ArrayList<>();

        File archivo = new File(filePath);
        if (!archivo.exists()) {
            System.out.println("No hay datos guardados. Se iniciará una lista vacía.");
            return redactores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Redactor redactor = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ARTICLE")) {
                    String[] artData = line.split(",");

                    if (artData.length < 5) {
                        continue;
                    }

                    String keyword = artData[2];
                    int articleId = Integer.parseInt(artData[1]);
                    int wordNums = Integer.parseInt(artData[3]);
                    Article.Estado estado = Article.Estado.valueOf(artData[4]);

                    Article art = new Article(keyword, redactor, estado);
                    art.setArticleId(articleId);
                    art.setWordNums(wordNums);
                    redactor.addArticle(art);
                } else if (line.equals("END REDACTOR")) {
                    redactores.add(redactor);
                } else {
                    String[] parts = line.split(",");
                    redactor = new Redactor(parts[1], Double.parseDouble(parts[2]), Redactor.Region.valueOf(parts[3]));
                    //redactor.redactorId = Integer.parseInt(parts[0]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos " + e.getMessage());
        }
        return redactores;
    }

}
