/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.ArrayList;
import javax.swing.*;

public class Editor implements Consult {

    private String editorName;
    private int editorId;

    public Editor(String editorName, int editorId) {
        this.editorName = editorName;
        this.editorId = editorId;
    }
    
    public Editor(){
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public int getEditorId() {
        return editorId;
    }

    public void setEditorId(int editorId) {
        this.editorId = editorId;
    }

    //Objects
    ArrayList<Redactor> listRedactors = new ArrayList<>();
    ArrayList<Article> listArticles = new ArrayList<>();
    ArrayList<Article> listPublishArticles = new ArrayList<>();

    public void addRedactor(Redactor redactor) {
        listRedactors.add(redactor);
    }

    public boolean removeRedactor(int idToRemove) {
        return listRedactors.removeIf(redactor -> redactor.getRedactorId() == idToRemove);
    }

    public void showRedactors() {
        for (Redactor redactor : listRedactors) {
            System.out.println(redactor);
        }
    }

    public void showRedactor(int id) {
        for (Redactor redactor : listRedactors) {
            if (id == redactor.getRedactorId()) {
                System.out.println(redactor);
                break;
            } else {
                System.out.println("No existe ese redactor");
            }
        }

    }

    public Redactor searchRedactor(int id) {
        for (Redactor redactor : listRedactors) {
            if (id == redactor.getRedactorId()) {
                return redactor;
            } else {

                System.out.println("No existe ese redactor");
            }
        }
        return null;
    }

    /*
    public double calculatePayments( int id ){
    Utilizar la llista de articulos publicados
        for (Redactor redactor : listRedactors) {
            if (id == redactor.getRedactorId()) {
                //obtiene los datos para calcular
                //calcula
                return pago;
            } else {

                System.out.println("no existe ese redactor");
            }
        }
    }*/
    @Override
    public void menuOptions() {
        byte optionEditor = 0;
        do {
            optionEditor = Byte.parseByte(JOptionPane.showInputDialog(""" 
                               1. Acciones con redactor.
                               2. Acciones con articulos.
                               3. Calcular pagos.
                               4. Salir.
                               Seleccione la accion que quiere hacer: """));
            switch (optionEditor) {
                case 1 -> {
                    menuActionsRedactor();
                }
                case 2 -> {
                    menuActionsArticle();
                }
                case 3 -> {
                    showRedactors();
                    int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Seleccione ingresando el id del redactor al que le quiere calcular el pago.\nIngrese ID: "));
                    //calculatePayments( idToSearch );    
                }
                case 4 -> {

                }
                default ->
                    System.out.println("Error en la opcion");
            }
            //end switch for user editor
        } while (optionEditor != 4);
    }

    public void menuActionsRedactor() {
        Redactor.Region[] regions = Redactor.Region.values();
        short optionActionsRedactor = 0;
        do {
            optionActionsRedactor = Short.parseShort(JOptionPane.showInputDialog("""
                               Seleccione la accion que quiere hacer:
                               1. Agregar redactor.
                               2. Eliminar redactor.
                               3. Consultar redactores.
                               4. Consultar redactor.
                               5. Salir.
                               """));
            switch (optionActionsRedactor) {

                case 1 -> {
                    String nameRedactor = JOptionPane.showInputDialog("Ingrese el nombre del redactor:");

                    double pricePerWord = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio por palabra:"));

                    Redactor.Region regionSelect = (Redactor.Region) JOptionPane.showInputDialog(null, "Selecciona la region del redactor", "Opciones",
                            JOptionPane.QUESTION_MESSAGE, null, regions, regions[0]);
                    Redactor newRedactor = new Redactor(nameRedactor, pricePerWord, regionSelect);
                    addRedactor(newRedactor);

                    JOptionPane.showMessageDialog(null, "Redactor agregado");

                    break;
                }
                case 2 -> {
                    if (!listRedactors.isEmpty()) {
                        int idToRemove = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del redactor que deseas eliminar: "));

                        if (removeRedactor(idToRemove)) {
                            System.out.print("Redactor eliminado con exito.");
                        } else {
                            System.out.print("No se encontro un redactor con ese ID.");
                        }
                    } else {
                        System.out.print("La lista de redactores esta vacia.");
                    }

                }
                case 3 -> {
                    if (!listRedactors.isEmpty()) {
                        showRedactors();
                    } else {
                        System.out.print("La lista de redactores esta vacia.");
                    }
                }
                case 4 -> {
                    if (!listRedactors.isEmpty()) {
                        int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del redactor que deseas buscar: "));
                        showRedactor(idToSearch);
                    } else {
                        System.out.print("La lista de redactores esta vacia.");
                    }
                }
                case 5 -> {
                }
                default ->
                    System.out.print("Error en la opcion");

            }//end switch for Actions whit redactor
        } while (optionActionsRedactor != 5);
    }

    public void addArticleToList(Article article) {
        if (article.getRedactor() != null) {
            GestionEditorial.redactor.addArticle(article);
        } else {
            listArticles.add(article);
        }
    }

    public void removeArticle(Article article) {
        listArticles.remove(article);
    }

    public void showArticles() {
        for (Article article : listArticles) {
            System.out.println("ID: " + article.getArticleId() + "\n Palabra clave: " + article.getKeyword());
        }
    }

    //Para buscar por ID cambiar la url por el ID que ingresa el usuario desde el menu
    public Article showArticle(int id) {
        for (Article article : listArticles) {
            if (article.getArticleId() == id) {
                System.out.println(article);
                return article;
            }
        }
        System.out.println("No existe el articulo con el id " + id);
        return null;
    }

    public Article asignArticle(Redactor redactor) {
        for (Article article : listArticles) {
            if ((article.getRedactor() == null) && (article.getEstado().equals(Article.Estado.POR_ASIGNAR))) {
                article.setRedactor(redactor); // asignas el redactor al artículo
                article.setEstado(Article.Estado.ASIGNADO); // cambias el estado a ASIGNADO
                redactor.addArticle(article);
                return article;
            } else {
                JOptionPane.showMessageDialog(null, "El articulo está: " + article.getEstado());
            }
        }
        return null;
    }

    public void reviewArticle(Article article) {
        article.setEstado(Article.Estado.CORREGIDO);
        //Debe eliminar desde el objeto
        GestionEditorial.redactor.removeArticle(article);
    }

    public void returnArticle(Article article) {
        article.setEstado(Article.Estado.DEVUELTO);
        //La función debe enviar y eliminar el artículo de la cola de cada redactor
        GestionEditorial.redactor.addArticle(article);
        removeArticle(article);
    }

    public void publishArticle(Article article) {
        article.setEstado(Article.Estado.PUBLICADO);
        listPublishArticles.add(article); //Función el editor que agrega a la lista de artículos publicados
        removeArticle(article); //Función del editor que quita los artículos de la cola general
    }

    public void menuActionsArticle() {
        short optionActionsArticle = 0;
        do {
            optionActionsArticle = Short.parseShort(JOptionPane.showInputDialog("""
                                                    Seleccione la accion que quiere hacer:
                                                    1. Agregar articulo.
                                                    2. Consultar articulos. 
                                                    3. Asignar articulo.
                                                    4. Revisar articulo
                                                    5. Salir.
                                                    """));
            //Verificar las funciones de consultar artículo para integrarlo 
            //con la lista completa de artículos, y tomar por ID el artículo que se quiera consultar
            switch (optionActionsArticle) {

                case 1 -> {
                    String newKeyword = JOptionPane.showInputDialog("Ingrese la keyword del articulo: ");

                    int searchedRedactorId = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del redactor que va escribir este articulo o -1. si no tiene redactor asignado: "));
                    if (searchedRedactorId != -1) {

                        Redactor redactorTpm = searchRedactor(searchedRedactorId);
                        Article newArticle = new Article(newKeyword, redactorTpm, Article.Estado.ASIGNADO);
                        addArticleToList(newArticle);
                        System.out.println("Artículo " + newArticle.getArticleId() + " creado");
                        //Busca al redactor, crea el artículo y lo agrega a la lista del redactor correspondiente
                    } else {
                        Article newArticle = new Article(newKeyword, null, Article.Estado.POR_ASIGNAR);
                        addArticleToList(newArticle);
                        System.out.println("Artículo " + newArticle.getArticleId() + " creado");//Si no tiene redactor lo deja en la lista de artículos generales
                    }

                }
                case 2 -> {

                    if (!listArticles.isEmpty()) {
                        showArticles();
                        char answer = JOptionPane.showInputDialog("Desea consultar un articulo especifico? S/N").charAt(0);

                        switch (Character.toUpperCase(answer)) {
                            case 'S' -> {
                                int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del articulo que deseas consultar: "));
                                showArticle(idToSearch);
                            }
                            case 'N' -> {
                                JOptionPane.showMessageDialog(null, "Regresando al menu principal");
                                break;
                            }
                            default -> {
                                JOptionPane.showMessageDialog(null, "Opcion no valida");
                                break;
                            }
                        }

                    } else {
                        System.out.println("La lista de articulos esta vacia.");
                    }
                    break;
                }
                case 3 -> {
                    int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del redactor al que va a asignar el articulo"));
                    Redactor redactorTpm = searchRedactor(idToSearch);
                    asignArticle(redactorTpm);
                }            
                
                case 4 -> {
                    int idForReview = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del articulo que quiere revisar"));
                    Article articleForReview = showArticle(idForReview);
                    
                    if ((articleForReview.getNumPalabras() <= 2500) && (newArticle.getEstado().equals(Article.Estado.COMPLETADO))) {
                        reviewArticle(newArticle);
                    } else if (newArticle.getNumPalabras() > 3000 && (newArticle.getEstado().equals(Article.Estado.COMPLETADO))) {
                        returnArticle(newArticle);
                    }
                    //Revisar la cola de articulos del redactor por estado y cambiar el estado según la revisión
                    break;
                }
                case 5 -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit Articles}

        
    }
    while (optionActionsArticle 
!= 5);
    }
}
