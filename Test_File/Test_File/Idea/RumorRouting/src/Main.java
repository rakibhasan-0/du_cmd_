import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Node> nodes = null;
        double maxDistance = 15.0;
        try (FileReader file = new FileReader(args[0])) {
            Scanner scan = new Scanner(file);
            int totalSize = scan.nextInt();

            for (int i = 0; i < totalSize; i++){
                StringBuffer str = new StringBuffer();
                str.append(scan.nextLine());
                int j = 0;

                StringBuffer temp = new StringBuffer();
                while (str.charAt(j) != ','){
                    temp.append(str.charAt(j));
                    j++;
                }
                String st = temp.toString();
                int x = Integer.parseInt(st);

                StringBuffer temp1 = new StringBuffer();
                while (j < str.length()){
                    temp1.append(str.charAt(j));
                    j++;
                }
                st = temp.toString();
                int y = Integer.parseInt(st);
                Position pos = new Position(x,y);
                nodes.add(new Node(pos));
            }

            int size = nodes.size();
            for (Node node : nodes) {
                for (Node node1 : nodes) {
                    if (node1.getPosiiton().getDistance(node.getPosiiton()) <= maxDistance) {
                        if(!node.getPosiiton().equals(node1.getPosiiton())) {
                            node.addNeighbhour(node1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file could not get opened");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}