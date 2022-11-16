import java.util.ArrayList;

public class Node {
    Position position;
    ArrayList <Node> neighbours;

    public Node (Position pos){
        this.position = pos;
    }

    public Position getPosiiton (){
        return this.position;
    }

    public void addNeighbhour (Node node){
        this.neighbours.add(node);
    }
}
