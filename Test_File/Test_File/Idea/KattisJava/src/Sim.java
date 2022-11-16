import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Sim {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        while(testCases >= 0){
            StringBuilder program = new StringBuilder();
            program.append(scan.nextLine());
            ArrayList<Character> str = new ArrayList<Character>();
            int homeIndex = 0;
            boolean homeFound = false;
            boolean endFound = false;

            for(int i = 0; i < program.length(); i++){
                boolean deleted = false;
                boolean notBeAdded = false;

                if(program.charAt(i) == '<' && !homeFound && !endFound){
                    if(str.size() > 0){
                       // System.out.println(str);
                        int index = str.size() -1;
                        str.remove(index);
                        deleted = true;
                    }
                }
                if(program.charAt(i) == '['){
                    homeFound = true;
                    endFound = false;
                    homeIndex = 0;
                    continue;
                }
                if(program.charAt(i) == ']'){
                    endFound = true;
                    homeFound = false;
                    homeIndex = 0;
                    continue;
                }
                if(homeFound){
                    notBeAdded = true;
                    if(program.charAt(i) != '[' &&  program.charAt(i) != ']'){
                      // System.out.println(str);
                        if(program.charAt(i) == '<'){
                            if(str.size()>0 && homeIndex -1 >= 0){
                                str.remove(homeIndex-1);
                                homeIndex--;
                            }
                        }
                        else{
                            str.add(homeIndex,program.charAt(i));
                            homeIndex++;
                        }
                    }
                }
                if(endFound){
                    notBeAdded = true;
                    if(program.charAt(i) != '[' &&  program.charAt(i) != ']'){
                      //  System.out.println(str);
                        if(program.charAt(i) == '<'){
                            if(str.size()>0){
                                str.remove(str.size()-1);
                            }
                        }
                        else {
                            str.add(program.charAt(i));
                        }
                    }
                }
                if(!deleted && !notBeAdded && program.charAt(i) != '<'&& program.charAt(i) != '['&&program.charAt(i) != ']'){
                    str.add(program.charAt(i));
                }
            }
            String words = str.stream().map(String::valueOf).collect(Collectors.joining());
            System.out.println(words);
            testCases--;
        }
    }
}

