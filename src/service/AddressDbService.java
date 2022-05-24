package src.service;

import src.config.DbConnection;
import src.model.Address;
import src.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDbService{
    private Connection connection;

    public AddressDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    public void saveAddress(Address address) {
        String query = "insert into address values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, address.getAdresaId());
            preparedStatement.setString(2, address.getJudet());
            preparedStatement.setString(3, address.getLocalitate());
            preparedStatement.setString(4, address.getSector());
            preparedStatement.setString(5, address.getStrada());
            preparedStatement.setString(6, address.getnr());
            preparedStatement.setString(7, address.getBloc());
            preparedStatement.setString(8, address.getScara());
            preparedStatement.setString(9, address.getEtaj());
            preparedStatement.setString(10, address.getAp());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();

        String query = "select * from address";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int addressId = resultSet.getInt(1);
                String judet = resultSet.getString(2);
                String localitate = resultSet.getString(3);
                String sector = resultSet.getString(4);
                String strada = resultSet.getString(5);
                String nr = resultSet.getString(6);
                String bloc = resultSet.getString(7);
                String scara = resultSet.getString(8);
                String etaj = resultSet.getString(9);
                String ap = resultSet.getString(10);
                addresses.add(new Address(judet, localitate, sector, strada, nr, bloc, scara, etaj, ap));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }
}
