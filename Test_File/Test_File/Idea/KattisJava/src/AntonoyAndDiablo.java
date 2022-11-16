import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AntonoyAndDiablo {
    public static void main(String[]args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        double A = Double.parseDouble(s.split(" ")[0]);
        double N = Double.parseDouble(s.split(" ")[1]);

        double num = (2 * Math.PI*Math.sqrt(A/Math.PI));
        if(num - N <= 0){
            System.out.println("Diablo is happy!");
        }
        else{
            System.out.println("Need more materials!");
        }
    }
}
