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
    public static Redactor redactor = new Redactor();
    public Editor editor = new Editor();
    byte option = 0;
    
    //Busca redactores en la lista del editor
    public boolean searchRedactors(int id) {
        for (Redactor redactor : editor.listRedactors) {
            return id == redactor.getRedactorId();      
        }
        return false;
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
                    // Utiliza la función searchRedactors para buscar
                    if (searchRedactors(idToSearch)){                        
                        redactor.menuOptions();
                    }else{
                        System.out.println("No existe ese redactor");
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
