package src.repository;

import src.model.Address;
import src.model.Client;
import src.model.Driver;

import java.util.List;
import java.util.Objects;

public class UserService {
    private Client loggedInClient = null;
    private Driver loggedInDriver = null;
    private List<Client> clients;
    private List<Driver> drivers;
    private List<Address> adrese;
    private UserCsvService userCsvService;
    private AddressCsvService addressCsvService;

    public UserService() {
        userCsvService = UserCsvService.getInstance();
        addressCsvService = AddressCsvService.getInstance();

        addressCsvService.getAddressesFromCsv();
        adrese = addressCsvService.getAdrese();
//        userCsvService.getClientsFromCsv(adrese);
        clients = userCsvService.getClients();
        drivers = userCsvService.getDrivers();
    }

    public void registerClient(Client client){
        clients.add(client);
        adrese.addAll(client.getAdrese());

        userCsvService.setClients(clients);
        addressCsvService.setAdrese(adrese);

        userCsvService.writeClientsToCsv();
        addressCsvService.writeAddresesToCsv();

        System.out.println("Client Registered!");
    }

    public void registerDriver(Driver driver){
        drivers.add(driver);
        userCsvService.setDrivers(drivers);
        userCsvService.writeDriversToCsv();
        System.out.println("Driver Registered!");
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

    public void login(String email, String password) {
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

    public void adaugareAdresa(Address newAddress) {
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

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public Driver getLoggedInDriver() {
        return loggedInDriver;
    }

}
