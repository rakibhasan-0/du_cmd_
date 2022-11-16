import java.io.FileReader;

/**
 * A test program to check whether the robot reaches the goal by traversing in the maze.
 * @author Gazi Md Rakibul Hasan
 */
public class RightHandRobotTest {
    private RightHandRuleRobot robot;
    private Maze maze;
    /*
     * In that function, the robot will traverse the maze iteratively,
     * and the robot's current position will be printed.
     */
    public void traverseInMaze () {
        int count = 0;
        while (!robot.hasReachedGoal()){
            System.out.print("Current ");
            System.out.println(toString());
            robot.move();
           // System.out.println("dir::"+robot.getRobotDirection());
            count++;
        }
        System.out.print("Goal ");
        System.out.println(toString());
        count++;
        System.out.println(count);
    }

    public static void main (String[] args) throws Exception {
        RightHandRobotTest test = new RightHandRobotTest();
        test.maze = new Maze(new FileReader(args[0]));
        System.out.println("NUMBER OF ROW == "+test.maze.getNumRows() + " NUMBER OF COLS =="+test.maze.getNumColumns());
        test.robot = new RightHandRuleRobot(test.maze);
        System.out.println("Start Positio::" +test.toString());
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



