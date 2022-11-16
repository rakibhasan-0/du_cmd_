import java.util.*;

/**
 * In that class, we will create a right-hand robot. The robot will follow
 * the right-hand rule. Hence, A wall must exist for the Right-Hand Rule
 * robot on the robot's right-hand side. The wall must exist on the Robot's
 * right-hand side until the Robot detects the goal position. The Maze's
 * x and y coordinates get rotated. Thus, we should consider x-axis as the y
 * and y-axis should be considered as the x-axis.
 * @author Gazi Md Rakibul Hasan
 * @version 3.0
 */
public class RightHandRuleRobot implements Robot{
    private Position position;
    private Position prevPos;
    private final Maze theMaze;
    private int direction;
    private final HashMap<Position, Integer> map;
    private boolean firstMove;
    public char [][] labyrint;



    /**
     * In that constructor method, we will create a right-hand robot object.
     * we take a maze object as the parameter of that method. We define
     * the robot's current position by assigning the start position in
     * the maze to the robot's current position
     * @param maze The maze.
     */
    public RightHandRuleRobot(Maze maze) throws Exception {
        this.theMaze = maze;
        position = maze.getStart();
        direction = 0;
        prevPos = null;
        this.map = new HashMap<>();
        // initialisationOfPrintingArray(maze);
        this.firstMove = false;
    }

    /**
     * for the testing usage.
     *
     */
    private void initialisationOfPrintingArray(Maze maze) {
        labyrint = new char[maze.getNumRows()][maze.getNumColumns()];
        for(int i = 0; i < maze.getNumRows(); i++){
            for(int j = 0; j < maze.getNumColumns(); j++){
                labyrint[i][j] = maze.getTheMaze()[i][j];
            }
        }
    }

    /**
     * According to the right-hand rule robot, a wall will exist on the robot's right-hand side.
     * It will start checking if there exists a wall on the robot's right hand. If the robot can
     * not go right, it will check if it can forward. If there is no movable position, it will
     * check if the robot can go to the left side. If any of them positions are movable,
     * then the robot will return to the previous position.
     * @throws RuntimeException If the robot is in a situation where exists no wall on
     *         the robot's right-hand side and the robot starts to circulate within some positions.
     */
    @Override
    public void move() {
        checkFirstMove();
        if(!firstMove){
            throw new RuntimeException("the robot can not move");
        }
        //printDirections();
        Position[] nextPositions = getNextPositions();

        int offset = ((direction + 3) % 4);

        if (map.containsKey(position)){
            if (map.get(position).equals(direction)) {
                throw new RuntimeException("the robot get stuck at " + "x == " + getPosition().getX() + "  y == " + getPosition().getY() + "the dir ==" + direction);
            }
        }
        for (Position nextPosition: nextPositions){
            if (theMaze.isMovable(nextPositions[offset])){
                map.put(position,direction);
                setPosition(nextPositions[offset]);
                direction = offset;
                //System.out.println("dir::"+ direction);
                //System.out.println("y:: "+ position.getX()+ "   x::" +position.getY());
                return;
            }
            offset = (offset + 1) % 4;

        }
    }

    /**
     * for the testing usage.
     */
    private void printDirections() {
        if(getDirection() == 0){
            labyrint[position.getY()][position.getX()] = '^';
        }
        if(getDirection() == 1){
            labyrint[position.getY()][position.getX()] = '<';
        }
        if(getDirection() == 2){
            labyrint[position.getY()][position.getX()] = 'v';
        }
        if(getDirection() == 3){
            labyrint[position.getY()][position.getX()]  = '>';
        }
    }

    /**
     * it gets a position's neighbours positions.
     * @return Position
     */
    private Position[] getNextPositions() {
        return new Position[]{
                position.getPosToNorth(),
                position.getPosToWest(),
                position.getPosToSouth(),
                position.getPosToEast()
        };
    }

    /**
     * it checks if the robot's first move is valid or not.
     */
    private void checkFirstMove() {
        if(!firstMove){
            if(theMaze.isMovable(position.getPosToWest()) && !theMaze.isMovable(position.getPosToNorth())){
                direction = 1;
                firstMove = true;
            }
            if(theMaze.isMovable(position.getPosToSouth()) && !theMaze.isMovable(position.getPosToWest())){
                direction = 2;
                firstMove = true;
            }
            if(theMaze.isMovable(position.getPosToEast()) && !theMaze.isMovable(position.getPosToSouth())){
                direction = 3;
                firstMove = true;
            }
            if(theMaze.isMovable(position.getPosToNorth()) && !theMaze.isMovable(position.getPosToEast())){
                direction = 0;
                firstMove = true;
            }
        }
    }

    /**
     * Invoking that method will give the robot's current position.
     * @return It gives the robot's current position.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * It checks if the robot has reached the goal position.
     * @return true, if the current position is the goal position. Otherwise,
     *         it will return false.
     */
    @Override
    public boolean hasReachedGoal() {
        return theMaze.isGoal(position);
    }

    /**
     * Set the given position as the current position and set the
     * current positoin as the previous position.
     * @param pos The given position.
     */
    private void setPosition (Position pos){
        prevPos = position;
        position = pos;
    }

    /**
     * For the testing purpose.
     */
    public int getDirection (){
        return this.direction;
    }
}