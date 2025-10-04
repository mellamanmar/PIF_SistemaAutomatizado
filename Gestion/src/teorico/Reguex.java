package teorico;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Clase para validar URLs de artículos usando expresiones regulares
 * Valida que la URL contenga la keyword del artículo con las siguientes reglas:
 * - Debe estar en minúsculas
 * - Puede contener guiones medios (-) y números
 * - Máximo 40 caracteres
 * - Solo caracteres ASCII
 */
public class Reguex {
    
    // Patrón regex para validar URLs que contengan la keyword
    private static final String URL_PATTERN = "^[a-z0-9\\-]{1,40}$";
    private static final Pattern pattern = Pattern.compile(URL_PATTERN);
    
    /**
     * Valida si una URL es válida según las reglas establecidas
     * @param url La URL a validar
     * @param keyword La keyword que debe estar contenida en la URL
     * @return true si la URL es válida, false en caso contrario
     */
    public static boolean validarUrl(String url, String keyword) {
        if (url == null || keyword == null || url.trim().isEmpty() || keyword.trim().isEmpty()) {
            return false;
        }
        
        // Convertir a minúsculas para la comparación
        String urlLower = url.toLowerCase();
        String keywordLower = keyword.toLowerCase();
        
        // Verificar que la URL cumpla con el patrón regex primero
        Matcher matcher = pattern.matcher(urlLower);
        if (!matcher.matches()) {
            return false;
        }
        
        // Verificar que todas las palabras de la keyword estén contenidas en la URL
        // Dividir la keyword en palabras individuales
        String[] palabrasKeyword = keywordLower.split("\\s+");
        
        for (String palabra : palabrasKeyword) {
            if (palabra.trim().isEmpty()) {
                continue;
            }
            // Verificar que cada palabra de la keyword esté en la URL
            if (!urlLower.contains(palabra.trim())) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Valida si una URL cumple con el formato básico (sin verificar keyword)
     * @param url La URL a validar
     * @return true si la URL tiene el formato correcto, false en caso contrario
     */
    public static boolean validarFormatoUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        String urlLower = url.toLowerCase();
        Matcher matcher = pattern.matcher(urlLower);
        return matcher.matches();
    }
    
    /**
     * Genera una URL sugerida basada en la keyword
     * @param keyword La keyword del artículo
     * @return Una URL sugerida válida
     */
    public static String generarUrlSugerida(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "articulo-sin-keyword";
        }
        
        // Convertir a minúsculas y reemplazar espacios con guiones
        String urlSugerida = keyword.toLowerCase()
                .replaceAll("\\s+", "-")  // Reemplazar espacios con guiones
                .replaceAll("[^a-z0-9\\-]", "-")  // Reemplazar caracteres especiales con guiones
                .replaceAll("-+", "-")  // Reemplazar múltiples guiones con uno solo
                .replaceAll("^-|-$", "");  // Eliminar guiones al inicio y final
        
        // Limitar a 40 caracteres
        if (urlSugerida.length() > 40) {
            urlSugerida = urlSugerida.substring(0, 40);
            // Asegurar que no termine con guión
            if (urlSugerida.endsWith("-")) {
                urlSugerida = urlSugerida.substring(0, urlSugerida.length() - 1);
            }
        }
        
        return urlSugerida;
    }
    
    /**
     * Obtiene el mensaje de error específico para una URL inválida
     * @param url La URL que falló la validación
     * @param keyword La keyword esperada
     * @return Mensaje de error descriptivo
     */
    public static String obtenerMensajeError(String url, String keyword) {
        if (url == null || url.trim().isEmpty()) {
            return "La URL no puede estar vacía";
        }
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return "La keyword no puede estar vacía";
        }
        
        String urlLower = url.toLowerCase();
        String keywordLower = keyword.toLowerCase();
        
        // Verificar formato primero
        if (!validarFormatoUrl(url)) {
            return "La URL solo puede contener letras minúsculas, números y guiones medios (-)";
        }
        
        if (url.length() > 40) {
            return "La URL no puede tener más de 40 caracteres (actual: " + url.length() + ")";
        }
        
        // Verificar que todas las palabras de la keyword estén en la URL
        String[] palabrasKeyword = keywordLower.split("\\s+");
        StringBuilder palabrasFaltantes = new StringBuilder();
        
        for (String palabra : palabrasKeyword) {
            if (palabra.trim().isEmpty()) {
                continue;
            }
            if (!urlLower.contains(palabra.trim())) {
                if (palabrasFaltantes.length() > 0) {
                    palabrasFaltantes.append(", ");
                }
                palabrasFaltantes.append("'").append(palabra.trim()).append("'");
            }
        }
        
        if (palabrasFaltantes.length() > 0) {
            return "La URL debe contener todas las palabras de la keyword. Faltan: " + palabrasFaltantes.toString();
        }
        
        return "URL inválida";
    }
}

