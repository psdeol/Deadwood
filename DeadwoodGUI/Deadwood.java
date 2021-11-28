import javax.swing.JOptionPane;

public class Deadwood{

    public static int numDays = 4;
    public static int currDay = 1;
    public static int numPlayers = 0;
    public static Board board = new Board();
    public static Player currentPlayer;
    public static Player[] players;
    public static BoardFrame boardFrame;

    public static void main(String[] args) {
        boardFrame = BoardFrame.getInstance();

        while (numPlayers == 0) {
            getNumPlayers();
        }
        
        players = initializePlayers(numPlayers);
        findNextPlayer();

        boardFrame.setVisible(true);
        boardFrame.setPlayerDice(players);
        boardFrame.updatePlayerStats();
    }

    // gets the number of players
    public static void getNumPlayers() {
        String input = JOptionPane.showInputDialog(boardFrame, "Enter the number of players (2-8)");

        if (input == null) System.exit(0);

        try{
            int num = Integer.parseInt(input);

            if (2 <= num && num <= 8)  numPlayers = num;
            
        } catch (Exception e) {
            System.out.println("Enter a integer between 2-8.");
        }
    }

    // resets board and increments currDay
    public static void endDay(){
        currDay++;

        for (int i = 0; i < players.length; i++){
            players[i].setRoom(board.trailers);
            players[i].setRole(null);
        }

        if (currDay <= numDays) board.resetBoard();
    }

    // returns a string with each players score and who won
    public static String getScoreString() {
        Player winner = findWinner();
        String str = "GAME OVER\n\n";

        for (Player p : players) {
            str += "Player " + p.getID() + " (r: " + p.getRank() + ", d: " + p.getDollars() + ", c: " + p.getCredits() + ")  score: " + p.score + "\n"; 
        }

        str += "\nWINNER: Player " + winner.getID();

        return str;
    }

    // finds the player with the highes score
    public static Player findWinner() {
        Player winner = players[0];

        for (int i = 0; i < players.length; i++) {
            if (players[i].score > winner.score) {
                winner = players[i];
            }
        }

        return winner;
    }

    // calculates the scores for each player
    public static void calcScores() {
        for (Player p : players) {
            p.score = (p.getRank() * 5) + p.getCredits() + p.getDollars();
        }
    }

    // intializes the players depending on how many there are
    public static Player[] initializePlayers(int count){
        Player[] players = new Player[count];
        for (int i = 0; i < players.length; i++)
            players[i] = new Player(i+1, board.trailers); 
        
        switch(count){
            case 2:
            case 3:
                numDays = 3;
                break;
            case 5:
                for (int i = 0; i < players.length; i++)
                    players[i].setCredits(2);
                break;
            case 6:
                for (int i = 0; i < players.length; i++)
                    players[i].setCredits(4);
                break;
            case 7:
            case 8:
                for (int i = 0; i < players.length; i++)
                    players[i].setRank(2);
                break;
        }

        return players;
    }

    // updates current player to next player
    public static void findNextPlayer(){
        for (Player p: players) {
            if (p.myTurn){
                currentPlayer = p;
                boardFrame.updateMessage("<html><p>Player " + currentPlayer.getID() + "'s turn.</p></html>");
                System.out.println("\nCurrent Player: Player " + currentPlayer.getID());
                return;
            }
        }

        for (Player p: players)
            p.myTurn = true;
        
        findNextPlayer();
    }
}
