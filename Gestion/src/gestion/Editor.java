/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.ArrayList;
// comentario de lesly
/**
 *
 * @author general
 */
public class Editor implements Consult {

    private String editorName;
    private int editorId;

    public Editor(String editorName, int editorId) {
        this.editorName = editorName;
        this.editorId = editorId;
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
    
    public Redactor searchRedactor(int id){
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
            System.out.print(""" 
                             
                               1. Acciones con redactor.
                               2. Acciones con articulos.
                               3. Calcular pagos.
                               4. Salir.
                               Seleccione la accion que quiere hacer: """);
            optionEditor = GestionEditorial.read.nextByte();
            switch (optionEditor) {
                case 1 -> {
                    menuActionsRedactor();
                    break;
                }
                case 2 -> {
                    menuActionsArticle();
                    break;
                }
                case 3 -> {
                    showRedactors();
                    System.out.print("Seleccione ingresando el id del redactor al que le quiere calcular el pago.\nIngrese ID: ");
                    int idToSearch = GestionEditorial.read.nextInt();                    
                    //calculatePayments( idToSearch );   
                    break;
                }
                case 4 -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");
            }
            //end switch for user editor
        } while (optionEditor != 4);
    }

    public void menuActionsRedactor() {
        byte optionActionsRedactor = 0;
        do {
            System.out.print("""
                               1. Agregar redactor.
                               2. Eliminar redactor.
                               3. Consultar redactores.
                               4. Consultar redactor.
                               5. Salir.
                               Seleccione la accion que quiere hacer: """);
            optionActionsRedactor = GestionEditorial.read.nextByte();
            switch (optionActionsRedactor) {
                case 1 -> {
                    System.out.print("Ingresa el ID del redactor: ");
                    int idRedactor = GestionEditorial.read.nextInt();
                    GestionEditorial.read.nextLine();

                    System.out.print("Ingrese el nombre del redactor: ");
                    String nameRedactor = GestionEditorial.read.nextLine();
                    
                    System.out.println("Ingrese el precio por palabra");
                    double pricePerWord = GestionEditorial.read.nextDouble();
                    
                    //System.out.println("Ingrese la region a la que pertenece el redactor");
                    //String region = GestionEditorial.read.next();

                    Redactor newRedactor = new Redactor(idRedactor, nameRedactor, pricePerWord, Redactor.Region.SUR_AMERICA);

                    addRedactor(newRedactor);
                    break;
                }
                case 2 -> {
                    if (!listRedactors.isEmpty()) {
                        System.out.print("Ingrese el ID del redactor que deseas eliminar: ");
                        int idToRemove = GestionEditorial.read.nextInt();
                        GestionEditorial.read.nextLine();

                        if (removeRedactor(idToRemove)) {
                            System.out.println("Redactor eliminado con exito.");
                        } else {
                            System.out.println("No se encontro un redactor con ese ID.");
                        }
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }

                    break;
                }
                case 3 -> {
                    if (!listRedactors.isEmpty()) {
                        showRedactors();
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                    break;
                }
                case 4 -> {
                    if (!listRedactors.isEmpty()) {
                        System.out.print("Ingrese el ID del redactor que deseas buscar: ");
                        int idToSearch = GestionEditorial.read.nextInt();
                        showRedactor(idToSearch);
                    } else {
                        System.out.println("La lista de redactores esta vacia.");
                    }
                }
                case 5 -> {
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit redactor
        } while (optionActionsRedactor != 5);
    }

    
    public void addArticle(Article article){
        if (article.getRedactor()!= null){
            GestionEditorial.redactor.addArticle(article);
        }else {
            listArticles.add(article);
        }   
    }
    
    public void removeArticle(Article article){
        listArticles.remove(article);
    }
    
    //Se necesita que traiga el objeto (salida de la función) porque esta trayendo el apuntador. LESLY
    public void showArticles() {
        for (Article article : listArticles) {
            System.out.println(article);
        }
    }
    
    //Para buscar por ID cambiar la url por el ID que ingresa el usuario desde el menu
    public void showArticle(String url) {
        for (Article article : listArticles) {
            if (!url.equalsIgnoreCase(article.getUrl())) {                
                System.out.println("No existe ese articulo");
            } else {
                System.out.println(article.toString());
                break;
            }
        }
    }
    
    public void reviewArticle(Article article){
        article.setEstado(Article.Estado.CORREGIDO);
        GestionEditorial.redactor.removeArticle(article);
    }
    
    public void returnArticle(Article article){
        article.setEstado(Article.Estado.DEVUELTO);
        GestionEditorial.redactor.addArticle(article);
        removeArticle(article);
    }
    
    public void publishArticle(Article article){
        article.setEstado(Article.Estado.PUBLICADO);
        listPublishArticles.add(article);
        removeArticle(article);
    }
    
    public void addToEditorsQueue(Article article){
        
    }
    
    public void menuActionsArticle() {
        short optionActionsArticle = 0;
        do {
            System.out.print("""
                               1. Agregar articulo.
                               2. Consultar articulos.
                               3. Consultar articulo. 
                               4. Asignar articulo.
                               5. Revisar articulo
                               6. Salir.
                               Seleccione la accion que quiere hacer: """);
            //Verificar las funciones de consultar artículo para integrarlo 
            //con la lista completa de artículos, y tomar por ID el artículo que se quiera consultar
            optionActionsArticle = GestionEditorial.read.nextByte();
            switch (optionActionsArticle) {
                case 1 -> {
                    System.out.print("Ingrese el nombre del articulo: ");
                    String articleName = GestionEditorial.read.nextLine();
                    GestionEditorial.read.nextLine();
                    System.out.print("Ingrese el url del articulo: ");                    
                    String articleUrl = GestionEditorial.read.nextLine();
                    GestionEditorial.read.nextLine();
                    System.out.print("Ingrese el ID del redactor que va escribir este articulo o -1. si no tiene redactor asignado: ");
                    int searchedId = GestionEditorial.read.nextInt();
                    GestionEditorial.read.nextLine();
                    
                    if (searchedId != -1 ){
                        Redactor redactorTpm = searchRedactor(searchedId);
                        Article article = new Article(articleName, articleUrl, redactorTpm, 0, Article.Estado.ASIGNADO);
                        addArticle(article);
                        //Se ejecuta la función y agrega el artículo a la lista correspondiente
                    }else{
                         Article article = new Article(articleName, articleUrl, null, 0, Article.Estado.POR_ASIGNAR);
                         addArticle(article);
                    }
                    break;
                }
                case 2 -> {
                    if (!listArticles.isEmpty()) {                        
                        showArticles();
                    } else {
                        System.out.println("La lista de articulos esta vacia.");
                    }
                    break;
                }
                case 3 -> {
                    if (!listArticles.isEmpty()) {
                        System.out.print("Ingresa el url del articulo que deseas buscar: ");
                        String urlToSearch = GestionEditorial.read.nextLine();
                        showArticle(urlToSearch);
                    } else {
                        System.out.println("La lista de articulos esta vacia.");
                    }
                    break;
                }
                case 4 -> {
                    System.out.println("Ingrese el ID del redactor al que va a asignar el articulo");
                    int idToSearch = GestionEditorial.read.nextInt();
                    Redactor redactorTpm = searchRedactor(idToSearch);
                    for (Article article : listArticles){
                        if ((article.getRedactor()!= null) && (article.getEstado().equals(Article.Estado.POR_ASIGNAR))){
                            redactorTpm.addArticle(article);
                        } else {
                            System.out.println(article.getEstado());
                        }
                    }
                    break;
                }
                case 5 ->{
                    //Revisar la cola de articulos del redactor por estado y cambiar el estado según la revisión
                    break;
                }
                case 6 ->{
                    break;
                }
                default ->
                    System.out.println("Error en la opcion");

            }//end switch for Actions whit Articles}

        } while (optionActionsArticle != 6);
    }
}
