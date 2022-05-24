package src.service;

import src.config.DbConnection;
import src.model.Address;
import src.model.Food;
import src.model.Restaurant;
import src.model.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantDbService{
    private Connection connection;

    public RestaurantDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    public void saveRestaurant(Restaurant restaurant) {
        String query = "insert into restaurants values(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, restaurant.getRestaurantId_());
            preparedStatement.setString(2, restaurant.getNume());
            preparedStatement.setInt(3, restaurant.getAdresa().getAdresaId());
            preparedStatement.setString(4, restaurant.getNrTelefon());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Food f: restaurant.getMeniu())
        {
            query = "insert into menu values(?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, restaurant.getRestaurantId_());
                preparedStatement.setInt(2, f.getFoodId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = new ArrayList<>();

        String query = "select * from restaurants";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int restaurantId = resultSet.getInt(1);
                String nume = resultSet.getString(2);
                int addressId = resultSet.getInt(3);
                String nrTelefon = resultSet.getString(4);

                String query0 = "SELECT * FROM address WHERE id = " + addressId + ";";
                Address address = null;
                try (PreparedStatement preparedStatement0 = connection.prepareStatement(query0)) {
                    ResultSet resultSet0 = preparedStatement0.executeQuery();
                    while (resultSet0.next()) {
                        int addressId_ = resultSet0.getInt(1);
                        String judet = resultSet0.getString(2);
                        String localitate = resultSet0.getString(3);
                        String sector = resultSet0.getString(4);
                        String strada = resultSet0.getString(5);
                        String nr = resultSet0.getString(6);
                        String bloc = resultSet0.getString(7);
                        String scara = resultSet0.getString(8);
                        String etaj = resultSet0.getString(9);
                        String ap = resultSet0.getString(10);
                        address = new Address(addressId_, judet, localitate, sector, strada, nr, bloc, scara, etaj, ap);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                List<Food> foods = new ArrayList<>();
                String query1 = "SELECT * FROM foods WHERE id IN (SELECT idFood from menu WHERE idRestaurant = " + restaurantId + ");";
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        int foodId = resultSet1.getInt(1);
                        String numeF = resultSet1.getString(2);
                        String continut = resultSet1.getString(3);
                        String descriere = resultSet1.getString(4);
                        double pret = resultSet1.getDouble(5);
                        foods.add(new Food(foodId, numeF, continut.split(" "), descriere, pret));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                List<Review> reviews = new ArrayList<>();
                String query2 = "SELECT * FROM reviews WHERE id IN (SELECT idReview from restaurants_reviews WHERE idRestaurant = " + restaurantId + ");";
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

                restaurants.add(new Restaurant(nume, address, nrTelefon, foods, reviews));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
