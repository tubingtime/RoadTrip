import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TripCalc {
    HashMap<String,Integer> cities; //maybe change to String,String for debug?
    HashMap<String,Integer> attractions;
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
        while ((line = roadsBuff.readLine()) != null){
            String[] values = line.split(COMMA_DELIMETER);
            Integer city1 = cities.get(values[0]);
            Integer city2 = cities.get(values[1]);

            if (city1 == null){
                cities.put(values[0],i);
                city1 = i;
                roadMap.addV();
                i++;

            }
            if (city2 == null){
                cities.put(values[1],i);
                city2 = i;
                roadMap.addV();
                i++;
            }
            int cost = Integer.parseInt(values[3]);
            roadMap.addEdge(city1,city2,cost);
            roadMap.addEdge(city2,city1,cost);

        }
        //read attractions and assign corresponding city num
        attractions = new HashMap<>();
        while ((line = attractionsBuff.readLine()) != null){
            String[] values = line.split(COMMA_DELIMETER);
            attractions.put(values[0],cities.get(values[1]));
        }
    }

    public List<String> route (String starting_city, String ending_city, List<String> attractions){
        //find path from starting city -> closest attraction(loop) -> ending city
        return null;
    }
    private List<String> route (String starting_city, String ending_city){
        int startCityCode = cities.get(starting_city);
        LinkedList<int[]> start = roadMap.arr.get(startCityCode);
        //PATH FINDER from CITY1 to CITY2

        ArrayList<DNode> tracker = new ArrayList<>(); //could convert to hashmap for better pref
        //find least cost unkown vertex FROM starting vertex
        Iterator<int[]> iterator = start.listIterator();
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < start.size(); i++){
            int[] city = iterator.next();
            if (!containsCity(tracker,city[0]))
                tracker.add(new DNode(city[0],city[1]));
            if (city[1] < minCost ){
                minCost = city[1];
            }
        }
        return null;
    }

    private boolean containsCity(ArrayList<DNode> tracker, int cityCode){
        for (DNode v : tracker){
            if (v.cityCode == cityCode)
                return true;
        }
        return false;
    }


    public static void main(String [] args){
        System.out.println("ROAD TRIPPER");
        try {
            TripCalc trippy = new TripCalc("/home/thomas/IdeaProjects/RoadTrip/spec/attractions.csv","/home/thomas/IdeaProjects/RoadTrip/spec/roads.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
