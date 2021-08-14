import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TripCalc {
    HashMap<String,Integer> cities; //City Name, CityCode
    HashMap<Integer,String> cityNames; //CityCode, City Name
    HashMap<String,String> attractions; //Attraction, City
    AdjacencyList roadMap;
    private static final String COMMA_DELIMETER = ",";

    public TripCalc(String attractionsDir, String roadsDir) throws Exception {
        FileReader attractionsFile = new FileReader(attractionsDir);
        FileReader roadsFile = new FileReader(roadsDir);
        BufferedReader attractionsBuff = new BufferedReader(attractionsFile);
        BufferedReader roadsBuff = new BufferedReader(roadsFile);
        String line;
        //read road csv and fill adj matrix
        int i = 0;
        roadMap = new AdjacencyList();
        cities = new HashMap<>();
        cityNames = new HashMap<>();
        while ((line = roadsBuff.readLine()) != null){
            String[] values = line.split(COMMA_DELIMETER);
            Integer city1 = cities.get(values[0]);
            Integer city2 = cities.get(values[1]);

            if (city1 == null){
                cities.put(values[0],i); // also go the other way around?
                cityNames.put(i,values[0]);
                city1 = i;
                roadMap.addV();
                i++;

            }
            if (city2 == null){
                cities.put(values[1],i);
                cityNames.put(i,values[1]);
                city2 = i;
                roadMap.addV();
                i++;
            }
            int cost = Integer.parseInt(values[3]);
            roadMap.addEdge(city1,city2,cost);
            roadMap.addEdge(city2,city1,cost);

        }
        //read attractions and put into hashmap
        attractions = new HashMap<>();
        while ((line = attractionsBuff.readLine()) != null){
            String[] values = line.split(COMMA_DELIMETER);
            attractions.put(values[0],values[1]);
        }
    }

    /* Finds the closest city to given location and list of cities.
    * Returns a RouteNode to that city */
    private RouteNode closestAttraction(String location, List<String> cities){
        int minCost = Integer.MAX_VALUE;
        RouteNode closest = null;
        RouteNode curRoute;
        for (String city : cities){
            curRoute = route(location,city);
            if (curRoute.totalCost < minCost){
                minCost = curRoute.totalCost;
                closest = curRoute;
            }
        }
        return closest;
    }

    private String attractionToCity(String attraction){
        return this.attractions.get(attraction);
    }
    public List<RouteNode> route (String starting_city, String ending_city, List<String> attractions){ //attractions stack!
        if (attractions.size() == 0){
            ArrayList<RouteNode> AR = new ArrayList<>();
            AR.add(route(starting_city,ending_city));
            return AR;
        }

        ArrayList<String> cities = new ArrayList<>();
        for (String attr : attractions){
            cities.add(attractionToCity(attr));
        }
        RouteNode lastRoute = closestAttraction(starting_city, cities);
        int finalCost = lastRoute.totalCost;
        ArrayList<String> stringRoute = new ArrayList<>(lastRoute.route);
        String lastCity = lastRoute.route.get(lastRoute.route.size()-1);
        cities.remove(lastCity);
        boolean ran = false;
        while (cities.size() > 1){
            ran = true;
            lastRoute = closestAttraction(lastCity,cities);
            lastCity = lastRoute.route.get(lastRoute.route.size()-1);
            cities.remove(lastCity);
            finalCost += lastRoute.totalCost;
            stringRoute.addAll(lastRoute.route);
        }
        if (!ran){
            lastRoute = route(lastCity,ending_city);
        }
        else {
            lastRoute = route(lastCity, cities.get(0));
            finalCost += lastRoute.totalCost;
            stringRoute.addAll(lastRoute.route);
            lastRoute = route(cities.get(0), ending_city);
        }
        finalCost += lastRoute.totalCost;
        stringRoute.addAll(lastRoute.route);

        ArrayList<RouteNode> AR = new ArrayList<>();
        AR.add(new RouteNode(stringRoute,finalCost));
        return AR;
    }
    private RouteNode route (String starting_city, String ending_city){
        int startCityCode = cities.get(starting_city);
        int endCityCode = cities.get(ending_city);
        LinkedList<int[]> start = roadMap.arr.get(startCityCode);
        //PATH FINDER from CITY1 to CITY2
        //loop until we've reached city2 and cost to visit any edge is greater or equal to current cost

        HashMap<Integer,DNode> tracker = new HashMap<>(); //use indexed prio queue for better running time?
        PriorityQueue<DNode> vQueue = new PriorityQueue<>();
        //create and set origin vertex
        DNode originVertex = new DNode(startCityCode,0);
        tracker.put(originVertex.cityCode,originVertex);
        while(originVertex.cityCode != endCityCode) { // && cost to visit any edge is greater or equal to current cost
                                                            //^^actually not necessary because dikjstra is greedy
            // update neighbors and find least cost unkown vertex
            if (originVertex.known) { // if we've already found a shorter path (todo: test simply editing cost?)
                originVertex = vQueue.remove(); continue; //continue to next vertex
            }

            originVertex.known = true;
            LinkedList<int[]> edges = roadMap.arr.get(originVertex.cityCode);
            Iterator<int[]> iterator = edges.iterator();
            for (int i = 0; i < edges.size(); i++) {
                int[] city = iterator.next();
                //check if we've added the node already
                if (!tracker.containsKey(city[0])) { //todo: simplify to v = tracker.get(city[0]) and then check if null
                    ArrayList<Integer> vPath = new ArrayList<>(originVertex.path);
                    DNode v = new DNode(city[0], city[1] + originVertex.cost, vPath);
                    v.path.add(originVertex.cityCode);
                    tracker.put(v.cityCode,v); //add edge
                    vQueue.add(v);
                }
                else {
                    DNode v = tracker.get(city[0]);
                    if (!v.known && originVertex.cost+city[1] < v.cost){ //check if not known and cost is smaller
                        //update cost
                        v.cost = originVertex.cost + city[1];
                        //update path to the path to current vertex (originVertex)
                        v.path = new ArrayList<>(originVertex.path);
                        v.path.add(originVertex.cityCode);
                        //update prio queue
                        vQueue.add(v);
                    }
                }
            }
            originVertex = vQueue.remove();
        }
        RouteNode pRoute = new RouteNode();
        ArrayList<String> route = new ArrayList<>();
        for (Integer cityCode : originVertex.path){
            route.add(cityNames.get(cityCode));
        }
        route.add(ending_city);

        pRoute.route = route;
        pRoute.totalCost = originVertex.cost; // time to get there
        return pRoute;
    }




    public static void main(String [] args){
        System.out.println("ROAD TRIPPER");
        try {
            TripCalc trippy = new TripCalc("/home/thomas/IdeaProjects/RoadTrip/spec/attractions.csv","/home/thomas/IdeaProjects/RoadTrip/spec/roads.csv");
            ArrayList<String> attractions = new ArrayList<>();
            System.out.println("\n--== Welcome to the Roadtrip Calculator ==--");
            Scanner console = new Scanner(System.in);
            System.out.println("Name of starting city (or EXIT to quit)");
            String s = console.nextLine();
            if (s.equalsIgnoreCase("exit")){
                return;
            }
            String startCity = s;
            System.out.println("Name of ending city");
            s = console.nextLine();
            String endCity = s;
            while (true) {
                System.out.println("List an attraction along the way (or ENOUGH to stop listing):");
                s = console.nextLine();
                if (s.equalsIgnoreCase("ENOUGH")) {
                    System.out.println("Exiting...");
                    break;
                }
                attractions.add(s);
            }
            List<RouteNode> routenodeL = trippy.route(startCity,endCity,attractions);
            RouteNode routenode = routenodeL.get(0);
            System.out.println(routenode.route);
            System.out.println(routenode.totalCost);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
