/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

/**
 *
 * @author general
 */
public class Redactor implements Consult {
    private String nombre;
    
    public Redactor (String nombre){
        this.nombre = nombre;
    }
    
    @Override
    public void menuOptions() {
        byte optionRedactor = 0;
        System.out.println("""
                               ---Seleccione la accion que quiere hacer:
                               1. Consultar articulos.
                               2. Modificar ESTADO de articulo.
                               3. Ver reporte de mes.
                               4. Salir""");
        optionRedactor = GestionEditorial.read.nextByte();
        switch (optionRedactor) {
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
        } // end switch for user redactor
    }

}
