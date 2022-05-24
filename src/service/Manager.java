package src.service;

import src.model.*;

import java.sql.SQLException;
import java.util.*;

public class Manager {
    private UserDbService userDbService;
    private AddressDbService addressDbService;
    private FoodDbService foodDbService;
    private ReviewDbService reviewDbService;
    private RestaurantDbService restaurantDbService;
    private OrderDbService orderDbService;

    private final InputService inputService = new InputService();

    private Client loggedInClient = null;
    private Driver loggedInDriver = null;
    private List<Client> clients;
    private List<Driver> drivers;
    private List<Address> adrese;
    private List<Order> orders;
    private List<Food> foods;
    private List<Restaurant> restaurants;
    private List<Review> reviews;

    public Manager() {
        try {
            userDbService = new UserDbService();
            addressDbService = new AddressDbService();
            foodDbService = new FoodDbService();
            reviewDbService = new ReviewDbService();
            restaurantDbService = new RestaurantDbService();
            orderDbService = new OrderDbService();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adrese = addressDbService.getAll();
        foods = foodDbService.getAll();
        reviews = reviewDbService.getAll();
        orders = orderDbService.getAll();

        clients = userDbService.getAllClients();
        drivers = userDbService.getAllDrivers();

        restaurants = restaurantDbService.getAll();

    }


    public void registerClient() {
        Client client = inputService.getClient();
        clients.add(client);
        adrese.addAll(client.getAdrese());

        for (Address a: client.getAdrese()) {
            addressDbService.saveAddress(a);
        }
        userDbService.saveClient(client);

        System.out.println("Client Registered!");
    }

    public void registerDriver() {
        Driver driver = inputService.getDriver();
        drivers.add(driver);
        userDbService.saveDriver(driver);
        System.out.println("Driver Registered!");
    }

    public void login() {
        Scanner in = new Scanner(System.in);
        System.out.println("Email :");
        String email = in.nextLine();
        System.out.println("Password :");
        String password = in.nextLine();

        Client client = clients.stream().filter(cl -> Objects.equals(cl.getEmail(), email)).findAny().orElse(null);
        Driver driver = drivers.stream().filter(dr -> Objects.equals(dr.getEmail(), email)).findAny().orElse(null);

        if (client != null && Objects.equals(client.getPassword(), password)) {
            loggedInClient = client;
            System.out.println("Login success!");
        } else if (driver != null && Objects.equals(driver.getPassword(), password)) {
            loggedInDriver = driver;
            System.out.println("Login success!");
        }
        else {
            System.out.println("Wrong username or password!");
        }
    }

    public void afisareUser() {
        if (loggedInClient != null)
        {
            System.out.println(loggedInClient);
        }
        else if (loggedInDriver != null){
            System.out.println(loggedInDriver);
        }
        else {
            System.out.println("You are not logged in!");
        }
    }

    public void adaugareAdresaClient() {
        Address newAddress = inputService.getAddress();

        if (loggedInDriver != null) {
            System.out.println("Only Users can add an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            var idx = clients.indexOf(loggedInClient);
            loggedInClient.addAdresa(newAddress);
            clients.set(idx, loggedInClient);
        }
    }

    public void addRestaurant() {
        Restaurant restaurant = inputService.getRestaurant();

        foods.addAll(restaurant.getMeniu());
        for (Food f: restaurant.getMeniu())
            foodDbService.saveFood(f);

        adrese.add(restaurant.getAdresa());
        addressDbService.saveAddress(restaurant.getAdresa());

        restaurants.add(restaurant);
        restaurantDbService.saveRestaurant(restaurant);
    }


    public void afisareRestaurante() {
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

    public void afisareDrivers() {
        if (drivers.size() > 0) {
            int i = 1;
            for (Driver driver:drivers) {
                System.out.println(i + ". " + driver.getUserId() + " " + driver.getNume() + " " + driver.getPrenume());
                i += 1;
            }
        }
        else {
            System.out.println("Nu exista soferi.");
        }
    }

    public void afisareMeniuRestaurant() {
        Scanner in = new Scanner(System.in);
        afisareRestaurante();
        if (restaurants.size() > 0) {
            System.out.println("Introdu nr. restaurantului :");
            int nr = in.nextInt();
            Restaurant restaurant = restaurants.get(nr - 1);
            System.out.println(restaurant.meniuToString());
        }
    }

    public void afisareReviewsRestaurant() {
        Scanner in = new Scanner(System.in);
        afisareRestaurante();
        if (restaurants.size() > 0) {
            System.out.println("Introdu nr. restaurantului :");
            int nr = in.nextInt();
            Restaurant restaurant = restaurants.get(nr - 1);
            System.out.println(restaurant.getReviews());;
        }
    }

    public void creareReviewRestaurant() {
        if (loggedInClient != null){
            Review review = inputService.getReview(loggedInClient);
            Scanner in = new Scanner(System.in);
            System.out.println("Introdu nr. restaurantului :");
            afisareRestaurante();
            if (restaurants.size() > 0) {
                int nr = in.nextInt();
                Restaurant restaurant = restaurants.get(nr - 1);
                restaurant.addReview(review);
                restaurants.set(nr-1, restaurant);


                reviews.add(review);
                reviewDbService.saveReview(review);

                reviewDbService.addRestaurantReview(review, nr);
            }
        }
        else {
            System.out.println("You must log in!");
        }
    }

    public void creareReviewDriver() {
        if (loggedInClient != null){
            Review review = inputService.getReview(loggedInClient);
            Scanner in = new Scanner(System.in);
            System.out.println("Introdu nr. driver :");
            afisareDrivers();
            if (drivers.size() > 0) {
                int nr = in.nextInt();
                Driver driver = drivers.get(nr - 1);
                driver.addReview(review);
                drivers.set(nr-1, driver);

                reviews.add(review);
                reviewDbService.saveReview(review);

                reviewDbService.addDriverReview(review, nr);
            }
        }
        else {
            System.out.println("You must log in!");
        }
    }

    public void creareOrder() {
        if (loggedInClient != null){
            Scanner in = new Scanner(System.in);
            System.out.println("Introdu nr. restaurantului :");
            afisareRestaurante();
            if (restaurants.size() > 0) {
                int nr = Integer.parseInt(in.nextLine());
                Restaurant restaurant = restaurants.get(nr - 1);
                System.out.println("Alege din meniu: (id cu spatiu intre)");
                System.out.println(restaurant.meniuToString());
                String[] foodsIds = in.nextLine().split(" ");
                List<Food> comanda = new ArrayList<>();
                for (String f : foodsIds) {
                    comanda.add(foods.stream().filter(food -> food.getFoodId() == Integer.parseInt(f)).toList().get(0));
                }
                System.out.println("Alege adresa: ");
                loggedInClient.showAdrese();
                int adrNr = Integer.parseInt(in.nextLine());
                Address address = adrese.get(adrNr - 1);

                Random rand = new Random();
                int driverIdx = rand.nextInt(drivers.size());

                Driver driver = drivers.get(driverIdx);
                int driverId = driver.getUserId();
                Order order = inputService.getOrder(loggedInClient, nr, comanda, address, driverId);
                driver.addlivrare(order);

                var idx = clients.indexOf(loggedInClient);
                loggedInClient.addComanda(order);
                clients.set(idx, loggedInClient);
                orders.add(order);

                orderDbService.saveOrder(order);
            }
        }
        else {
            System.out.println("You must log in!");
        }
    }
}
