package airline.util;

import airline.model.Flight;

public class SortingUtil {
    public static void sortFlightsByPrice(Flight[] flights) {
        int n = flights.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (flights[j].price < flights[minIdx].price) {
                    minIdx = j;
                }
            }
            Flight temp = flights[minIdx];
            flights[minIdx] = flights[i];
            flights[i] = temp;
        }
    }
}
