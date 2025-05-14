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
            System.out.println(redactor);
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
                               """)); //Revisar el id de los redactores cuando se crean
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
                            System.out.println("Redactor eliminado con exito.");
                        } else {
                            System.out.println("No se encontro un redactor con ese ID.");
                        }
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }

                }
                case 3 -> {
                    if (!listRedactors.isEmpty()) {
                        showRedactors();
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                }
                case 4 -> {
                    Object[] redactors = listRedactors.toArray();

                    if (!listRedactors.isEmpty()) {
                        Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Seleccione el redactor a consultar",
                                "Redatores", JOptionPane.QUESTION_MESSAGE, null,
                                redactors, redactors[0]);

                        if (selectedRedactor != null) {
                            selectedRedactor.showArticlesRedactor();
                        }
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                }
                case 5 -> {
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit redactor
        } while (optionActionsRedactor != 5);
    }

    public void addArticleToList(Article article) {
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
        System.out.println("DEBUG...");
        article.setEstado(Article.Estado.CORREGIDO);
        //Debe eliminar desde el objeto
        redactor.removeArticle(article);
    }

    public void returnArticle(Article article, Redactor redactor) {
        System.out.println("DEBUG...");
        article.setEstado(Article.Estado.DEVUELTO);
        //La función debe enviar y eliminar el artículo de la cola de cada redactor
        redactor.addArticle(article);
        removeArticle(article);
    }

    public void publishArticle(Article article) {
        System.out.println("DEBUG...");
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
                    System.out.println(selectedRedactor);// Probar si toma el redactor
                    Article.Estado newEstado = (selectedRedactor != null) ? Article.Estado.ASIGNADO : Article.Estado.POR_ASIGNAR;
                    Article newArticle = new Article(newKeyword, selectedRedactor, newEstado);
                    addArticleToList(newArticle);
                    System.out.println(newArticle);// Probar si crea el artículo
                    System.out.println("Articulo " + newArticle.getArticleId() + " creado");
                }
                case 2 -> {

                    if (!listArticles.isEmpty()) {
                        showArticles();
                        char answer = JOptionPane.showInputDialog("Desea consultar un articulo especifico? S/N").charAt(0);

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

                    } else {
                        System.out.println("La lista de articulos esta vacia.");
                    }
                    break;
                }
                case 3 -> {
                    if (listArticles.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay artículos disponibles para asignar.");
                        break;
                    }
                    if (listRedactors.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay redactores registrados para asignar artículos.");
                        break;
                    }
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
                    System.out.println(articleForAssign.getEstado()); // Imprime el estado para verificar
                    selectedRedactor.addArticle(articleForAssign); // Agrega a la lista del redactor
                    JOptionPane.showMessageDialog(null, "Artículo asignado correctamente al redactor " + selectedRedactor.getRedactorName());
                    listArticles.remove(articleForAssign); // Luego lo elimina de la lista del editor
                }

                case 4 -> {
                    // Busca el redactor de interés
                    Redactor[] redactorsListTmp = listRedactors.toArray(Redactor[]::new);
                    Redactor selectedRedactor = (Redactor) JOptionPane.showInputDialog(null, "Selecciona un redactor para revisar sus artículos:", "Redactores",
                            JOptionPane.QUESTION_MESSAGE, null, redactorsListTmp, redactorsListTmp.length > 0 ? redactorsListTmp[0].getRedactorName() : null);
                    if (selectedRedactor != null) {

                        selectedRedactor.showArticlesRedactor(); //Muestra todos los artículos en la cola del redactor
                        
                        // Muestra la lista de los artículos de la cola del redactor en cuestión
                        Article[] articlesListTmp = selectedRedactor.articlesQueue.toArray(Article[]::new);
                        Article articleForReview = (Article) JOptionPane.showInputDialog(null, "Selecciona un artículo para asignar:", "Artículos",
                                JOptionPane.QUESTION_MESSAGE, null, articlesListTmp, articlesListTmp.length > 0 ? articlesListTmp[0] : null);

//                        int idForReview = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del artículo que quiere revisar"));
//                        Article articleForReview = selectedRedactor.showArticle(idForReview); //Muestra la información del artículo seleccionado
                        if ((articleForReview.getNumPalabras() <= 2500) && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                            reviewArticle(articleForReview, selectedRedactor);
                        } else if (articleForReview.getNumPalabras() > 3000 && (articleForReview.getEstado().equals(Article.Estado.COMPLETADO))) {
                            returnArticle(articleForReview, selectedRedactor);
                        }
                    }
                    //Revisa la cola de articulos del redactor por estado y cambiar el estado según la revisión
                }
                case 5 -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit Articles}

        } while (optionActionsArticle
                != 5);
    }
}
