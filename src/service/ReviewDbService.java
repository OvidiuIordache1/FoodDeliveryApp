package src.service;

import src.config.DbConnection;
import src.model.Food;
import src.model.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewDbService{
    private Connection connection;

    public ReviewDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    public void saveReview(Review review) {
        String query = "insert into reviews values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, review.getReviewId());
            preparedStatement.setInt(2, review.getUser());
            preparedStatement.setString(3, review.getMesaj());
            preparedStatement.setInt(4, review.getNr());
            preparedStatement.setDate(5, review.getData());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();

        String query = "select * from reviews";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reviewId = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                String mesaj = resultSet.getString(3);
                int nr = resultSet.getInt(4);
                Date date = resultSet.getDate(5);
                reviews.add(new Review(userId, mesaj, nr, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void addRestaurantReview(Review review, int idRestaurant) {
        String query = "insert into restaurants_reviews values(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idRestaurant);
            preparedStatement.setInt(2, review.getReviewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDriverReview(Review review, int idDriver) {
        String query = "insert into drivers_reviews values(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idDriver);
            preparedStatement.setInt(2, review.getReviewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
