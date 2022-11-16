import java.io.FileNotFoundException;
import java.util.Scanner;

public class DirtScore {
    private static String printDart(int x){
        if(x == 0){
            return "";
        }
        else if (x % 3 == 0){
            return "triple " + (x /3)+("\n");
        }
        else if (x % 2 == 0){
            return "double " + (x /2)+("\n");
        }
        return "single "+x +("\n");
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        int targetScore = scan.nextInt();
        boolean found = false;

            loop:for (int a = 1; a <= 3; a++){
            for (int b = 0; b <= 3; b++){
                for(int c = 0; c <= 3; c++){
                    for(int i =  1; i <= 20; i++){
                        for(int j = 1; j <= 20; j++){
                            for(int k = 1; k <= 20; k++){
                                int n = a * i + b * j + c * k;
                                if(n == targetScore){
                                    System.out.print(printDart(a*i));
                                    System.out.print(printDart(b*j));
                                    System.out.print(printDart(c*k));
                                    found = true;
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!found){
            System.out.println("impossible");
        }
        scan.close();
    }
}
