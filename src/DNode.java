public class DNode {
    int cityCode;
    boolean known;
    int cost;
    int path;

    public DNode(){
        cityCode = -1;
        known = false;
        cost = Integer.MAX_VALUE;
        path = -1;
    }
    public DNode(int cityCode) {
        this.cityCode = cityCode;
        known = false;
        cost = Integer.MAX_VALUE;
        path = -1;
    }
    public DNode(int cityCode, int cost){
        this.cityCode = cityCode;
        known = false;
        this.cost = cost;
        path = -1;
    }
}
