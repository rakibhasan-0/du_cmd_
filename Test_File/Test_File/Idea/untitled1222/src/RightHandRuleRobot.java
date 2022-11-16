public class RightHandRuleRobot implements Robot {

    private Maze currentMaze;
    private Position position;
    private Position previousPosition;
    private enum Direction{SOUTH, NORTH, WEST, EAST };
    private Direction facing;

    public RightHandRuleRobot(Maze maze) throws Exception {
        currentMaze = maze;
        position = maze.getStart();
        previousPosition = position;
        facing = Direction.NORTH; // Kollar norr från början.
    }


    /**
     * Skickar tillbaka robotens nuvarande position.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosition() { return position; }


    /**
     * Skickar tillbaka ifall roboten har nått mål eller inte.
     * @return boolean
     */
    public boolean hasReachedGoal() {
        if (currentMaze.isGoal(position)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Roboten går alltid åt höger om den kan först. Om inte så försöker den att gå frammåt
     * så länge som den har en vägg till höger om sig, samt att positionen frammåt är giltig.
     * Om positionen frammåt inte är giltig så kommer den först försöka gå till vänster.
     * Om vänster inte är giltigt så går den tillbaka fast nu med höger handen på motsatt vägg.
     */
    public void move() {
        Position rightPosition = getRightPosition(facing);

        // Kolla om den kan röra sig åt höger.
        if (currentMaze.isMovable(rightPosition)) {
            previousPosition = position;
            position = rightPosition;
            setPosition(position);
            facing = getForwardHeading();
        } else if (currentMaze.isMovable(getForwardPosition(facing))) // Om det är en vägg så ska den gå frammåt om det går.
        {
            previousPosition = position;
            position = getForwardPosition(facing);
            setPosition(position);
            facing = getForwardHeading();
        } else if (currentMaze.isMovable(getLeftPosition(facing))) // Måste vara i ett hörn, kolla först vänster.
        {
            previousPosition = position;
            position = getLeftPosition(facing);
            setPosition(position);
            facing = getForwardHeading();
        } else
        {
            previousPosition = position;
            position = previousPosition;
            setPosition(position);
            facing = getForwardHeading();
        }
    }
    /**
     * Anropa endast om en rörelse har skett, inte innan.
     * Tar nuvarande frammåt vädersträck och skickar tillbaka
     * den nya frammåt vädersträcket efter rörelsen.
     * @return Direction
     */
    private Direction getForwardHeading() {
        if (position.getX() - previousPosition.getX() > 0) // Rört sig år höger
        {
            return Direction.EAST;
        } else if (position.getX() - previousPosition.getX() < 0) // Rört sig åt vänster
        {
            return Direction.WEST;
        } else if (position.getY() - previousPosition.getY() > 0) // Rört sig neråt
        {
            return Direction.SOUTH;
        } else if (!position.equals(previousPosition)) // Kontrollera så att den har rört sig
        {
            return Direction.NORTH;
        } else // Om den inte har rört sig så går den åt motsatt håll
        {
            if (facing == Direction.NORTH) { return Direction.SOUTH; }
            else if (facing == Direction.SOUTH) {return Direction.NORTH; }
            else if (facing == Direction.EAST) {return Direction.WEST; }
            else { return Direction.EAST; }
        }
    }


    /**
     * Tar in en riktning och skickar sedan tillbaka
     * positionen rakt frammåt.
     * @param dir
     * @return se.umu.cs.c19jem.essentials.Position
     */
    private Position getForwardPosition(Direction dir){
        switch (dir) {
            case SOUTH:
                return position.getPosToSouth();
            case NORTH:
                return position.getPosToNorth();
            case WEST:
                return position.getPosToWest();
            case EAST:
                return position.getPosToEast();
            default:
                return position;
        }
    }


    /**
     * Tar in en riktning och skickar sedan tillbaka
     * positionen till höger om den riktningen.
     * @param dir
     * @return se.umu.cs.c19jem.essentials.Position
     */
    private Position getRightPosition(Direction dir){
        switch (dir) {
            case SOUTH:
                return position.getPosToWest();
            case NORTH:
                return position.getPosToEast();
            case WEST:
                return position.getPosToNorth();
            case EAST:
                return position.getPosToSouth();
            default:
                return position;
        }
    }


    /**
     * Tar in en riktning och skickar sedan tillbaka
     * positionen till vänster om den riktningen.
     * @param dir
     * @return se.umu.cs.c19jem.essentials.Position
     */
    private Position getLeftPosition(Direction dir){
        switch (dir) {
            case SOUTH:
                return position.getPosToEast();
            case NORTH:
                return position.getPosToWest();
            case WEST:
                return position.getPosToSouth();
            case EAST:
                return position.getPosToNorth();
            default:
                return position;
        }
    }


    /**
     * Ändrar robotens position i labyrinten.
     * @param pos
     */
    private void setPosition(Position pos) {
        this.position = pos;
        System.out.println(this.position.toString());
    }
}
