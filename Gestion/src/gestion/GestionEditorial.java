/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.Scanner;

/**
 *
 * @author general
 */
public class GestionEditorial {
    public static final Scanner read = new Scanner(System.in);
    static final Redactor redactor = new Redactor(1, "simon", 0, Redactor.Region.SUR_AMERICA);
    static final Editor editor = new Editor("simon", 123);
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
            System.out.print("""
                             
                               1. Redactor.
                               2. Editor.
                               0. Salir del programa.
                               Seleccione su usuario: """);
            option = read.nextByte();
            switch (option) {
                case 1 -> {
                    System.out.print("Ingrese su ID:");
                    int idToSearch = read.nextInt();
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
