import java.io.FileReader;

public class MemoryRobotTest {
    private MemoryRobot robot;
    private Maze maze;

    public void traverseInMaze () {
        while (!robot.hasReachedGoal()){
            robot.move();
        }
    }


    public static void main(String[] args) throws Exception {
        MemoryRobotTest test = new MemoryRobotTest();
        test.maze = new Maze(new FileReader("file.txt"));
        test.robot = new MemoryRobot(test.maze);
        test.traverseInMaze();
    }

    /**
     * Position to string.
     * @return It converts the robot's current position to a string.
     */
    @Override
    public String toString() {
        return "Position{" +
                "x = " + robot.getPosition().getX() + " ," + "y = " + robot.getPosition().getY()+
                '}';
    }
}
