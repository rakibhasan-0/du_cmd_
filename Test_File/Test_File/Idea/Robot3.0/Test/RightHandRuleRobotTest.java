import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class RightHandRuleRobotTest {

    /**
     *
     * rightHand.txt
     *      **************************
     *      *          G             *
     *      ****************         *
     *      *              *        **
     *      *    *   *     **** *   *
     *      *    *   *        * *   *
     *      *    *   *        *S*   *
     *      **************************
     *
     *      We will try to get the start position of a maze. Then we
     *      will compare if we get the right coordinates by comparing
     *      the maze's start coordinates with the start positions coordiantes.
     */
    @Test
    public void checkPositionAndMaze() throws Exception {
        Maze maze = new Maze(new Scanner(new File("rightHand.txt")));
        RightHandRuleRobot robot = new RightHandRuleRobot(maze);
        Position pos = robot.getPosition();
        assertEquals(pos,maze.getStart());
        assertEquals(pos.getX(),19);
        assertEquals(pos.getY(),6);
    }

    /**
     * rightHand.txt
     *      **************************
     *      *          G             *
     *      ****************         *
     *      *              *        **
     *      *    *   *     **** *   *
     *      *    *   *        * *   *
     *      *    *   *        *S*   *
     *      **************************
     *
     *      We will let out robot to traves in the maze. The right-hand rule robot
     *      is deteminsitic so, we can predict its next moves.
     *      We will compare and verify if the robot is traversing in the maze according
     *      to the right-hand rule or not.
     */
    @Test
    public void verifyRightHandRule() throws Exception {
        Maze maze = new Maze(new Scanner(new File("rightHand.txt")));
        RightHandRuleRobot robot = new RightHandRuleRobot(maze);
        // As we can see from the maze, that robot only can go north for 3 steps.
        for(int i = 0; i < 3; i++){
            robot.move();
        }
        // after the first three steps, the robot supposed to move east for 2 steps.
        Position pos = null;
        pos = robot.getPosition();
        robot.move();
        assertEquals(pos.getPosToEast(),robot.getPosition());
        pos = robot.getPosition();
        robot.move();
        assertEquals(pos.getPosToEast(),robot.getPosition());
        //the roobot supposed to move south for 3 steps.
        for(int i = 0; i < 3; i++){
            pos = robot.getPosition();
            robot.move();
            assertEquals(robot.getPosition(),pos.getPosToSouth());
        }
        // the robot supposed to move east for 2 steps.
        for (int i = 0; i < 2; i++){
            pos = robot.getPosition();
            robot.move();
            assertEquals(robot.getPosition(),pos.getPosToEast());
        }
        // the robot supposed to move north for 4 steps.
        for(int i = 0; i <4; i++){
            pos = robot.getPosition();
            robot.move();
            assertEquals(robot.getPosition(),pos.getPosToNorth());
        }
        // The robot should move to one step to the east, then one step to the north.
        pos = robot.getPosition();
        robot.move();
        assertEquals(pos.getPosToEast(),robot.getPosition());
        pos = robot.getPosition();
        robot.move();
        assertEquals(pos.getPosToNorth(),robot.getPosition());
        // As we can see that the robot will move to the west positions until it reaches the goal.
        while(robot.hasReachedGoal()){
            pos = robot.getPosition();
            robot.move();
            assertEquals(robot.getPosition(),pos.getPosToWest());
        }
    }


    /**
     * positionChecker:
     *
     *      ***********************
     *      *                     *
     *      *         S           *
     *      *                     *
     *      * *   *           *   *
     *      * *G  *           *   *
     *      ***********************
     *
     *      the right hadnd robot should not able to move if there exists no wall
     *      around itself. We will check if the right hand throws exception or not
     *      if finds no movable position to move according to the right hand rule.
     */
    @Test
    public void checkAPositionWhereExistsNoWall() throws Exception {
        Maze maze = new Maze(new Scanner(new File("positionChecker")));
        RightHandRuleRobot robot = new RightHandRuleRobot(maze);
        Exception exception = assertThrows(Exception.class, robot::move);
        assertEquals("the robot can not move",exception.getMessage());
    }


    /**
     * deadEnds.txt:

     *      ********** *****************
     *      **   G*
     *      ** **** **** *
     *      *  **** **** *
     *      ** **** ***
     *      *  ****S**** *
     *      ** *** ***** *
     *      *            ****************
     *      ****** *** *********
     *      ***************************
     *
     *      In that method, first we will count how many steps the right hand supposed to take if the robot traverse in
     *      the maze.
     *      Then we will compare the steps with the right hand robot that we have implemented in that
     *      assignment.
     *
     */
    @Test
    public void stepCounts() throws Exception {
        Maze maze = new Maze(new Scanner(new File("deadEnds.txt")));
        RightHandRuleRobot robot = new RightHandRuleRobot(maze);
        int countSteps = 0;
        while(!robot.hasReachedGoal()){
            robot.move();
            countSteps++;
        }
        assertEquals(38,countSteps);
        assertTrue(robot.hasReachedGoal());
    }
}
