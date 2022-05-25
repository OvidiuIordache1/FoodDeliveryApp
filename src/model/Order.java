package src.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private int restaurantId;
    private int driverId;
    protected List<Food> comanda;
    protected Date data;
    protected double totalPrice = 0;
    private Address adresa;
    private String status;

    private static int nextId = 1;

    public Order() {

    }

    public Order(int user, int restaurantId, List<Food> comanda, Date data, Address address, String status, int driverId) {
        this.orderId = nextId;
        nextId++;
        this.userId = user;
        this.restaurantId = restaurantId;
        this.comanda = comanda;
        this.data = data;
        for (Food f: comanda) {
            this.totalPrice += f.getPret();
        }
        this.adresa = address;
        this.status = status;
        this.driverId = driverId;
    }

    public Order(int user, int restaurantId, int driverId, List<Food> comanda, Date data, Double totalPrice, Address address, String status) {
        this.orderId = nextId;
        nextId++;
        this.userId = user;
        this.restaurantId = restaurantId;
        this.driverId = driverId;
        this.comanda = comanda;
        this.data = data;
        this.totalPrice = totalPrice;
        this.adresa = address;
        this.status = status;
    }

    public int getOrderId() {return orderId; }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Food> getComanda() {
        return comanda;
    }

    public void setComanda(List<Food> comanda) {
        this.comanda = comanda;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Address getAdresa() {
        return adresa;
    }

    public void setAdresa(Address adresa) {
        this.adresa = adresa;
    }

    public String getStatus() { return status; };

    public void setStatus(String status) { this.status = status; }

    public int getDriverId() { return driverId; }

    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String formatForCsv() {
        StringBuilder continutComandaStr = new StringBuilder();
        for (Food f : comanda) {
            continutComandaStr.append(f.getFoodId()).append(" ");
        }
        return  orderId +
                "," + userId +
                "," + restaurantId +
                "," + continutComandaStr +
                "," + data +
                "," + totalPrice +
                "," + adresa.getAdresaId() +
                "," + status +
                "," + driverId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + userId +
                "data='" + data + '\'' +
                ", comanda=" + comanda +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
