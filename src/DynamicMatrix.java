import java.util.Arrays;

public class DynamicMatrix {
    public int[][] arr;

    public DynamicMatrix(){
        arr = new int[10][10];
        for(int i = 0; i < arr[0].length; i++){
            Arrays.fill(arr[i],-1);
        }
    }

    public DynamicMatrix(int cols, int rows){
        arr = new int[cols][rows];
        for(int i = 0; i < arr[0].length; i++){
            Arrays.fill(arr[i],-1);
        }
    }
    private int fillArray(int city){
        for(int i = 0; i < arr[0].length-1; i++){
            //roadMatrix[]
        }
        return -1;
    }

    public void growArray(){
        int [][] newArray = new int[arr.length*2][arr.length*2];
        for(int i = 0; i < newArray[0].length; i++){
            Arrays.fill(newArray[i],-1); //todo: ONLY fill new part of array
        }
        for (int i = 0; i < arr.length; i++){
            System.arraycopy(arr[i],0,newArray[i],0,arr.length);
        }
        arr = newArray;
    }
    public void growArrayCols(){
        int [][] newArray = new int[arr.length*2][arr.length];
        for(int i = 0; i < newArray[0].length; i++){
            Arrays.fill(newArray[i],-1); //todo: ONLY fill new part of array
        }
        for (int i = 0; i < arr.length; i++){
            System.arraycopy(arr[i],0,newArray[i],0,arr.length);
        }
        arr = newArray;
    }

}
