package gestion;
import java.io.*;
import java.util.*;


/**
 *
 * @author marim
 */
public class PersistenciaDatos {
    public static void saveData (ArrayList<Redactor> redactores, String filePath){
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
           for (Redactor redactor : redactores){
               writer.write(redactor.getRedactorId()+ "," +redactor.getRedactorName()+ "," 
                       +redactor.getPricePerWord()+ "," +redactor.getRegion()+ "\n------------");
               writer.newLine();
               for (Article article : redactor.articlesQueue){
                   writer.write("ARTICLE," + article.getArticleId()+ "," + article.getKeyword()+ "," 
                           + article.getWordNums()+ "," +article.getEstado()+ "\n-----");
                   writer.newLine();
               }
               writer.write(("END REDACTOR"));
               writer.newLine();
           }
       } catch (Exception e){
           System.out.println("Error al guardar los datos" + e.getMessage());
       }
    }
    
    public static ArrayList<Redactor> loadData(String filePath){
        ArrayList<Redactor> redactores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Redactor redactor = null;
            while((line = reader.readLine()) != null){
                if (line.startsWith("ARTICLE")){
                    String[] artData = line.split(",");
                    Article art = new Article(artData[2], redactor, Article.Estado.valueOf(artData[5]));
                    art.setArticleId(Integer.parseInt(artData[1]));
                    art.setWordNums(Integer.parseInt(artData[3]));
                    redactor.addArticle(art);
                } else if (line.equals("END REDACTOR")){
                    redactores.add(redactor);
                }else {
                    String[] parts = line.split(",");
                    redactor = new Redactor(parts[1], Double.parseDouble(parts[2]), Redactor.Region.valueOf(parts[3]));
                    //redactor.redactorId = Integer.parseInt(parts[0]);
                }
            }
        }catch (Exception e){
            System.out.println("Error al cargar los datos " + e.getMessage());
        }
        return redactores;
    }
    
}
