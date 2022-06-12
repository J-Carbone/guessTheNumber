package guessthenumber;

import java.text.ChoiceFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class main {
    static ChoiceFormat guess = new ChoiceFormat("1# guess| 1< guesses");
    static public Random r = new Random();
    static public String name;
    public static void main(String[] args) {
        Scanner p = new Scanner(System.in);
        System.out.println("Hello! What is your name?");
        name = p.next();
        playGame();
    }
    public static void playGame() {
        Scanner p = new Scanner(System.in);
        int parsedInt;
        int randomNum = r.nextInt(20);
        System.out.println("Any character or number outside 1-20 will consume a guess");
        System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.");
        for (int i = 0; i<=5; i++) {
            System.out.println("Take a guess.");
            try {
                parsedInt = p.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please only type numbers!");
                System.out.println("You have " + (5-i) + " guesses left!");
                continue;
            }
            if (parsedInt < 1 || parsedInt > 20) {
                System.out.println("Please only type within 1-20!");
                System.out.println("You have " + (5-i) + " guesses left!");
                continue;
            }
            if (parsedInt > randomNum) {
                System.out.println("Your guess is too high.");
            }
            else if (parsedInt < randomNum) {
                System.out.println("Your guess is too low.");
            } else {
                System.out.println("Good job, " + name + "! You guessed my number in " + (i+1) + main.guess.format(i+1) + "!");
                break;
            }
            System.out.println("You have " + (5-i) + " guesses left!");
            if (i == 5) System.out.println(name + " is a failure! The number was " + randomNum);
        }
        System.out.println("Press 'Y' to play again otherwise any key to exit");
        String answer = p.next();
        if(answer.toLowerCase().startsWith("y")) playGame();
    }
}
