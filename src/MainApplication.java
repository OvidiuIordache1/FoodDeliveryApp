// platforma food delivery(localuri, comenzi, soferi, useri)
/*
Address
User
Client
Driver
Restaurant
Food
Order
Review
*/

package src;
import src.service.AuditService;
import src.service.Manager;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainApplication {

    static List<String> interogari = Arrays.asList("create_client", "create_driver", "login", "show_logged_in_user", "add_address",
            "modify_address_client", "delete_address_client",
            "create_restaurant", "modify_food_restaurant", "delete_food_restaurant",
            "create_review_restaurant", "modify_review_restaurant", "delete_review_restaurant",
            "create_review_driver", "show_restaurants", "show_restaurants_by_reviews",
            "show_menu_restaurant", "show_reviews_restaurant", "add_order", "modify_order", "delete_order", "close");

    private static void printCommands() {
        for(int i = 0; i < interogari.size(); i++){
           System.out.println(i+1 + ". " + interogari.get(i));
        }
    }

    public static void main(String[] args) {
        AuditService auditService = new AuditService();
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
                    case "modify_address_client" -> manager.modificareAdresaClient();
                    case "delete_address_client" -> manager.stergereAdresaClient();

                    case "create_restaurant" -> manager.addRestaurant();
                    case "modify_food_restaurant" -> manager.modificareFood();
                    case "delete_food_restaurant" -> manager.stergereFood();

                    case "create_review_restaurant" -> manager.creareReviewRestaurant();
                    case "modify_review_restaurant" -> manager.modificareReviewRestaurant();
                    case "delete_review_restaurant" -> manager.stergereReviewRestaurant();

                    case "create_review_driver" -> manager.creareReviewDriver();
                    case "show_restaurants" -> manager.afisareRestaurante();
                    case "show_restaurants_by_reviews" -> manager.afisareRestauranteDupaReviews();
                    case "show_menu_restaurant" -> manager.afisareMeniuRestaurant();
                    case "show_reviews_restaurant" -> manager.afisareReviewsRestaurant();

                    case "add_order" -> manager.creareOrder();
                    case "modify_order" -> manager.modificareOrder();
                    case "delete_order" -> manager.stergereOrder();
                    case "close" -> close = true;
                }
                if (interogari.contains(cmd)){
                    auditService.writeAction(cmd);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
}
}
