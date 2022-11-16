import java.util.Scanner;

public class Greater {
    public static void main(String[] args){
        Scanner num = new Scanner(System.in);
        long a = num.nextLong();
        long b = num.nextLong();

        if(a > b){
            System.out.println("1");
        }
        else{
            System.out.println("0");
        }

    }
}
