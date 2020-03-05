/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.View;

import io.VideoClub.Controller.AppController;
import io.VideoClub.Model.Client;
import io.VideoClub.Model.Comparadores.CompararaClientes;
import io.VideoClub.Model.Enums.GameCategory;
import io.VideoClub.Model.Enums.MovieCategory;
import io.VideoClub.Model.Enums.ProductsTypes;
import io.VideoClub.Model.Product;
import io.VideoClub.Utilities.Utilities;
import java.time.LocalDateTime;

/**
 *
 * @author Manueh
 */
public class GUI {

    public static void principal() {
        AppController ni = AppController.getInstance();
        //System.out.println(ni.createClient("rqwe", "fasef", "ad", LocalDateTime.now()));
        /*
        System.out.println(ni.listAllProducts());
        System.out.println(ni.loadCatalogFromDDBB());
        System.out.println(ni.listAllProducts());
         */
        System.out.println(ni.loadAllDDBB());
        
        //System.out.println(ni.closeReservation(ni.devolverUnaReserva(1)));
        //System.out.println(ni.listAllProducts());
        //System.out.println(ni.listAllAmountOfProducts("Minecraft"));
        //System.out.println("---------------");
        //System.out.println(ni.addProduct("300", ProductsTypes.Peliculas));
        System.out.println(ni.listAllProducts());
        System.out.println(ni.listAllReservations());
        System.out.println(ni.saveAllDDBB());
        
        //System.out.println(ni.listAllAmountOfProducts("Minecraft"));
        /* 
        System.out.println(ni.listAllProducts());
        System.out.println(ni.loadCatalogFromDDBB());
        System.out.println(ni.listAllProducts());
         */
        // PrimerMenu();
    }

    private static void PrimerMenu() {
        int opcion = 0;

        do {
            opcion = Utilities.Menu();
            ControladorPrimerMenu(opcion);
        } while (opcion != 26);

    }

    private static void ControladorPrimerMenu(int op1) {
        switch (op1) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                Utilities.P("Opción no válida, vuelve a intentarlo.");
        }
    }

}
