import org.w3c.dom.Document;
import java.util.ArrayList;

public class CastingOffice {
    private ArrayList<Integer> dollarPrices = new ArrayList<Integer>();
    private ArrayList<Integer> creditPrices = new ArrayList<Integer>();
    
    public CastingOffice() {
        getUpgradeData();
    }

    private void getUpgradeData() {
        Document doc = null;
        Parser parser = new Parser();
        ArrayList<ArrayList<Integer>> upgradeData = new ArrayList<ArrayList<Integer>>();

        try {
            doc = parser.getDocument("board.xml");
            upgradeData = parser.readUpgradeData(doc);
            dollarPrices = upgradeData.get(0);
            creditPrices = upgradeData.get(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidDollars(Player player, int rank) {
        return (player.getDollars() >= dollarPrices.get(rank - 2));
    }

    public boolean checkValidCredits(Player player, int rank) {
        return (player.getCredits() >= creditPrices.get(rank - 2));
    }

    public int getDollarPrice(int rank) {
        return dollarPrices.get(rank - 2);
    }

    public int getCreditPrice(int rank) {
        return creditPrices.get(rank - 2);
    }
}