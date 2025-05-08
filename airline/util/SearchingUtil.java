package airline.util;

import airline.model.Flight;

import java.util.ArrayList;

public class SearchingUtil {
    public static Flight[] linearSearchByRoute(Flight[] flights, String source, String destination) {
        ArrayList<Flight> results = new ArrayList<>();
        for (Flight f : flights) {
            if (f.source.equalsIgnoreCase(source) && f.destination.equalsIgnoreCase(destination)) {
                results.add(f);
            }
        }
        return results.toArray(new Flight[0]);
    }


}
