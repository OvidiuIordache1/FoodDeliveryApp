package src.service;

import src.config.DbConnection;
import src.model.Address;
import src.model.Client;
import src.model.Driver;
import src.model.Review;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDbService{
    private Connection connection;

    public UserDbService() throws SQLException {
            this.connection = DbConnection.getInstance();
    }

    public void saveClient(Client client) {
        String query = "insert into clients values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getUserId());
            preparedStatement.setString(2, client.getUsername());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getNume());
            preparedStatement.setString(6, client.getPrenume());
            preparedStatement.setString(7, client.getNrTelefon());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Address a: client.getAdrese())
        {
            query = "insert into clients_address values(?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, client.getUserId());
                preparedStatement.setInt(2, a.getAdresaId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDriver(Driver driver) {
        String query = "insert into drivers values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, driver.getUserId());
            preparedStatement.setString(2, driver.getUsername());
            preparedStatement.setString(3, driver.getPassword());
            preparedStatement.setString(4, driver.getEmail());
            preparedStatement.setString(5, driver.getNume());
            preparedStatement.setString(6, driver.getPrenume());
            preparedStatement.setString(7, driver.getNrTelefon());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        String query = "select * from clients";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String email = resultSet.getString(4);
                String nume = resultSet.getString(5);
                String prenume = resultSet.getString(6);
                String nrTelefon = resultSet.getString(7);
                List<Address> addresses = new ArrayList<>();
                String query2 = "SELECT * FROM address WHERE id IN (select idAddress from clients_address where idClient = " + userId + ");";
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet2.next()) {
                        int addressId = resultSet2.getInt(1);
                        String judet = resultSet2.getString(2);
                        String localitate = resultSet2.getString(3);
                        String sector = resultSet2.getString(4);
                        String strada = resultSet2.getString(5);
                        String nr = resultSet2.getString(6);
                        String bloc = resultSet2.getString(7);
                        String scara = resultSet2.getString(8);
                        String etaj = resultSet2.getString(9);
                        String ap = resultSet2.getString(10);
                        addresses.add(new Address(addressId, judet, localitate, sector, strada, nr, bloc, scara, etaj, ap));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clients.add(new Client(username, password, email, nume, prenume, nrTelefon, addresses));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();

        String query = "select * from drivers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String email = resultSet.getString(4);
                String nume = resultSet.getString(5);
                String prenume = resultSet.getString(6);
                String nrTelefon = resultSet.getString(7);

                List<Review> reviews = new ArrayList<>();
                String query2 = "SELECT * FROM reviews WHERE id IN (SELECT idReview from drivers_reviews WHERE idDriver = " + userId + ");";
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet2.next()) {
                        int reviewId = resultSet2.getInt(1);
                        int usrId = resultSet2.getInt(2);
                        String mesaj = resultSet2.getString(3);
                        int nr = resultSet2.getInt(4);
                        Date data = resultSet2.getDate(5);
                        reviews.add(new Review(reviewId, usrId, mesaj, nr, data));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                drivers.add(new Driver(username, password, email, nume, prenume, nrTelefon, reviews));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers;
    }
}
