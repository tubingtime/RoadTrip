import java.util.ArrayList;

public class DNode implements Comparable<DNode> {
    int cityCode;
    boolean known;
    int cost;
    ArrayList<Integer> path;

    public DNode(){
        cityCode = -1;
        known = false;
        cost = Integer.MAX_VALUE;
        path = new ArrayList<>();
    }
    public DNode(int cityCode) {
        this.cityCode = cityCode;
        known = false;
        cost = Integer.MAX_VALUE;
        path = new ArrayList<>();
    }
    public DNode(int cityCode, int cost){
        this.cityCode = cityCode;
        known = false;
        this.cost = cost;
        path = new ArrayList<>();
    }
    public DNode(int cityCode, int cost, ArrayList<Integer> path){
        this.cityCode = cityCode;
        known = false;
        this.cost = cost;
        this.path = path;
    }

    @Override
    public int compareTo(DNode o) {
        //returns < 0 if this is greater
        return Integer.compare(this.cost, o.cost);
    }
}
