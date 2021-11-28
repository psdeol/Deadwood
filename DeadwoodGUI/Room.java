import java.util.ArrayList;

public class Room {
    private int totalShots;
    private int shotsRemaining;
    private int roomNum;
    private int[] roomArea;
    private boolean roomCompleted = false;
    private String roomName;
    private Scene currentScene;
    private ArrayList<int[]> shotAreas;
    private ArrayList<Role> extraRoles;
    private ArrayList<Room> adjacentRooms;
    private ArrayList<Player> playersInRoom = new ArrayList<Player>();

    public boolean roomVisited = false;

    // constructor
    public Room(int totalShots, int roomNum, int[] roomArea, ArrayList<int[]> shotAreas, String roomName, Scene currentScene, ArrayList<Role> extraRoles) {
        this.totalShots = totalShots;
        this.shotsRemaining = totalShots;
        this.roomNum = roomNum;
        this.roomArea = roomArea;
        this.shotAreas = shotAreas;
        this.roomName = roomName;
        this.currentScene = currentScene;
        this.extraRoles = extraRoles;
    }

    // returns number of shots remaining
    public int getShotsRemaining() {
        return shotsRemaining;
    }

    // returns the room number
    public int getRoomNum() {
        return roomNum;
    }

    // returns total number of shots
    public int getTotalShots() {
        return totalShots;
    }

    // returns the area of the room
    public int[] getRoomArea() {
        return roomArea;
    }

    // returns room name
    public String getRoomName() {
        return roomName;
    }

    // returns the current scene
    public Scene getCurrentScene() {
        return currentScene;
    }

    // returns a list of arrays containing areas for each shot
    public ArrayList<int[]> getShotAreas() {
        return shotAreas;
    }

    // returns ArrayList of extra roles
    public ArrayList<Role> getExtraRoles() {
        return extraRoles;
    }

    // returns ArrayList of scene roles
    public ArrayList<Role> getSceneRoles() {
        if (currentScene == null) return null;
        
        return currentScene.getRoles();
    }

    // returns ArrayList of untaken roles of input rank
    public ArrayList<Role> getAvailableRoles(int rank) {
        if (currentScene == null || this.extraRoles == null) return null;

        ArrayList<Role> availableRoles = new ArrayList<Role>();
        ArrayList<Role> mainRoles = getSceneRoles();

        for (Role role : extraRoles)
            if (!role.isTaken() && role.getRank() <= rank) availableRoles.add(role);

        for (Role role : mainRoles)
            if (!role.isTaken() && role.getRank() <= rank) availableRoles.add(role);

        return availableRoles;
    }

    // returns ArrayList of adjacentRooms
    public ArrayList<Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    // returns ArrayList of players in room
    public ArrayList<Player> getPlayersInRoom() {
        return playersInRoom;
    }

    // returns true if current room is completed
    public boolean isCompleted() {
        return roomCompleted;
    } 

    // sets adj rooms list to input list
    public void setAdjacentRooms(ArrayList<Room> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    // sets the current scene to input scene
    public void setScene(Scene scene) {
        this.currentScene = scene;
    }

    // adds the input player to playersInRoom list
    public void addPlayer(Player player) {
        playersInRoom.add(player);
    }

    // removes the input player from playersInRoom list
    public void removePlayer(Player player) {
        playersInRoom.remove(player);
    }

    // removes a shot from board
    public void completeShot() {
        shotsRemaining--;
    }

    // distributes bonus payouts to players
    public void endRoom() {
        roomCompleted = true;
        System.out.print("\n" + roomName + " has finished filming. Budget was $" + currentScene.getBudget() + "M. ");

        PaymentManager pay = new PaymentManager();
        pay.payBonuses(this);

        for (Player p : playersInRoom){
            p.resetRehearsals();
            p.setRole(null);
        }

        ArrayList<Role> roles = getExtraRoles();
        
        for (Role role : roles) {
            role.leaveRole();
        }
    }

    // removes all players from room, sets extra roles to untaken
    public void resetRoom() {
        roomCompleted = false;
        roomVisited = false;
        shotsRemaining = totalShots;

        ArrayList<Role> roles = getExtraRoles();
        
        if (roles != null){
            for (int i = 0; i < roles.size(); i++) {
                roles.get(i).leaveRole();
            }
        }

        for (int i = 0; i < playersInRoom.size(); i++) {
            playersInRoom.remove(i);
        }
    }
}
