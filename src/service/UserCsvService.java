package src.service;

import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserCsvService {
    private static UserCsvService instance;
    private List<Client> clients = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private List<Address> adrese = new ArrayList<>();
    private File userFile;

    private UserCsvService() {

    }

    public static UserCsvService getInstance() {
        if (instance == null) {
            instance = new UserCsvService();
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

    public void getClientsFromCsv(List<Address> allAddresses, List<Order> allOrders){
        this.userFile = new File("src/resources/clients.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            String userId = line[0];
            String username = line[1];
            String password = line[2];
            String email = line[3];
            String nume = line[4];
            String prenume = line[5];
            String nrTelefon = line[6];
            String[] comenziIds = line[7].split(" ");
            String[] adrIds = line[8].split(" ");
            List<Address> adrese = new ArrayList<>();
            List<Order> comenzi = new ArrayList<>();

            for (String f : comenziIds) {
                if (!Objects.equals(f, "")) {
                    comenzi.add(allOrders.stream().filter(order -> order.getOrderId() == Integer.parseInt(f)).toList().get(0));
                }
            }

            for (String id : adrIds) {
                if (!Objects.equals(id, "")) {
                    adrese.add(allAddresses.stream().filter(address -> address.getAdresaId() == Integer.parseInt(id)).toList().get(0));
                }
            }

            Client newClient = new Client(username, password, email, nume, prenume, nrTelefon,comenzi, adrese);
            clients.add(newClient);
        }
    }

    public void writeClientsToCsv() {
        this.userFile = new File("src/resources/clients.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Client client: clients) {
                String clientStr = client.formatForCsv();
                wr.write(clientStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
//
//        try {
//            FileWriter wr = new FileWriter(userFile);
//            for (Client client: clients) {
//                String clientStr = client.formatForCsv();
//                wr.write(clientStr);
//                wr.write('\n');
//            }
//            wr.close();
//        }
//        catch (IOException e) {
//            System.out.println(e);
//        }

//        this.userFile = new File("src/resources/clients-addresses.csv");
//        if(!userFile.exists()) {
//            try {
//                userFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            FileWriter wr = new FileWriter(userFile);
//            for (Client client: clients) {
//                int clientId = client.getUserId();
//                List<Address> addresses = client.getAdrese();
//                for (Address adresa : addresses) {
//                    wr.write(clientId + "," + adresa.getAdresaId());
//                    wr.write('\n');
//                }
//            }
//            wr.close();
//        }
//        catch (IOException e) {
//            System.out.println(e);
//        }
    }
    public void getDriversFromCsv(List<Review> allReviews, List<Order> allOrders){
        this.userFile = new File("src/resources/drivers.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            String username = line[1];
            String password = line[2];
            String email = line[3];
            String nume = line[4];
            String prenume = line[5];
            String nrTelefon = line[6];

            String[] reviewsIds;
            try {
                reviewsIds = line[7].split(" ");
            } catch (Exception e) {
                reviewsIds = "".split(" ");
            }
            String[] ordersIds;
            try {
                ordersIds = line[8].split(" ");
            } catch (Exception e) {
                ordersIds = "".split(" ");
            }
            List<Review> reviews = new ArrayList<>();
            List<Order> comenzi = new ArrayList<>();

            for (String f : ordersIds) {
                if (!Objects.equals(f, "")) {
                    comenzi.add(allOrders.stream().filter(order -> order.getOrderId() == Integer.parseInt(f)).toList().get(0));
                }
            }

            for (String id : reviewsIds) {
                if (!Objects.equals(id, "")) {
                    reviews.add(allReviews.stream().filter(review -> review.getReviewId() == Integer.parseInt(id)).toList().get(0));
                }
            }

            Driver newDriver = new Driver(username, password, email, nume, prenume, nrTelefon, reviews, comenzi);
            drivers.add(newDriver);
        }
    }

    public void writeDriversToCsv() {
        this.userFile = new File("src/resources/drivers.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Driver driver: drivers) {
                String driverStr = driver.formatForCsv();
                wr.write(driverStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<Client> getClients()
    {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Driver> getDrivers()
    {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Address> getAdrese()
    {
        return adrese;
    }

    public void setAdrese(List<Address> adrese) {
        this.adrese = adrese;
    }
}
