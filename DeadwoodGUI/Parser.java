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
            String sceneDesc = "", sceneName = "", sceneImg = "";
            ArrayList<Role> roles = new ArrayList<Role>();

            sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
            sceneImg = card.getAttributes().getNamedItem("img").getNodeValue();
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
                    int[] area = new int[4];

                    NodeList cardInfoChildren = cardInfo.getChildNodes();

                    for (int k = 0; k < cardInfoChildren.getLength(); k++) {
                        Node child = cardInfoChildren.item(k);

                        if (child.getNodeName().equals("area")) {
                            area[0] = Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue());
                            area[1] = Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue());
                            area[2] = Integer.parseInt(child.getAttributes().getNamedItem("h").getNodeValue());
                            area[3] = Integer.parseInt(child.getAttributes().getNamedItem("w").getNodeValue());
                        }
                    }

                    Role role = new Role(roleRank, roleName, roleQuote, false, area);
                    roles.add(role);
                }
            }

            Scene scene = new Scene(sceneNum, sceneBudget, sceneImg, sceneName, sceneDesc, roles);
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
            ArrayList<int[]> shotAreas = new ArrayList<int[]>();
            String roomName = roomNode.getAttributes().getNamedItem("name").getNodeValue();
            int[] roomArea = new int[4];
            int shots = 0;

            NodeList roomChildren = roomNode.getChildNodes();

            for (int j = 0; j < roomChildren.getLength(); j++) {
                Node roomInfo = roomChildren.item(j);

                if (roomInfo.getNodeName().equals("area")) {
                    roomArea[0] = Integer.parseInt(roomInfo.getAttributes().getNamedItem("x").getNodeValue());
                    roomArea[1] = Integer.parseInt(roomInfo.getAttributes().getNamedItem("y").getNodeValue());
                    roomArea[2] = Integer.parseInt(roomInfo.getAttributes().getNamedItem("h").getNodeValue());
                    roomArea[3] = Integer.parseInt(roomInfo.getAttributes().getNamedItem("w").getNodeValue());

                } else if (roomInfo.getNodeName().equals("takes")) {
                    NodeList takes = roomInfo.getChildNodes();

                    for (int k = 0; k < takes.getLength(); k++) {
                        Node take = takes.item(k);

                        if (take.getNodeName().equals("take")) {
                            shots++;
                            NodeList takeChildren = take.getChildNodes();
                            Node areaInfo = takeChildren.item(0);
                            int[] area = new int[4];
                            area[0] = Integer.parseInt(areaInfo.getAttributes().getNamedItem("x").getNodeValue());
                            area[1] = Integer.parseInt(areaInfo.getAttributes().getNamedItem("y").getNodeValue());
                            area[2] = Integer.parseInt(areaInfo.getAttributes().getNamedItem("h").getNodeValue());
                            area[3] = Integer.parseInt(areaInfo.getAttributes().getNamedItem("w").getNodeValue());
                            shotAreas.add(area);
                        }
                    }

                } else if (roomInfo.getNodeName().equals("parts")) {
                    NodeList parts = roomInfo.getChildNodes();

                    for (int k = 0; k < parts.getLength(); k++) {
                        Node part = parts.item(k);

                        if (part.getNodeName().equals("part")) {
                            String roleName = part.getAttributes().getNamedItem("name").getNodeValue();
                            int roleRank = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
                            String roleQuote = "\"" + part.getTextContent().replaceAll("\\s+", " ").replaceAll("\n", " ").trim() + "\"";
                            int[] area = new int[4];

                            NodeList partChildren = part.getChildNodes();

                            for (int x = 0; x < partChildren.getLength(); x++) {
                                Node child = partChildren.item(x);

                                if (child.getNodeName().equals("area")) {
                                    area[0] = Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue());
                                    area[1] = Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue());
                                    area[2] = Integer.parseInt(child.getAttributes().getNamedItem("h").getNodeValue());
                                    area[3] = Integer.parseInt(child.getAttributes().getNamedItem("w").getNodeValue());
                                }
                            }

                            Role role = new Role(roleRank, roleName, roleQuote, true, area);
                            roles.add(role);
                        }
                    }
                }

            }

            Room room = new Room(shots, i, roomArea, shotAreas, roomName, null, roles);
            boardRooms[i] = room;
        }

        // room objects for casting office and trailers
        int[] officeArea = {9, 459, 208, 209};
        int[] trailersArea = {991, 248, 194, 201};
        Room castingOffice = new Room(0, 10, officeArea, null, "office", null, null);
        Room trailers = new Room(0, 11, trailersArea, null, "trailer", null, null);
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
