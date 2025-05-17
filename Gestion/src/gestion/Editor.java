package gestion;

import java.util.ArrayList;
import java.util.List;
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

    public void calculatePayments() {
        if (!listRedactors.isEmpty()) {
            StringBuilder reporte = new StringBuilder("PAGOS DE REDACTORES\n\n");

            for (Redactor redactor : listRedactors) {
                double totalPago = 0;
                int publishedArticles = 0;

                for (Article articulo : listPublishArticles) {
                    totalPago += articulo.getWordNums() * redactor.getPricePerWord();
                    publishedArticles++;
                }

                reporte.append("Redactor: ").append(redactor.getRedactorName()).append("\n")
                        .append("ID: ").append(redactor.getRedactorId()).append("\n")
                        .append("Artículos publicados: ").append(publishedArticles).append("\n")
                        .append("Total a pagar: $").append(String.format("%.2f", totalPago)).append("\n\n");
            }

            JOptionPane.showMessageDialog(null, reporte.toString());

        } else {
            JOptionPane.showMessageDialog(null, "No hay redactores registrados.");
            return;
        }

    }

    @Override
    public void menuOptions() {
        String optionEditor = " ";
        do {
            optionEditor = JOptionPane.showInputDialog(null, """ 
                               1. Acciones con redactor.
                               2. Acciones con articulos.
                               3. Calcular pagos.
                               4. Volver.
                               Seleccione la accion que quiere hacer: """, "Menú Editor", JOptionPane.INFORMATION_MESSAGE);
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
                    //Considerar trabajarlo por redactor o todos a la vez como ya está
                    //int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Seleccione ingresando el id del redactor al que le quiere calcular el pago.\nIngrese ID: "));
                    calculatePayments();
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
            optionActionsRedactor = JOptionPane.showInputDialog(null, """
                               Seleccione la accion que quiere hacer:
                               1. Agregar redactor.
                               2. Eliminar redactor.
                               3. Consultar redactores.
                               4. Consultar redactor.
                               5. Volver.
                               """, "Editor - Menú redactores", JOptionPane.INFORMATION_MESSAGE); //Revisar el id de los redactores cuando se crean
            if (optionActionsRedactor == null || optionActionsRedactor.isBlank()) {
                break;
            }
            switch (optionActionsRedactor) {

                case "1" -> {
                    try {
                        String nameRedactor = JOptionPane.showInputDialog(null, "Ingrese el nombre del redactor:", "Menú redactores - Agregar", JOptionPane.INFORMATION_MESSAGE);
                        if (nameRedactor == null || nameRedactor.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada. Nombre inválido.");
                            break;
                        }

                        String priceInput = JOptionPane.showInputDialog(null, "Ingrese el precio por palabra:", "Menú redactores - Agregar", JOptionPane.INFORMATION_MESSAGE);
                        if (priceInput == null || priceInput.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada. Precio no ingresado.");
                            break;
                        }

                        double pricePerWord = Double.parseDouble(priceInput);

                        Redactor.Region regionSelect = (Redactor.Region) JOptionPane.showInputDialog(null, "Selecciona la region del redactor", "Menú redactores - Agregar",
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

                        Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                        Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona el redactor que quieres eliminar:", "Menú redactores - Elimiar",
                                JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp.length > 0 ? redactorsListTmp[0].getRedactorName() : null);
                        if (selectedRedactor == null) {
                            break;
                        }

                        try {
                            int idToRemove = selectedRedactor.getRedactorId();

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
                            System.out.println("Algo sucedio al buscar los redactores " + e);
                        }
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }

                }
                case "4" -> {
                    Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);

                    if (!listRedactors.isEmpty()) {
                        try {
                            Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Seleccione el redactor a consultar",
                                    "Menú redactores - Consultar", JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp[0]);

                            if (selectedRedactor != null) {
                                selectedRedactor.showArticlesRedactor();
                            }
                        } catch (Exception e) {
                            System.out.println("Algo sucedio al buscar los redactores " + e);
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
        System.out.println("-----------------------------------------------------------\nArtículos de la lista del editor");
        for (Article article : listArticles) {
            System.out.println(article);
        }
        System.out.println("-----------------------------------------------------------");
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

    public Article asignArticle() {
        //Filtra de la lista de artículos del editor solo los que estan POR_ASIGNAR
        List<Article> unassignedArticles = listArticles.stream()
                .filter(article -> article.getEstado() == Article.Estado.POR_ASIGNAR)
                .toList();

        // Busca los artículos sin asignar en la lista del editor
        Article[] articlesListTmp = unassignedArticles.toArray(Article[]::new);
        Article articleForAssign = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para asignar:", "Menú artículos - Asignar",
                JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);
        if (articleForAssign == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada");
            return null;
        }
        // Busca todos los redactores
        Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
        Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona un redactor para asignar el artículo:", "Menú artículos - Asignar",
                JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp.length > 0 ? redactorsListTmp[0].getRedactorName() : null);
        if (selectedRedactor == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada");
            return null;
        }

        articleForAssign.setEstado(Article.Estado.ASIGNADO); // Cambia el estado del artículo seleccionado
        articleForAssign.setRedactor(selectedRedactor); // Cambia el redactor por el escogido en la lista
        selectedRedactor.addArticle(articleForAssign); // Agrega a la lista del redactor
        JOptionPane.showMessageDialog(null, "Artículo asignado correctamente al redactor " + selectedRedactor.getRedactorName());
        listArticles.remove(articleForAssign); // Luego lo elimina de la lista del editor

        return articleForAssign;
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

        System.out.println("Se agrego a la lista de publicados");

        removeArticle(article); //Función del editor que quita los artículos de la cola general

        System.out.println("Se elimino de la lista general");
    }

    public double pricePerArticlePublished(Article article) {
        double articlePrice = 0;
        Redactor redactorTmp = article.getRedactor();
        double priceRedactor = redactorTmp.getPricePerWord();
        int wordsOfArticle = article.getWordNums();
        articlePrice = priceRedactor * wordsOfArticle;
        JOptionPane.showMessageDialog(null, "El costo del artículo de " + redactorTmp.getRedactorName() + " es de " + articlePrice);

        return articlePrice;
    }

    public void menuActionsArticle() {
        String optionActionsArticle = "";
        do {
            optionActionsArticle = JOptionPane.showInputDialog(null, """
                                                    Seleccione la acción que quiere hacer:
                                                    1. Agregar artículo.
                                                    2. Consultar artículos del editor. 
                                                    3. Asignar artículo.
                                                    4. Revisar artículo.
                                                    5. Publicar artículo.
                                                    6. Volver.
                                                    """, "Editor - Menú artículos", JOptionPane.INFORMATION_MESSAGE);
            if (optionActionsArticle == null || optionActionsArticle.isBlank()) {
                break;
            }
            switch (optionActionsArticle) {

                case "1" -> {
                    try {
                        String newKeyword = JOptionPane.showInputDialog(null, "Ingrese la keyword del articulo: ", "Menú artículos - Agregar", JOptionPane.INFORMATION_MESSAGE);
                        if (newKeyword == null) {
                            JOptionPane.showMessageDialog(null, "La keyword no puede estar vacía.");
                            break;
                        }

                        // Diálogo para decidir si agregar redactor en este punto
                        int response = JOptionPane.showConfirmDialog(
                                null,
                                "¿Asignar redactor ahora?",
                                "Menú artículos - Agregar",
                                JOptionPane.YES_NO_OPTION
                        );
                        Redactor selectedRedactor = null;

                        if (response == JOptionPane.YES_OPTION) {
                            if (!listRedactors.isEmpty()) {
                                Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                                selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Seleccione un redactor para asignarle el artículo:", "Menú artículos - Agregar",
                                        JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp[0]);
                            } else {
                                JOptionPane.showMessageDialog(null, "No hay redactores en la lista");
                            }
                        }

                        // Asigna el estado según corresponda
                        Article.Estado newEstado = (selectedRedactor != null)
                                ? Article.Estado.ASIGNADO
                                : Article.Estado.POR_ASIGNAR;

                        // Crea el artículo y lo agrega a la lista
                        Article newArticle = new Article(newKeyword, selectedRedactor, newEstado);
                        addArticleList(newArticle);
                        JOptionPane.showMessageDialog(null, "Artículo creado\n" + newArticle);

                        if (response == JOptionPane.CANCEL_OPTION) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Algo ocurrió con la creación del artículo " + e);
                    }
                    break;
                }

                case "2" -> {
                    if (!listArticles.isEmpty()) {
                        try {
                            showArticles(); //Se muestran todos los artículos

                            int response = JOptionPane.showConfirmDialog(
                                    null,
                                    "¿Consultar un articulo especifico?",
                                    "Menú artículos - Consultar",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (response == JOptionPane.YES_OPTION) {
                                //Busca en la lista de artículos
                                Article[] articlesListTmp = listArticles.toArray(Article[]::new);
                                Article articleToShow = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo:", "Menú artículos - Consultar",
                                        JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);

                                if (articleToShow == null) {
                                    break;
                                }

                                JOptionPane.showMessageDialog(null, articleToShow.articleInfo());
                            }

                            if (response == JOptionPane.CANCEL_OPTION) {
                                JOptionPane.showMessageDialog(null, "Regresando al menú principal");
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Error al mostrar los artículos " + e + "\n-----------------------------------------");
                        }

                    } else {
                        System.out.println("La lista de articulos esta vacia.\n------------------------------");  //Cuando no hay artículos POR_ASIGNAR o COMPLETADOS en la llista general
                    }
                    break;
                }

                case "3" -> {
                    boolean flag = true;
                    if (listArticles.isEmpty()) {
                        flag = false;
                        System.out.println("No hay articulos por asignar \n----------------------------");
                        break;
                    }

                    if (listRedactors.isEmpty()) {
                        flag = false;
                        System.out.println("La lista de redactores esta vaacia\n----------------------");
                        break;
                    }

                    if (flag) {
                        try {
                            asignArticle(); // Asigna el artículo a través de la función
                        } catch (Exception e) {
                            System.out.println("Algo sucedió al asignar el articulo " + e + "\n----------------------");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No hay redactores registrados para asignar artículos");
                    }
                }

                case "4" -> {
                    if (!listRedactors.isEmpty() && !listArticles.isEmpty()) {
                        try {
                            //Muestra todos los artículos de la lista general del editor
                            Article[] articlesListTmp = listArticles.toArray(Article[]::new);
                            Article articleForReview = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para revisar:", "Menú artículos - Revisar",
                                    JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);
                            if (articleForReview == null) {
                                break;
                            }

                            int idForReview = articleForReview.getArticleId();
                            Redactor selectedRedactor = articleForReview.getRedactor();
                            System.out.println(showArticle(idForReview)); //Muestra la información del artículo seleccionado

                            if ((articleForReview.getWordNums() > 0) && (articleForReview.getWordNums() < 3000) && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                                reviewArticle(articleForReview, selectedRedactor);
                            } else if (articleForReview.getWordNums() >= 3000 && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                                returnArticle(articleForReview, selectedRedactor);
                            }

                        } catch (Exception e) {
                            System.out.println("Error al corregir al redactor " + e + "\n----------------------------");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No hay artículos por revisar");
                        break;
                    }
                }
                case "5" -> {
                    if (!listArticles.isEmpty()) {
                        try {
                            // Recorre los artículos de la lista general y toma los que estan CORREGIDOS
                            List<Article> toPublish = listArticles.stream()
                                    .filter(a -> a.getEstado() == Article.Estado.CORREGIDO)
                                    .toList();

                            if (toPublish.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "No hay artículos para publicar.");
                                break;
                            }

                            Article[] articlesListTmp = toPublish.toArray(Article[]::new);
                            Article articleToPublish = (Article) JOptionPane.showInputDialog(null, "Selecciona alguno de los artículos para publicar:", "Menú artículos - Publicar",
                                    JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);

                            if (articleToPublish == null) {
                                break;
                            }

                            System.out.println(showArticle(articleToPublish.getArticleId())); //Muestra la información del artículo

                            String url = JOptionPane.showInputDialog(null, "Ingrese la URL del artículo", "Menú artículos - Publicar", JOptionPane.INFORMATION_MESSAGE);
                            if (url == null || url.isBlank()) {
                                JOptionPane.showMessageDialog(null, "Publicación cancelada");
                                break;
                            }

                            articleToPublish.setUrl(url); // Asigna una URL como condición para publicar
                            publishArticle(articleToPublish);

                            //Diálogo para preguntar si quiere revisar el precio
                            int response = JOptionPane.showConfirmDialog(
                                    null,
                                    "¿Ver el precio del artículo?",
                                    "Menú artículos - Publicar",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (response == JOptionPane.YES_OPTION) {
                                pricePerArticlePublished(articleToPublish);
                            }
                            if (response == JOptionPane.CANCEL_OPTION) {
                                break;
                            }

                        } catch (Exception e) {
                            System.out.println("Ocurrio algo en la publicación de los artículos " + e + "\n----------------------");
                        }

                    }
                }

                case "6" -> {
                    break;
                }
                default ->
                    System.out.println(
                            "Error en la opcion");
            }//end switch for Actions whit Articles}

        } while (!"6".equals(optionActionsArticle));
    }
}
