import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class RoleButtonListener implements ActionListener {
    public BoardFrame frame;

    // constructor
    public RoleButtonListener(BoardFrame frame) {
        this.frame = frame;
    }

    // updates the player's role depending on
    // which button in the frame is pressed
    public void actionPerformed(ActionEvent e) {
        Player player = Deadwood.currentPlayer;

        if (!player.getRoom().isCompleted()) {
            if (e.getSource() == frame.bRole1) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(0));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole2) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(1));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole3) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(2));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole4) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(3));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole5) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(4));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole6) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(5));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.bRole7) {
                ArrayList<Role> roles = player.getRoom().getAvailableRoles(player.getRank());
                player.setRole(roles.get(6));
                frame.movePlayerRole(player.getID() - 1);

            } else if (e.getSource() == frame.cancelRole) {
                player.setRole(null);
            }

            if (player.getRole() != null) {
                frame.updateMessage("<html><p>Player " + player.getID() + " took role: " + player.getRole().getTitle() + " [" + player.getRole().getQuote() + "].</p></html>");
            }
            
            // moves to next player
            frame.showRoleButtons(false);
            frame.showActionButtons(true);
            frame.nextPlayer();
        }
    }
}