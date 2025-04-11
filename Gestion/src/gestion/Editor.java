/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.util.ArrayList;

/**
 *
 * @author general
 */
public class Editor implements Consult {
    ArrayList<Redactor> listRedactors = new ArrayList<>();
    
    public void addRedactor (Redactor redactor){
        listRedactors.add(redactor);
    }
    @Override
    public void menuOptions() {
        short optionEditor = 0;
        System.out.println("""
                               Seleccione la accion que quiere hacer:
                               1. Acciones con redactor.
                               2. Acciones con articulos.
                               3. Calcular pagos.
                               4. Salir""");
        optionEditor = GestionEditorial.read.nextByte();
        switch (optionEditor) {
            case 1 -> {
                menuActionsRedactor();
                break;
            }
            case 2 -> {
                menuActionsArticle();
                break;
            }
            case 3 -> {
                break;
            }
            case 4 -> {
                break;
            }
            default ->
                System.out.println("Error en la opcion");
        }
        //end switch for user editor
    }

    public void menuActionsRedactor() {
        byte optionActionsRedactor = 0;
        System.out.println("""
                               Seleccione la accion que quiere hacer:
                               1. Agregar redactor.
                               2. Eliminar redactor.
                               3. Consultar redactor.
                               4. Salir""");
        optionActionsRedactor = GestionEditorial.read.nextByte();
        switch (optionActionsRedactor) {
            case 1 -> {
                
                break;
            }
            case 2 -> {
                break;
            }
            case 3 -> {
                break;
            }
            case 4 -> {
                break;
            }
            default ->
                System.out.println("Error en la opcion");

        }//end switch for Actions whit redactor
    }

    public void menuActionsArticle() {
        short optionActionsArticle = 0;
        System.out.println("""
                               Seleccione la accion que quiere hacer:
                               1. Agregar articulo.
                               2. Consultar articulo.
                               3. Asignar articulo.
                               4. Revisar articulo
                               5. salir """);
        optionActionsArticle = GestionEditorial.read.nextByte();
        switch (optionActionsArticle) {
            case 1 -> {

                break;
            }
            case 2 -> {
                break;
            }
            case 3 -> {
                break;
            }
            case 4 -> {
                break;
            }
            default ->
                System.out.println("Error en la opcion");

        }//end switch for Actions whit Articles}

    }
}
