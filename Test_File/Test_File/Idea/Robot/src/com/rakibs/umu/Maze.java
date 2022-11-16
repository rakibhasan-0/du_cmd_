package com.rakibs.umu;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner; 

/**
 *  We will read an input file from the file stream. After that, we will create the maze.
 *  A two-dimensional array will represent the maze.
 */
public class Maze {
    private char[][] theMaze;
    private int rows;
    private int cols;
    private StringBuilder buffer;

    /** Create a maze from the input file stream.
     * @param fileName the input file.
     * @throws FileNotFoundException If we couldn't open the file or we couldn't find the file.
     * @throws IOException If we couldn't close the input file.
     */
    public Maze(String fileName) {
        FileReader inputFile = null;
        try {
            inputFile = new FileReader(fileName);
            Scanner scan = new Scanner(inputFile);
            this.buffer = new StringBuilder();

            readingFromFile(scan);
            this.theMaze = new char[getNumRows()][getNumColumns()];
            fillArrayAndCheckGoalPosition();

        } catch (FileNotFoundException e) {
            System.err.println("File couldn't open");
        }
        finally{
            if (inputFile != null){
                try{
                    inputFile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the total column number.
     * @return That method will return the total column number in the maze array.
     */
    public int getNumColumns(){
        this.cols = this.buffer.length()/this.rows;
        return this.cols;
    }

    /**
     * Get the total row number.
     * @return That method will return the total row number in the maze array.
     */
    public int getNumRows(){
        return this.rows;
    }


    /**
     * Get the robot's start position.
     * @return That method will return to that position where the robot will
     *         begin traversing the maze.
     */
    public Position getStart(){
      //  boolean found = false;
        for (int i = 0; i < getNumRows(); i++){
            for (int j = 0; j < getNumColumns(); j++){
                if (theMaze[i][j] == 'S'){
                    //found = true;
                    return new Position(i,j);
                }
            }
        }
        throw new RuntimeException("The robot could not find any start position in the maze");
    }

    /**
     * That method checks whether the position is the goal position in the maze.
     * @param pos The position.
     * @return True, if the position is the goal position, otherwise it will return false.
     */
    public boolean isGoal (Position pos){
        return theMaze[pos.getX()][pos.getY()] == 'G';
    }

    /**
     * That method checks if the given position has not any barrier/wall.
     * @param pos the position.
     * @return True, if the given position contains a space and the letter 'G',
     *         on the other hand, it will return false.
     */
    public boolean isMovable(Position pos){
        if (pos.getX() >= 0 && pos.getX() < rows){
            if (pos.getY() >= 0 && pos.getY() < cols){
                return theMaze[pos.getX()][pos.getY()] == ' '|| theMaze[pos.getX()][pos.getY()] == 'G';
            }
        }
        return false;
    }

    /**
     * The maze's representation in a 2D array. At the same time it checks if the maze
     * has the goal position.
     */
    private void fillArrayAndCheckGoalPosition(){
        int index = 0;
        boolean found = false;
        for (int i = 0; i < getNumRows(); i++ ){
            for (int j = 0; j < getNumColumns(); j++){
                this.theMaze[i][j] = buffer.charAt(index);
                if ('G' == buffer.charAt(index)){
                    found = true;
                }
                index++;
            }
        }
        if (!found){
            throw new RuntimeException("The robot could not find any goal position");
        }
    }

    /**
     * In that method, we will read the file from the input file stream,
     * and each line will be saved in a string buffer.
     * @param scan The file stream.
     */
    private void readingFromFile (Scanner scan){
        while (scan.hasNext()){
            buffer.append(scan.nextLine());
            this.rows++;
        }
    }

}
