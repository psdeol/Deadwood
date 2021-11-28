import java.awt.event.ActionListener;
import java.awt.event.*;

public class ActionButtonLIstener implements ActionListener {
    public BoardFrame frame;

    // constructor
    public ActionButtonLIstener(BoardFrame frame) {
        this.frame = frame;
    }

    // executes the correct action depending on
    // which button is pressed in the frame
    public void actionPerformed(ActionEvent e) {
        Player player = Deadwood.currentPlayer;

        // act button
        if (e.getSource() == frame.bAct) {
            if (player.getRole() != null) {
                Dice dice = new Dice();
                int roll = dice.roll();

                int budget = player.getRoom().getCurrentScene().getBudget();
                boolean success = ((roll + player.getRehearsalsDone()) >= budget);

                PaymentManager pay = new PaymentManager();
                pay.payActAction(player, success);

                if (player.getRehearsalsDone() + roll >= budget) {
                    frame.removeShot();
                }

                System.out.println("Player " + player.getID() + " rolled a " + (roll + player.getRehearsalsDone()) + " budget was " + budget);
                
                if (success) frame.updateMessage("<html><p>Player " + player.getID() + " rolled a " + roll + ".\nAct successful.</p></html>");
                else frame.updateMessage("<html><p>Player " + player.getID() + " rolled a " + roll + ".\nAct unsuccessful.</p></html>");
                
                frame.nextPlayer();

            } else {
                frame.updateMessage("<html><p>Unable to act. No role taken. Take a role to act.</p></html>");
            }
        
        // rehearse button
        } else if (e.getSource() == frame.bRehearse) {
            if (player.getRole() != null) {
                int budget = player.getRoom().getCurrentScene().getBudget();
                
                if (player.getRehearsalsDone() < budget-1) {
                    player.addRehearsal();
                    System.out.println("Player " + player.getID() + " rehearsed");
                    frame.updateMessage("<html><p>Player " + player.getID() + " rehearsed.</p></html>");
                    frame.nextPlayer();

                } else {
                    frame.updateMessage("<html><p>Unable to rehearse anymore. Guaranteed success on next act.</p></html>");
                    System.out.println("Player " + player.getID() + " can't rehearse anymore, must act");
                }

            } else {
                frame.updateMessage("<html><p>Unable to rehearse. No role taken. Take a role to rehearse.</p></html>");
            }
        
        // move button
        } else if (e.getSource() == frame.bMove) {
            if (player.getRole() == null) {
                frame.showActionButtons(false);
                frame.showRoomButtons(true);
            } else {
                frame.updateMessage("<html><p>You cannot move while filming a scene.</p></html>");
            }

        // upgrade button
        } else if (e.getSource() == frame.bUpgrade) {
            if (player.getRoom().getRoomName().equals("office")) {
                frame.showActionButtons(false);
                frame.showUpgradeButtons(true);

            } else {
                frame.updateMessage("<html><p>Unable to upgrade. You must be in casting office to upgrade your rank.</p></html>");
            }

        // skip turn button
        } else if (e.getSource() == frame.bSkip) {
            frame.updateMessage("<html><p>Player " + player.getID() + " skipped their turn</p></html>");
            frame.nextPlayer();
        }
    }
}
