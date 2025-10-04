package teorico;

import java.util.HashMap;
import java.util.Map;

/**
 * Autómata Finito Determinista (AFD) para controlar los estados de los artículos
 * 
 * ESTADOS:
 * q0 = POR_ASIGNAR
 * q1 = ASIGNADO  
 * q2 = COMPLETADO
 * q3 = CORREGIDO
 * q4 = DEVUELTO
 * q5 = PUBLICADO (estado final)
 * q6 = PRUEBA
 * 
 * ALFABETO (acciones):
 * - asignar: Asignar artículo a redactor
 * - completar: Redactor completa el artículo
 * - revisar_ok: Revisión exitosa (< 3000 palabras)
 * - revisar_error: Revisión con error (≥ 3000 palabras)
 * - publicar: Publicar artículo
 * - devolver: Devolver artículo al redactor
 * 
 * TIPO: DETERMINISTA
 * Justificación: Para cada estado y cada entrada, hay exactamente una transición definida.
 * No hay ambigüedad en las transiciones.
 */
public class Automata {
    
    // Estados del autómata
    public enum EstadoAutomata {
        POR_ASIGNAR(0, "POR_ASIGNAR"),
        ASIGNADO(1, "ASIGNADO"),
        COMPLETADO(2, "COMPLETADO"),
        CORREGIDO(3, "CORREGIDO"),
        DEVUELTO(4, "DEVUELTO"),
        PUBLICADO(5, "PUBLICADO"),
        PRUEBA(6, "PRUEBA");
        
        private final int codigo;
        private final String nombre;
        
        EstadoAutomata(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }
        
        public int getCodigo() { return codigo; }
        public String getNombre() { return nombre; }
        
        public static EstadoAutomata fromCodigo(int codigo) {
            for (EstadoAutomata estado : values()) {
                if (estado.codigo == codigo) return estado;
            }
            return null;
        }
    }
    
    // Acciones del alfabeto
    public enum Accion {
        ASIGNAR("asignar"),
        COMPLETAR("completar"),
        REVISAR_OK("revisar_ok"),
        REVISAR_ERROR("revisar_error"),
        PUBLICAR("publicar"),
        DEVOLVER("devolver");
        
        private final String nombre;
        
        Accion(String nombre) {
            this.nombre = nombre;
        }
        
        public String getNombre() { return nombre; }
    }
    
    // Estado actual del autómata
    private EstadoAutomata estadoActual;
    
    // Tabla de transiciones: Map<Estado, Map<Accion, Estado>>
    private Map<EstadoAutomata, Map<Accion, EstadoAutomata>> tablaTransiciones;
    
    /**
     * Constructor del autómata
     * @param estadoInicial Estado inicial del autómata
     */
    public Automata(EstadoAutomata estadoInicial) {
        this.estadoActual = estadoInicial;
        this.tablaTransiciones = new HashMap<>();
        inicializarTablaTransiciones();
    }
    
    /**
     * Inicializa la tabla de transiciones del autómata
     * Define todas las transiciones válidas según el flujo del sistema
     */
    private void inicializarTablaTransiciones() {
        // Estado POR_ASIGNAR (q0)
        Map<Accion, EstadoAutomata> transicionesQ0 = new HashMap<>();
        transicionesQ0.put(Accion.ASIGNAR, EstadoAutomata.ASIGNADO);
        tablaTransiciones.put(EstadoAutomata.POR_ASIGNAR, transicionesQ0);
        
        // Estado ASIGNADO (q1)
        Map<Accion, EstadoAutomata> transicionesQ1 = new HashMap<>();
        transicionesQ1.put(Accion.COMPLETAR, EstadoAutomata.COMPLETADO);
        tablaTransiciones.put(EstadoAutomata.ASIGNADO, transicionesQ1);
        
        // Estado COMPLETADO (q2)
        Map<Accion, EstadoAutomata> transicionesQ2 = new HashMap<>();
        transicionesQ2.put(Accion.REVISAR_OK, EstadoAutomata.CORREGIDO);
        transicionesQ2.put(Accion.REVISAR_ERROR, EstadoAutomata.DEVUELTO);
        tablaTransiciones.put(EstadoAutomata.COMPLETADO, transicionesQ2);
        
        // Estado CORREGIDO (q3)
        Map<Accion, EstadoAutomata> transicionesQ3 = new HashMap<>();
        transicionesQ3.put(Accion.PUBLICAR, EstadoAutomata.PUBLICADO);
        tablaTransiciones.put(EstadoAutomata.CORREGIDO, transicionesQ3);
        
        // Estado DEVUELTO (q4)
        Map<Accion, EstadoAutomata> transicionesQ4 = new HashMap<>();
        transicionesQ4.put(Accion.DEVOLVER, EstadoAutomata.ASIGNADO);
        tablaTransiciones.put(EstadoAutomata.DEVUELTO, transicionesQ4);
        
        // Estado PUBLICADO (q5) - Estado final, no transiciones
        Map<Accion, EstadoAutomata> transicionesQ5 = new HashMap<>();
        tablaTransiciones.put(EstadoAutomata.PUBLICADO, transicionesQ5);
        
        // Estado PRUEBA (q6)
        Map<Accion, EstadoAutomata> transicionesQ6 = new HashMap<>();
        transicionesQ6.put(Accion.ASIGNAR, EstadoAutomata.ASIGNADO);
        tablaTransiciones.put(EstadoAutomata.PRUEBA, transicionesQ6);
    }
    
