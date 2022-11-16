/**
 * An interface that includes abstract methods, all robots will
 * inherit those methods.
 * @author Gazi Md Rakibul Hasan
 */
public interface Robot {
    public void move();
    public  Position getPosition();
    public boolean hasReachedGoal();
}
