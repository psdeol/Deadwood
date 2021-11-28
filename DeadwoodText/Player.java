public class Player {
    private int id = 0;
    private int rank = 1;
    private int credits = 0;
    private int dollars = 0;
    private int rehearsalsDone = 0;
	private Room room;
    private Role role;
    
    public boolean validTurn;
    public boolean myTurn;

    public Player(int id, Room room){
        this.id = id;
        this.room = room;
    }
    public int getID() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public int getDollars() {
        return dollars;
    }

    public int getCredits() {
        return credits;
    }

    public int getRehearsalsDone() {
        return rehearsalsDone;
    }

    public Room getRoom() {
        return room;
    }

    public Role getRole() {
        return role;
    }


    
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public void addDollars(int amount) {
        dollars += amount;
    }

    public void removeDollars(int amount) {
        dollars -= amount;
    }

    public void addCredits(int amount) {
        credits += amount;
    }

    public void removeCredits(int amount) {
        credits -= amount;
    }

    public void addRehearsal() {
        rehearsalsDone++;
    }

    public void resetRehearsals() {
        rehearsalsDone = 0;
    }



    public void takeTurn(){
        InputManager input = new InputManager();
        Action action = new Action(this);
        validTurn = false;

        while (!validTurn){
            System.out.println("\t1. Move   2. Take Role   3. Act   4. Rehearse   5. Upgrade   6. More Info   0. Skip Turn");
            System.out.print("Enter the number of the action you wish to take: ");
            int choice = input.newIntInput(0, 6);

            switch(choice){
                case 1:
                    //move
                    if (role == null){
                        action.move();
                        action.takeRole();
                    }
                    else System.out.println("Cannot move while filming a scene.");
                    break;

                case 2:
                    if (role == null) action.takeRole();
                    else System.out.println("You already have a role.");
                    break;

                case 3:
                    //act
                    action.act();
                    break;
                case 4:
                    //rehearse
                    action.rehearse();
                    break;
                case 5:
                    //upgrade
                    action.upgrade();
                    break;
                case 6:
                    //display something
                    moreInfo();
                    break;
                default:
                    //skip
                    validTurn = true;
                    break;
            }
        }

        validTurn = false;
        myTurn = false;
        System.out.println("End of turn.");
    }

    private void moreInfo(){
        System.out.println("\t1. Room Info   2. Player Status   3. Rules   0. Cancel");
        System.out.print("Enter the number of the action you wish to take: ");
        InputManager input = new InputManager();
        int choice = input.newIntInput(0, 3);
        Display display = new Display();
        switch(choice){
            case 1:
                display.displayRoomInfo(room);
                break;
            case 2:
                display.displayPlayerStats(this);
                break;
            case 3:
                display.displayRules(Deadwood.numDays);
                break;
            default:
                break;
        }
    }
}
