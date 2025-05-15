package gestion;

import java.util.ArrayList;
import javax.swing.*;

public class Editor implements Consult {

    private static int counter;
    private String editorName;
    private int editorId;

    public Editor(String editorName) {
        if (counter == 0) {
            counter = 1;
        } else {
            counter++;
        }
        this.editorName = editorName;
        this.editorId = counter;
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
            System.out.println(redactor.redactorInfo());
        }
        System.out.println("------------------");
    }

    public void showRedactor(int id) {
        boolean find = false;
        for (Redactor redactor : listRedactors) {
            if (id == redactor.getRedactorId()) {
                System.out.println(redactor);
                find = true;
                break;
            }
        }
        if (!find) {
            System.out.println("No existe ese redactor");
        }
    }

    public Redactor searchRedactor(int id) {
        for (Redactor redactor : listRedactors) {
            if (id == redactor.getRedactorId()) {
                return redactor;
            }
        }
        System.out.println("No existe ese redactor");
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
        String optionEditor = " ";
        do {
            optionEditor = JOptionPane.showInputDialog(""" 
                               1. Acciones con redactor.
                               2. Acciones con articulos.
                               3. Calcular pagos.
                               4. Salir.
                               Seleccione la accion que quiere hacer: """);
            if (optionEditor == null || optionEditor.isBlank()) {
                break;
            }

            switch (optionEditor) {

                case "1" -> {
                    menuActionsRedactor();
                }
                case "2" -> {
                    menuActionsArticle();
                }
                case "3" -> {
                    showRedactors();
                    int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Seleccione ingresando el id del redactor al que le quiere calcular el pago.\nIngrese ID: "));
                    // Aquí solo se va a sumar el precio por artículo de cada redactor
                    //calculatePayments( idToSearch );    
                }
                case "4" -> {

                }
                default ->
                    System.out.println("Error en la opcion");

            }

            //end switch for user editor
        } while (!"4".equals(optionEditor));
    }

    public void menuActionsRedactor() {
        Redactor.Region[] regions = Redactor.Region.values();
        String optionActionsRedactor = " ";
        do {
            optionActionsRedactor = JOptionPane.showInputDialog("""
                               Seleccione la accion que quiere hacer:
                               1. Agregar redactor.
                               2. Eliminar redactor.
                               3. Consultar redactores.
                               4. Consultar redactor.
                               5. Salir.
                               """); //Revisar el id de los redactores cuando se crean
            if (optionActionsRedactor == null || optionActionsRedactor.isBlank()) {
                break;
            }
            switch (optionActionsRedactor) {

                case "1" -> {
                    try {
                        String nameRedactor = JOptionPane.showInputDialog("Ingrese el nombre del redactor:");
                        if (nameRedactor == null || nameRedactor.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada o nombre inválido.");
                            break;
                        }

                        String priceInput = JOptionPane.showInputDialog("Ingrese el precio por palabra:");
                        if (priceInput == null || priceInput.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada o precio no ingresado.");
                            break;
                        }

                        double pricePerWord = Double.parseDouble(priceInput);

                        Redactor.Region regionSelect = (Redactor.Region) JOptionPane.showInputDialog(null, "Selecciona la region del redactor", "Opciones",
                                JOptionPane.QUESTION_MESSAGE, null, regions, regions[0]);

                        if (regionSelect == null) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada. Región no seleccionada.");
                            break;
                        }

                        Redactor newRedactor = new Redactor(nameRedactor, pricePerWord, regionSelect);
                        addRedactor(newRedactor);

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "El redactor ingresado no es válido. Intente de nuevo." + e);
                    }

                    JOptionPane.showMessageDialog(null, "Redactor agregado");
                    break;
                }

                case "2" -> {
                    if (!listRedactors.isEmpty()) {
                        String input = JOptionPane.showInputDialog("Ingrese el ID del redactor que deseas eliminar: ");

                        if (input == null || input.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada.");
                            break;
                        }
                        try {
                            int idToRemove = Integer.parseInt(input);

                            if (removeRedactor(idToRemove)) {
                                System.out.println("Redactor eliminado con exito.");
                            } else {
                                System.out.println("No se encontro un redactor con ese ID.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("ID inválido " + e);
                        }

                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                    break;
                }
                case "3" -> {
                    if (!listRedactors.isEmpty()) {
                        try {
                            showRedactors();
                        } catch (Exception e) {
                            System.out.println("Algo sucedió al buscar los redactores " + e);
                        }
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }

                }
                case "4" -> {
                    Object[] redactors = listRedactors.toArray();

                    if (!listRedactors.isEmpty()) {
                        try {
                            Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Seleccione el redactor a consultar",
                                    "Redatores", JOptionPane.QUESTION_MESSAGE, null,
                                    redactors, redactors[0]);

                            if (selectedRedactor != null) {
                                selectedRedactor.showArticlesRedactor();
                            }
                        } catch (Exception e) {
                            System.out.println("Algo sucedió al buscar los redactores " + e);
                        }

                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                }
                case "5" -> {
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit redactor
        } while (!"5".equals(optionActionsRedactor));
    }

    public void addArticleList(Article article) {
        if (article.getRedactor() != null) {
            article.getRedactor().addArticle(article);
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
        System.out.println("------------------------");
    }

    public Article showArticle(int id) {
        for (Article article : listArticles) {
            if (article.getArticleId() == id) {
                return article;
            }
        }
        System.out.println("No existe el articulo con el id " + id);
        return null;
    }

    public Article asignArticle(Redactor redactor) {
        for (Article article : listArticles) {
            if ((article.getRedactor() == null) && (article.getEstado().equals(Article.Estado.POR_ASIGNAR))) {
                article.setRedactor(redactor); // Se asigna el redactor al artículo
                article.setEstado(Article.Estado.ASIGNADO); // Se cambia el estado a ASIGNADO
                redactor.addArticle(article); // Asigna el artículo a la cola del redactor
                return article;
            }
        }
        JOptionPane.showMessageDialog(null, "No hay artículos por asignar");
        return null;
    }

    public void reviewArticle(Article article, Redactor redactor) {
        System.out.println("Corrigiendo...");
        article.setEstado(Article.Estado.CORREGIDO);
        redactor.removeArticle(article);

        //Prueba
        System.out.println("Se corrige el artículo --- Estado nuevo: " + article.getEstado());
    }

    public void returnArticle(Article article, Redactor redactor) {
        System.out.println("Devolviendo...");
        listArticles.remove(article);
        article.setEstado(Article.Estado.DEVUELTO);

        //Prueba
        System.out.println("El artículo debe estar devuelto ::::" + article.getEstado());
        //La función debe enviar y eliminar el artículo de la cola de cada redactor
    }

    public void publishArticle(Article article) {
        System.out.println("Publicando...");
        article.setEstado(Article.Estado.PUBLICADO);
        listPublishArticles.add(article); //Función el editor que agrega a la lista de artículos publicados

        System.out.println("Se agregó a la lista de publicados");

        removeArticle(article); //Función del editor que quita los artículos de la cola general

        System.out.println("Se emilinó de la lista general");
    }

    public void menuActionsArticle() {
        String optionActionsArticle = "";
        do {
            optionActionsArticle = JOptionPane.showInputDialog("""
                                                    Seleccione la acción que quiere hacer:
                                                    1. Agregar artículo.
                                                    2. Consultar artículos. 
                                                    3. Asignar artículo.
                                                    4. Revisar artículo.
                                                    5. Publicar artículo.
                                                    6. Salir.
                                                    """);
            if (optionActionsArticle == null || optionActionsArticle.isBlank()) {
                break;
            }
            switch (optionActionsArticle) {

                case "1" -> {
                    try {
                        String newKeyword = JOptionPane.showInputDialog("Ingrese la keyword del articulo: ");
                        if (newKeyword == null) {
                            JOptionPane.showMessageDialog(null, "La keyword no puede estar vacía.");
                            break;
                        }

                        // Diálogo para decidir si agregar redactor en este punto
                        int response = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseas asignar un redactor al artículo?",
                                "Asignar Redactor",
                                JOptionPane.YES_NO_OPTION
                        );
                        Redactor selectedRedactor = null;

                        if (response == JOptionPane.YES_OPTION && !listRedactors.isEmpty()) {

                            Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                            selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona un redactor para revisar sus artículos:", "Redactores",
                                    JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp[0]);
                        }
                        if (response == JOptionPane.CANCEL_OPTION || listRedactors.isEmpty()) {
                            break;
                        }

                        Article.Estado newEstado = (selectedRedactor != null)
                                ? Article.Estado.ASIGNADO
                                : Article.Estado.POR_ASIGNAR;

                        // Crea el artículo y lo agrega a la lista
                        Article newArticle = new Article(newKeyword, selectedRedactor, newEstado);
                        addArticleList(newArticle);

                        JOptionPane.showMessageDialog(null, "Artículo creado\n" + newArticle);
                    } catch (Exception e) {
                        System.out.println("Algo ocurrió con la creación del artículo " + e);
                    }
                    break;
                }
                case "2" -> {

                    if (!listArticles.isEmpty()) {
                        try {
                            showArticles(); //Se muestran todos los artículos

                            String input = JOptionPane.showInputDialog("Desea consultar un articulo especifico? S/N");
                            if (input == null || input.isBlank()) {
                                break;
                            }
                            char answer = input.charAt(0);
                            switch (Character.toUpperCase(answer)) {
                                case 'S' -> {
                                    int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del articulo que deseas consultar: "));
                                    System.out.println(showArticle(idToSearch));
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
                        } catch (Exception e) {
                            System.out.println("Algo ocurrio al mostrar los artículos " + e);
                        }

                    } else {
                        System.out.println("La lista de articulos esta vacia.");  //Cuando no hay artículos POR_ASIGNAR o COMPLETADOS en la llista general
                    }
                    break;
                }
                case "3" -> {

                    boolean flag = true;
                    if (listArticles.isEmpty()) {
                        flag = false;
                        break;
                    }
                    if (listRedactors.isEmpty()) {
                        flag = false;
                        break;
                    }
                    if (flag) {
                        try {
                            // Busca los artículos en la lista del editor
                            Article[] articlesListTmp = listArticles.toArray(Article[]::new);
                            Article articleForAssign = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para asignar:", "Artículos",
                                    JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);
                            if (articleForAssign == null) {
                                break;
                            }

                            // Busca todos los redactores 
                            Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                            Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona un redactor para asignar el artículo:", "Redactores",
                                    JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp.length > 0 ? redactorsListTmp[0].getRedactorName() : null);
                            if (selectedRedactor == null) {
                                break;
                            }

                            articleForAssign.setEstado(Article.Estado.ASIGNADO); // Cambia el estado del artículo seleccionado
                            articleForAssign.setRedactor(selectedRedactor); // Cambia el redactor por el escogido en la lista
                            selectedRedactor.addArticle(articleForAssign); // Agrega a la lista del redactor
                            JOptionPane.showMessageDialog(null, "Artículo asignado correctamente al redactor " + selectedRedactor.getRedactorName());
                            listArticles.remove(articleForAssign); // Luego lo elimina de la lista del editor
                        } catch (Exception e) {
                            System.out.println("Algo sucedió al alsignar el articulo " + e);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No hay redactores registrados para asignar artículos.");

                    }

                }

                case "4" -> {
                    if (!listRedactors.isEmpty() && !listArticles.isEmpty()) {
                        try {
                            // Busca el redactor de interés
                            Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                            Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona un redactor para revisar:", "Redactores",
                                    JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp.length > 0 ? redactorsListTmp[0].getRedactorName() : null);
                            if (selectedRedactor != null) {

                                

                                // ¿Mostrar los artículos por Redactor?
                                
                                //Muestra todos los artículos en la lista general del editor
                                Article[] articlesListTmp = listArticles.toArray(Article[]::new);
                                Article articleForReview = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para revisar:", "Artículos",
                                        JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);

                                int idForReview = articleForReview.getArticleId();
                                System.out.println(showArticle(idForReview) + "\n-------------"); //Muestra la información del artículo seleccionado

                                if ((articleForReview.getWordNums() <= 2500) && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                                    reviewArticle(articleForReview, selectedRedactor);
                                } else if (articleForReview.getWordNums() > 3000 && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                                    returnArticle(articleForReview, selectedRedactor);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Hubo algún error al corregir al redactor " + e);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró qué revisar");
                        break;
                    }
                }
                case "5" -> {
                    // Pasar artículos publicados a la lista correspondiente y totalizar el precio por artículo
                }
                case "6" -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit Articles}

        } while (!"6".equals(optionActionsArticle));
    }
}
