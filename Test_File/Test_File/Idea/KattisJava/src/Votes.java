import java.util.Scanner;

public class Votes {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        for(int i = 0; i < testCases; i++){
            int candidates = scan.nextInt();
            int[] totalVotes = new int[candidates];
            int sumOfAllvotes = 0;
            for(int j = 0; j < candidates; j++){
                totalVotes[j] = scan.nextInt();
                sumOfAllvotes = totalVotes[j] + sumOfAllvotes;
            }
            int max = totalVotes[0];
            int candidateNumber = 0;
            int duplciateCount = 0;
            for(int k = 0; k < totalVotes.length; k++){
                if(max < totalVotes[k]){
                    max = totalVotes[k];
                    candidateNumber = k;
                }
                else if(totalVotes[0] == totalVotes[k]){
                    duplciateCount++;
                }
            }
            int winners = 0;
            for (int totalVote : totalVotes) {
                if (max == totalVote) {
                    winners++;
                }
            }
            if(winners > 1){
                System.out.println("no winner");
            }
            else{
                if(sumOfAllvotes - max <  max){
                    System.out.println("majority winner "+ (candidateNumber+1));
                }
                if(sumOfAllvotes - max >=  max){
                    System.out.println("minority winner " + (candidateNumber+1));
                }
            }
        }
    }
}
