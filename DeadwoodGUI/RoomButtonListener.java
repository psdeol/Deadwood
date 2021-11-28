import java.awt.event.ActionListener;
import java.awt.event.*;

public class RoomButtonListener implements ActionListener { 
    public BoardFrame frame;

    // constructor
    public RoomButtonListener(BoardFrame frame) {
        this.frame = frame;
    }

    // moves the player to the correct room depending
    // on which button in the frame is pressed
    public void actionPerformed(ActionEvent e) {
        Player player = Deadwood.currentPlayer;

        // room change canceled
        if (e.getSource() == frame.cancelRoom) {
            frame.showRoomButtons(false);
            frame.showActionButtons(true);
            return;
        }

        player.getRoom().removePlayer(player);
        
        // move to train station
        if (e.getSource() == frame.TSbutton) {
            if (!Deadwood.board.trainStation.roomVisited) frame.showRoomScene(0);
            player.setRoom(Deadwood.board.trainStation);
            Deadwood.board.trainStation.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 0);
        
        // move to secret hideout
        } else if (e.getSource() == frame.SHbutton) {
            if (!Deadwood.board.secretHideout.roomVisited) frame.showRoomScene(1);
            player.setRoom(Deadwood.board.secretHideout);
            Deadwood.board.secretHideout.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 1);

        // move to church
        } else if (e.getSource() == frame.Cbutton) {
            if (!Deadwood.board.church.roomVisited) frame.showRoomScene(2);
            player.setRoom(Deadwood.board.church);
            Deadwood.board.church.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 2);

        // move to hotel
        } else if (e.getSource() == frame.Hbutton) {
            if (!Deadwood.board.hotel.roomVisited) frame.showRoomScene(3);
            player.setRoom(Deadwood.board.hotel);
            Deadwood.board.hotel.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 3);
        
        // move to main street
        } else if (e.getSource() == frame.MSbutton) {
            if (!Deadwood.board.mainStreet.roomVisited) frame.showRoomScene(4);
            player.setRoom(Deadwood.board.mainStreet);
            Deadwood.board.mainStreet.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 4);

        // move to jail
        } else if (e.getSource() == frame.Jbutton) {
            if (!Deadwood.board.jail.roomVisited) frame.showRoomScene(5);
            player.setRoom(Deadwood.board.jail);
            Deadwood.board.jail.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 5);

        // move to general store
        } else if (e.getSource() == frame.GSbutton) {
            if (!Deadwood.board.generalStore.roomVisited) frame.showRoomScene(6);
            player.setRoom(Deadwood.board.generalStore);
            Deadwood.board.generalStore.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 6);
        
        // move to ranch
        } else if (e.getSource() == frame.Rbutton) {
            if (!Deadwood.board.ranch.roomVisited) frame.showRoomScene(7);
            player.setRoom(Deadwood.board.ranch);
            Deadwood.board.ranch.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 7);
        
        // move to bank
        } else if (e.getSource() == frame.Bbutton) {
            if (!Deadwood.board.bank.roomVisited) frame.showRoomScene(8);
            player.setRoom(Deadwood.board.bank);
            Deadwood.board.bank.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 8);
        
        // move to saloon
        } else if (e.getSource() == frame.Sbutton) {
            if (!Deadwood.board.saloon.roomVisited) frame.showRoomScene(9);
            player.setRoom(Deadwood.board.saloon);
            Deadwood.board.saloon.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 9);
        
        // move to casting office
        } else if (e.getSource() == frame.Obutton) {
            player.setRoom(Deadwood.board.office);
            Deadwood.board.office.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 10);
        
        // move to trailers
        } else if (e.getSource() == frame.Tbutton) {
            player.setRoom(Deadwood.board.trailers);
            Deadwood.board.trailers.addPlayer(player);
            frame.movePlayer(player.getID() - 1, 11);
        }

        frame.updateMessage("<html><p>Player " + player.getID() + " moved to " + player.getRoom().getRoomName() + "</p></html>");
        frame.showRoomButtons(false);
        
        Room room = player.getRoom();

        // show available roles if any
        if (!room.isCompleted() && room.getAvailableRoles(player.getRank()) != null && room.getAvailableRoles(player.getRank()).size() > 0) {
            frame.showRoleButtons(true);

        } else {
            frame.showActionButtons(true);
            frame.nextPlayer();
        }
    }
    
}
