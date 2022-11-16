import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class RandomRobotTest {

    /**
     * we can see that there exists no dead ends thus the robot shouldn't return to the
     * previous positon.
     * And the robot should find the goal position by randomly moving to the next positions.
     *
     * randomRobot.txt":
     *
     *      ************
     *      *          *
     *      * S        *
     *      *          *
     *      *          *
     *      *          *
     *      *    G     *
     *      *          *
     *      *          *
     *      *          *
     *      *          *
     *      ************
     */

    @Test
    public void moveCheck() throws Exception {
        Maze maze = new Maze(new Scanner(new File("randomRobot.txt")));
        RandomRobot robot = new RandomRobot(maze);
        Position currentPos = robot.getPosition();
        Position prevPos = null;
        while(!robot.hasReachedGoal()){
            robot.move();
            prevPos = currentPos;
            currentPos = robot.getPosition();
            assertNotEquals(prevPos,currentPos);
        }
    }

}