import java.util.Scanner;

public class Game2048 {
    static int[][] game;
    static boolean[][] board;
    private static void moveLeft(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                if(j+1 < 4){
                    if(game[i][j] == game[i][j+1] && game[i][j] != 0 && !board[i][j]){
                        game[i][j] = game[i][j+1] *2;
                        game[i][j+1] = 0;
                        board[i][j] = true;
                    }
                    if(game[i][j] == 0 && game[i][j+1] != 0){
                        game[i][j] = game[i][j+1];
                        game[i][j+1] = 0;
                        for(int k = j; k >= 0; k--){
                            if(k-1>= 0){
                                if(game[i][k] != 0 && game[i][k-1] == 0){
                                    game[i][k-1] = game[i][k];
                                    game[i][k] = 0;
                                }
                                if(game[i][k] == game[i][k-1] && game[i][k] != 0 &&!board[i][k-1] &&!board[i][k]){
                                    game[i][k-1] = game[i][k] *2;
                                    game[i][k] = 0;
                                    board[i][k-1] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static void moveRight(){
       for(int i = 0; i < 4; i++){
           for (int j = 3; j >= 0; j--){
               if(j-1 >= 0){
                   if(game[i][j-1] == game[i][j] && game[i][j] != 0 && !board[i][j]){
                       game[i][j] = game[i][j] * 2;
                       game[i][j-1] = 0;
                       board[i][j] = true;
                   }
                   if(game[i][j] == 0 && game[i][j-1] != 0){
                       game[i][j] = game[i][j-1];
                       game[i][j-1] = 0;
                   }
                   for (int k = j; k < 4; k++){
                       if(k+1 < 4){
                           if(game[i][k] != 0 && game[i][k+1] == 0){
                               game[i][k+1] = game[i][k];
                               game[i][k] = 0;
                           }
                           if(game[i][k+1] == game[i][k] && game[i][k] != 0 &&!board[i][k+1] && !board[i][k]){
                               game[i][k+1] = game[i][k+1] * 2;
                               game[i][k] = 0;
                               board[i][k+1] = true;
                           }
                       }
                   }
               }
           }
       }
    }
    private static void moveUp(){
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if(j+1 < 4){
                    if(game[j][i] == game[j+1][i] && game[j+1][i] != 0 && !board[j][i] &&!board[j+1][i]){
                        game[j][i] = game[j+1][i] * 2;
                       // System.out.println("game[j][i] == "+game[j][i]);
                        //System.out.println("game[j+1][i] == "+game[j+1][i]);
                        game[j+1][i] = 0;
                        board[j][i] = true;
                    }
                    if(game[j+1][i] != 0 && game[j][i] == 0){
                        game[j][i] = game[j+1][i];
                        game[j+1][i] = 0;
                        //System.out.println("game[j][i] == "+game[j][i]);
                        //System.out.println("game[j+1][i] == "+game[j+1][i]);
                    }
                    for (int k = j; k >= 0; k--){
                        if(k-1>= 0){
                            if(game[k-1][i] == 0 && game[k][i] != 0){
                                game[k-1][i] = game[k][i];
                                game[k][i] = 0;
                            }
                            if(game[k-1][i] == game[k][i] && game[k][i] != 0 && !board[k-1][i] && !board[k][i]){
                                game[k-1][i] = game[k][i] *2;
                                game[k][i] = 0;
                                board[k-1][i] = true;
                            }
                        }
                    }
                }
            }
        }
    }
    private static void moveDown(){
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if(j+1 < 4){
                    if(game[j][i] == game[j+1][i] &&game[j+1][i] != 0 && !board[j+1][i] &&!board[j][i]){
                        game[j+1][i] = game[j+1][i] * 2;
                        game[j][i] = 0;
                        board[j+1][i] = true;
                    }
                    if(game[j][i] != 0 && game[j+1][i] == 0){
                        game[j+1][i] = game[j][i];
                        game[j][i] = 0;
                    }
                    for (int k = j; k >= 0; k--){
                        if(k-1 >= 0){
                            if(game[k][i] == 0 && game[k-1][i] != 0){
                                game[k][i] = game[k-1][i];
                                game[k-1][i]= 0;
                            }
                        }
                    }
                }
            }
        }

    }
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        game = new int[4][4];
        board = new boolean[4][4];

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                game[i][j] = scan.nextInt();
            }
        }
        int move = scan.nextInt();
        switch (move){
            case 0: moveLeft();
            break;
            case 2: moveRight();
            break;
            case 1:moveUp();
            break;
            case 3:moveDown();
            break;
            default: break;
        }

        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(game[i][j]+" ");
            }
            System.out.println();
        }
      /*  for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(board[i][j]+"  ");
            }
            System.out.println();
        }*/
    }
}
