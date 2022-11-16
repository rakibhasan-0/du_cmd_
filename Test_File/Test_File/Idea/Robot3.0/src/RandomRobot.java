import java.util.Random;

/**
 * The robot, moves randomly changes its position in the maze.
 */
public class RandomRobot implements Robot{
    private Position position;
    private Position prevPos;
    private Maze maze;

    /**
     * It defines the robot's current position by assigning
     * the start position in the maze to the robot's current position.
     * @param maze The maze.
     */
    public RandomRobot (Maze maze) throws Exception {
        this.maze = maze;
        position = maze.getStart();
        prevPos = null;
    }

    /**
     * In that method, the robot will move randomly to a movable position.
     */
    @Override
    public void move() {
        Position[] nextPositions = new Position[4];
        fillArrayRandomly(nextPositions);
        for(Position nextPositon : nextPositions){
            if (maze.isMovable(nextPositon)){
                if (!nextPositon.equals(prevPos) && !nextPositon.equals(position)){
                    setPosition(nextPositon);
                    return;
                }
            }
        }
        setPosition(prevPos);
    }

    /**
     * We will insert the four directions randomly in an array in that method.
     * @param pos The Postions array.
     */
    private void fillArrayRandomly(Position[] pos){
        Random rand = new Random();
        int i = 0;
        boolean north,south,east,west;
        north = south = east = west = false;

        while (i <= 3){
            int val = rand.nextInt(4);
            if ( val == 1 && !north){
                pos[i] = position.getPosToNorth();
                north = true;
                i++;
            }
            if (val == 0 && !south){
                pos[i] = position.getPosToSouth();
                south = true;
                i++;
            }
            if (val == 2 && !east){
                pos[i] = position.getPosToEast();
                east = true;
                i++;
            }
            if (val == 3 && !west){
                pos[i] = position.getPosToWest();
                west = true;
                i++;
            }
        }
    }

    /**
     * Get the current position.
     * @return It will return the robot's current position.
     */
    @Override
    public Position getPosition(){
        return this.position;
    }

    /**
     * Set the given position as the current position and set the
     * current positoin as the previous position.
     * @param position Given position.
     */
    private void setPosition(Position position){
        this.prevPos = this.position;
        this.position = position;
    }

    /**
     * It checks whether the current position is the goal position in the maze.
     * @return true, if the cuurent position has reached the goal. Otherwise, it
     *         will return false.
     */
    @Override
    public boolean hasReachedGoal(){
        return maze.isGoal(position);
    }

}