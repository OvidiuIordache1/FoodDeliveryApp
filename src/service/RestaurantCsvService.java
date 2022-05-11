package src.service;

import src.model.Address;
import src.model.Food;
import src.model.Restaurant;
import src.model.Review;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantCsvService {
    private static RestaurantCsvService instance;
    private List<Restaurant> restaurants = new ArrayList<>();
    private File userFile;

    private RestaurantCsvService() {

    }

    public static RestaurantCsvService getInstance() {
        if (instance == null) {
            instance = new RestaurantCsvService();
        }
        return instance;
    }

    private List<String[]> getCSVCData(File fileName){
        List<String[]> data = new ArrayList<>();
        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;
            while((line = in.readLine()) != null ) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Nothing saved in file.");
        }
        return data;
    }

    public void getRestaurantsFromCsv(List<Address> allAddresses, List<Food> allFoods, List<Review> allReviews){
        this.userFile = new File("src/resources/restaurants.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            Integer restaurantId = Integer.parseInt(line[0]);
            String nume = line[1];
            Integer addressId = Integer.parseInt(line[2]);
            Address adresa = allAddresses.stream().filter(address -> address.getAdresaId() == addressId).toList().get(0);
            String nrTelefon = line[3];
            String[] foodIds = line[4].split(" ");
            String[] reviewsId;
            try {
                reviewsId = line[5].split(" ");
            } catch (Exception e) {
                reviewsId = "".split(" ");
            }
            List<Food> foods = new ArrayList<>();
            for (String f : foodIds) {
                if (!Objects.equals(f, "")) {
                    foods.add(allFoods.stream().filter(food -> food.getFoodId() == Integer.parseInt(f)).toList().get(0));
                }
            }
            List<Review> reviews = new ArrayList<>();
            for (String r : reviewsId) {
                if (!Objects.equals(r, "")) {
                    reviews.add(allReviews.stream().filter(review -> review.getReviewId() == Integer.parseInt(r)).toList().get(0));
                }
            }
            restaurants.add(new Restaurant(nume, adresa, nrTelefon, foods, reviews));
        }
    }

    private void writeToCsv(File userFile){
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Restaurant restaurant: restaurants) {
                String restaurantStr = restaurant.formatForCsv();
                wr.write(restaurantStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeRestaurantsToCsv() {
        this.userFile = new File("src/resources/restaurants.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeToCsv(userFile);
    }

    public List<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
