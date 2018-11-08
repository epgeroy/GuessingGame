import java.util.Scanner;

public class Agent1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int maxThreshold = Integer.parseInt(input);
        int lowThreshold = 0;
        int lastNumber = maxThreshold / 2;

        System.out.println(String.format("%d", lastNumber));

        while (true) {
            input = scanner.nextLine();
            int response = Integer.parseInt(input);

            if(response == -1){
                lowThreshold = lastNumber + 1;
                lastNumber = (maxThreshold + lastNumber) / 2;
            }else {
                maxThreshold = lastNumber - 1;
                lastNumber = (lastNumber + lowThreshold) / 2;
            }
            System.out.println(String.format("%d", lastNumber));
        }
    }
}
