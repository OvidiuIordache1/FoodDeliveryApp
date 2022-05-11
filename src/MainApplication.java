// platforma food delivery(localuri, comenzi, soferi, useri)
/*
Address
User
Client
Driver
Restaurant
Menu
Food
Order
Delivery
Review
*/

/*
Register Client
Register Dirver
Login
Afisare informatii client
Adaugare Adresa Noua Client
Creare Restaurant
Afisare Restaurante
Afisare Meniu Restaurant
Afisare Reviews Restaurant
Creare Review Restaurant
Creare Review Driver
Creare comanda
Vizualizare comenzi active pentru user
 */
package src;
import src.service.Manager;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainApplication {

    static List<String> interogari = Arrays.asList("create_client", "create_driver", "login", "show_logged_in_user", "add_address",
            "create_restaurant", "create_review_restaurant", "create_review_driver", "show_restaurants", "show_menu_restaurant",
            "show_reviews_restaurant", "add_order", "close");

    private static void printCommands() {
        for(int i = 0; i < interogari.size(); i++){
           System.out.println(i+1 + ". " + interogari.get(i));
        }
    }

    public static void main(String[] args) {


        Manager manager = new Manager();

        Scanner in = new Scanner(System.in);
        boolean close = false;

        while (!close) {
            printCommands();
            String cmd = in.nextLine().toLowerCase(Locale.ROOT);
            try {
                switch (cmd) {
                    case "create_client" -> manager.registerClient();
                    case "create_driver" -> manager.registerDriver();
                    case "login" -> manager.login();
                    case "show_logged_in_user" -> manager.afisareUser();
                    case "add_address_client" -> manager.adaugareAdresaClient();
                    case "create_restaurant" -> manager.addRestaurant();
                    case "create_review_restaurant" -> manager.creareReviewRestaurant();
                    case "create_review_driver" -> manager.creareReviewDriver();
                    case "show_restaurants" -> manager.afisareRestaurante();
                    case "show_menu_restaurant" -> manager.afisareMeniuRestaurant();
                    case "show_reviews_restaurant" -> manager.afisareReviewsRestaurant();
                    case "add_order" -> manager.creareOrder();
                    case "close" -> close = true;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
}
}
