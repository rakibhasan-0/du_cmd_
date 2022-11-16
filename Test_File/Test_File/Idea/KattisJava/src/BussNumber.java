import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BussNumber {
    public static void main(String[]args) {
        ArrayList<Integer> busNumber = new ArrayList<>();
        ArrayList<String> stations = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        for (int i = 0; i < num; i++) {
            int number = scan.nextInt();
            busNumber.add(number);
        }
        Collections.sort(busNumber);
        int count = 0;
        Integer first;
        Integer last = null;
        boolean found = false;
        int i = 0;
        first = busNumber.get(i);
        last = busNumber.get(i);

        for(int j = i+1; j < busNumber.size(); j++){
            int number = busNumber.get(i)+1;
            if(number == busNumber.get(j)){
                count++;
                i++;
                last = busNumber.get(j);
            }
            if(number != busNumber.get(j)){
                if(count >= 2){
                    stations.add(first.toString()+"-"+last.toString());
                }
                else if(count == 1){
                    stations.add(first.toString());
                    //stations.add(busNumber.get(j-1).toString());
                    stations.add(last.toString());
                }
                else if(count == 0){
           //         stations.add(first.toString());
                    stations.add(last.toString());
                }
                first = busNumber.get(j);
                last = busNumber.get(j);
                count = 0;
                i++;
            }
        }

        if(count >= 2){
            stations.add(first.toString()+"-"+last.toString());
        }
        else if(count == 1){
            stations.add(first.toString());
            //stations.add(busNumber.get(j-1).toString());
            stations.add(last.toString());
        }
        else{
          //  stations.add(first.toString());
            stations.add(last.toString());
        }
        for (String s:stations){
            System.out.print(s+" ");
        }
    }
}
