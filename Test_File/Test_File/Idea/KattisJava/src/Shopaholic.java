import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Shopaholic {
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        long items = scan.nextInt();
        ArrayList<Long> prices = new ArrayList<>();
        for (int i = 0; i < items; i++ ) {
            prices.add(scan.nextLong());
        }
        long discountPrice = 0;

        prices.sort(Collections.reverseOrder());
        for (int i = 0; i < prices.size(); i++){
            if(i % 3 == 2){
                discountPrice = discountPrice + prices.get(i);
            }
        }
        System.out.println(discountPrice);
        //System.out.println(prices);
    }
}
