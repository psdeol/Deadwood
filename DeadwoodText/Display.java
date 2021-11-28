import java.util.ArrayList;

public class Display{
    
    public Display() {}

    public void displayUpgrades() {
        CastingOffice office = new CastingOffice();
        System.out.println("\tRANK   DOLLARS   CREDITS");
        for (int i = 2; i < 7; i++) {
            if (i == 2) System.out.println(("\t " + i + "  ") + "   " + ("   " + office.getDollarPrice(i) + "   ") + "   " + ("   " + office.getCreditPrice(i)) + "  ");
            else System.out.println(("\t " + i + "  ") + "   " + ("  " + office.getDollarPrice(i) + "   ") + "   " + ("  " + office.getCreditPrice(i)) + "  ");
        }
    }

    public void displayPlayerStats(Player player) {
        if (player.getRoom() == null){
            System.out.println("Player " + player.getID() + ": (rank=" + player.getRank() + ", dollars=" + player.getDollars() + ", credits=" + player.getCredits() + ")");
            return;
        }
        if (player.getRole()== null){
            System.out.println("Player " + player.getID() + ": (rank=" + player.getRank() + ", dollars=" + player.getDollars() + ", credits=" + player.getCredits() + ") Room = " + player.getRoom());
            return;
        }
        System.out.println("Player " + player.getID() + ": (rank=" + player.getRank() + ", dollars=" + player.getDollars() + ", credits=" + player.getCredits() + ", rehearsals=" + player.getRehearsalsDone() + ") Room = " + player.getRoom() + ", Role = " + player.getRole());
    }

    // displays the scene info in current room
    public void displayRoomInfo(Room room){
        if (!room.isCompleted() && room.getShotsRemaining() > 0){
            ArrayList<Role> cardRoles = room.getCurrentScene().getRoles();
            int descLength = room.getCurrentScene().getDescription().length();
            String hrLine = "";
            String blankSpace = "";
            for (int i = 0; i < descLength + 10; i++){
                hrLine += "_";
                blankSpace += " ";
            }
            ArrayList<Role> extraRoles = room.getExtraRoles();
            String extrasRolePrint = "";
            for (int i = 0; i < extraRoles.size(); i++){
                extrasRolePrint += "|    Role " + (i+1) + ": " + extraRoles.get(i) + "\n";
            }
            System.out.println("                 ~~~~~ Room Info ~~~~~\n" +
                                " ____"  +  hrLine  +  "____\n" +
                                "|    " + blankSpace + "    |\n" +
                                "|    Title: " + room.getRoomName() + "   " + room.getShotsRemaining() + " shots remain" + blankSpace.substring(23+ room.getRoomName().length()) + "   |\n" +
                                "|    " + blankSpace + "    |\n" + extrasRolePrint +
                                "|    "  +  hrLine  +  "    |\n" +
                                "|   |" + blankSpace + "|   |\n" +
                                "|   |  Budget: $" + room.getCurrentScene().getBudget() + "M   Title: " + room.getCurrentScene().getTitle() + blankSpace.substring(23+ room.getCurrentScene().getTitle().length()) + "|   |\n" +
                                "|   |  Des: " + room.getCurrentScene().getDescription() + "   |   |\n" +
                                "|   |" + blankSpace + "|   |\n" +
                                "|   |  Role 1: " + ((cardRoles.size() >= 1 && cardRoles.get(0) != null) ? cardRoles.get(0) : "empty" ) + blankSpace.substring((10+((cardRoles.size() >= 1 && cardRoles.get(0) != null) ? ("" + cardRoles.get(0)).length() : 5 ))) + "|   |\n" +
                                "|   |  Role 2: " + ((cardRoles.size() >= 2 && cardRoles.get(1) != null) ? cardRoles.get(1) : "empty" ) + blankSpace.substring((10+((cardRoles.size() >= 2 && cardRoles.get(1) != null) ? ("" + cardRoles.get(1)).length() : 5 ))) + "|   |\n" +
                                "|   |  Role 3: " + ((cardRoles.size() >= 3 && cardRoles.get(2) != null) ? cardRoles.get(2) : "empty" ) + blankSpace.substring((10+((cardRoles.size() >= 3 && cardRoles.get(2) != null) ? ("" + cardRoles.get(2)).length() : 5 ))) + "|   |\n" +
                                "|   |" + hrLine + "|   |\n" +
                                "|____" + hrLine + "____|");
        }
        else{
            int nameLength = room.getRoomName().length();
            String hrLine = "";
            String blankSpace = "";
            for (int i = 0; i < nameLength + 4; i++){
                hrLine += "_";
                blankSpace += " ";
            }
            System.out.println("  ~~~~~ Room Info ~~~~~\n" +
                                " _" + hrLine + "_ \n" +
                                "| " + blankSpace + " |\n" +
                                "|   " + room.getRoomName() + "   |  *NOT FILMING*\n" +
                                "|_" + hrLine + "_|");
        }
    }

