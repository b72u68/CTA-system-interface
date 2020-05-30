package project;

import java.util.ArrayList;

public class CTAStation extends GeoLocation {
    // CTAStation Instances
    private String name;
    private String location;
    private boolean wheelchair;
    private int index;

    // Default Constructor
    public CTAStation() {
        super();
        this.name = "35th-Bronzeville-IIT";
        this.location = "elevated";
        this.wheelchair = true;
        this.index = 1;
    }

    // Default Constructor
    public CTAStation(double lat, double lng) {
        setLat(lat);
        setLng(lng);
        this.name = "35th-Bronzeville-IIT";
        this.location = "elevated";
        this.wheelchair = true;
        this.index = 1;
    }

    // Non-default Constructor
    public CTAStation(String name, double lat, double lng, String location, boolean wheelchair, int index) {
        setLat(lat);
        setLng(lng);
        this.name = name;
        this.location = location;
        this.wheelchair = wheelchair;
        this.index = index;
    }

    // Getters and Setters for the Instances
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean hasWheelchair() { return wheelchair; }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    // toString method
    @Override
    public String toString() {
        return "CTAStation name: " + name
                + "\nGeoLocation: " + super.toString()
                + "\nLocation: " + location
                + "\nWheelchair Available: " + wheelchair;
    }

    // Equals method for CTAStation
    @Override
    public boolean equals(CTAStation station) {
        if (!super.equals(station)) {
            return false;
        } else if (!station.getName().equals(name)) {
            return false;
        } else if (!station.getLocation().equals(location)) {
            return false;
        } else if (station.hasWheelchair() != wheelchair) {
            return false;
        }
        return true;
    }
}
