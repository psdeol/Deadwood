import java.util.ArrayList;

public class Scene {
    private int number;
    private int budget;
    private String title;
    private String description;
    private String imgName;
    private ArrayList<Role> roles;

    // constructor
    public Scene(int number, int budget, String imgName, String title, String description, ArrayList<Role> roles) {
        this.number = number;
        this.budget = budget;
        this.imgName = imgName;
        this.title = title;
        this.description = description;
        this.roles = roles;
    }

    // returns scene number
    public int getSceneNum() {
        return number;
    }

    // returns scene budget
    public int getBudget() {
        return budget;
    }

    // returns the file name of the image for this scene
    public String getSceneImg() {
        return imgName;
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