    /**
     * Procesa una acción y cambia el estado del autómata
     * @param accion La acción a procesar
     * @return true si la transición es válida, false en caso contrario
     */
    public boolean procesarAccion(Accion accion) {
        if (estadoActual == null) {
            return false;
        }
        
        Map<Accion, EstadoAutomata> transicionesDisponibles = tablaTransiciones.get(estadoActual);
        
        if (transicionesDisponibles == null || !transicionesDisponibles.containsKey(accion)) {
            return false; // Transición no válida
        }
        
        EstadoAutomata nuevoEstado = transicionesDisponibles.get(accion);
        estadoActual = nuevoEstado;
        return true;
    }
    
    /**
     * Verifica si una acción es válida desde el estado actual
     * @param accion La acción a verificar
     * @return true si la acción es válida, false en caso contrario
     */
    public boolean esAccionValida(Accion accion) {
        if (estadoActual == null) {
            return false;
        }
        
        Map<Accion, EstadoAutomata> transicionesDisponibles = tablaTransiciones.get(estadoActual);
        return transicionesDisponibles != null && transicionesDisponibles.containsKey(accion);
    }
    
    /**
     * Obtiene el estado actual del autómata
     * @return El estado actual
     */
    public EstadoAutomata getEstadoActual() {
        return estadoActual;
    }
    
    /**
     * Establece el estado del autómata
     * @param estado El nuevo estado
     */
    public void setEstado(EstadoAutomata estado) {
        this.estadoActual = estado;
    }
    
    /**
     * Verifica si el autómata está en un estado final
     * @return true si está en estado final, false en caso contrario
     */
    public boolean esEstadoFinal() {
        return estadoActual == EstadoAutomata.PUBLICADO;
    }
    
    /**
     * Obtiene las acciones válidas desde el estado actual
     * @return Array de acciones válidas
     */
    public Accion[] getAccionesValidas() {
        if (estadoActual == null) {
            return new Accion[0];
        }
        
        Map<Accion, EstadoAutomata> transicionesDisponibles = tablaTransiciones.get(estadoActual);
        if (transicionesDisponibles == null) {
            return new Accion[0];
        }
        
        return transicionesDisponibles.keySet().toArray(new Accion[0]);
    }
    
    /**
     * Obtiene el estado siguiente para una acción dada
     * @param accion La acción
     * @return El estado siguiente, o null si la acción no es válida
     */
    public EstadoAutomata getEstadoSiguiente(Accion accion) {
        if (estadoActual == null) {
            return null;
        }
        
        Map<Accion, EstadoAutomata> transicionesDisponibles = tablaTransiciones.get(estadoActual);
        if (transicionesDisponibles == null) {
            return null;
        }
        
        return transicionesDisponibles.get(accion);
    }
    
