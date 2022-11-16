/**
 * Ett interface för dem olika robotarna
 * att implementera för dem nödvändiga funktionerna.
 */
public interface Robot {

    Position getPosition();

    boolean hasReachedGoal();

    void move();
}

