import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class Parser {

    // returns a document after parsing the input file
    public Document getDocument(String filename) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try {
            doc = db.parse(filename);
        } catch (Exception e) {
            System.out.println("XML parse failure");
            e.printStackTrace();
        }

        return doc;
    }

    // returns an arraylist of scene objects that are created using the data in the input document
    public ArrayList<Scene> readCardData(Document doc) {
        ArrayList<Scene> sceneDeck = new ArrayList<Scene>();
        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        for (int i = 0; i < cards.getLength(); i++) {
            Node card = cards.item(i);
            NodeList children = card.getChildNodes();

            int sceneNum = 0, sceneBudget = 0;
            String sceneDesc = "", sceneName = "";
            ArrayList<Role> roles = new ArrayList<Role>();

            sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
            sceneBudget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());

            for (int j = 0; j < children.getLength(); j++) {
                Node cardInfo = children.item(j);

                if (cardInfo.getNodeName().equals("scene")) {
                    sceneNum = Integer.parseInt(cardInfo.getAttributes().getNamedItem("number").getNodeValue());
                    sceneDesc = cardInfo.getTextContent().replaceAll("\n", " ").replaceAll("\\s+", " ").trim();

                } else if (cardInfo.getNodeName().equals("part")) {
                    int roleRank = Integer.parseInt(cardInfo.getAttributes().getNamedItem("level").getNodeValue());
                    String roleName = cardInfo.getAttributes().getNamedItem("name").getNodeValue();
                    String roleQuote = "\"" + cardInfo.getTextContent().replaceAll("\n", " ").replaceAll("\\s+", " ").trim() + "\"";

                    Role role = new Role(roleRank, roleName, roleQuote, false);
                    roles.add(role);
                }
            }

            Scene scene = new Scene(sceneNum, sceneBudget, sceneName, sceneDesc, roles);
            sceneDeck.add(scene);
        }

        return sceneDeck;
    }

    // returns an array of room objects that are created using the data in the input document
    public Room[] readBoardData(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList rooms = root.getElementsByTagName("set");

        Room[] boardRooms = new Room[12];

        for (int i = 0; i < rooms.getLength(); i++) {
            Node roomNode = rooms.item(i);

            ArrayList<Role> roles = new ArrayList<Role>();
            String roomName = roomNode.getAttributes().getNamedItem("name").getNodeValue();
            int shots = 0;

            NodeList roomChildren = roomNode.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("takes")) {
                    NodeList takes = roomInfo.getChildNodes();

                    for (int k = 0; k < takes.getLength(); k++) {
                        Node take = takes.item(k);

                        if (take.getNodeName().equals("take")) shots++;
                    }

                } else if (roomInfo.getNodeName().equals("parts")) {
                    NodeList parts = roomInfo.getChildNodes();

                    for (int k = 0; k < parts.getLength(); k++) {
                        Node part = parts.item(k);

                        int roleRank;
                        String roleName, roleQuote;

                        if (part.getNodeName().equals("part")) {
                            roleName = part.getAttributes().getNamedItem("name").getNodeValue();
                            roleRank = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
                            roleQuote = "\"" + part.getTextContent().replaceAll("\\s+", " ").replaceAll("\n", " ").trim() + "\"";

                            Role role = new Role(roleRank, roleName, roleQuote, true);
                            roles.add(role);
                        }
                    }
                }

            }

            Room room = new Room(shots, roomName, null, roles);
            boardRooms[i] = room;
        }

        // room objects for casting office and trailers
        Room castingOffice = new Room(0, "office", null, null);
        Room trailers = new Room(0, "trailer", null, null);
        boardRooms[10] = castingOffice;
        boardRooms[11] = trailers;

        return boardRooms;
    }

    // returns a 2d arraylist containing the adjacent rooms for each room
    public ArrayList<ArrayList<String>> readAdjRoomData(Document doc) {
        ArrayList<ArrayList<String>> allAdjRoomData = new ArrayList<ArrayList<String>>();
        Element root = doc.getDocumentElement();

        // adjacent rooms for acting rooms
        NodeList rooms = root.getElementsByTagName("set");

        for (int i = 0; i < rooms.getLength(); i++) {
            Node room = rooms.item(i);
            NodeList roomChildren = room.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("neighbors")) {
                    NodeList neighbors = roomInfo.getChildNodes();
                    ArrayList<String> adjRooms = new ArrayList<String>();

                    for (int k = 0; k < neighbors.getLength(); k++) {
                        Node neighbor = neighbors.item(k);

                        if (neighbor.getNodeName().equals("neighbor"))
                            adjRooms.add(neighbor.getAttributes().getNamedItem("name").getNodeValue());
                    }

                    allAdjRoomData.add(adjRooms);
                }
            }
        }

        // adjacent room for casting office
        rooms = root.getElementsByTagName("office");

        for (int i = 0; i < rooms.getLength(); i++) {
            Node room = rooms.item(i);
            NodeList roomChildren = room.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("neighbors")) {
                    NodeList neighbors = roomInfo.getChildNodes();
                    ArrayList<String> adjRooms = new ArrayList<String>();

                    for (int k = 0; k < neighbors.getLength(); k++) {
                        Node neighbor = neighbors.item(k);

                        if (neighbor.getNodeName().equals("neighbor"))
                            adjRooms.add(neighbor.getAttributes().getNamedItem("name").getNodeValue());
                    }

                    allAdjRoomData.add(adjRooms);
                }
            }


        }

        // adjacent rooms for trailers
        rooms = root.getElementsByTagName("trailer");

        for (int i = 0; i < rooms.getLength(); i++) {
            Node room = rooms.item(i);
            NodeList roomChildren = room.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("neighbors")) {
                    NodeList neighbors = roomInfo.getChildNodes();
                    ArrayList<String> adjRooms = new ArrayList<String>();

                    for (int k = 0; k < neighbors.getLength(); k++) {
                        Node neighbor = neighbors.item(k);

                        if (neighbor.getNodeName().equals("neighbor"))
                            adjRooms.add(neighbor.getAttributes().getNamedItem("name").getNodeValue());
                    }

                    allAdjRoomData.add(adjRooms);
                }
            }


        }


        return allAdjRoomData;
    }

    // returns a 2d string array containing the prices to upgrade to each rank
    public ArrayList<ArrayList<Integer>> readUpgradeData(Document doc){
        ArrayList<ArrayList<Integer>> upgradeData = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> dollarData = new ArrayList<Integer>();
        ArrayList<Integer> creditData = new ArrayList<Integer>();
        Element root = doc.getDocumentElement();
        NodeList rooms = root.getElementsByTagName("office");

        for (int i = 0; i < rooms.getLength(); i++) {
            Node room = rooms.item(i);
            NodeList roomChildren = room.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("upgrades")) {
                    NodeList upgrades = roomInfo.getChildNodes();

                    for (int k = 0; k < upgrades.getLength(); k++) {
                        Node upgrade = upgrades.item(k);

                        if (upgrade.getNodeName().equals("upgrade")) {
                            String currency = upgrade.getAttributes().getNamedItem("currency").getNodeValue();

                            if (currency.equals("dollar")) dollarData.add(Integer.parseInt(upgrade.getAttributes().getNamedItem("amt").getNodeValue()));
                            else creditData.add(Integer.parseInt(upgrade.getAttributes().getNamedItem("amt").getNodeValue()));
                        }
                    }
                }
            }
        }

        upgradeData.add(dollarData);
        upgradeData.add(creditData);

        return upgradeData;
    }
}
