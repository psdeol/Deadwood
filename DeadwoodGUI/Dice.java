import java.util.Random;
import java.util.Arrays;

public class Dice {
    private Random random;

    // constructor
    public Dice() {
        random = new Random();
    }

    // returns random int beteen 1-6
    public int roll() {
        int roll = random.nextInt(6) + 1;
        return roll;
    }

    // returns sorted int array of random numbers between 1-6
    public int[] nRolls(int numRolls) {
        int[] rolls = new int[numRolls];

        for (int i = 0; i < numRolls; i++) {
            rolls[i] = random.nextInt(6) + 1;
        }
        
        Arrays.sort(rolls);

        return rolls;
    }
}
