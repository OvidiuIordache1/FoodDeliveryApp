package src.model;

import java.util.Comparator;

public class RestaurantAvgRev implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant e1, Restaurant e2) {
        if (e1.getAvgReview() < e2.getAvgReview()) {
            return 1;
        } else {
            return -1;
        }
    }
}
