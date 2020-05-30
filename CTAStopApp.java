package project;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CTAStopApp {

    /** Main Method: User Interface **/
    public static void main(String[] args) {

        // Input data file
        File stationData = new File("src\\project\\CTAStops.csv");

        // List CTAStation of each lines
        ArrayList<CTAStation> red = new ArrayList<>();
        ArrayList<CTAStation> green = new ArrayList<>();
        ArrayList<CTAStation> blue = new ArrayList<>();
        ArrayList<CTAStation> brown = new ArrayList<>();
        ArrayList<CTAStation> purple = new ArrayList<>();
        ArrayList<CTAStation> pink = new ArrayList<>();
        ArrayList<CTAStation> orange = new ArrayList<>();
        ArrayList<CTAStation> yellow = new ArrayList<>();

        createRouteData(stationData, red, green, blue, brown, purple, pink, orange, yellow);

        // Routes existed in the system
        CTARoute redRoute = new CTARoute("Red Line", sort(red));
        CTARoute greenRoute =  new CTARoute("Green Line", sort(green));
        CTARoute blueRoute = new CTARoute("Blue Line", sort(blue));
        CTARoute brownRoute = new CTARoute("Brown Line", sort(brown));
        CTARoute purpleRoute = new CTARoute("Purple Line", sort(purple));
        CTARoute pinkRoute = new CTARoute("Pink Line", sort(pink));
        CTARoute orangeRoute = new CTARoute("Orange Line", sort(orange));
        CTARoute yellowRoute = new CTARoute("Yellow Line", sort(yellow));

        // User option (int)
        int option;
        do {
            menuMain();
            option = getInteger();

            if (option == 1) {
                addStation(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 2) {
                modifyStation(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 3) {
                removeStation(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 4) {
                searchStation(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 5) {
                displayNearestStation(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 6) {
                generatePath(redRoute, greenRoute, blueRoute, brownRoute, purpleRoute, pinkRoute, orangeRoute, yellowRoute);
            } else if (option == 7) {
                System.out.println("\nThank you for using CTA Station Service.");
                System.out.println("*** EXIT ***");
            }
        } while (!(option == 7));
    }

    /** Menu Methods **/
    // Create Main Menu Method
    public static void menuMain() {
        System.out.println("\n*** CTA STATION SERVICE ***");
        System.out.println("CTA STATIONS MENU");
        System.out.println("1. Add New Station");
        System.out.println("2. Modify Existing Station");
        System.out.println("3. Remove Station");
        System.out.println("4. Search Station");
        System.out.println("5. Display Nearest Station");
        System.out.println("6. Display Path between 2 Stations");
        System.out.println("7. Exit");
    }

    // Create Route Menu and Take in User Input
    public static CTARoute menuRoute(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        System.out.println("\nCTA Route List: ");
        System.out.println("1. Red Line");
        System.out.println("2. Green Line");
        System.out.println("3. Blue Line");
        System.out.println("4. Brown Line");
        System.out.println("5. Purple Line");
        System.out.println("6. Pink Line");
        System.out.println("7. Orange Line");
        System.out.println("8. Yellow Line");

        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};
        int option = 0;

        do {
            option = getInteger();
        } while (!(1 <= option && option <= 8));

        return routes[option-1];
    }

    // Create Menu Station: Take in list of CTAStation objects and return the chosen CTAStation
    public static CTAStation createMenuStation(ArrayList<CTAStation> stations, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        for (int i = 0; i < stations.size(); i++) {
            System.out.println("\nOption " + (i+1) + ": " + stations.get(i));

            ArrayList<CTARoute> routes = routeHasStation(stations.get(i), red, green, blue, brown, purple, pink, orange, yellow);
            System.out.print("This Station is Found in Route(s): ");
            for (int j = 0; j < routes.size(); j++) {
                System.out.print(routes.get(j).getName() + ", ");
            }
            System.out.println();
        }

        System.out.println("\nEnter Option Number From 1 to " + stations.size());
        // User option (int)
        int option = 0;
        do {
            option = getInteger();
        } while (!(1 <= option && option <= stations.size()));

        return stations.get(option-1);
    }

    // Create Route Menu Method: Take in list of CTARoute objects and create a menu
    public static void createMenuRoute(ArrayList<CTARoute> routes) {
        for (int i = 0; i < routes.size(); i++) {
            System.out.println("Option " + (i+1) + ": " + routes.get(i).getName());
        }
    }

    // Create Route Menu Method 2: Take in list of CTARoute objects, create a menu and return the chosen route
    public static CTARoute createMenuRoute2(ArrayList<CTARoute> routes) {
        for (int i = 0; i < routes.size(); i++) {
            System.out.println("Option " + (i+1) + ": " + routes.get(i).getName());
        }

        // user option (int)
        int option = 0;
        System.out.println("\nEnter Option Number From 1 to " + routes.size());
        do {
            option = getInteger();
        } while (!(1 <= option && option <= routes.size()));

        return routes.get(option-1);
    }

    /** Read Data From Input Method **/
    // Create Route Data Method: Read input file and store data in the list
    public static void createRouteData(File stationData, ArrayList<CTAStation> red, ArrayList<CTAStation> green, ArrayList<CTAStation> blue, ArrayList<CTAStation> brown, ArrayList<CTAStation> purple, ArrayList<CTAStation> pink, ArrayList<CTAStation> orange, ArrayList<CTAStation> yellow) {
        try {
            // Input File Scanner
            Scanner file = new Scanner(stationData);
            // Skip first 2 lines in the file
            file.nextLine();
            file.nextLine();

            while (file.hasNextLine()) {
                // Read lines in the file
                String line = file.nextLine();
                // list of file data splitted by the commas
                String[] stations = line.split(",");

                // CTAStation object created by the file data
                CTAStation station = new CTAStation();

                for (int i = 5; i < stations.length; i++) {
                    if (Integer.parseInt(stations[i]) != -1) {
                        if (i == 5) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            red.add(station);
                        } else if (i == 6) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            green.add(station);
                        } else if (i == 7) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            blue.add(station);
                        } else if (i == 8) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            brown.add(station);
                        } else if (i == 9) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            purple.add(station);
                        } else if (i == 10) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            pink.add(station);
                        } else if (i == 11) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            orange.add(station);
                        } else if (i == 12) {
                            station = new CTAStation(stations[0], Double.parseDouble(stations[1]), Double.parseDouble(stations[2]), stations[3], Boolean.parseBoolean((stations[4]).toLowerCase()), Integer.parseInt(stations[i]));
                            yellow.add(station);
                        }
                    }
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error Occurred: Data File Not Found");
        }
    }

    // Sort Method: Take in list of CTAStation objects and sort them by indexes
    public static ArrayList<CTAStation> sort(ArrayList<CTAStation> station) {
        for (int i = 0; i < station.size(); i++) {
            for (int j = 0; j < station.size()-1; j++) {
                if (station.get(j).getIndex() > station.get(j+1).getIndex()) {
                    CTAStation temp = station.get(j);
                    station.set(j, station.get(j+1));
                    station.set(j+1, temp);
                }
            }
        }
        return station;
    }

    /** Methods Dealing With User Interface Requirements **/
    // Add Station Method: Add New Station to the route
    public static void addStation(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // User chosen route
        CTARoute route = menuRoute(red, green, blue, brown, purple, pink, orange, yellow);

        System.out.println();

        // Station information given by user
        String name = getStationName();
        double lat = getLat();
        double lng = getLng();
        String location = getLocation();
        boolean wheelchair = getWheelchair();
        int index = getStationIndex(route);

        // New CTAStation object created from user input
        CTAStation station = new CTAStation(name, lat, lng, location, wheelchair, index);

        if (index == route.getStops().size()) {
            route.addStation(station);
        } else {
            route.insertStation(index, station);
        }

        fixIndex(red, green, blue, brown, purple, pink, orange, yellow);
        System.out.println("\nAdding Station Successfully");
    }

    // Modify Station Method: Modify existed station in route
    public static void modifyStation(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // boolean to control the do-while loop
        boolean done = false;

        do {
            System.out.println("\nWhich Station Do You Want to Modify?");
            // User chosen station to be modified
            CTAStation station = getStationForPath(red, green, blue, brown, purple, pink, orange, yellow);
            System.out.println("\n" + station);
            System.out.println("\nAre You Sure to Modify This Station?");

            if (getYesNo()) {
                done = true;
                // list of routes that have station
                ArrayList<CTARoute> routes = routeHasStation(station, red, green, blue, brown, purple, pink, orange, yellow);
                // temporary station which has all modified station data
                CTAStation tempStation = new CTAStation(station.getName(), station.getLat(), station.getLng(), station.getLocation(), station.hasWheelchair(), station.getIndex());

                // Station modified data
                System.out.println("\nEnter New Information For Station Here: ");
                String name = getStationName();
                double lat = getLat();
                double lng = getLng();
                String location = getLocation();
                boolean wheelchair = getWheelchair();

                for (int i = 0; i < routes.size(); i++) {
                    for (int j = 0; j < routes.get(i).getStops().size(); j++) {
                        if (routes.get(i).getStops().get(j).equals(tempStation)) {
                            routes.get(i).getStops().get(j).setName(name);
                            routes.get(i).getStops().get(j).setLat(lat);
                            routes.get(i).getStops().get(j).setLng(lng);
                            routes.get(i).getStops().get(j).setLocation(location);
                            routes.get(i).getStops().get(j).setWheelchair(wheelchair);
                        }
                    }
                }
            }
        } while (!done);

        System.out.println("\nModifying Station Successfully");
    }

    // Remove Station Method: remove existed station in routes
    public static void removeStation(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // boolean to control do-while loop
        boolean done = false;

        do {
            System.out.println("\nWhich Station Do You Want to Remove?");
            // user chosen station
            CTAStation station = getStationForPath(red, green, blue, brown, purple, pink, orange, yellow);
            System.out.println("\n" + station);
            System.out.println("\nAre You Sure to Remove This Station?");

            if (getYesNo()) {
                done = true;
                // list of routes that have station
                ArrayList<CTARoute> routes = routeHasStation(station, red, green, blue, brown, purple, pink, orange, yellow);

                if (routes.size() == 1) {
                    routes.get(0).removeStation(station);
                } else {
                    System.out.println("\nWhich Route Do You Want to Remove This Station?");
                    createMenuRoute(routes);
                    System.out.println("Option " + (routes.size()+1) + ": Remove Station in All Routes");

                    // User input option (int)
                    int option = 0;
                    do {
                        option = getInteger();
                    } while (!(1 <= option && option <= routes.size()+1));

                    if (option == routes.size()+1) {
                        for (int i = 0; i < routes.size(); i++) {
                            routes.get(i).removeStation(station);
                        }
                    } else {
                        routes.get(option-1).removeStation(station);
                    }
                }
            }
        } while (!done);
        System.out.println("\nRemoving Station Successfully");
    }

    // Search Station Method: Search station in the CTA system
    public static void searchStation(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // Scanner to read user input
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter Station Name: ");
        // user input station name
        String name = in.nextLine();

        // list of stations with the same name
        ArrayList<CTAStation> stations = stationSameName(name, red, green, blue, brown, purple, pink, orange, yellow);

        if (stations.size() == 0) {
            System.out.println("\nNo Station Found");
        } else {
            System.out.println("\n" + stations.size() + " Station(s) Found: ");
            for (int i = 0; i < stations.size(); i++) {
                System.out.println("\n" + stations.get(i));

                ArrayList<CTARoute> routes = routeHasStation(stations.get(i), red, green, blue, brown, purple, pink, orange, yellow);
                System.out.print("This Station is Found in Route(s): ");
                for (int j = 0; j < routes.size(); j++) {
                    System.out.print(routes.get(j).getName() + ", ");
                }
                System.out.println();
            }
        }
    }

    // Display Nearest Station Method: display nearest station to user location
    public static void displayNearestStation(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};

        System.out.println();
        // get latitude and longitude data from user
        double lat = getLat();
        double lng = getLng();

        // List of nearest stations from each route
        ArrayList<CTAStation> nearestStation = new ArrayList<>(Arrays.asList(red.nearestStation(lat, lng), green.nearestStation(lat, lng), blue.nearestStation(lat, lng),
                brown.nearestStation(lat, lng), purple.nearestStation(lat, lng), pink.nearestStation(lat, lng), orange.nearestStation(lat, lng), yellow.nearestStation(lat, lng)));

        // CTARoute object contains nearest stations to user location from each route
        CTARoute stations = new CTARoute("Nearest Stations", nearestStation);
        // nearest station to user location
        CTAStation station = stations.nearestStation(lat, lng);

        System.out.println("\nThe Nearest Station to Your Location is: \n" + station);
        System.out.print("This Station Available in Route(s): ");

        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].getStops().size(); j++) {
                if (routes[i].getStops().get(j).equals(station)) {
                    System.out.print(routes[i].getName() + ", ");
                }
            }
        }
        System.out.println();
    }

    // Generate Path Method: Generate Path between 2 Stations and save it in a file
    public static void generatePath(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        System.out.println("\nEnter Your Start Location Here");
        // get start station
        CTAStation startStation = getStationForPath(red, green, blue, brown, purple, pink, orange, yellow);
        // get start route
        CTARoute startRoute = getRouteForPath(startStation, red, green, blue, brown, purple, pink, orange, yellow);

        System.out.println("\nEnter Your End Location Here");
        // get end station
        CTAStation endStation = getStationForPath(red, green, blue, brown, purple, pink, orange, yellow);
        // get end route
        CTARoute endRoute = getRouteForPath(endStation, red, green, blue, brown, purple, pink, orange, yellow);

        // String path
        String path = "";

        System.out.println("\n---PATH---");
        if (sameRoute(startStation, endStation, red, green, blue, brown, purple, pink, orange, yellow) != null) {
            path = "CTA Route [" + sameRoute(startStation, endStation, red, green, blue, brown, purple, pink, orange, yellow).getName() + "]: " + startStation.getName() + " to " + endStation.getName();
        } else {
            if (startRoute.commonStation(endRoute) != null) {
                // switch station between 2 routes
                CTAStation switchStation = startRoute.commonStation(endRoute);

                path = "CTA Route [" + startRoute.getName() + "]: " + startStation.getName() + " to " + switchStation.getName() +
                        "\nSwitch to [" + endRoute.getName() + "] at Station: " + switchStation.getName() +
                        "\nCTA Route [" + endRoute.getName() + "]: " + switchStation.getName() + " to " + endStation.getName();

            } else {
                if (getSwitchRoute(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow) != null) {
                    // switch route between 2 routes
                    CTARoute switchRoute = getSwitchRoute(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow);

                    path = "CTA Route [" + startRoute.getName() + "]" + startStation.getName() + " to " + startRoute.commonStation(switchRoute).getName() +
                            "\nSwitch to [" + switchRoute.getName() + "] at Station: " + startRoute.commonStation(switchRoute).getName() +
                            "\nCTA Route [" + switchRoute.getName() + "]" + startRoute.commonStation(switchRoute).getName() + " to " + switchRoute.commonStation(endRoute).getName() +
                            "\nSwitch to [" + endRoute.getName() + "] at Station: " + switchRoute.commonStation(endRoute).getName() +
                            "\nCTA Route [" + endRoute.getName() + "]: " + switchRoute.commonStation(endRoute).getName() + " to "+ endStation.getName();

                } else if (getSwitchRoute2(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow) != null){
                    // list of 2 switch routes
                    CTARoute[] switchRoutes = getSwitchRoute2(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow);
                    // switch route from start route
                    CTARoute switchRoute1 = switchRoutes[0];
                    // switch route to end route
                    CTARoute switchRoute2 = switchRoutes[1];

                    path = "CTA Route [" + startRoute.getName() + "]: " + startStation.getName() + " to " + startRoute.commonStation(switchRoute1).getName() +
                            "\nSwitch to [" + switchRoute1.getName() + "] at Station: " + startRoute.commonStation(switchRoute1).getName() +
                            "\nCTA Route [" + switchRoute1.getName() + "]: " + startRoute.commonStation(switchRoute1).getName() + " to " + switchRoute1.commonStation(switchRoute2).getName() +
                            "\nSwitch to [" + switchRoute2.getName() + "] at Station: " + switchRoute1.commonStation(switchRoute2).getName() +
                            "\nCTA Route [" + switchRoute2.getName() + "]: " + switchRoute1.commonStation(switchRoute2).getName() + " to " + switchRoute2.commonStation(endRoute).getName() +
                            "\nSwitch to [" + endRoute.getName() + "] at Station: " + switchRoute2.commonStation(endRoute).getName() +
                            "\nCTA Route [" + endRoute.getName() + "]: " + switchRoute2.commonStation(endRoute).getName() + " to " + endStation.getName();

                } else {
                    System.out.println("Cannot Find Path to This Station.");
                }
            }
        }
        System.out.println(path);
        askForSave(path);
    }

    // Stations With Same Name Method: Return a list of stations with same name
    public static ArrayList stationSameName(String name, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};
        // list of stations with the same name
        ArrayList<CTAStation> stations = new ArrayList<>();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].getStops().size(); j++) {
                if (name.equalsIgnoreCase(routes[i].getStops().get(j).getName())) {
                    if (!existedStation(stations, routes[i].getStops().get(j))) {
                        stations.add(routes[i].getStops().get(j));
                    }
                }
            }
        }
        return stations;
    }

    // Existed Station Method: Check if the station is already existed in the list
    public static boolean existedStation(ArrayList<CTAStation> stations, CTAStation station) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).equals(station)) {
                return true;
            }
        }
        return false;
    }

    // Check Valid Station Method: Check if the station name is a real station name exited in the routes
    public static boolean validStation(String name, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].getStops().size(); j++) {
                if (routes[i].getStops().get(j).getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Get Switch Route Method: Return the switch route between 2 routes
    public static CTARoute getSwitchRoute(CTARoute startRoute, CTARoute endRoute, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of list of common routes
        ArrayList<ArrayList<CTARoute>> commonRoute = getCommonRoutes(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow);
        // list of routes that have common stations with start route
        ArrayList<CTARoute> startCommon = commonRoute.get(0);
        // list of routes that have common stations with end route
        ArrayList<CTARoute> endCommon = commonRoute.get(1);

        for (int i = 0; i < startCommon.size(); i++) {
            for (int j = 0; j < endCommon.size(); j++) {
                if (endCommon.get(j).equals(startCommon.get(i))) {
                    return startCommon.get(i);
                }
            }
        }

        return null;
    }

    // Get 2 Switch Routes Method: Return 2 switch routes between 2 routes
    public static CTARoute[] getSwitchRoute2(CTARoute startRoute, CTARoute endRoute, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of list of common routes
        ArrayList<ArrayList<CTARoute>> commonRoute = getCommonRoutes(startRoute, endRoute, red, green, blue, brown, purple, pink, orange, yellow);
        // list of routes that have common stations with start route
        ArrayList<CTARoute> startCommon = commonRoute.get(0);
        // list of routes that have common stations with end route
        ArrayList<CTARoute> endCommon = commonRoute.get(1);

        // list contains 2 switch routes
        CTARoute[] switchRoutes = new CTARoute[2];

        for (int i = 0; i < startCommon.size(); i++) {
            for (int j = 0; j < endCommon.size(); j++) {
                if ((startCommon.get(i).commonStation(endCommon.get(j)) != null) && !startCommon.get(i).equals(endCommon.get(j))) {
                    switchRoutes[0] = startCommon.get(i);
                    switchRoutes[1] = endCommon.get(j);
                    return switchRoutes;
                }
            }
        }
        return null;
    }

    // Get Common Routes Method: Return route the the start route and end route share the same stations
    public static ArrayList getCommonRoutes(CTARoute startRoute, CTARoute endRoute, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};

        // list of routes that share common stations with start route
        ArrayList<CTARoute> startCommon = new ArrayList<>();
        for (int i = 0; i < routes.length; i++) {
            if (routes[i].commonStation(startRoute) != null) {
                startCommon.add(routes[i]);
            }
        }

        // list of routes that share common stations with end route
        ArrayList<CTARoute> endCommon = new ArrayList<>();
        for (int i = 0; i < routes.length; i++) {
            if (routes[i].commonStation(endRoute) != null) {
                endCommon.add(routes[i]);
            }
        }

        // list of list of common routes
        ArrayList<ArrayList<CTARoute>> commonRoute = new ArrayList<>(Arrays.asList(startCommon, endCommon));

        return commonRoute;
    }

    /** Get Station Information from User **/

    // Get Station Name Method: Prompt user for station name
    public static String getStationName() {
        // Scanner to read user input
        Scanner in = new Scanner(System.in);

        // station name (String)
        String name = "";
        do {
            System.out.print("Enter Station Name: ");
            name = in.nextLine();
        } while (name.equalsIgnoreCase(""));

        return name;
    }

    // Get Station Latitude Method: Prompt user for station latitude
    public static double getLat() {
        // station latitude (double)
        double lat = 0;
        System.out.println("Enter Station Latitude from -90 to 90. ");
        do {
            lat = getDouble();
        } while (!(-90 <= lat && lat <= 90));

        return lat;
    }

    // Get Longitude Method: Prompt user for station longitude
    public static double getLng() {
        // station longitude (double)
        double lng = 0;
        System.out.println("Enter Station Longitude from -180.9 to 180.9. ");
        do {
            lng = getDouble();
        } while (!(-180.9 <= lng && lng <= 180.9));

        return lng;
    }

    // Get Location Method: Prompt user for station location
    public static String getLocation() {
        // Scanner to read user input
        Scanner in = new Scanner(System.in);

        // station location (String)
        String location = "";
        do {
            System.out.print("Enter Station Location: ");
            location = in.nextLine();
        } while (!checkLocation(location));

        return location.toLowerCase();
    }

    // Check Location Method: Check if user input location is right
    public static boolean checkLocation(String location) {
        // list of valid station location
        String[] validLocation = {"subway", "elevated", "elevated/subway", "surface", "embankment", "underground", "at-grade", "street level"};
        for (int i = 0; i < validLocation.length; i++) {
            if (location.equalsIgnoreCase(validLocation[i])) {
                return true;
            }
        }
        return false;
    }

    // Get Wheelchair Method: Prompt user for station wheelchair status
    public static boolean getWheelchair() {
        // Scanner to read user input
        Scanner in = new Scanner(System.in);
        // Wheelchair status (String)
        String wheelchair = "";
        do {
            System.out.print("Enter Station Wheelchair Access Status (true/false): ");
            wheelchair = in.nextLine();
        } while (!(wheelchair.equalsIgnoreCase("true") || wheelchair.equalsIgnoreCase("false")));

        return Boolean.parseBoolean(wheelchair.toLowerCase());
    }

    // Get Station Index Method: Prompt user for station index
    public static int getStationIndex(CTARoute route) {
        // Index variable (int)
        int index;
        System.out.print("Enter Station Index from 0 to " + route.getStops().size() + ": ");
        do {
            index = getInteger();
        } while (!(0 <= index && index <= route.getStops().size()));

        return index;
    }

    // Get Station for Path: prompt user for station name (for path generating)
    public static CTAStation getStationForPath(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // Scanner to read user input
        Scanner in = new Scanner(System.in);
        // initiate station name variable (String)
        String name = "";
        do {
            System.out.print("Enter Station Name: ");
            name = in.nextLine();
        } while(!validStation(name, red, green, blue, brown, purple, pink, orange, yellow));

        // list of stations with the same name
        ArrayList<CTAStation> stations = stationSameName(name, red, green, blue, brown, purple, pink, orange, yellow);
        // CTAStation object
        CTAStation station = new CTAStation();

        if (stations.size() == 1) {
            station = stations.get(0);
        } else {
            System.out.println("\n" + stations.size() + " Station(s) Found: ");
            station = createMenuStation(stations, red, green, blue, brown, purple, pink, orange, yellow);
        }

        return station;
    }

    // Get Route for Path: prompt user for route (for path generating)
    public static CTARoute getRouteForPath(CTAStation station, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of routes share the same given station
        ArrayList<CTARoute> routes = routeHasStation(station, red, green, blue, brown, purple, pink, orange, yellow);
        // CTARoute object chosen by the user
        CTARoute route = new CTARoute();

        if (routes.size() == 1) {
            route = routes.get(0);
        } else {
            System.out.println("\nThis Station Found in Route(s). Choose The Route You Want to Take: \n");
            route = createMenuRoute2(routes);
        }

        return route;
    }

    // Get Yes-No Answer Method: Get yes no string from user input
    public static boolean getYesNo() {
        // Scanner reading user input
        Scanner in = new Scanner(System.in);
        // User input answer (String)
        String ans = "";
        do {
            System.out.print("Enter Your Answer Here (yes/no): ");
            ans = in.nextLine();
        } while (!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("no")));

        if (ans.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    // Get Integer Method: get integer variable from user
    public static int getInteger() {
        // Scanner reading user input
        Scanner in = new Scanner(System.in);
        // input number (String)
        String integer = "";

        do {
            System.out.print("Enter Number Here: ");
            integer = in.nextLine();
        } while (!isInteger(integer));

        return Integer.parseInt(integer);
    }

    // Is Integer Method: Check user input is integer or not
    public static boolean isInteger(String integer) {
        if (integer.equalsIgnoreCase("")) {
            return false;
        }
        try {
            Integer.parseInt(integer);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    // Get Double Method: get double variable from user
    public static double getDouble() {
        // Scanner reading user input
        Scanner in = new Scanner(System.in);
        // user input number (String)
        String number = "";

        do {
            System.out.print("Enter Number Here: ");
            number = in.nextLine();
        } while (!isDouble(number));

        return Double.parseDouble(number);
    }

    // Is Double Method: Check user input is a double or not
    public static boolean isDouble(String number) {
        if (number.equalsIgnoreCase("")) {
            return false;
        }
        try {
            Double.parseDouble(number);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    // Create Save File Method: create new file (pass if the file is already existed)
    public static void createSaveFile() {
        try {
            // Create new file to save path
            File pathFile = new File("src\\project\\path.txt");
            if (pathFile.createNewFile()) {
                System.out.println();
            } else {
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error Occurred.");
        }
    }

    // Save Path to File Method: Save generated path to file
    public static void savePathToFile(String path) {
        createSaveFile();
        try {
            // Writer object to overwrite or add new info to file
            Writer savedPath = new FileWriter("src\\project\\path.txt", false);
            savedPath.write(path);
            savedPath.close();
            System.out.println("Your Path Has Been Saved Successfully.");
        } catch (IOException e) {
            System.out.println("Error Occurred.");
        }
    }

    // Ask For Save Method: prompt user for saving path into the file
    public static void askForSave(String path) {
        System.out.println();
        System.out.println("Do You Want to Save Path into File?");
        if (getYesNo()) {
            savePathToFile(path);
        } else {
            System.out.println("\nExiting...");
        }
    }

    // Same Route Method: Check if two stations are in the same route
    public static CTARoute sameRoute(CTAStation startStation, CTAStation endStation, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // list of CTARoute objects that have the given start station
        ArrayList<CTARoute> startRoutes = routeHasStation(startStation, red, green, blue, brown, purple, pink, orange, yellow);
        // list of CTARoute objects that have the given end station
        ArrayList<CTARoute> endRoutes = routeHasStation(endStation, red, green, blue, brown, purple, pink, orange, yellow);

        for (int i = 0; i < startRoutes.size(); i++) {
            for (int j = 0; j < endRoutes.size(); j++) {
                if (endRoutes.get(j).equals(startRoutes.get(i))) {
                    return startRoutes.get(i);
                }
            }
        }
        return null;
    }

    // Route Has Station Method: Return routes have the given station
    public static ArrayList routeHasStation(CTAStation station, CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // List of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};

        // list of CTARoute objects that have given station
        ArrayList<CTARoute> routesHasStation = new ArrayList<>();

        for (int i = 0; i < routes.length; i++) {
            if (routes[i].hasStation(station)) {
                routesHasStation.add(routes[i]);
            }
        }
        return routesHasStation;
    }

    // Fix Index Method: Fix index of the list of CTAStation after deleting or adding station
    public static void fixIndex(CTARoute red, CTARoute green, CTARoute blue, CTARoute brown, CTARoute purple, CTARoute pink, CTARoute orange, CTARoute yellow) {
        // List of existed routes
        CTARoute[] routes = {red, green, blue, brown, purple, pink, orange, yellow};
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].getStops().size(); j++) {
                routes[i].getStops().get(j).setIndex(j);
            }
        }
    }
}
