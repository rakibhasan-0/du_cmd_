package com.rakibs.umu;
import java.util.Objects;

/**
 * A position in the maze represents by x and y coordinates.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a new position object with the help of x and y coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Position (int x, int y) throws IllegalArgumentException{
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of the x coordinate.
     * @return That method will give us value of the given position's x coordinate.
     */
    public int getX (){
        return x;
    }

    /**
     * Get the value of the y coordinate.
     * @return That method will give us value of the given position's y coordinate.
     */
    public int getY(){
        return y;
    }

    /**
     * Get the position of the current position's southward position.
     * @return We will get the current position's southwards position by
     *         moving the given position horizontally forward.
     */
    public Position getPosToSouth(){
       return new Position(x+1,y);
    }

    /**
     * Get the position of the current position's northern position.
     * @return We will get the current position's northern position by
     *         moving the given position horizontally backwards.
     */
    public Position getPosToNorth(){
        return new Position(x-1,y);
    }

    /**
     * Get the position of the current position's eastwards position.
     * @return We will get the current position's eastwards position by
     *         moving the given position vertically forward.
     */
    public Position getPosToEast(){
        return  new Position(x,y+1);
    }

    /**
     * Get the position of the current position's westwards position.
     * @return We will get the current position's eastwards position by
     *         moving the given position vertically backwards.
     */
    public Position getPosToWest(){
        return  new Position(x,y-1);
    }

    /**
     * It checks whether two positions is equal or not.
     * @param o The object.
     * @return True, if the two postions are equal otherwise it will
     *         return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return getX() == position.getX() && getY() == position.getY();
    }

    /**
     * Get the hash value of a postions.
     * @return We will get the hash code of the of the given position by
     *         using the x and y coordinate as the key of the hash map.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}