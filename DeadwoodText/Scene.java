import java.util.ArrayList;

public class Scene {
    private int number;
    private int budget;
    private String title;
    private String description;
    private ArrayList<Role> roles;

    public Scene(int number, int budget, String title, String description, ArrayList<Role> roles) {
        this.number = number;
        this.budget = budget;
        this.title = title;
        this.description = description;
        this.roles = roles;
    }

    @Override
    public String toString(){
        return title + " (budget: $" + budget + "M)";
    }

    // returns scene number
    public int getSceneNum() {
        return number;
    }

    // returns scene budget
    public int getBudget() {
        return budget;
    }

    // returns scene title
    public String getTitle() {
        return title;
    }

    // retursn scene description
    public String getDescription() {
        return description;
    }

    // returns arraylist of scene roles
    public ArrayList<Role> getRoles() {
        return roles;
    }

    // returns true if input role is part of the scene
    public boolean roleOnCard(Role role){
        return getRoles().contains(role);
    }
}
