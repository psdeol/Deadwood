import java.util.ArrayList;

public class PaymentManager {
    public PaymentManager() {}

    public void payBonuses(Room room) {
        ArrayList<Player> playersOnCard = new ArrayList<Player>();
        ArrayList<Player> playersOffCard = new ArrayList<Player>();
        ArrayList<Role> rolesOnCard = room.getCurrentScene().getRoles();
        ArrayList<Role> rolesOffCard = room.getExtraRoles();
        Dice dice = new Dice();
        int[] budgetRoll = dice.nRolls(room.getCurrentScene().getBudget());

        System.out.print("Distributing $[");
        for (int i = 0; i < budgetRoll.length; i++) { 
            if (i+1 < budgetRoll.length) System.out.print(budgetRoll[i] + ", ");
            else System.out.print(budgetRoll[i]);
        }
        System.out.println("] to actors.");


        for (Role r : rolesOnCard) {
            if (r.isTaken()) {
                for (Player p : room.getPlayersInRoom()) {
                    if (room.getCurrentScene().roleOnCard(p.getRole()) && p.getRole() == r) {
                        playersOnCard.add(p);
                    }
                }
            }
        }

        for (Role r : rolesOffCard) {
            if (r.isTaken()) {
                for (Player p : room.getPlayersInRoom()) {
                    if (p.getRole() == r) {
                        playersOffCard.add(p);
                    }
                }
            }
        }

        // distribute bonues
        if (playersOnCard.size() > 0) {
            System.out.println("Bonuses for on card actors:");

            int pos = playersOnCard.size() - 1;
            for (int i = budgetRoll.length-1; i >= 0; i--) {
                if (pos < 0) pos = playersOnCard.size() - 1;
                
                playersOnCard.get(pos).addDollars(budgetRoll[i]);
                System.out.println("\t$$$ Player " + playersOnCard.get(pos).getID() + "(role rank: " + playersOnCard.get(pos).getRole().getRank() + ") awarded " + budgetRoll[i] + " dollars $$$");
                
                pos--;
            }

            // off card roles only get paid if there was at least one main role
            if (playersOffCard.size() > 0) {
                System.out.println("Bonuses for off card actors.");
                for (Player p : playersOffCard) {
                    p.addDollars(p.getRole().getRank());
                    System.out.println("\t$$$ Player " + p.getID() + "(role ranke: " + p.getRole().getRank() + " awarded " + p.getRole().getRank() + " dollars $$$");
                }
    
            }
        
        } else System.out.println("There were no card actors. No bonuses dirtibuted.");
    }

    public void payActAction(Player player, boolean success) {
        Role role = player.getRole();

        if (role.isExtra()) {
            if (success) {
                System.out.println("$$$ Player " + player.getID() + "(role rank: " + player.getRole().getRank() + ") awarded 1 credit and 1 dollar $$$");
                player.addCredits(1);
                player.addDollars(1);
            } else {
                System.out.println("$$$ Player " + player.getID() + "(role rank: " + player.getRole().getRank() + ") awarded 1 dollar $$$");
                player.addDollars(1);
            }
        } else {
            if (success) {
                System.out.println("$$$ Player " + player.getID() + "(role rank: " + player.getRole().getRank() + ") awarded 2 credits $$$");
                player.addCredits(2);
            }
        }
    }
}
