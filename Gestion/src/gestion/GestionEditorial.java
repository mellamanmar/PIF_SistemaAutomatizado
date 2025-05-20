
package gestion;

import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author general
 */
public class GestionEditorial {
    public static final Scanner read = new Scanner(System.in);
    public static Editor editor = new Editor("Editor1");
    File archivo = new File("redactores.txt");
    String option = "";
    
    //Busca redactores en la lista del editor
    public Redactor searchRedactors(int id) {
        for (Redactor redactor : editor.listRedactors) {
            if (redactor.getRedactorId()==id){
                return redactor; 
            }    
        }
        return null;
    }
    
    
    
    //Menú principal
    public void mainMenu() {
        do {
            option = JOptionPane.showInputDialog(null, """
                               1. Redactor.
                               2. Editor.
                               0. Salir del programa.
                               Seleccione su usuario: """, "Menú principal", JOptionPane.INFORMATION_MESSAGE);
            if (option == null || option.isBlank()) {
                break;
            }
            switch (option) {
                case "1" -> {
                    String input = JOptionPane.showInputDialog(null, "Ingrese su ID:", "Menú Redactor", JOptionPane.INFORMATION_MESSAGE);
                    
                    if (input == null || input.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada");
                            break;
                        }
                    int idToSearch = Integer.parseInt(input);
                    Redactor redactorFound = searchRedactors(idToSearch); // Utiliza la función searchRedactors para buscar
                    
                    if (redactorFound != null){
                        redactorFound.menuOptions();
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe un redactor con ese ID.");
                    }
                    
                    break;
                }
                case "2" -> {
                    editor.menuOptions();
                    break;
                }
                case "0" -> {
                    PersistenciaDatos.saveData(editor.listRedactors, "redactores.txt");
                    System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
                    break;
                }
                default -> {
                    System.out.println("Error en la opcion");
                }
            }//end of switch

        } while (!"0".equals(option));
    }
}
