import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class main {
    static Scanner p = new Scanner(System.in);
    static Random r = new Random();
    static String name;
    public static void main(String[] args) {
        System.out.println("Hello! What is your name?");
        name = p.next();
        playGame();
    }
    public static void playGame() {
        int parsedInt;
        int randomNum = r.nextInt(20);
        System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.");
        for (int i = 0; i<=5; i++) {
            System.out.println("Take a guess.");
            String guess = p.next();
            try {
                parsedInt = Integer.parseInt(guess);
            } catch (NumberFormatException e) {
                parsedInt = -1;
                System.out.println("Please only type numbers!");
            }
            if (parsedInt > randomNum) System.out.println("Your guess is too high.");
            else if (parsedInt < randomNum) {
                System.out.println("Your guess is too low.");
            } else {
                System.out.println("Good job," + name + "! You guessed my number in " + (i+1) + " guesses!");
                break;
            }
            if (i == 5) System.out.println(name + " is a failure! The number was " + randomNum);
        }
        System.out.println("Would you like to play again? (y or n)");
        String answer = p.next();
        if(answer.toLowerCase().startsWith("y")) playGame();
    }
}
