package src.service;

import src.config.DbConnection;
import src.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodDbService{
    private Connection connection;

    public FoodDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    public void saveFood(Food food) {
        String query = "insert into foods values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, food.getFoodId());
            preparedStatement.setString(2, food.getNume());
            preparedStatement.setString(3, Arrays.toString(food.getContinut()));
            preparedStatement.setString(4, food.getDescriere());
            preparedStatement.setDouble(5, food.getPret());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Food> getAll() {
        List<Food> foods = new ArrayList<>();

        String query = "select * from foods";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int foodId = resultSet.getInt(1);
                String nume = resultSet.getString(2);
                String continut = resultSet.getString(3);
                String descriere = resultSet.getString(4);
                double pret = resultSet.getDouble(5);
                foods.add(new Food(nume, continut.split(" "), descriere, pret));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }
}
