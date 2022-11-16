import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class APrizeNoCanWin {
    public static void main(String[]args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int X = scan.nextInt();

        ArrayList<Integer> arrays = new ArrayList<>();
        for(int i = 0; i < n; i++){
            arrays.add(scan.nextInt());
        }
        Collections.sort(arrays);
        int item = 1;
        for (int i = 1; i < arrays.size(); i++){
            if(arrays.get(i) + arrays.get(i-1) <= X){
                item++;
            }
        }
        System.out.println(item);
        scan.close();
    }
}
