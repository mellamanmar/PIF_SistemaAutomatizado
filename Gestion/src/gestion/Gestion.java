
package gestion;

import java.util.*;

/**
 *
 * @author general
 */
public class Gestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        ArrayList<Redactor> redactores = PersistenciaDatos.loadData("redactores.txt");
        
        GestionEditorial.editor.listRedactors = redactores;
        
        GestionEditorial gestion = new GestionEditorial();
        
        gestion.mainMenu();
                
        System.out.println("Cantidad de redactores: " + Redactor.counter);
        
        
    }

}
