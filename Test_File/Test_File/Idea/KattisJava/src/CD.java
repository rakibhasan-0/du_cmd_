import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CD {
    public static void main(String[]args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        long jackCD = Long.parseLong(s.split(" ")[0]);
        long jilCD = Long.parseLong(s.split(" ")[1]);
        ArrayList<Long> cDofJack = new ArrayList<>();
        ArrayList<Long> cDofJil = new ArrayList<>();

        int count = 0;
        boolean countJack = true;
        boolean countJil = false;

        while(scan.hasNext()){
            long l = scan.nextLong();
            if(jackCD == count){
                countJack = false;
                countJil = true;
                count = 0;
            }
            if(countJack && jackCD > count){
                cDofJack.add(l);
              //  System.out.println(count);
                count++;
            }
            if(jilCD == count){
                countJil = false;
            }
            if(countJil && jilCD > count){
                cDofJil.add(l);
                count++;
                //System.out.println("number"+count);
            }
        }
        cDofJil.remove(cDofJil.size()-1);
        cDofJil.remove(cDofJil.size()-1);
        Object[] cdJils = cDofJil.toArray();
        int sellCd = 0;
        for(Long i:cDofJack){
            if(Arrays.binarySearch(cdJils,i) >= 0){
                sellCd++;
            }
        }
        System.out.println(sellCd);

    }
}
