import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class constructs a maze using input
 * from a text file. Each position in the maze
 * contains a variable which different types
 * of robot classes interact with, while going
 * from a starting position to the goal position
 *
 * @author Liam Asplund (et19lad)
 *
 */
public class Maze {

    private int mazeRows;
    private int mazeCols;
    private char[][] maze;
    private ArrayList<String> readStringLines;
    private Position mazeGoalPos = null;
    private Position robotStartPos = null;

    /**
     * This constructor method constructs the maze using
     * input from a file contained in the scanner parameter
     * It also determines if there are any starting/goals and
     * where their positions are in the maze.
     * Columnlength and Rowlength does not have no be equally long
     *
     * @param in The text file contained in a scanner
     * @throws java.io.IOException Exception for input file
     */
    public Maze(Scanner in) throws java.io.IOException{

        readStringLines = new ArrayList<>();

        String line = in.nextLine();

        mazeCols = line.length();
        mazeRows = 0;

        readStringLines.add(line);

        mazeRows++;

        while ((in.hasNextLine())) {

            line = in.nextLine();

            if (line.length() > mazeCols) {

                mazeCols = line.length();

            }

            readStringLines.add(line);

            mazeRows++;
        }

        maze = new char[mazeRows][mazeCols];

        for (int i = 0; i < mazeRows; i++) {

            String newLine = readStringLines.get(i);

            int lineLength = newLine.length();

            for (int j = 0; j < mazeCols; j++) {

                if ((j < lineLength)) {

                    maze[i][j] = newLine.charAt(j);

                    if (maze[i][j] == 'S') {

                        robotStartPos = new Position(j, i);

                    }
                    if (maze[i][j] == 'G') {

                        mazeGoalPos = new Position(j, i);

                    }
                }
            }
        }

        if (mazeGoalPos == null || robotStartPos == null) {

            throw new IllegalArgumentException("No goal position or start position!");

        }
    }

    /**
     * A method for determening if a  position is fit to occupy.
     * It does this by checking/trying if the positions x,y-values are not
     * out of bounds, or else it will return false or catch it using an exception.
     *
     * @param p Position to be checked/tried.
     * @return A boolean value.
     */
    public boolean isMovable(Position p) {

        int posX = p.getX();
        int posY = p.getY();

        if ((posY >= mazeRows || posY < 0) || (posX >= mazeCols || posX < 0)) {

            return false;

        }

        try {

            char checkChar = maze[posY][posX];

            if (checkChar == ' ' || checkChar == 'G' || checkChar == 'S') {

                return true;

            }
        }
        catch (ArrayIndexOutOfBoundsException excep) {

            System.out.println("index out of bounds in isMovable");

        }

        return false;

    }

    /**
     * This method checks/tries if a positions index contains a wall variable '*'
     * while also checking if the position is not out of bounds, else
     * it will return false or catch the exception.
     *
     * @param p The position to be checked/tried.
     * @return A boolean.
     */
    public boolean isMazeWall(Position p) {

        int posX = p.getX();
        int posY = p.getY();

        if ((posY >= mazeRows || posY < 0) || (posX >= mazeCols || posX < 0)) {

            return false;

        }

        try {

            char checkChar = maze[posY][posX];

            if (checkChar == '*') {

                return true;

            }

        }
        catch (ArrayIndexOutOfBoundsException excep) {

            System.out.println("index out of bounds in isMazeWall");

        }

        return false;

    }

    /**
     * Checks if the variable at a position is the goal variable 'G'
     * in the maze.
     *
     * @param p The position to be checked.
     * @return A boolean value.
     */
    public boolean isGoal(Position p) {

        int posX = p.getX();
        int posY = p.getY();

        return (maze[posY][posX] == 'G');

    }

    /**
     * Returns the start position containing 'S' of the maze.
     *
     * @return The start position.
     */
    public Position getStartPos() {

        return robotStartPos;

    }

    /**
     * Returns the number max number of columns in the maze.
     *
     * @return An int
     */
    public int getNumColumns() {

        return mazeCols;

    }

    /**
     * Returns the number of rows in the maze.
     *
     * @return An int.
     */
    public int getNumRows() {

        return mazeRows;

    }

    public Position getStart(){
        return robotStartPos;
    }
}