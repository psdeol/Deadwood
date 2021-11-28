import java.util.ArrayList;

public class Action{
    PaymentManager pay = new PaymentManager();
    InputManager input = new InputManager();
    Display display = new Display();
    Player player;
    Room currentRoom;
    ArrayList<Room> adjRooms;

    public Action(Player player) {
        this.player = player;
        this.currentRoom = player.getRoom();
        this.adjRooms = currentRoom.getAdjacentRooms();
    }

    public void move() {
        if (currentRoom != null && adjRooms != null) {
            System.out.print("\t");
            for (int i = 0; i < adjRooms.size(); i++)
                System.out.print((i+1) + ". " + adjRooms.get(i).getRoomName() + "   ");

            System.out.println("0. Cancel Move");
            System.out.print("Enter the number of a room to move to: ");

            int choice = input.newIntInput(0, adjRooms.size());
            Room nextRoom;  

            if (choice <= 0 || choice > adjRooms.size()) nextRoom = null;
            else nextRoom = adjRooms.get(choice - 1);
            
            if (nextRoom != null){
                System.out.println("Successfully moved from " + currentRoom.getRoomName() + " to " + nextRoom.getRoomName() + ".\t");
                currentRoom.removePlayer(player);
                nextRoom.addPlayer(player);
                player.setRoom(nextRoom);
                player.validTurn = true;
            } else System.out.println("Move canceled.");
        }
    }

    public void takeRole() {
        currentRoom = player.getRoom();

        if (!currentRoom.isCompleted() && !currentRoom.getRoomName().equals("trailer") && !currentRoom.getRoomName().equals("office")) {
            ArrayList<Role> availableRoles = currentRoom.getAvailableRoles();
            ArrayList<Role> extraRoles = currentRoom.getExtraRoles();
            ArrayList<Role> sceneRoles = currentRoom.getSceneRoles();
            int count = 0;

            System.out.println("Available Roles:");
            System.out.print("\tExtra Roles:");
            for (int i = 0; i < extraRoles.size(); i++) {
                Role role = extraRoles.get(i);
                if (availableRoles.contains(role)) {
                    count++;
                    System.out.print("   " + (count) + ". " + role.getTitle() + "(r: " + role.getRank() + ")");
                }
            }
            System.out.print("\n\tMain Roles:");
            for (int i = 0; i < sceneRoles.size(); i++) {
                Role role = sceneRoles.get(i);
                if (availableRoles.contains(role)) {
                    count++;
                    System.out.print("   " + (count) + ". " + role.getTitle() + "(r: " + role.getRank() + ")");
                }
            }
            if (count == 0) {
                System.out.println("No roles available in this room.");
                return;
            } else {
                System.out.print("   0. CANCEL");
                System.out.print("\nEnter the number of a role to choose: ");
            }

            int choice = input.newIntInput(0, availableRoles.size());
            Role role;

            if (choice > 0 && choice <= availableRoles.size()) {
                if (availableRoles.get(choice - 1).getRank() > player.getRank()) {
                    System.out.println("You are not good enough for a rank " + availableRoles.get(choice - 1).getRank() + " role yet.");
                    role = null;
                } else role = availableRoles.get(choice - 1);
            } else role = null;

            if (role != null) {
                role.takeRole();
                player.setRole(role);
                player.validTurn = true;
            }

        } else System.out.println("This room has no available roles");
    }

    public void act(){
         if (currentRoom != null && !currentRoom.isCompleted()) {
            Role currentRole = player.getRole();

            if (currentRole == null) System.out.println("No active role. Take a role to act.");
            else {    
                Dice dice = new Dice();
                int roll = dice.roll();
                boolean success = ((roll + player.getRehearsalsDone()) >= currentRoom.getCurrentScene().getBudget());

                System.out.println("Need to roll a " + (currentRoom.getCurrentScene().getBudget() - player.getRehearsalsDone()));
                System.out.println("Rolled a " + roll);

                if (success) {
                    System.out.println("Successfully acts as " + currentRole.getTitle() + " [" + currentRole.getQuote() + "]");
                    currentRoom.completeShot();
                } else System.out.print("Unsuccessfully acts as " + currentRole.getTitle() + " [\"...Line?\", the director shakes their head.]");

                pay.payActAction(player, success);

                if (currentRoom.getShotsRemaining() <= 0) Deadwood.board.completeRoom(currentRoom);
                
                player.validTurn = true;
            }

        } else System.out.println("This room is not filming.");
    }
    
    public void rehearse() {
        if (currentRoom != null && !currentRoom.isCompleted()) {
            Role currentRole = player.getRole();

            if (currentRole == null) System.out.println("No active role. Take a role to rehearse.");
            else {
                if (player.getRehearsalsDone() < currentRoom.getCurrentScene().getBudget()) {
                    System.out.println("Rehearsal successful");
                    player.addRehearsal();
                    player.validTurn = true;
                } else System.out.println("Can't rehearse anymore, you must act.");
            }

        } else System.out.println("This room is not filming.");
    }

    public void upgrade() {
        if (currentRoom.getRoomName().equals("office")) {
            CastingOffice office = new CastingOffice();

            display.displayUpgrades();
            System.out.println("Enter the rank you want to upgrade to (0 or 1 to cancel).");
            int rank = input.newIntInput(0, 6);

            if (rank == 0 || rank == 1) return;
            if (rank == player.getRank()) {
                System.out.println("You are already this rank.");
                return;
            }

            if (office.checkValidCredits(player, rank) || office.checkValidDollars(player, rank)) {
                System.out.println("Select the currency to use:");
                System.out.println("\t1. Dollars   2. Credits   0. Cancel");
                int currency = input.newIntInput(0, 2);

                if (currency == 1) {
                    if (office.checkValidDollars(player, rank)) {
                        player.setRank(rank);
                        player.removeDollars(office.getDollarPrice(rank));
                        System.out.println("You are now rank " + rank + ".");
                    } else System.out.println("You don't have enough money to upgrade to this rank.");
                } else if (currency == 2) {
                    if (office.checkValidCredits(player, rank)) {
                        player.setRank(rank);
                        player.removeCredits(office.getCreditPrice(rank));
                        System.out.println("You are now rank " + rank + ".");
                    } else System.out.println("You don't have enough credits to upgrade to this rank.");
                } else return;
            } else System.out.println("You have insufficient funds to upgrade to this rank.");
        } else System.out.println("Must be in casting office to upgrade rank.");
    }
}
