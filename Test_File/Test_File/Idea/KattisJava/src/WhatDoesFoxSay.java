import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class WhatDoesFoxSay {
    public static void main(String[]args)throws FileNotFoundException {

        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        scan.nextLine();
        //System.out.println(testCases);
        //System.out.println(scan.nextLine());
        while(testCases > 0){
            String rec = scan.nextLine();
//            System.out.println(rec);
            String[] records = rec.split(" ");


            ArrayList<String> animalSounds = new ArrayList<>();
            Collections.addAll(animalSounds, records);
           // String sounds = scan.nextLine()

           // System.out.println("size == "+animalSounds.size());
           // System.out.println("string" + animalSounds.toString());

            String sounds = scan.nextLine();
            while(!sounds.contains("does")){
//                System.out.println("sounds == "+sounds);
                ArrayList<String> randomSounds = new ArrayList<>();
                String[] str1 = sounds.split(" ");
                randomSounds.add(str1[2]);
                //string animalsSOnd = str1[1];

                animalSounds.removeAll(randomSounds);
                //System.out.println(animalSounds);
              //  String[] str2 = "Dog goes woof".split("goes");
               // System.out.println(str2[1]);

                sounds = scan.nextLine();
            }
            testCases--;
            for(String s: animalSounds){
                System.out.print(s+" ");
            }
            System.out.println();
        }
    }
}

