package com.rakibs.umu;
/**
 * A test program to check whether the robot reaches the goal by traversing in the maze.
 * @author Gazi Md Rakibul Hasan
 */
public class RobotTest {
    private RandomRobot robot;
    private Maze maze;
    /*
     * In that function, the robot will traverse the maze iteratively,
     * and the robot's current position will be printed.
     */
    public void traverseInMaze () {
        while (!robot.hasReachedGoal()){
            System.out.print("Current ");
            System.out.println(toString());
            robot.move();
        }
        System.out.print("Goal ");
        System.out.println(toString());
    }

    public static void main (String[] args) {
        RobotTest test = new RobotTest();
        test.maze = new Maze(args[0]);
        test.robot = new RandomRobot(test.maze);
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
