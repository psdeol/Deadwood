public class Player {
    private int id = 0;
    private int rank = 1;
    private int credits = 0;
    private int dollars = 0;
    private int rehearsalsDone = 0;
	private Room room;
    private Role role;
    private String[] diceImgs;
    
    public boolean validTurn;
    public boolean myTurn;
    public int score;
    public String currDiceImg;
    public String diceColor;

    // constructor
    public Player(int id, Room room){
        this.id = id;
        this.room = room;
    }

    // retunrs player's ID
    public int getID() {
        return id;
    }

    // returns player's rank
    public int getRank() {
        return rank;
    }

    // returns player's dollars
    public int getDollars() {
        return dollars;
    }

    // returns player's credits
    public int getCredits() {
        return credits;
    }

    // returns rehearsals done
    public int getRehearsalsDone() {
        return rehearsalsDone;
    }

    // returns player's room
    public Room getRoom() {
        return room;
    }

    // returns player's role
    public Role getRole() {
        return role;
    }

    // returns string array of names of dice images
    public String[] getDiceImgs() {
        return diceImgs;
    }

    // sets rank to input rank
    public void setRank(int rank) {
        this.rank = rank;
    }

    // sets dollars to input amount
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    // sets credits to input amount
    public void setCredits(int credits) {
        this.credits = credits;
    }

    // sets player's room to input room
    public void setRoom(Room room) {
        this.room = room;

        if (room.roomVisited == false) {
            room.roomVisited = true;
        }
    }

    // sets player's role to input role
    public void setRole(Role role) {
        this.role = role;
        if (role != null) role.takeRole();
    }

    // sets diceImgs to input array
    public void setDiceImgs(String[] diceImgs) {
        this.diceImgs = diceImgs;
    }

    // adds input amount of dollars
    public void addDollars(int amount) {
        dollars += amount;
    }

    // removes input amount of dollars
    public void removeDollars(int amount) {
        dollars -= amount;
    }

    // adds input amount of credits
    public void addCredits(int amount) {
        credits += amount;
    }

    // removes input amout of credits
    public void removeCredits(int amount) {
        credits -= amount;
    }

    // adds 1 to rehearsals
    public void addRehearsal() {
        rehearsalsDone++;
    }

    // sets rehearsals to 0
    public void resetRehearsals() {
        rehearsalsDone = 0;
    }
}