    /**
     * Obtiene una representación textual del autómata
     * @return String con la información del autómata
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== AUTÓMATA FINITO DETERMINISTA ===\n");
        sb.append("Estado actual: ").append(estadoActual.getNombre()).append("\n");
        sb.append("Es estado final: ").append(esEstadoFinal()).append("\n");
        sb.append("Acciones válidas: ");
        
        Accion[] accionesValidas = getAccionesValidas();
        for (int i = 0; i < accionesValidas.length; i++) {
            sb.append(accionesValidas[i].getNombre());
            if (i < accionesValidas.length - 1) {
                sb.append(", ");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Obtiene el diagrama de transiciones en formato texto
     * @return String con el diagrama de transiciones
     */
    public String getDiagramaTransiciones() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DIAGRAMA DE TRANSICIONES ===\n");
        sb.append("q0 (POR_ASIGNAR) --[asignar]--> q1 (ASIGNADO)\n");
        sb.append("q1 (ASIGNADO) --[completar]--> q2 (COMPLETADO)\n");
        sb.append("q2 (COMPLETADO) --[revisar_ok]--> q3 (CORREGIDO)\n");
        sb.append("q2 (COMPLETADO) --[revisar_error]--> q4 (DEVUELTO)\n");
        sb.append("q3 (CORREGIDO) --[publicar]--> q5 (PUBLICADO) [FINAL]\n");
        sb.append("q4 (DEVUELTO) --[devolver]--> q1 (ASIGNADO)\n");
        sb.append("q6 (PRUEBA) --[asignar]--> q1 (ASIGNADO)\n");
        return sb.toString();
    }
    
    /**
     * Método de prueba para demostrar el funcionamiento del autómata
     */
    public static void demostrarFuncionamiento() {
        System.out.println("=== DEMOSTRACIÓN DEL AUTÓMATA FINITO DETERMINISTA ===\n");
        
        // Crear un autómata en estado inicial
        Automata automata = new Automata(EstadoAutomata.POR_ASIGNAR);
        System.out.println("Estado inicial: " + automata.getEstadoActual().getNombre());
        System.out.println("Acciones válidas: " + java.util.Arrays.toString(automata.getAccionesValidas()));
        System.out.println();
        
        // Simular el flujo completo de un artículo
        System.out.println("=== FLUJO COMPLETO DE UN ARTÍCULO ===");
        
        // 1. Asignar artículo
        System.out.println("1. Asignando artículo...");
        if (automata.procesarAccion(Accion.ASIGNAR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 2. Completar artículo
        System.out.println("2. Completando artículo...");
        if (automata.procesarAccion(Accion.COMPLETAR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 3. Revisar artículo (éxito)
        System.out.println("3. Revisando artículo (éxito)...");
        if (automata.procesarAccion(Accion.REVISAR_OK)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 4. Publicar artículo
        System.out.println("4. Publicando artículo...");
        if (automata.procesarAccion(Accion.PUBLICAR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
            System.out.println("   ✓ ¿Es estado final? " + automata.esEstadoFinal());
        }
        
        System.out.println("\n=== FLUJO ALTERNATIVO (ARTÍCULO DEVUELTO) ===");
        
        // Crear otro autómata para demostrar el flujo alternativo
        Automata automata2 = new Automata(EstadoAutomata.COMPLETADO);
        System.out.println("Estado inicial: " + automata2.getEstadoActual().getNombre());
        
        // Revisar artículo (error)
        System.out.println("1. Revisando artículo (error)...");
        if (automata2.procesarAccion(Accion.REVISAR_ERROR)) {
            System.out.println("   ✓ Estado: " + automata2.getEstadoActual().getNombre());
        }
        
        // Devolver artículo
        System.out.println("2. Devolviendo artículo...");
        if (automata2.procesarAccion(Accion.DEVOLVER)) {
            System.out.println("   ✓ Estado: " + automata2.getEstadoActual().getNombre());
        }
        
        System.out.println("\n=== DIAGRAMA DE TRANSICIONES ===");
        System.out.println(automata.getDiagramaTransiciones());
    }
    
    /**
     * Método de prueba para demostrar el flujo de artículo devuelto
     */
    public static void demostrarFlujoDevuelto() {
        System.out.println("=== DEMOSTRACIÓN: FLUJO DE ARTÍCULO DEVUELTO ===\n");
        
        // Crear un autómata en estado COMPLETADO
        Automata automata = new Automata(EstadoAutomata.COMPLETADO);
        System.out.println("Estado inicial: " + automata.getEstadoActual().getNombre());
        
        // 1. Revisar artículo con error (≥ 3000 palabras)
        System.out.println("1. Editor revisa artículo con ≥ 3000 palabras...");
        if (automata.procesarAccion(Accion.REVISAR_ERROR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 2. Redactor modifica el artículo (devuelve a redactor)
        System.out.println("2. Redactor modifica el artículo...");
        if (automata.procesarAccion(Accion.DEVOLVER)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 3. Redactor completa el artículo corregido
        System.out.println("3. Redactor completa el artículo corregido...");
        if (automata.procesarAccion(Accion.COMPLETAR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 4. Editor revisa el artículo corregido
        System.out.println("4. Editor revisa el artículo corregido...");
        if (automata.procesarAccion(Accion.REVISAR_OK)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
        }
        
        // 5. Publicar el artículo
        System.out.println("5. Publicar el artículo...");
        if (automata.procesarAccion(Accion.PUBLICAR)) {
            System.out.println("   ✓ Estado: " + automata.getEstadoActual().getNombre());
            System.out.println("   ✓ ¿Es estado final? " + automata.esEstadoFinal());
        }
        
        System.out.println("\n=== RESUMEN DEL FLUJO ===");
        System.out.println("COMPLETADO → REVISAR_ERROR → DEVUELTO");
        System.out.println("DEVUELTO → DEVOLVER → ASIGNADO");
        System.out.println("ASIGNADO → COMPLETAR → COMPLETADO");
        System.out.println("COMPLETADO → REVISAR_OK → CORREGIDO");
        System.out.println("CORREGIDO → PUBLICAR → PUBLICADO");
    }
}
