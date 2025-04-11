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
    Redactor redactor = new Redactor("simon");
    Editor editor = new Editor();
    byte option = 0;

    public void mainMenu() {
        do {
            System.out.println("""
                               Seleccione su usuario:
                               1. Redactor.
                               2. Editor.
                               3. Salir del programa.""");
            option = read.nextByte();
            switch (option) {
                case 1 -> {
                    redactor.menuOptions();
                    break;
                }
                case 2 -> {
                    editor.menuOptions();
                    break;
                }
                case 3 -> {
                    break;
                }
                default -> {
                    System.out.println("Error en la opcion");
                }
            }//end of switch

        } while (option!= 3);
    }
}
