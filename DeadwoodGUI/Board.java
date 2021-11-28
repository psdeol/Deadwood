import org.w3c.dom.Document;
import java.util.ArrayList;

public class Board {
    private Room[] rooms = new Room[12];
    private ArrayList<Room> completedRooms = new ArrayList<Room>();
    private SceneDeck sceneDeck = new SceneDeck();
    private int roomsRemaining = 10;

    public Room mainStreet, saloon, ranch, secretHideout, bank, hotel, church, trainStation, jail, generalStore, office, trailers;

    // constructor
    public Board() {
        setRooms();
        setAdjacentRooms();
        
        mainStreet = rooms[4];
        saloon = rooms[9];
        ranch = rooms[7];
        secretHideout = rooms[1];
        bank = rooms[8];
        hotel = rooms[3];
        church = rooms[2];
        trainStation = rooms[0];
        jail = rooms[5];
        generalStore = rooms[6];
        office = rooms[10];
        trailers = rooms[11];
    }

    // reads the board.xml file and sets up the
    // rooms array with room objects
    private void setRooms() {
        Document doc = null;
        Parser parser = new Parser();

        // read file for room data
        try {
            doc = parser.getDocument("./xmlfiles/board.xml");
            rooms = parser.readBoardData(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // set the scene for each room
        for (int i = 0; i < rooms.length; i++) {
            String roomName = rooms[i].getRoomName();

            if (roomName.equals("trailer") || roomName.equals("office"))
                continue;
            
            rooms[i].setScene(sceneDeck.getRandomScene());
        }

    }

    // reads the board.xml file to find the adjacent rooms for 
    // each room and then sets the adjacent rooms for each room
    private void setAdjacentRooms() {
        Document doc = null;
        Parser parser = new Parser();
        ArrayList<ArrayList<String>> allAdjRoomData = new ArrayList<ArrayList<String>>();

        // read file for adjacent rooms
        try {
            doc = parser.getDocument("./xmlfiles/board.xml");
            allAdjRoomData = parser.readAdjRoomData(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // adjacent rooms are set for each room
        for (int i = 0; i < allAdjRoomData.size(); i++) {
            ArrayList<String> adjRoomData = allAdjRoomData.get(i);
            ArrayList<Room> adjRooms = new ArrayList<Room>();

            for (int j = 0; j < adjRoomData.size(); j++) {

                Room room = findRoom(adjRoomData.get(j));

                if (room != null) adjRooms.add(room);
            }

            rooms[i].setAdjacentRooms(adjRooms);
        } 
    }

    // returns the room that with the same name as the
    // input string, returns null if not found 
    private Room findRoom(String roomName) {
        Room room = null;

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getRoomName().equals(roomName)) {
                room = rooms[i];
                break;
            }
        }

        return room;
    } 

    // returns the number of roomes remaining
    public int getRoomsRemaining() {
        return roomsRemaining;
    }

    // returns array of rooms
    public Room[] getRooms() {
        return rooms;
    }

    // calls endRoom funciton for current room
    public void completeRoom(Room room) {
        room.endRoom();
        roomsRemaining--;
        completedRooms.add(room);
    }

    // resets each room in board
    public void resetBoard() {
        roomsRemaining = 10;
        completedRooms.clear();

        for (int i = 0; i < rooms.length; i++) {
            rooms[i].resetRoom();

            if (rooms[i].getRoomName().equals("trailer") || rooms[i].getRoomName().equals("office"))
                continue;

            rooms[i].setScene(sceneDeck.getRandomScene());
        }
    }
}
