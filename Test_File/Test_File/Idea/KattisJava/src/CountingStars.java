import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CountingStars {
    public static class Position{
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[]args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        int testcases = 1;

        while (scan.hasNext()) {
            int stars = 0;
            int rows = scan.nextInt();
            int cols = scan.nextInt();
           // System.out.println(rows+" "+cols);
            char[][] skysAndStars = new char[rows][cols];
            StringBuilder buffer = new StringBuilder();
            int rowNcols = 0;

           while (rows >= rowNcols){
               String s = scan.nextLine();
               buffer.append(s);
               rowNcols++;
           }

           // System.out.println(buffer);

            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    skysAndStars[i][j] = buffer.charAt(index);
                    index++;
                }
            }
            /*for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(skysAndStars[i][j]);
                }
                System.out.println();
            }*/
            boolean[][] visited = new boolean[rows][cols];
            int r = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    boolean aldreadyCounted = false;
                    int count = 0;
                    if (skysAndStars[i][j] == '-') {
                        boolean exist = false;
                        if (!visited[i][j]) {
                            visited[i][j] = true;
                            exist = true;
                            count++;
                        }
                        if (visited[i][j] && !exist) {
                            aldreadyCounted = true;
                        }
                        if (j - 1 >= 0) {
                            if (skysAndStars[i][j - 1] == '-') {
                                boolean e = false;
                                if (!visited[i][j - 1]) {
                                    visited[i][j - 1] = true;
                                    e = true;
                                    count++;
                                }
                                if (visited[i][j - 1] && !e) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (j + 1 < cols) {
                            if (skysAndStars[i][j + 1] == '-') {
                                boolean exists = false;
                                if (!visited[i][j + 1]) {
                                    visited[i][j + 1] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i][j + 1] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (i + 1 < rows) {
                            if (skysAndStars[i + 1][j] == '-') {
                                boolean exists = false;
                                if (!visited[i + 1][j]) {
                                    visited[i + 1][j] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i + 1][j] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (i + 1 < rows && j + 1 < cols) {
                            if (skysAndStars[i + 1][j + 1] == '-') {
                                boolean exists = false;
                                if (!visited[i + 1][j + 1]) {
                                    visited[i + 1][j + 1] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i + 1][j + 1] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (i + 1 < rows && j - 1 >= 0) {
                            if (skysAndStars[i + 1][j - 1] == '-') {
                                boolean exists = false;
                                if (!visited[i + 1][j - 1]) {
                                    visited[i + 1][j - 1] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i + 1][j - 1] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (i - 1 >= 0 && j + 1 < cols) {
                            if (skysAndStars[i - 1][j + 1] == '-') {
                                boolean exists = false;
                                if (!visited[i - 1][j + 1]) {
                                    visited[i - 1][j + 1] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i - 1][j + 1] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (skysAndStars[i - 1][j - 1] == '-') {
                                boolean exists = false;
                                if (!visited[i - 1][j - 1]) {
                                    visited[i - 1][j - 1] = true;
                                    exists = true;
                                    count++;
                                }
                                if (visited[i - 1][j - 1] && !exists) {
                                    aldreadyCounted = true;
                                }
                            }
                        }
                    }

                   // System.out.print("i::" + i + " j::" + j + " ");
                    //System.out.println("r::" + r + "  boolean::" + aldreadyCounted + "  count::" + count + "stars::" + stars);
                    if (count >= 1 && !aldreadyCounted) {
                        stars++;
                    }
                }
                r++;
            }
            /*System.out.println("Stars ==" + stars);
            for (boolean[] v : visited) {
                for (boolean t : v) {
                    System.out.print(t + " ");
                }
                System.out.println();
            }*/
            System.out.println("Case "+testcases+": "+stars);
            testcases++;
        }

        //System.out.println(Arrays.deepToString(skysAndStars));
    }
}
