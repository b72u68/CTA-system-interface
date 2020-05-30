package project;

import java.util.ArrayList;

public class CTARoute {
    // CTAStation Instances
    private String name;
    private ArrayList<CTAStation> stops;

    // Default Constructor
    public CTARoute() {
        this.name = "";
        this.stops = new ArrayList<CTAStation>();
    }

    // Non-default Constructor
    public CTARoute(String name, ArrayList<CTAStation> stops) {
        this.name = name;
        this.stops = stops;
    }

    // Getters and Setters for the Instances
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CTAStation> getStops() {
        return stops;
    }

    public void setStops(ArrayList<CTAStation> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return name + ": " + " " + stops;
    }

    // Add new station at the end of the list
    public void addStation(CTAStation station) {
        stops.add(station);
    }

    // remove station in the list
    public void removeStation(CTAStation station) {
        for (int i = 0; i < stops.size(); i++) {
            if (station.equals(stops.get(i))) {
                stops.remove(i);
            }
        }
    }

    // insert station in chosen position
    public void insertStation(int position, CTAStation station) {
        stops.add(position, station);
    }

    // Return nearest station to given latitude and longitude
    public CTAStation nearestStation(double lat, double lng) {
        double minDistance = stops.get(0).calcDistance(lat, lng);
        CTAStation nearestStation = stops.get(0);
        for (int i = 0; i < stops.size(); i++) {
            if (minDistance > stops.get(i).calcDistance(lat, lng)) {
                minDistance = stops.get(i).calcDistance(lat, lng);
                nearestStation = stops.get(i);
            }
        }
        return nearestStation;
    }

    // Equals method for CTARoute object
    public boolean equals(CTARoute route) {
        if (!route.getName().equalsIgnoreCase(name)) {
            return false;
        } else {
            if (route.getStops().size() != stops.size()) {
                return false;
            } else {
                for (int i = 0; i < route.getStops().size(); i++) {
                    if (!route.getStops().get(i).equals(stops.get(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Common Station Method: Return common station of two routes
    public CTAStation commonStation(CTARoute route) {
        for (int i = 0; i < stops.size(); i++) {
            for (int j = 0; j < route.getStops().size(); j++) {
                if (route.getStops().get(j).equals(stops.get(i))) {
                    return route.getStops().get(j);
                }
            }
        }
        return null;
    }

    // Has Station Method: Check if the route has given station
    public boolean hasStation(CTAStation station) {
        for (int i = 0; i < stops.size(); i++) {
            if (station.equals(stops.get(i))) {
                return true;
            }
        }
        return false;
    }
}
