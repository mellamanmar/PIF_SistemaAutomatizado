package gestion;

import teorico.Automata;

public class Article {
    private static int counter;
    private int articleId;
    private String keyword;
    private String url;
    private Redactor redactor;
    private int wordNums;
    private Estado estado;
    private Automata automata;

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
        
        // Inicializar el autómata según el estado
        this.automata = new Automata(convertirEstadoAAutomata(estado));
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
        // Sincronizar el autómata con el nuevo estado
        this.automata.setEstado(convertirEstadoAAutomata(estado));
    }
    
    /**
     * Convierte un Estado del enum a EstadoAutomata del autómata
     * @param estado Estado del enum
     * @return EstadoAutomata correspondiente
     */
    private Automata.EstadoAutomata convertirEstadoAAutomata(Estado estado) {
        switch (estado) {
            case POR_ASIGNAR: return Automata.EstadoAutomata.POR_ASIGNAR;
            case ASIGNADO: return Automata.EstadoAutomata.ASIGNADO;
            case COMPLETADO: return Automata.EstadoAutomata.COMPLETADO;
            case CORREGIDO: return Automata.EstadoAutomata.CORREGIDO;
            case DEVUELTO: return Automata.EstadoAutomata.DEVUELTO;
            case PUBLICADO: return Automata.EstadoAutomata.PUBLICADO;
            case PRUEBA: return Automata.EstadoAutomata.PRUEBA;
            default: return Automata.EstadoAutomata.POR_ASIGNAR;
        }
    }
    
    /**
     * Convierte un EstadoAutomata del autómata a Estado del enum
     * @param estadoAutomata EstadoAutomata del autómata
     * @return Estado correspondiente del enum
     */
    private Estado convertirAutomataAEstado(Automata.EstadoAutomata estadoAutomata) {
        switch (estadoAutomata) {
            case POR_ASIGNAR: return Estado.POR_ASIGNAR;
            case ASIGNADO: return Estado.ASIGNADO;
            case COMPLETADO: return Estado.COMPLETADO;
            case CORREGIDO: return Estado.CORREGIDO;
            case DEVUELTO: return Estado.DEVUELTO;
            case PUBLICADO: return Estado.PUBLICADO;
            case PRUEBA: return Estado.PRUEBA;
            default: return Estado.POR_ASIGNAR;
        }
    }
    
    /**
     * Procesa una acción usando el autómata
     * @param accion La acción a procesar
     * @return true si la transición fue exitosa, false en caso contrario
     */
    public boolean procesarAccion(Automata.Accion accion) {
        boolean exito = automata.procesarAccion(accion);
        if (exito) {
            // Sincronizar el estado del enum con el autómata
            this.estado = convertirAutomataAEstado(automata.getEstadoActual());
        }
        return exito;
    }
    
    /**
     * Verifica si una acción es válida desde el estado actual
     * @param accion La acción a verificar
     * @return true si la acción es válida, false en caso contrario
     */
    public boolean esAccionValida(Automata.Accion accion) {
        return automata.esAccionValida(accion);
    }
    
    /**
     * Obtiene las acciones válidas desde el estado actual
     * @return Array de acciones válidas
     */
    public Automata.Accion[] getAccionesValidas() {
        return automata.getAccionesValidas();
    }
    
    /**
     * Obtiene el autómata del artículo
     * @return El autómata del artículo
     */
    public Automata getAutomata() {
        return automata;
    }
    
    /**
     * Obtiene información detallada del autómata
     * @return String con la información del autómata
     */
    public String getInfoAutomata() {
        return automata.toString();
    }
}