    public void displayBoard(Player[] p, Board board){
        //display board with dynamic role info
        String ts = board.trainStation.getShotsRemaining() > 0 ? board.trainStation.getShotsRemaining() + ":1,1,2,4" : "  ended  " ;
        String j = board.jail.getShotsRemaining() > 0 ? board.jail.getShotsRemaining() + ":2,3" : "ended" ;
        String ms = board.mainStreet.getShotsRemaining() > 0 ? board.mainStreet.getShotsRemaining() + ":1,2,2,4" : "  ended  " ;
        String g = board.generalStore.getShotsRemaining() > 0 ? board.generalStore.getShotsRemaining() + ":1,3" : "ended" ;
        String s = board.saloon.getShotsRemaining() > 0 ? board.saloon.getShotsRemaining() + ":1,2" : "ended" ;
        String r = board.ranch.getShotsRemaining() > 0 ? board.ranch.getShotsRemaining() + ":1,2,3" : " ended " ;
        String b = board.bank.getShotsRemaining() > 0 ? board.bank.getShotsRemaining() + ":2,3" : "ended" ;
        String h = board.hotel.getShotsRemaining() > 0 ? board.hotel.getShotsRemaining() + ":1,1,2,3" : "  ended  " ;
        String c = board.church.getShotsRemaining() > 0 ? board.church.getShotsRemaining() + ":1,2" : "ended" ;
        String sh = board.secretHideout.getShotsRemaining() > 0 ? board.secretHideout.getShotsRemaining() + ":1,2,3,4" : "  ended  " ;

        System.out.println("   ~~~~~ Deadwood Board ~~~~~\n" +
                            " _________ _________ __________________\n" +
                            "|         |  Jail   |   Main Street    |" + ((p.length >= 1 && p[0] != null) ? "  Player 1 is at " + p[0].getRoom() + ((p[0].getRole() != null) ? " acting as " + p[0].getRole() : "" ) : "" ) + "\n" +
                            "|  Train     "+j+"       " + ms +"     |" + ((p.length >= 2 && p[1] != null) ? "  Player 2 is at " + p[1].getRoom() + ((p[1].getRole() != null) ? " acting as " + p[1].getRole() : "" ) : "" ) + "\n" +
                            "| Station |____   __|__   ___ ____   __|" + ((p.length >= 3 && p[2] != null) ? "  Player 3 is at " + p[2].getRoom() + ((p[2].getRole() != null) ? " acting as " + p[2].getRole() : "" ) : "" ) + "\n" +
                            "|" + ts +"| General |        |         |" + ((p.length >= 4 && p[3] != null) ? "  Player 4 is at " + p[3].getRoom() + ((p[3].getRole() != null) ? " acting as " + p[3].getRole() : "" ) : "" ) + "\n" +
                            "|            Store    Saloon   Trailer |" + ((p.length >= 5 && p[4] != null) ? "  Player 5 is at " + p[4].getRoom() + ((p[4].getRole() != null) ? " acting as " + p[4].getRole() : "" ) : "" ) + "\n" +
                            "|            "+g+"    "+s+"    *start* |" + ((p.length >= 6 && p[5] != null) ? "  Player 6 is at " + p[5].getRoom() + ((p[5].getRole() != null) ? " acting as " + p[5].getRole() : "" ) : "" ) + "\n" +
                            "|___   ___|____   __|__   ___|___   ___|" + ((p.length >= 7 && p[6] != null) ? "  Player 7 is at " + p[6].getRoom() + ((p[6].getRole() != null) ? " acting as " + p[6].getRole() : "" ) : "" ) + "\n" +
                            "| Office  |         |        |         |" + ((p.length >= 8 && p[7] != null) ? "  Player 8 is at " + p[7].getRoom() + ((p[7].getRole() != null) ? " acting as " + p[7].getRole() : "" ) : "" ) + "\n" +
                            "| 2=4:5                                |\n" +
                            "| 3=10:10    Ranch     Bank            |   ___Key:_________\n" +
                            "| 4=18:15 | "+ r +" |  "+b+" |         |  |shotsLeft:      |\n" +
                            "| 5=28:20 |         |        |  Hotel  |  |  role1rank,    |\n" +
                            "| 6=40:25 |         |__   ___|" + h + "|  |  role2rank     |\n" +
                            "|__   ____|____   __|        |         |  |                |\n" +
                            "|  Secret Hideout   | Church |         |  |rankUpgrade=    |\n" +
                            "|    " + sh +"        "+c+"            |  | dollars:credit |\n" +
                            "|___________________|________|_________|  |________________|\n");

    }

    public void displayRules(int totalDays) {
        System.out.println("\nWelcome to Deadwood! Play with 2 to 8 players! \n"
                + "In this game, you must gain dollars, credits, and rank.\n"
                + "Higher rank will allow players to have more roles in a film to choose from.\n"
                + "\tTake a role by moving to an active room, or by deciding to Take a Role, then choosing a role with a rank less than or equal to your own.\n"
                + "Roles for extras will pay $1k and 1 credit when done correctly, but $1k only when a role is failed, and no bonus when a film wraps.\n"
                + "Roles on the card of a room will pay $2k when done correctly, nothing when a role is failed, and a distributed budget of a film when it wraps.\n"
                + "\tFor example, a film with budget=4M with 2 card actor spots will randomize 4 numbers from 1 to 6.\n"
                + "\t\tFor example, when rolling [6, 4, 3, 1], will give the top ranking role $6k + $3k, and the next ranking role gets $4k + $1k.\n"
                + "After " + totalDays + " days, you will be scored by: (rank * 5) + dollars + credits.\n"
                + "\tFor example, a player with (rank=3, dollars=20, credits=12) has 15+20+12 = 47 points!\n"
                + "Be careful taking higher ranking roles after upgrading at the Office, because the rank you take will have to be rolled or exceeded with a 6-sided dice.\n"
                + "This can be made easier by rehearsing at the start of a difficult role which lessens the required roll by 1 each time and lasts throughout the role.\n"
                + "Remember that you can always select 0 to cancel or to skip your turn. Good luck!\n");
    }
}