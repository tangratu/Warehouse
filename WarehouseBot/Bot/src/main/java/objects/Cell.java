package objects;

public class Cell {
    private int x;
    private int y;
    public Cell(int x1,int y1){
        x=x1;
        y=y1;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDist(Cell other){
        return Math.abs(x-other.getX()) + Math.abs(y-other.getY());
    }
}
