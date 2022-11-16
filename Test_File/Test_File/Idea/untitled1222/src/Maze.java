

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * En klass för att läsa in, skapa och kontrollera
 * en labyrint.
 */
public class Maze {
    ArrayList<String> mazeData = new ArrayList<String>();

    public Maze(FileReader read){
        Scanner scan = new Scanner(read);
        while (scan.hasNextLine()){
            mazeData.add(scan.nextLine());
        }
        scan.close();
        for (String line : mazeData) {
            System.out.println(line);
        }
    }


    /**
     * Kontrollerar ifall en position är giltig att gå till i labyrinten.
     * Returnerar true ifall den är giltig, annars false.
     * @param pos
     * @return boolean
     */
    public boolean isMovable(Position pos) {
        if (pos.getY() >= 0 && pos.getX() >= 0) {
            String line = mazeData.get(pos.getY());
            if (line.charAt(pos.getX()) == '*' || line == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Kontrollerar ifall en position är målet(G) i labyrinten.
     * Returnerar true ifall den är G, annars false.
     * @param pos
     * @return boolean
     */
    public boolean isGoal(Position pos) {
        String line = mazeData.get(pos.getY());
        if (line.charAt(pos.getX()) == 'G') {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Letar efter startpositionen i labyrinten och skickar tillbaka dens position.
     *
     * @return se.umu.cs.c19jem.essentials.Position
     * @throws Exception
     */
    public Position getStart() throws Exception {
        int xPos = 0;
        int yPos = 0;
        int numOfStartPos = 0;
        boolean foundStart = false;
        Position startPos = null;
        for (String line : mazeData) {
            if (line.contains("S")) {
                foundStart = true;
                numOfStartPos++;
                if (numOfStartPos > 1) {
                    System.err.println("ERROR: Hittade mer än 1 start position i filen!");
                    throw new IllegalStateException();
                } else {
                    xPos = line.indexOf('S');
                    startPos = new Position(xPos, yPos);
                }
            }
            yPos++;
        }
        if (foundStart) {
            System.out.println("Start: " + xPos + ", " + yPos);
            return startPos;
        } else {
            // Finns ingen start position!
            System.err.println("ERROR: Finns ingen start position i den valda filen!");
            System.exit(-1);
            return null;
        }
    }


    /**
     * Skickar tillbaka antalet kollumner i labyrinten.
     * @return int
     */
    public int getNumColumns() {
        int cols = 0;
        for (String line : mazeData) {
            cols = line.length();
        }
        return cols;
    }


    /**
     * Skickar tillbaka antalet rader i labyrinten.
     * @return int
     */
    public int getNumRows() {
        return mazeData.size();
    }
}
