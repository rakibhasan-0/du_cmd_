import java.io.IOException;
import java.util.Scanner;

/**
 *  We will read an input file from the file stream. After that, we will create the maze.
 *  A two-dimensional array will represent the maze.
 * @author Gazi Md Rakibul Hasan
 * @version 2.0
 */
public class Maze {
    private char[][] theMaze;
    private int rows;
    private int cols;
    private StringBuilder buffer;
    private StringBuffer buff;
    private StringBuffer bufSize;
    /** Create a maze from the input file stream.
     * @throws IOException If  we couldn't find the file.
     */
    public Maze(Scanner scan) throws IOException {
        this.buffer = new StringBuilder();
        buff = new StringBuffer();
        bufSize = new StringBuffer();
        readingFromFile(scan);
        this.theMaze = new char[getNumRows()][getNumColumns()];
        fillArray();
        scan.close();
    }

    /**
     * Get the total column number.
     * @return That method will return the total column number in the maze array.
     */
    public int getNumColumns(){
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
     * @throws RuntimeException If we could not find any start position.
     * @return That method will return to that position where the robot will
     *         begin traversing the maze.
     */
    public Position getStart(){
        for (int i = 0; i < getNumRows(); i++){
            for (int j = 0; j < getNumColumns(); j++){
                if (theMaze[i][j] == 'S'){
                    return new Position(j,i);
                }
            }
        }
        throw new RuntimeException("Couldn't find the start position");
    }

    /**
     * That method checks whether the position is the goal position in the maze.
     * @param pos The position.
     * @return True, if the position is the goal position, otherwise it will return false.
     */
    public boolean isGoal (Position pos){
        return theMaze[pos.getY()][pos.getX()] == 'G';
    }

    /**
     * That method checks if the given position has not any barrier/wall.
     * @param pos the position.
     * @return True, if the given position contains a space and the letter 'G',
     *         on the other hand, it will return false.
     */
    public boolean isMovable(Position pos){
        if (pos.getX() >= 0 && pos.getX() < cols){
            if (pos.getY() >= 0 && pos.getY() < rows){
                return checkIfMovable(pos);
            }
        }
        return false;
    }

    private boolean checkIfMovable(Position pos) {
        return theMaze[pos.getY()][pos.getX()] == ' ' || theMaze[pos.getY()][pos.getX()] == 'G' || theMaze[pos.getY()][pos.getX()] == 'S';
    }


    /**
     * The maze's representation in a 2D array.
     * @throws RuntimeException If we could not find any goal position in the maze.
     */
    private void fillArray(){
        int index = 0;
        boolean found = false;
        //System.out.println("The Maze:");
        //System.out.println("Col:::"+getNumColumns() +"Row::"+getNumRows());
        for (int i = 0; i < getNumRows(); i++ ){
            for (int j = 0; j < getNumColumns(); j++){
                this.theMaze[i][j] = buffer.charAt(index);
                //System.out.print(theMaze[i][j]);
                if ('G' == buffer.charAt(index)){
                    found = true;
                }
                index++;
            }
            // System.out.println();
        }

        if (!found){
            throw new RuntimeException("Didn't find any goal position");
        }
    }

    /**
     * In that method, we will read the file from the input file stream,
     * and each line will be saved in a string buffer.
     * @param scan The file stream.
     */
    private void readingFromFile (Scanner scan){
        int temp = 0;
        while (scan.hasNext()){
            String str = scan.nextLine();
            bufSize.append(str);
            buff.append(str);
            bufSize.append('|');
            int size = buff.length();
            if(temp < size){
                temp = size;
            }
            buff.delete(0,size);
            this.rows++;
        }
        this.cols = temp;
        buff.delete(0,buffer.length());
        int num = 0;
        int index = 0;

        for(int i = 0; i < bufSize.length(); i++){
            if(bufSize.charAt(i) == '|'){
                int size = this.cols - num;
                for(int j = 0; j < size; j++){
                    buffer.append(" ");
                    index++;
                }
                num = 0;
            }
            else{
                buffer.append(bufSize.charAt(i));
                index++;
                num++;
            }
        }
    }

    /**
     * For the test purpose.
     * @return the maze.
     */
    public char[][] getTheMaze() {
        return theMaze;
    }
}
