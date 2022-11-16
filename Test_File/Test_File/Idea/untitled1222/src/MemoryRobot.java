
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
/**
 * En klass som skapar en robot.
 * Denna robot använder sig av ett minne.
 * Detta minne håller koll på vart den har gått,
 * samt vilken väg den har gått.
 * Roboten använder sig av detta för att kontrollera
 * alla möjliga vägar på ett effektivt sätt.
 */
public class MemoryRobot implements Robot {

    private Maze currentMaze;
    private Position position;
    private Position previousPosition;
    private ArrayList<Position> previousPositions;
    private Stack<Position> walkedPath;

    public MemoryRobot(Maze maze) throws Exception {
        currentMaze = maze;
        position = maze.getStart();
        previousPosition = position;
        walkedPath = new Stack<Position>();
        previousPositions = new ArrayList<Position>();
    }


    /**
     * Skickar tillbaka robotens nuvarande position.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosition() { return position; }


    /**
     * Skickar tillbaka ifall roboten har nått mål eller inte.
     * @return boolean
     */
    public boolean hasReachedGoal() {
        if (currentMaze.isGoal(position)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Roboten försöker röra sig åt ett slumpmässat håll som den inte har besökt än samt är giltig.
     * Om roboten fastnar så kommer den att gå tillbaka samma väg som den gick,
     * fast samtidigt som den backar kontrollerar den ifall den kan röra sig åt en outforskad plats.
     */
    public void move() {
        Random rand = new Random(System.currentTimeMillis());
        boolean redo = true;
        boolean cantGoSouth = false, cantGoNorth = false, cantGoWest = false, cantGoEast = false;
        do {
            switch (rand.nextInt(4)) {
                case 0:
                    Position southPos = position.getPosToSouth();
                    if (currentMaze.isMovable(southPos) && !hasWalked(southPos)) {
                        previousPosition = position;
                        walkedPath.push(southPos);
                        previousPositions.add(previousPosition);
                        redo = false;
                        setPosition(southPos);
                    } else {
                        cantGoSouth = true;
                    }
                    break;
                case 1:
                    Position northPos = position.getPosToNorth();
                    if (currentMaze.isMovable(northPos) && !hasWalked(northPos)) {
                        previousPosition = position;
                        walkedPath.push(northPos);
                        previousPositions.add(previousPosition);
                        redo = false;
                        setPosition(northPos);
                    } else {
                        cantGoNorth = true;
                    }
                    break;
                case 2:
                    Position westPos = position.getPosToWest();
                    if (currentMaze.isMovable(westPos) && !hasWalked(westPos)) {
                        previousPosition = position;
                        walkedPath.push(westPos);
                        previousPositions.add(previousPosition);
                        redo = false;
                        setPosition(westPos);
                    } else {
                        cantGoWest = true;
                    }
                    break;
                case 3:
                    Position eastPos = position.getPosToEast();
                    if (currentMaze.isMovable(eastPos) && !hasWalked(eastPos)) {
                        previousPosition = position;
                        walkedPath.push(eastPos);
                        previousPositions.add(previousPosition);
                        redo = false;
                        setPosition(eastPos);
                    } else {
                        cantGoEast = true;
                    }
                    break;
            }
            if (cantGoEast && cantGoNorth && cantGoSouth && cantGoWest) {
                previousPosition = position;
                previousPositions.add(previousPosition);
                walkedPath.pop(); // Gå bakåt ett steg
                setPosition(walkedPath.peek());
                redo = false;
            }
        } while (redo);
    }


    /**
     * Kontrollerar ifall roboten har varit på
     * en specifik position. Om den har det så
     * skickas true tillbaka, annars false.
     * @param pos
     * @return boolean
     */
    private boolean hasWalked(Position pos){
        for (Position p : previousPositions) {
            if (p.equals(pos)) { return true; }
        }
        return false;
    }


    /**
     * Ändrar robotens position i labyrinten.
     * @param pos
     */
    private void setPosition(Position pos) {
        this.position = pos;
        System.out.println(this.position.toString());
    }

}
