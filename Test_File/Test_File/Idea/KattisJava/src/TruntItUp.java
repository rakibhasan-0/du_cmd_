import java.util.Scanner;

class TruntItUp {
    public static void main(String[] args) {
        int initalNum = 7;
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        String str = scan.nextLine();
        for (int i = 0; i < num; i++){
            str = scan.nextLine();
            if(str.equals("Skru op!")){
                if(initalNum < 10){
                    initalNum++;
                }
            }
            if(str.equals("Skru ned!")){
                if(initalNum > 0){
                    initalNum--;
                }
            }
        }
        System.out.println("\n" +initalNum);
    }
}