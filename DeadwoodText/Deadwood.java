public class Deadwood{

    public static int numDays = 3;
    public static int currDay = 1;
    public static int numPlayers;
    public static Board board = new Board();
    public static Room startingRoom = board.trailers;
    public static Room upgradeRoom = board.office;
    public static Player[] players;
    public static Display display = new Display();
    public static InputManager input = new InputManager();

    public static void main(String[] args) {
      
        display.displayRules(numDays);

        // input for player amount
        System.out.print("Please enter the amount of players: ");
        numPlayers = input.newIntInput(2, 8);

        // initialize all players considering special rules
        players = initializePlayers(numPlayers);
        while (currDay <= numDays) {
            System.out.println();

            while (board.getRoomsRemaining() > 1) {

                Player currentPlayer = findNextPlayer(players);
                display.displayPlayerStats(currentPlayer);
                currentPlayer.takeTurn();
                System.out.println();
            }

            endDay(currDay++, players);
        }

        System.out.println("~~~~~~~~~~~~~~\n\tEnd of game\n~~~~~~~~~~~~~~");
        calcScores(players);
    }

    public static void endDay(int day, Player[] players){
        System.out.println("~~~~~~~\n\tEnd of day " + day);

        //send all players back to starting room
        for (int i = 0; i < players.length; i++){
            players[i].setRoom(startingRoom);
            players[i].setRole(null);
        }

        if (day+1 <= numDays){
            board.resetBoard();
            System.out.println("All players returned to the Trailer");
            display.displayBoard(players, board);
        }

        System.out.println("\t~~~~~~~");
    }

    // calculate and display player scores
    private static void calcScores(Player[] players) {
        int scores[] = new int[players.length];
        System.out.println("~~~~~~~\nScores:");

        for (Player p : players) {
            scores[p.getID()] = (p.getRank() * 5) + p.getCredits() + p.getDollars();
            System.out.println("\tPlayer " + p.getID() + ": " + scores[p.getID()] + " points");
        }
    }

    public static Player[] initializePlayers(int count){

        Player[] players = new Player[count];
        for (int i = 0; i < players.length; i++){
            players[i] = new Player(i+1, startingRoom);     //initialize player id to i and set in trailer room
        }

        //consider special rules
        switch(count){
            case 2:
            case 3:
                numDays = 3;

                for (int i = 0; i < players.length; i++) {
                    players[i].setRank(2);
                    players[i].addDollars(30);
                    players[i].addCredits(18);
                }

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

    /*
    **  Cycles through all players, returns the first player who has not yet had their turn this cycle.
    **  If all players have taken their turn during this cycle, reset the cycle and return the first player.
    */
    public static Player findNextPlayer(Player[] playerArr){
        for(Player p: playerArr){
            if (p.myTurn){
                return p;   //next player to take their turn
            }
        }
        //new turn cycle
        display.displayBoard(playerArr, board);

        //reset player turn cycle
        for(Player p: playerArr){
            p.myTurn = true;
        }
        return findNextPlayer(playerArr);
    }
}
