package src.repository;

import src.model.Address;
import src.model.Food;
import src.model.Restaurant;
import src.model.Review;

import java.util.*;

public class RestaurantService {
    private List<Food> foods = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Address> adrese = new ArrayList<>();
    private FoodCsvService foodCsvService;
    private RestaurantCsvService restaurantCsvService;
    private AddressCsvService addressCsvService;

    public RestaurantService() {
        foodCsvService = FoodCsvService.getInstance();
        restaurantCsvService = RestaurantCsvService.getInstance();
        addressCsvService = AddressCsvService.getInstance();

        addressCsvService.getAddressesFromCsv();
        adrese = addressCsvService.getAdrese();
        foodCsvService.getFoodsFromCsv();
        foods = foodCsvService.getFoods();
//        restaurantCsvService.getRestaurantsFromCsv(adrese, foods);
        restaurants = restaurantCsvService.getRestaurants();
    }

    public void addRestaurant(Restaurant restaurant){
        foods.addAll(restaurant.getMeniu());
        foodCsvService.setFoods(foods);
        foodCsvService.writeFoodsToCsv(restaurant.getRestaurantId());

        adrese.add(restaurant.getAdresa());
        addressCsvService.setAdrese(adrese);
        addressCsvService.writeAddresesToCsv();

        restaurants.add(restaurant);
        restaurantCsvService.setRestaurants(restaurants);
        restaurantCsvService.writeRestaurantsToCsv();
    }

    public void afisareRestaurante(){
        if (restaurants.size() > 0) {
            int i = 1;
            for (Restaurant restaurant:restaurants) {
                System.out.println(i + ". " + restaurant);
                i += 1;
            }
        }
        else {
            System.out.println("Nu exista restaurante.");
        }
    }

    public void afisareMeniuRestaurant(Restaurant restaurant) {
        System.out.println(restaurant.getMeniu());
    }

    public void afisareReviewsRestaurant(Restaurant restaurant) {
        System.out.println(restaurant.getReviews());
    }

    public void creareReviewRestaurant(Review review) {
        Scanner in = new Scanner(System.in);
        System.out.println("Introdu nr. restaurantului :");
        this.afisareRestaurante();
        if (restaurants.size() > 0) {
            int nr = in.nextInt();
            Restaurant restaurant = restaurants.get(nr - 1);
            restaurant.addReview(review);
            restaurants.set(nr-1, restaurant);
        }
    }

    public List<Restaurant> getRestaurants() {
        Collections.sort(restaurants, Comparator.comparingDouble(Restaurant :: getAvgReview));
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
