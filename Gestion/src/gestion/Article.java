package gestion;


public class Article {
    private static int counter;
    private int articleId;
    private String keyword;
    private String url;
    private Redactor redactor;
    private int wordNums;
    private Estado estado;

    public enum Estado {
        POR_ASIGNAR,
        ASIGNADO,
        COMPLETADO,
        CORREGIDO,
        DEVUELTO,
        PUBLICADO,
        PRUEBA
    }

    public Article(String keyword, Redactor redactor, Estado estado) {
        if (counter == 0){
            counter = 1;
        }else {
            counter++;
        }
        this.keyword = keyword;
        this.redactor = redactor;
        this.estado = estado;
        this.articleId = counter; 
    }
    
    
    @Override
    public String toString(){
        return "- ID: " + this.getArticleId()+ "\n - Keyword del articulo: "+ getKeyword() +
                "\n - Estado: " + this.getEstado();
    }
    
    public String articleInfo(){
        return "-ID: " + this.getArticleId()+ "\n-Keyword del articulo: "+this.getKeyword() + "\n-Palabras: " + this.getWordNums() +
                "\n-Estado: " + this.getEstado() + "\n-Redactor: " + (redactor != null ? redactor.getRedactorName() : "Sin asignar");
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Redactor getRedactor() {
        return redactor;
    }

    public void setRedactor(Redactor redactor) {
        this.redactor = redactor;
    }

    public int getWordNums() {
        return wordNums;
    }

    public void setWordNums(int wordNums) {
        this.wordNums = wordNums;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
 
    
    

    

}
