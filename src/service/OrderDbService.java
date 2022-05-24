package src.service;

import src.config.DbConnection;
import src.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDbService{
    private Connection connection;

    public OrderDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    public void saveOrder(Order order) {
        String query = "insert into orders values(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setInt(2, order.getUserId());
            preparedStatement.setInt(3, order.getRestaurantId());
            preparedStatement.setInt(4, order.getDriverId());
            preparedStatement.setDate(5, (Date) order.getData());
            preparedStatement.setDouble(6, order.getTotalPrice());
            preparedStatement.setInt(7, order.getAdresa().getAdresaId());
            preparedStatement.setString(8, order.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Food f : order.getComanda()) {
            query = "insert into order_foods values(?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, order.getOrderId());
                preparedStatement.setInt(2, f.getFoodId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        String query = "select * from orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int restaurantId = resultSet.getInt(3);
                int driverId = resultSet.getInt(4);
                Date data = resultSet.getDate(5);
                Double totalPrice = resultSet.getDouble(6);
                int addressId = resultSet.getInt(7);
                String status = resultSet.getString(8);

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
                String query1 = "SELECT * FROM foods WHERE id IN (SELECT foodId from order_foods WHERE orderId = " + orderId + ");";
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

                orders.add(new Order(orderId, userId, restaurantId, driverId, foods, data, totalPrice, address, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
