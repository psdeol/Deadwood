import org.w3c.dom.Document;
import java.util.ArrayList;
import java.util.Random;

public class SceneDeck {
    private static ArrayList<Scene> scenes = new ArrayList<Scene>();

    // constructor
    public SceneDeck() {
        setSceneDeck();
    }

    // creates a parser object to read data from cards.xml fle
    private void setSceneDeck() {
        Document doc = null;
        Parser parser = new Parser();

        // read cards.xml file for scene data
        try {
            doc = parser.getDocument("./xmlfiles/cards.xml");
            scenes = parser.readCardData(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returns a random scene from the scene list
    // and removes it from list so it isn't repeated
    public Scene getRandomScene() {
        Random rand = new Random();
        int sceneNum = rand.nextInt(scenes.size());
        Scene scene = scenes.get(sceneNum);
        scenes.remove(sceneNum);
        return scene;
    }

    // returns the current number of scenes in the scene deck
    public int getNumScenes() {
        return scenes.size();
    }
}
