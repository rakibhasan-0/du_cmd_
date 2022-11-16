/**
 * Denna klass skapar och håller reda
 * på en position i labyrinten.
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Skickar tillbaka positionens x-värde.
     * @return int
     */
    public int getX() {
        return this.x;
    }


    /**
     * Skickar tillbaka positionens y-värde.
     * @return int
     */
    public int getY() {
        return this.y;
    }


    /**
     * Skickar tillbaka positionen till syd.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosToSouth() {
        return new Position(x, y+1);
    }

    /**
     * Skickar tillbaka positionen till norr.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosToNorth() {
        return new Position(x, y-1);
    }


    /**
     * Skickar tillbaka positionen till väst.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosToWest() {
        return new Position(x-1, y);
    }


    /**
     * Skickar tillbaka positionen till öst.
     * @return se.umu.cs.c19jem.essentials.Position
     */
    public Position getPosToEast() {
        return new Position(x+1, y);
    }


    /**
     * Skriver ut nuvarande position till konsollen.
     * @return String
     */
    @Override
    public String toString(){
        String printout = "se.umu.cs.c19jem.essentials.Position: [X:" + this.x + ",Y:" + this.y + "]";
        return printout;
    }


    /**
     * Kontrollerar ifall två positioner är likadana.
     * Ifall dem är det så skickas true tillbaka.
     * Annars skickas false.
     * @param prevPos
     * @return boolean
     */
    public boolean equals(Position prevPos){
        if (prevPos != null) {
            if (this.getX() == prevPos.getX() && this.getY() == prevPos.getY()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
