import org.w3c.dom.Document;
import java.util.ArrayList;

public class CastingOffice {
    private ArrayList<Integer> dollarPrices = new ArrayList<Integer>();
    private ArrayList<Integer> creditPrices = new ArrayList<Integer>();
    
    // constructor
    public CastingOffice() {
        getUpgradeData();
    }

    // reads upgrade prices form board.xml file
    private void getUpgradeData() {
        Document doc = null;
        Parser parser = new Parser();
        ArrayList<ArrayList<Integer>> upgradeData = new ArrayList<ArrayList<Integer>>();

        try {
            doc = parser.getDocument("./xmlfiles/board.xml");
            upgradeData = parser.readUpgradeData(doc);
            dollarPrices = upgradeData.get(0);
            creditPrices = upgradeData.get(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returns true if input player has enough dollars for input rank
    public boolean checkValidDollars(Player player, int rank) {
        return (player.getDollars() >= dollarPrices.get(rank - 2));
    }

    // returns true if input player has enough credits for input rank
    public boolean checkValidCredits(Player player, int rank) {
        return (player.getCredits() >= creditPrices.get(rank - 2));
    }

    // returns price of input rank in dollars
    public int getDollarPrice(int rank) {
        return dollarPrices.get(rank - 2);
    }

    // returns price of input rank in credits
    public int getCreditPrice(int rank) {
        return creditPrices.get(rank - 2);
    }
}