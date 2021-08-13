import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class AdjacencyList {

    ArrayList<LinkedList<int[]>> arr;
    public AdjacencyList(){
        arr = new ArrayList<>();
    }
    public void addV(){
        LinkedList<int[]> city = new LinkedList<>();
        arr.add(city);
    }

    public void addV(int city2, int cost){
        LinkedList<int[]> city = new LinkedList<>();
        int[] v = {city2, cost};
        city.add(v);
        arr.add(city);
    }

    public void addEdge(int city1, int city2, int cost){
        int[] v = {city2,cost};
        arr.get(city1).add(v);
    }




    public static void main (String [] args){
        AdjacencyList lili = new AdjacencyList();
        System.out.println(lili.arr.size());
        lili.addV();
        lili.addV();
        lili.addV();
        lili.addEdge(0,1,5);
        lili.addEdge(1,0,5);
        lili.addEdge(1,2,5);
    }
}
