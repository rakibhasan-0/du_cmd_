import java.util.HashMap;
import java.util.Scanner;

public class BabelFish {
    public static void main(String[] args){
        HashMap<String,String> dic = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        String all = scan.nextLine();
        while(all.contains(" ")){
            String val = all.split(" ")[0];
            String key = all.split(" ")[1];
            dic.put(key,val);
            all = scan.nextLine();
        }

        while(scan.hasNextLine()){
            String str = scan.nextLine();
            System.out.println(dic.getOrDefault(str, "eh"));
        }
    }
}
