package src.service;

import src.model.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class InputService {
    public Address getAddress() {
        Scanner in = new Scanner(System.in);
        System.out.println("Judet:");
        String judet = in.nextLine();
        System.out.println("Localitate:");
        String localitate = in.nextLine();
        System.out.println("Sector:");
        String sector = in.nextLine();
        System.out.println("Strada:");
        String strada = in.nextLine();
        System.out.println("Nr:");
        String nr = in.nextLine();
        System.out.println("Bloc:");
        String bloc = in.nextLine();
        System.out.println("Scara:");
        String scara = in.nextLine();
        System.out.println("Etaj:");
        String etaj = in.nextLine();
        System.out.println("Ap:");
        String ap = in.nextLine();
        return new Address(judet, localitate, sector, strada, nr, bloc, scara, etaj, ap);
    }

    public Client getClient(){
        Scanner in = new Scanner(System.in);
        System.out.println("Username:");
        String username = in.nextLine();
        System.out.println("Password:");
        String password = in.nextLine();
        System.out.println("Email:");
        String email = in.nextLine();
        System.out.println("Nume:");
        String nume = in.nextLine();
        System.out.println("Prenume:");
        String prenume = in.nextLine();
        System.out.println("NrTelefon:");
        String nrTelefon = in.nextLine();
        System.out.println("Nr. Adrese:");
        int nrAdrese = in.nextInt();
        if (nrAdrese > 0) {
            List<Address> adrese = new ArrayList<Address>();
            for (int j = 0; j < nrAdrese; j++) {
                Address newAddress = getAddress();
                adrese.add(newAddress);
            }
            return new Client(username, password, email, nume, prenume, nrTelefon, new ArrayList<>(), adrese);
        }
        return new Client(username, password, email, nume, prenume, nrTelefon);
    }

    public Driver getDriver(){
        Scanner in = new Scanner(System.in);
        System.out.println("Username:");
        String username = in.nextLine();
        System.out.println("Password:");
        String password = in.nextLine();
        System.out.println("Email:");
        String email = in.nextLine();
        System.out.println("Nume:");
        String nume = in.nextLine();
        System.out.println("Prenume:");
        String prenume = in.nextLine();
        System.out.println("NrTelefon:");
        String nrTelefon = in.nextLine();
        return new Driver(username, password, email, nume, prenume, nrTelefon);
    }

    public Food getFood() {
        Scanner in = new Scanner(System.in);
        System.out.println("Nume Food:");
        String nume = in.nextLine();
        System.out.println("Continut Food:");
        String[] continut = in.nextLine().split(" ");
        System.out.println("Descriere Food:");
        String descriere = in.nextLine();
        System.out.println("Pret Food:");
        double pret = in.nextDouble();
        return new Food(nume, continut, descriere, pret);
    }

    public Restaurant getRestaurant() {
        Scanner in = new Scanner(System.in);
        System.out.println("Nume restaurant:");
        String name = in.nextLine();
        System.out.println("Adresa restaurant:");
        Address address = getAddress();
        System.out.println("NrTelefon restaurant:");
        String nrTelefon = in.nextLine();
        System.out.println("Nr Food din meniu:");
        List<Food> foods = new ArrayList<Food>();
        int nrFood = in.nextInt();
        for (int j=0; j < nrFood; j++ ) {
            Food food = getFood();
            foods.add(food);
        }
        return new Restaurant(name, address, nrTelefon, foods);
    }

    public Review getReview(User user){
        Scanner in = new Scanner(System.in);
        System.out.println("Mesaj:");
        String mesaj = in.nextLine();
        System.out.println("Nr: (0-5)");
        int nr = in.nextInt();
        java.sql.Date data = new java.sql.Date(System.currentTimeMillis());
        return new Review(user.getUserId(), mesaj, nr, data);
    }

    public Order getOrder(User user, int restaurantId, List<Food> comanda, Address address, int driverId) {
        return new Order(user.getUserId(), restaurantId, comanda, new java.sql.Date(System.currentTimeMillis()), address, "In curs de livrare", driverId);
    }
}
