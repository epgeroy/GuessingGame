import java.util.Random;
import java.util.Scanner;

public class Agent2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int maxThreshold = Integer.parseInt(input);
        int lastNumber = maxThreshold / 2;
        System.out.println(String.format("%d", lastNumber));

        while (true) {
            input = scanner.nextLine();
            int response = Integer.parseInt(input);

            if(response == -1){
                lastNumber ++;
            }else {
                lastNumber --;
            }

            System.out.println(String.format("%d", lastNumber));
        }
    }
}
