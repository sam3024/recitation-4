import java.util.Random;

public class RPS_Player {
    private int numberOfGamesWon;
    private int numberOfGamesPlayed;
    private int choice; // 0: rock, 1: paper, 2: scissors
    private String name;
    private Random random;

    public RPS_Player(String name) {
        this.name = name;
        this.numberOfGamesWon = 0;
        this.numberOfGamesPlayed = 0;
        this.choice = -1; // No choice made initially
        this.random = new Random();
    }

    public void makeChoice(int choice) {
        if (choice < 0 || choice > 2) {
            throw new IllegalArgumentException("Choice must be 0 (rock), 1 (paper), or 2 (scissors).");
        }
        this.choice = choice;
    }

    public void clear() {
        numberOfGamesWon = 0;
        numberOfGamesPlayed = 0;
        choice = -1; // Reset choice
    }

    public RPS_Player challenge(RPS_Player anotherPlayer) {
        choice = random.nextInt(3); // Randomly choose rock, paper, or scissors
        anotherPlayer.choice = random.nextInt(3); // Opponent chooses randomly

        return determineWinner(anotherPlayer);
    }

    public RPS_Player keepAndChallenge(RPS_Player anotherPlayer) {
        if (choice == -1) {
            throw new IllegalStateException("You must make a choice before challenging.");
        }
        anotherPlayer.choice = random.nextInt(3); // Opponent chooses randomly
        return determineWinner(anotherPlayer);
    }

    private RPS_Player determineWinner(RPS_Player anotherPlayer) {
        numberOfGamesPlayed++;
        anotherPlayer.numberOfGamesPlayed++;

        // Determine the outcome
        if (this.choice == anotherPlayer.choice) {
            return null; // Draw
        } else if ((this.choice == 0 && anotherPlayer.choice == 2) || // Rock beats Scissors
                (this.choice == 1 && anotherPlayer.choice == 0) || // Paper beats Rock
                (this.choice == 2 && anotherPlayer.choice == 1)) { // Scissors beats Paper
            this.numberOfGamesWon++;
            return this; // This player wins
        } else {
            anotherPlayer.numberOfGamesWon++;
            return anotherPlayer; // Opponent wins
        }
    }

    public int getNumberOfGamesWon() {
        return numberOfGamesWon;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public double getWinPercentage() {
        if (numberOfGamesPlayed == 0) {
            return 0.0; // Avoid division by zero
        }
        return (double) numberOfGamesWon / numberOfGamesPlayed;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        RPS_Player player1 = new RPS_Player("Alice");
        RPS_Player player2 = new RPS_Player("Bob");

        // Player 1 challenges Player 2
        RPS_Player winner = player1.challenge(player2);
        if (winner == null) {
            System.out.println("It's a draw!");
        } else {
            System.out.println(winner.getName() + " wins!");
        }

        // Display statistics
        System.out.println(player1.getName() + " - Wins: " + player1.getNumberOfGamesWon() + ", Played: " + player1.getNumberOfGamesPlayed());
        System.out.println(player2.getName() + " - Wins: " + player2.getNumberOfGamesWon() + ", Played: " + player2.getNumberOfGamesPlayed());

        // Player 1 can keep and challenge another player
        winner = player1.keepAndChallenge(player2);
        if (winner == null) {
            System.out.println("It's a draw again!");
        } else {
            System.out.println(winner.getName() + " wins again!");
        }
    }
}

