/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author general
 */
public class GestionEditorial {
    public static final Scanner read = new Scanner(System.in);
    public static Editor editor = new Editor("Editor1");
    byte option = 0;
    
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
            option = Byte.parseByte(JOptionPane.showInputDialog("""
                               1. Redactor.
                               2. Editor.
                               0. Salir del programa.
                               Seleccione su usuario: """));
            switch (option) {
                case 1 -> {
                    int idToSearch = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID:"));
                    Redactor redactorFound = searchRedactors(idToSearch); // Utiliza la función searchRedactors para buscar
                    
                    if (redactorFound != null){
                        redactorFound.menuOptions();
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe un redactor con ese ID.");
                    }
                    
                    break;
                }
                case 2 -> {
                    editor.menuOptions();
                    break;
                }
                case 0 -> {
                    break;
                }
                default -> {
                    System.out.println("Error en la opcion");
                }
            }//end of switch

        } while (option!= 0);
    }
}
