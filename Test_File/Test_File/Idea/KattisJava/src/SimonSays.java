import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimonSays {
    public static void main(String[]args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        String simonSays = new String("simon says");

      //  String sub = simonSays.substring(0,10);
        //System.out.println(simonSays);
        //System.out.println(sub);

        while (scan.hasNext()){
            String cmd = scan.nextLine();
            if(cmd.length() >= 11){
                String s = cmd.substring(0,10);
                if(s.equals(simonSays)){
                    String line = cmd.substring(11,cmd.length());
                    System.out.println(line);
                }
                else{
                    System.out.println(" "+"\n");
                }
            }
            else{
                System.out.println(" "+"\n");
            }
        }

    }
}
