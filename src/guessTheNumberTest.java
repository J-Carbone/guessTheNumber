import guessthenumber.main;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class mockRandom extends Random {
    @Override
    public int nextInt(int bound) {
        return 5;
    }
}

class guessTheNumberTests {
    private void setInput(String data) {
        ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }
    private ByteArrayOutputStream getOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        return output;
    }

    private boolean containsOnce(String a, String b) {
        Pattern pattern = Pattern.compile(b);
        Matcher matcher = pattern.matcher(a);
        return matcher.find() && !matcher.find();
    }

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @After
    public void afterEach() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    @DisplayName("Checking for user's name")
    void testNameIsCorrect() {
        final String expectedName = "Flake";
        this.setInput(expectedName + "\n");
        try {
            main.main(new String[0]);
        } catch (Exception e) {

        }
        assertEquals(expectedName, main.name);
    }

    @Test
    @DisplayName("Checks numbers below Five")
    void testGuessBelowFive() {
        this.setInput("0\n1\n2\n3\n4\n4\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Your guess is too low."));
    }

    @Test
    @DisplayName("Checks numbers above Five")
    void testGuessAboveFive() {
        this.setInput("6\n7\n8\n9\n10\n11\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Your guess is too high."));
    }

    @Test
    @DisplayName("Checks when number equals Five")
    void testGuessOfFive() {
        this.setInput("5\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Good job, "));
    }

    @Test
    @DisplayName("Checks lower boundary is consuming guesses")
    void testLowerBoundary() {
        this.setInput("0\n0\n0\n0\n0\n0\n0\n5\nq");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Please only type within 1-20!"));
    }

    @Test
    @DisplayName("Checks upper boundary is consuming guesses")
    void testUpperBoundary() {
        this.setInput("21\n21\n21\n21\n21\n21\n21\n5\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Please only type within 1-20!"));
    }

    @Test
    @DisplayName("Checks for characters that are letters")
    void testGuessIsAlphaChar() {
        this.setInput("a\nb\nc\nd\ne\nf\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Please only type numbers!"));
    }

    @Test
    @DisplayName("Plural checking")
    void testGuessOfFiveAfterOne() {
        this.setInput("1\n5\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("2 guesses!"));
    }

    @Test
    @DisplayName("Singular checking")
    void testGuessOfFiveFirstTry() {
        this.setInput("5\nn");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        main.playGame();
        String output = outputStream.toString();
        assertTrue(output.contains("1 guess!"));
    }

    @Test
    @DisplayName("Checks if any character but 'Y' is sent")
    void testAnythingButY() {
        this.setInput("5\nh");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        try {
            main.playGame();
        } catch (Exception e){

        }
        String output = outputStream.toString();
        assertTrue(this.containsOnce(output,"Any character or number outside 1-20 will consume a guess"));
    }

    @Test
    @DisplayName("Checks if character 'Y' is sent")
    void testY() {
        this.setInput("5\ny");
        ByteArrayOutputStream outputStream = this.getOutput();
        main.r = new mockRandom();
        main.name = "sure";
        try {
            main.playGame();
        } catch (Exception e){

        }
        String output = outputStream.toString();
        assertFalse(this.containsOnce(output,"Any character or number outside 1-20 will consume a guess"));
    }

}