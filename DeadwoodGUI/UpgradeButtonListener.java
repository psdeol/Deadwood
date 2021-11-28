import java.awt.event.ActionListener;
import java.awt.event.*;

public class UpgradeButtonListener implements ActionListener{
    public BoardFrame frame;
    
    // constructor
    public UpgradeButtonListener(BoardFrame frame) {
        this.frame = frame;
    }

    // executes the correct action depending on 
    // which button in frame is pressed
    public void actionPerformed(ActionEvent e) {
        CastingOffice office = new CastingOffice();
        Player player = Deadwood.currentPlayer;

        // upgrade canceled
        if (e.getSource() == frame.cancelUpgrade) {
            frame.showUpgradeButtons(false);
            frame.showActionButtons(true);
            return;
        }

        // rank 2 using dollars button
        if (e.getSource() == frame.r2D) {
            if (player.getRank() != 2 && office.checkValidDollars(player, 2)) {
                player.setRank(2);
                player.removeDollars(office.getDollarPrice(2));
                frame.upgradePlayer(player.getID()-1, 2);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2 using " + office.getDollarPrice(2) + " dollars.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 3 using dollars button
        } else if (e.getSource() == frame.r3D) {
            if (player.getRank() != 3 && office.checkValidDollars(player, 3)) {
                player.setRank(3);
                player.removeDollars(office.getDollarPrice(3));
                frame.upgradePlayer(player.getID() - 1, 3);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getDollarPrice(3) + " dollars.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 4 using dollars button
        } else if (e.getSource() == frame.r4D) {
            if (player.getRank() != 4 && office.checkValidDollars(player, 4)) {
                player.setRank(4);
                player.removeDollars(office.getDollarPrice(4));
                frame.upgradePlayer(player.getID() - 1, 4);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getDollarPrice(4) + " dollars.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 5 using dollars button
        } else if (e.getSource() == frame.r5D) {
            if (player.getRank() != 5 && office.checkValidDollars(player, 5)) {
                player.setRank(5);
                player.removeDollars(office.getDollarPrice(5));
                frame.upgradePlayer(player.getID() - 1, 5);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getDollarPrice(5) + " dollars.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;
        
        // rank 6 using dollars button
        } else if (e.getSource() == frame.r6D) {
            if (player.getRank() != 6 && office.checkValidDollars(player, 6)) {
                player.setRank(6);
                player.removeDollars(office.getDollarPrice(6));
                frame.upgradePlayer(player.getID() - 1, 6);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getDollarPrice(6) + " dollars.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 2 using creditss button
        } else if (e.getSource() == frame.r2C) {
            if (player.getRank() != 2 && office.checkValidCredits(player, 2)) {
                player.setRank(2);
                player.removeCredits(office.getCreditPrice(2));
                frame.upgradePlayer(player.getID() - 1, 2);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getCreditPrice(2) + " credits.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 3 using credits button
        } else if (e.getSource() == frame.r3C) {
            if (player.getRank() != 3 && office.checkValidCredits(player, 3)) {
                player.setRank(3);
                player.removeCredits(office.getCreditPrice(3));
                frame.upgradePlayer(player.getID() - 1, 3);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getCreditPrice(3) + " credits.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 4 using credits button
        } else if (e.getSource() == frame.r4C) {
            if (player.getRank() != 4 && office.checkValidCredits(player, 4)) {
                player.setRank(4);
                player.removeCredits(office.getCreditPrice(4));
                frame.upgradePlayer(player.getID() - 1, 4);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getCreditPrice(4) + " credits.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 5 using credits button
        } else if (e.getSource() == frame.r5C) {
            if (player.getRank() != 5 && office.checkValidCredits(player, 5)) {
                player.setRank(5);
                player.removeCredits(office.getCreditPrice(5));
                frame.upgradePlayer(player.getID() - 1, 5);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getCreditPrice(5) + " credits.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;

        // rank 6 using credits button
        } else if (e.getSource() == frame.r6C) {
            if (player.getRank() != 6 && office.checkValidCredits(player, 6)) {
                player.setRank(6);
                player.removeCredits(office.getCreditPrice(6));
                frame.upgradePlayer(player.getID() - 1, 6);
                frame.updateMessage("<html><p>Player " + player.getID() + " upgraded to rank 2\n using " + office.getCreditPrice(6) + " credits.</p></html>");
                System.out.println("Player " + player.getID() + " upgraded to rank " + player.getRank());
            } else return;
        }

        // move to next player
        frame.showUpgradeButtons(false);
        frame.showActionButtons(true);
        frame.nextPlayer();
    }
}
