import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GoodMorning {
    public static void main(String[] args) throws FileNotFoundException {
        String keyBoard = "1234567890";
        String board = "123456789";

        Scanner scan = new Scanner(new File(args[0]));
        int testCases = scan.nextInt();
        while(scan.hasNext()){
            StringBuilder time = new StringBuilder();
            int num = scan.nextInt();
            String s = Integer.toString(num);
            int j = 0;
            int size = s.length();
            boolean check = s.charAt(size-1) == '3' || s.charAt(size-1) == '6' || s.charAt(size-1) == '9';

            int t = 0;

            for (int i = 0; i < keyBoard.length(); i++){
                if(s.charAt(j) == keyBoard.charAt(i) && size -1 > j){
                    time.append(keyBoard.charAt(i));
                    j++;
                   if(size -1 != j){
                       i--;
                   }
                }
                if(size-1 == j){
                    String toStore = time.toString();
                    time.append(keyBoard.charAt(i));
                    String str = time.toString();
                    int number = Integer.parseInt(str);
                    int lastIndex = j +1;
                    if(Math.abs(number - num) < Math.abs(t - num)){
                        t = number;
                    }
                    time.deleteCharAt(lastIndex-1);
                }
            }
            System.out.println(time);

            if(check){
                int len = time.length();
                time.deleteCharAt(len-1);
                System.out.println("t == "+t);
                len = time.length()-1;
                System.out.println(time);
                if(time.charAt(len) == '3'){
                    time.append('2');
                    System.out.println(time);
                }
                if(time.charAt(len) == '6'){
                    time.append('5');
                    System.out.println(time);
                }
                if(time.charAt(len) == '9'){
                    time.append('8');
                    System.out.println(time);
                }
                for(int i = 0; i < board.length(); i++){
                    String toStore = time.toString();
                    time.append(keyBoard.charAt(i));
                    String str = time.toString();
                    int number = Integer.parseInt(str);
                    int lastIndex = time.length();
                    if(Math.abs(number - num) < Math.abs(t - num)){
                        t = number;
                    }
                    time.deleteCharAt(lastIndex-1);
                    System.out.println("t == "+t);
                }
            }
            System.out.println(t);
        }
    }
}
