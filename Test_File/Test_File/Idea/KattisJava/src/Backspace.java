import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Backspace {
    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder program = new StringBuilder();
        Scanner scan = new Scanner(new File("SimonSays.txt"));
        StringBuilder str = new StringBuilder();
        while(scan.hasNext()){
            program.append(scan.nextLine());
        }
        for(int i = 0; i < program.length(); i++){
            if(program.charAt(i) == '<'){
                if(str.length() > 0){
                    int index = str.length() -1;
                    str.deleteCharAt(index);
                }
            }
            else{
                str.append(program.charAt(i));
            }
        }
        System.out.println(str.toString());
    }
}