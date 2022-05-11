package src.service;

import src.model.Address;
import src.model.Food;
import src.model.Order;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderCsvService {
    private static OrderCsvService instance;
    private List<Order> orders = new ArrayList<>();
    private File userFile;

    private OrderCsvService() {

    }

    public static OrderCsvService getInstance() {
        if (instance == null) {
            instance = new OrderCsvService();
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

    public void getOrdersFromCsv(List<Food> allFoods, List<Address> allAddresses){
        this.userFile = new File("src/resources/orders.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            int userId = Integer.parseInt(line[1]);
            int restaurantId = Integer.parseInt(line[2]);
            String[] foodsId = line[3].split(" ");
            LocalDate data_ = LocalDate.parse(line[4]);
            List<Food> comanda = new ArrayList<>();
            for (String f : foodsId) {
                comanda.add(allFoods.stream().filter(food -> food.getFoodId() == Integer.parseInt(f)).toList().get(0));
            }
            Address address = allAddresses.stream().filter(address1 -> address1.getAdresaId() == Integer.parseInt(line[6])).toList().get(0);
            String status = line[7];
            int driverId = Integer.parseInt(line[8]);
            Order order = new Order(userId, restaurantId, comanda, data_, address, status, driverId);
            orders.add(order);
        }
    }

    private void writeToCsv(File userFile){
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Order order: orders) {
                String reviewStr = order.formatForCsv();
                wr.write(reviewStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeOrdersToCsv() {
        this.userFile = new File("src/resources/orders.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeToCsv(userFile);
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
