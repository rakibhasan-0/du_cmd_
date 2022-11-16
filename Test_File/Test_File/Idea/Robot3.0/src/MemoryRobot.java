import java.util.Stack;

/**
 * In that class represents the Memory Robot class. Memory robot will
 * remember the positions it has already visited, and it will always move
 * to that adjacent position that has not seen yet.If the Robot finds no
 * movable position to move, it will return to that position it has
 * visited previously.The robot will remember the path it takes from the
 * start position to goal position. The Robot will do the depth-first
 * traversing method to visit the positions in the maze. The maze is rotated.
 * Thus, we should consider the x-axis as the y and y-axis should be considered
 * as the x-axis.
 * @author Gazi Md Rakibul Hasan
 * @version 3.0
 */
public class MemoryRobot implements Robot {
    private Position position;
    private final Maze theMaze;
    private Position prevPos;
    private boolean[][] visited;
    private Stack<Position> stk;
    /**
     * In that constructor method, we take a maze object as the parameter of that
     * method. We define the robot's current position by assigning the start
     * position in the maze to the robot's current position. We will create a
     * stack object.
     * @param maze The maze.
     */
    public MemoryRobot (Maze maze) throws Exception {
        theMaze = maze;
        position = maze.getStart();
        prevPos = null;
        initialiseVisitedArray(visited);
        this.stk = new Stack<>();
        stk.push(position);
        visited[position.getY()][position.getX()] = true; // mark start position as visited.
    }

    /**
     * The robot will move, according to the memory robot's rule, in that method.
     * The robot can remember each step that it takes.
     */
    @Override
    public void move() {
        //  System.out.println(toString());
        Position[] nextPositions =new Position[4];
        boolean availablePos = false;
        initialsationOfNextPositions(nextPositions);
        for(Position pos: nextPositions){
            if(theMaze.isMovable(pos)){
                if(!visited[pos.getY()][pos.getX()]){
                    availablePos = true;
                }
            }
        }
        if(!availablePos){
            position = stk.pop();
            prevPos = position;
        }
        for (Position nextPosition: nextPositions){
            if (theMaze.isMovable(nextPosition)){
                if (!visited[nextPosition.getY()][nextPosition.getX()]){
                    visited[nextPosition.getY()][nextPosition.getX()] = true;
                    prevPos = this.position;
                    this.position = nextPosition;
                    stk.push(position);
                    return;
                }
            }
        }
        backTracking();
    }

    /**
     * Fill the array with the potential available positions to a given position.
     * @param nextPositions The array.
     */
    private void initialsationOfNextPositions(Position[] nextPositions) {
        nextPositions[0] = position.getPosToNorth();
        nextPositions[1] = position.getPosToSouth();
        nextPositions[2] = position.getPosToEast();
        nextPositions[3] = position.getPosToWest();
    }

    /**
     * If there are the dead ends, then a robot should begin to backtracking by popping/removing
     * an element from the stack.
     */
    private void backTracking() {
        if(position.equals(stk.peek())){
            stk.pop();
        }
        prevPos = position;
        position = stk.peek();
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
     * Used for the debugging.
     */
    public boolean stackIsEmpty(){
        return !stk.empty();
    }

    /**
     * In that method, we will initialize a boolean array. The size
     * of the array will be identical to the maze array used to
     * represent the maze.
     * @param visited The boolean 2D array.
     */
    private void initialiseVisitedArray (boolean[][] visited){
        this.visited = new boolean[theMaze.getNumRows()][theMaze.getNumColumns()];
        for (int i = 0; i < theMaze.getNumRows(); i++){
            for (int j = 0; j < theMaze.getNumColumns(); j++){
                this.visited[i][j] = false;
            }
        }
    }

    /**
     * Used for the testing usage.
     */
    public int sizeOfTheStack(){
        return stk.size();
    }
    /**
     * Used for the testing usage.
     */
    public void PopFromTheStack(){
        position = stk.pop();
    }

    /**
     * For the testing usage.
     */
    @Override
    public String toString() {
        return "x:: "+ position.getX()+ "   y::" +position.getY();
    }
    /**
     * get an element from the stack.
     */
    public Position getElementFromTheStack(){
        return stk.peek();
    }
    /**
     * For the tetsing purpose, we will mark the given position as visited.
     */
    public void markingApositionAsVisited(Position pos){
        visited[pos.getY()][pos.getX()] = true;
    }
}