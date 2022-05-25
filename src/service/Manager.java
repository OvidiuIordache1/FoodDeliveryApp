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
    private List<Review> reviews;
    private List<Restaurant> restaurants;
    TreeMap<Restaurant, Integer>  orderedRestaurants = new TreeMap<Restaurant, Integer>(new RestaurantAvgRev());


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

        for (Restaurant restaurant : restaurants) {
            orderedRestaurants.put(restaurant, restaurant.getRestaurantId_());
        }
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
            adrese.add(newAddress);
            clients.set(idx, loggedInClient);

            addressDbService.saveAddress(newAddress);
        }
    }

    public void modificareAdresaClient() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            if (loggedInClient.getAdrese().size() > 0) {
                loggedInClient.showAdrese();
                System.out.println("Introdu nr. adresei :");
                int nr = in.nextInt();

                Address adr = loggedInClient.getAdrese().get(nr - 1);


                System.out.println(adr.getAdresaId());


                System.out.println("Introdu adresa noua :");
                Address newAddress = inputService.getAddress();

                adrese.set(adr.getAdresaId() - 1, newAddress);
                loggedInClient.getAdrese().set(nr - 1, newAddress);
                clients.set(clients.indexOf(loggedInClient), loggedInClient);

                addressDbService.updateAddress(adr.getAdresaId(), newAddress);
            }
        }
    }

    public void stergereAdresaClient() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            var idx = clients.indexOf(loggedInClient);
            if (loggedInClient.getAdrese().size() > 0) {
                loggedInClient.showAdrese();
                System.out.println("Introdu nr. adresei :");
                int nr = in.nextInt();

                adrese.remove(nr - 1);
                loggedInClient.getAdrese().remove(nr - 1);
                clients.set(idx, loggedInClient);

                addressDbService.deleteAddress(nr);
            }
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

    public void modificareFood() {
        Scanner in = new Scanner(System.in);

        afisareRestaurante();
        System.out.println("Introdu nr. restaurantului :");
        int idxRestaurant = in.nextInt();

        Restaurant restaurant = restaurants.get(idxRestaurant - 1);
        for (Food f: restaurant.getMeniu()) {
            System.out.println(f);
        }

        System.out.println("Introdu nr. food :");
        int idxFood = in.nextInt();

        Food newfood = inputService.getFood();

        foodDbService.updateFood(idxFood, newfood);
        foods = foodDbService.getAll();
        orders = orderDbService.getAll();
        restaurants = restaurantDbService.getAll();
    }

    public void stergereFood() {
        Scanner in = new Scanner(System.in);

        afisareRestaurante();
        System.out.println("Introdu nr. restaurantului :");
        int idxRestaurant = in.nextInt();

        Restaurant restaurant = restaurants.get(idxRestaurant - 1);
        for (Food f: restaurant.getMeniu()) {
            System.out.println(f);
        }

        System.out.println("Introdu nr. food :");
        int idxFood = in.nextInt();

        foodDbService.deleteFood(idxFood);
        foods = foodDbService.getAll();
        orders = orderDbService.getAll();

    }



    public void afisareRestaurante() {
        if (restaurants.size() > 0) {
            int i = 1;
            for (Restaurant restaurant: restaurants) {
                System.out.println(i + ". " + restaurant);
                i += 1;
            }
        }
        else {
            System.out.println("Nu exista restaurante.");
        }
    }

    public void afisareRestauranteDupaReviews() {
        Scanner in = new Scanner(System.in);
        if (orderedRestaurants.size() > 0) {
            int i = 1;
            System.out.println("Introdu nr. de restaurante : (din " + orderedRestaurants.size() + ")");
            int nr = in.nextInt();
            while (i <= nr)
                for (Restaurant restaurant: orderedRestaurants.keySet()) {
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

    public void modificareReviewRestaurant() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            List<Review> reviewList = reviews.stream().filter(review1 -> review1.getUser() == loggedInClient.getUserId()).toList();
            for (Review r: reviewList) {
                System.out.println(r);
            }
            System.out.println("Introdu nr. review :");
            int idx = Integer.parseInt(in.nextLine());
            System.out.println("Noul Mesaj:");
            String msg = in.nextLine();
            System.out.println("Nour nr (0-5): ");
            int nr = in.nextInt();
            reviewDbService.updateReview(idx, msg, nr);
            reviews = reviewDbService.getAll();
        }
    }

    public void stergereReviewRestaurant() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            List<Review> reviewList = reviews.stream().filter(review1 -> review1.getUser() == loggedInClient.getUserId()).toList();
            for (Review r: reviewList) {
                System.out.println(r);
            }
            System.out.println("Introdu nr. review :");
            int idx = Integer.parseInt(in.nextLine());

            reviewDbService.deleteReview(idx);
            reviews = reviewDbService.getAll();
            restaurants = restaurantDbService.getAll();
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

    public void modificareOrder() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            List<Order> order_ = orders.stream().filter(order -> order.getUserId() == loggedInClient.getUserId()).toList();
            for (Order o: order_) {
                System.out.println(o);
            }
            System.out.println("Introdu nr. order :");
            int idx = in.nextInt();

            Order order = order_.get(idx - 1);

            orderDbService.updateOrder(order.getOrderId());
            orders = orderDbService.getAll();
        }
    }

    public void stergereOrder() {
        Scanner in = new Scanner(System.in);

        if (loggedInDriver != null) {
            System.out.println("Only Users can modify an address!");
        }
        else if (loggedInClient == null) {
            System.out.println("You must log in!");
        }
        else {
            List<Order> order_ = orders.stream().filter(order -> order.getUserId() == loggedInClient.getUserId()).toList();
            for (Order o: order_) {
                System.out.println(o);
            }
            System.out.println("Introdu nr. order :");
            int idx = in.nextInt();

            Order order = order_.get(idx - 1);

            orderDbService.deleteOrder(order.getOrderId());
            orders = orderDbService.getAll();
        }
    }
}
