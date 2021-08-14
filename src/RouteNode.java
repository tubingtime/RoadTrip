import java.lang.reflect.Array;
import java.util.ArrayList;

public class RouteNode implements Comparable<RouteNode> {
    ArrayList<String> route;
    int totalCost;

    public RouteNode(){
        this.route = new ArrayList<>();
        totalCost = 0;
    }
    public RouteNode(ArrayList<String> route, int totalCost){
        this.route = route;
        this.totalCost = totalCost;
    }

    @Override
    public int compareTo(RouteNode o) {
        return Integer.compare(this.totalCost, o.totalCost);
    }
}
