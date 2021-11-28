import java.util.ArrayList;

public class Room {
    private int totalShots;
    private int shotsRemaining;
    private boolean roomCompleted = false;
    private String roomName;
    private Scene currentScene;
    private ArrayList<Role> extraRoles;
    private ArrayList<Room> adjacentRooms;
    private ArrayList<Player> playersInRoom = new ArrayList<Player>();

    public Room(int totalShots, String roomName, Scene currentScene, ArrayList<Role> extraRoles) {
        this.totalShots = totalShots;
        this.shotsRemaining = totalShots;
        this.roomName = roomName;
        this.currentScene = currentScene;
        this.extraRoles = extraRoles;
    }

    @Override
    public String toString(){
        if (shotsRemaining <= 0){
            return roomName + " (room is completed)";
        }
        return roomName + " (" + currentScene + " has " + shotsRemaining + "/" + totalShots + " shots remaining)";
    }

    /* GETTER METHODS */

    // returns number of shots remaining
    public int getShotsRemaining() {
        return shotsRemaining;
    }

    // returns total number of shots
    public int getTotalShots() {
        return totalShots;
    }

    // returns room name
    public String getRoomName() {
        return roomName;
    }

    // returns the current scene
    public Scene getCurrentScene() {
        return currentScene;
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

    public ArrayList<Role> getAvailableRoles() {
        if (currentScene == null) return null;

        ArrayList<Role> availableRoles = new ArrayList<Role>();
        ArrayList<Role> mainRoles = getSceneRoles();

        for (Role role : extraRoles)
            if (!role.isTaken()) availableRoles.add(role);

        for (Role role : mainRoles)
            if (!role.isTaken()) availableRoles.add(role);

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

    /* SETTER METHODS */

    public void setAdjacentRooms(ArrayList<Room> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public void setScene(Scene scene) {
        this.currentScene = scene;
    }

    public void addPlayer(Player player) {
        playersInRoom.add(player);
    }

    public void removePlayer(Player player) {
        playersInRoom.remove(player);
    }

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

    public void resetRoom() {
        roomCompleted = false;
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
