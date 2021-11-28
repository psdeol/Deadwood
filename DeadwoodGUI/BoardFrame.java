import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class BoardFrame extends JFrame {
     private static BoardFrame boardLL = new BoardFrame();

     public static int[][][] moveCoords = {
          {{10,180}, {240,810}, {730,655}, {1010,460}, {900,150}, {285,150}, {285,400}, {270,610}, {835,460}, {735,225}, {7,460}, {991,248}},
          {{60,180}, {290,810}, {780,655}, {1050,460}, {950,150}, {335,150}, {335,400}, {320,610}, {835,510}, {785,225}, {57,460}, {1041,248}},
          {{10,230}, {340,810}, {730,695}, {1000,510}, {770,70}, {385,150}, {385,400}, {270,660}, {600,600}, {835,225}, {107,460}, {1091,248}},
          {{60,230}, {390,810}, {780,695}, {1000,560}, {820,70}, {385,180}, {435,400}, {320,660}, {650,600}, {885,225}, {157,460}, {1141,248}},
          {{110,180}, {240,860}, {830,685}, {1000,610}, {870,70}, {435,200}, {485,400}, {370,610}, {755,600}, {935,275}, {7,510}, {991,298}},
          {{160,180}, {290,860}, {900,765}, {1100,620}, {920,70}, {485,200}, {285,360}, {370,660}, {805,600}, {935,325}, {7,560}, {1041,298}},
          {{200,10}, {340,860}, {800,850}, {1150,620}, {770,120}, {485,160}, {335,360}, {550,520}, {855,600}, {935,375}, {7,610}, {1091,298}},
          {{130,390}, {390,860}, {610,850}, {950,690}, {820,120}, {530,180}, {190,360}, {550,570}, {700,600}, {785,395}, {175,550}, {1141,298}}};

     // JLabels
     JLabel[] playerLabels;
     JLabel[] roomLabels = new JLabel[10];
     JLabel[] roomBackLabels = new JLabel[10];
     ArrayList<ArrayList<JLabel>> shotLabels = new ArrayList<ArrayList<JLabel>>();
     JLabel TSlabel, SHlabel, Clabel, Hlabel, MSlabel, Jlabel, GSlabel, Rlabel, Blabel, Slabel, Olabel, Tlabel; // order in roomlabels and shotlabels

     JLabel boardlabel;
     JLabel actionsLabel, adjRoomsLabel, rolesLabel, upgradesLabel;
     JLabel pID, pDollars, pCredits, pRehearsals;
     JLabel upgradesTitle, r2, r3, r4, r5, r6;
     JLabel feedTitle, message, message2, message3, message4, message5;

     // JButtons
     JButton[] roomButtons = new JButton[12];
     JButton TSbutton, SHbutton, Cbutton, Hbutton, MSbutton, Jbutton, GSbutton, Rbutton, Bbutton, Sbutton, Obutton, Tbutton;
     JButton[] roleButtons = new JButton[7];
     JButton bRole1, bRole2, bRole3, bRole4, bRole5, bRole6, bRole7;
     JButton bAct, bRehearse, bMove, bUpgrade, bSkip;
     JButton r2D, r3D, r4D, r5D, r6D, r2C, r3C, r4C, r5C, r6C;
     JButton cancelRoom, cancelRole, cancelUpgrade;

     // JLayered Pane
     JLayeredPane bPane;

     private BoardFrame() {
          super("Deadwood");
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setSize(1430, 950);
          
          bPane = getLayeredPane();

          boardlabel = new JLabel();
          boardlabel.setIcon(new ImageIcon("./images/board.jpg")); 
          boardlabel.setBounds(0,0,1200,900);
          bPane.add(boardlabel, new Integer(0));
          
          setRoomBackLables();
          setRoomLabels();
          setShotMarkers();
          setActionButtons();
          setRoomButtons();
          setRoleButtons();
          setUpgradeButtons();
          setCancelButtons();
          setPlayerStats();
          setFeed();
     }

     public static BoardFrame getInstance() {
          if (boardLL == null) boardLL = new BoardFrame();
          return boardLL;
     }



     /* SETTER METHODS FOR INITIALIZING LABELS AND BUTTONS */

     private void setRoomBackLables() {
          for (int i = 0; i < roomBackLabels.length; i++) {
               int[] area = Deadwood.board.getRooms()[i].getRoomArea();

               roomBackLabels[i] = new JLabel();
               roomBackLabels[i].setIcon(new ImageIcon("./images/CardBack.jpg"));
               roomBackLabels[i].setBounds(area[0], area[1], area[3], area[2]);
               roomBackLabels[i].setOpaque(true);
               bPane.add(roomBackLabels[i], new Integer(2));

          }
     }
     
     private void setRoomLabels() {
          for (int i = 0; i < roomLabels.length; i++) {
               Scene scene = Deadwood.board.getRooms()[i].getCurrentScene();
               int[] area = Deadwood.board.getRooms()[i].getRoomArea();

               roomLabels[i] = new JLabel();
               roomLabels[i].setIcon(new ImageIcon("./images/cards/" + scene.getSceneImg()));
               roomLabels[i].setBounds(area[0], area[1], area[3], area[2]);
               roomLabels[i].setOpaque(true);
               bPane.add(roomLabels[i], new Integer(1));
               
          }
     }

     private void setShotMarkers() {
          for (Room room : Deadwood.board.getRooms()) {
               if (room.getRoomName().equals("trailer") || room.getRoomName().equals("office")) continue;

               ArrayList<int[]> shotAreas = room.getShotAreas();
               ArrayList<JLabel> roomShots = new ArrayList<JLabel>();

               for (int i = 0; i < shotAreas.size(); i++) {
                    int[] area = shotAreas.get(i);

                    JLabel take = new JLabel();
                    take.setIcon(new ImageIcon("./images/shot.png"));
                    take.setBounds(area[0], area[1], area[3], area[2]);
                    take.setOpaque(false);
                    bPane.add(take, new Integer(1));
                    roomShots.add(take);
               }

               shotLabels.add(roomShots);
          }
     }

     private void setActionButtons() {
          ActionButtonLIstener ABL = new ActionButtonLIstener(this);

          actionsLabel = new JLabel("ACTIONS");
          actionsLabel.setBounds(1240, 0, 100, 25);
          bPane.add(actionsLabel, new Integer(2));

          bAct = new JButton("ACT");
          bAct.addActionListener(ABL);
          bAct.setBackground(Color.white);
          bAct.setBounds(1210, 30, 150, 25);
          bPane.add(bAct, new Integer(2));

          bRehearse = new JButton("REHEARSE");
          bRehearse.addActionListener(ABL);
          bRehearse.setBackground(Color.white);
          bRehearse.setBounds(1210, 60, 150, 25);
          bPane.add(bRehearse, new Integer(2));

          bMove = new JButton("MOVE");
          bMove.addActionListener(ABL);
          bMove.setBackground(Color.white);
          bMove.setBounds(1210, 90, 150, 25);
          bPane.add(bMove, new Integer(2));

          bUpgrade = new JButton("UPGRADE");
          bUpgrade.addActionListener(ABL);
          bUpgrade.setBackground(Color.white);
          bUpgrade.setBounds(1210, 120, 150, 25);
          bPane.add(bUpgrade, new Integer(2));

          bSkip = new JButton("END TURN");
          bSkip.addActionListener(ABL);
          bSkip.setBackground(Color.white);
          bSkip.setBounds(1210, 150, 150, 25);
          bPane.add(bSkip, new Integer(2));
     }

     private void setRoomButtons() {
          RoomButtonListener RBL = new RoomButtonListener(this);

          adjRoomsLabel = new JLabel("ADJACENT ROOMS");
          adjRoomsLabel.setBounds(1240, 0, 150, 25);
          adjRoomsLabel.setVisible(false);
          bPane.add(adjRoomsLabel, new Integer(2));

          TSbutton = new JButton("Train Station");
          TSbutton.addActionListener(RBL);
          TSbutton.setVisible(false);
          bPane.add(TSbutton, new Integer(2));
          roomButtons[0] = TSbutton;

          SHbutton = new JButton("Secret Hideout");
          SHbutton.addActionListener(RBL);
          SHbutton.setVisible(false);
          bPane.add(SHbutton, new Integer(2));
          roomButtons[1] = SHbutton;

          Cbutton = new JButton("Church");
          Cbutton.addActionListener(RBL);
          Cbutton.setVisible(false);
          bPane.add(Cbutton, new Integer(2));
          roomButtons[2] = Cbutton;

          Hbutton = new JButton("Hotel");
          Hbutton.addActionListener(RBL);
          Hbutton.setVisible(false);
          bPane.add(Hbutton, new Integer(2));
          roomButtons[3] = Hbutton;

          MSbutton = new JButton("Main Street");
          MSbutton.addActionListener(RBL);
          MSbutton.setVisible(false);
          bPane.add(MSbutton, new Integer(2));
          roomButtons[4] = MSbutton;

          Jbutton = new JButton("Jail");
          Jbutton.addActionListener(RBL);
          Jbutton.setVisible(false);
          bPane.add(Jbutton, new Integer(2));
          roomButtons[5] = Jbutton;

          GSbutton = new JButton("General Store");
          GSbutton.addActionListener(RBL);
          GSbutton.setVisible(false);
          bPane.add(GSbutton, new Integer(2));
          roomButtons[6] = GSbutton;

          Rbutton = new JButton("Ranch");
          Rbutton.addActionListener(RBL);
          Rbutton.setVisible(false);
          bPane.add(Rbutton, new Integer(2));
          roomButtons[7] = Rbutton;

          Bbutton = new JButton("Bank");
          Bbutton.addActionListener(RBL);
          Bbutton.setVisible(false);
          bPane.add(Bbutton, new Integer(2));
          roomButtons[8] = Bbutton;

          Sbutton = new JButton("Saloon");
          Sbutton.addActionListener(RBL);
          Sbutton.setVisible(false);
          bPane.add(Sbutton, new Integer(2));
          roomButtons[9] = Sbutton;

          Obutton = new JButton("Office");
          Obutton.addActionListener(RBL);
          Obutton.setVisible(false);
          bPane.add(Obutton, new Integer(2));
          roomButtons[10] = Obutton;

          Tbutton = new JButton("Trailers");
          Tbutton.addActionListener(RBL);
          Tbutton.setVisible(false);
          bPane.add(Tbutton, new Integer(2));
          roomButtons[11] = Tbutton;
     }

     private void setRoleButtons() {
          RoleButtonListener RBL = new RoleButtonListener(this);

          rolesLabel = new JLabel("AVAILABLE ROLES");
          rolesLabel.setBounds(1210, 0, 150, 25);
          rolesLabel.setVisible(false);
          bPane.add(rolesLabel, new Integer(2));

          bRole1 = new JButton();
          bRole1.addActionListener(RBL);
          bRole1.setVisible(false);
          bPane.add(bRole1, new Integer(2));
          roleButtons[0] = bRole1;

          bRole2 = new JButton();
          bRole2.addActionListener(RBL);
          bRole2.setVisible(false);
          bPane.add(bRole2, new Integer(2));
          roleButtons[1] = bRole2;

          bRole3 = new JButton();
          bRole3.addActionListener(RBL);
          bRole3.setVisible(false);
          bPane.add(bRole3, new Integer(2));
          roleButtons[2] = bRole3;

          bRole4 = new JButton();
          bRole4.addActionListener(RBL);
          bRole4.setVisible(false);
          bPane.add(bRole4, new Integer(2));
          roleButtons[3] = bRole4;

          bRole5 = new JButton();
          bRole5.addActionListener(RBL);
          bRole5.setVisible(false);
          bPane.add(bRole5, new Integer(2));
          roleButtons[4] = bRole5;

          bRole6 = new JButton();
          bRole6.addActionListener(RBL);
          bRole6.setVisible(false);
          bPane.add(bRole6, new Integer(2));
          roleButtons[5] = bRole6;

          bRole7 = new JButton();
          bRole7.addActionListener(RBL);
          bRole7.setVisible(false);
          bPane.add(bRole7, new Integer(2));
          roleButtons[6] = bRole7;
     }

     private void setUpgradeButtons() {
          UpgradeButtonListener UBL = new UpgradeButtonListener(this);
          CastingOffice office = new CastingOffice();

          upgradesLabel = new JLabel("UPGRADES");
          upgradesLabel.setBounds(1250, 0, 150, 20);
          upgradesLabel.setVisible(false);
          bPane.add(upgradesLabel, new Integer(2));

          upgradesTitle = new JLabel("Rank     Dollars       Credits");
          upgradesTitle.setBounds(1210, 30, 150, 20);
          upgradesTitle.setVisible(false);
          bPane.add(upgradesTitle, new Integer(2));

          r2 = new JLabel("2");
          r2.setBounds(1220, 60, 30, 20);
          r2.setVisible(false);
          bPane.add(r2, new Integer(2));

          r3 = new JLabel("3");
          r3.setBounds(1220, 90, 30, 20);
          r3.setVisible(false);
          bPane.add(r3, new Integer(2));

          r4 = new JLabel("4");
          r4.setBounds(1220, 120, 30, 20);
          r4.setVisible(false);
          bPane.add(r4, new Integer(2));

          r5 = new JLabel("5");
          r5.setBounds(1220, 150, 30, 20);
          r5.setVisible(false);
          bPane.add(r5, new Integer(2));

          r6 = new JLabel("6");
          r6.setBounds(1220, 180, 30, 20);
          r6.setVisible(false);
          bPane.add(r6, new Integer(2));

          r2D = new JButton(office.getDollarPrice(2) + "");
          r2D.addActionListener(UBL);
          r2D.setBounds(1250, 60, 50, 20);
          r2D.setBackground(Color.white);
          r2D.setVisible(false);
          bPane.add(r2D, new Integer(2));

          r3D = new JButton(office.getDollarPrice(3) + "");
          r3D.addActionListener(UBL);
          r3D.setBounds(1250, 90, 50, 20);
          r3D.setBackground(Color.white);
          r3D.setVisible(false);
          bPane.add(r3D, new Integer(2));

          r4D = new JButton(office.getDollarPrice(4) + "");
          r4D.addActionListener(UBL);
          r4D.setBounds(1250, 120, 50, 20);
          r4D.setBackground(Color.white);
          r4D.setVisible(false);
          bPane.add(r4D, new Integer(2));

          r5D = new JButton(office.getDollarPrice(5) + "");
          r5D.addActionListener(UBL);
          r5D.setBounds(1250, 150, 50, 20);
          r5D.setBackground(Color.white);
          r5D.setVisible(false);
          bPane.add(r5D, new Integer(2));

          r6D = new JButton(office.getDollarPrice(6) + "");
          r6D.addActionListener(UBL);
          r6D.setBounds(1250, 180, 50, 20);
          r6D.setBackground(Color.white);
          r6D.setVisible(false);
          bPane.add(r6D, new Integer(2));

          r2C = new JButton(office.getCreditPrice(2) + "");
          r2C.addActionListener(UBL);
          r2C.setBounds(1310, 60, 50, 20);
          r2C.setBackground(Color.white);
          r2C.setVisible(false);
          bPane.add(r2C, new Integer(2));

          r3C = new JButton(office.getCreditPrice(3) + "");
          r3C.addActionListener(UBL);
          r3C.setBounds(1310, 90, 50, 20);
          r3C.setBackground(Color.white);
          r3C.setVisible(false);
          bPane.add(r3C, new Integer(2));

          r4C = new JButton(office.getCreditPrice(4) + "");
          r4C.addActionListener(UBL);
          r4C.setBounds(1310, 120, 50, 20);
          r4C.setBackground(Color.white);
          r4C.setVisible(false);
          bPane.add(r4C, new Integer(2));

          r5C = new JButton(office.getCreditPrice(5) + "");
          r5C.addActionListener(UBL);
          r5C.setBounds(1310, 150, 50, 20);
          r5C.setBackground(Color.white);
          r5C.setVisible(false);
          bPane.add(r5C, new Integer(2));

          r6C = new JButton(office.getCreditPrice(6) + "");
          r6C.addActionListener(UBL);
          r6C.setBounds(1310, 180, 50, 20);
          r6C.setBackground(Color.white);
          r6C.setVisible(false);
          bPane.add(r6C, new Integer(2));
     }

     private void setCancelButtons() {
          cancelRoom = new JButton("CANCEL");
          cancelRoom.setBackground(Color.white);
          cancelRoom.addActionListener(new RoomButtonListener(this));
          cancelRoom.setVisible(false);
          bPane.add(cancelRoom, new Integer(2));

          cancelRole = new JButton("NO ROLE");
          cancelRole.setBackground(Color.white);
          cancelRole.addActionListener(new RoleButtonListener(this));
          cancelRole.setVisible(false);
          bPane.add(cancelRole, new Integer(2));

          cancelUpgrade = new JButton("CANCEL");
          cancelUpgrade.setBackground(Color.white);
          cancelUpgrade.addActionListener(new UpgradeButtonListener(this));
          cancelUpgrade.setBounds(1250, 210, 110, 20);
          cancelUpgrade.setVisible(false);
          bPane.add(cancelUpgrade, new Integer(2));
     }

     private void setPlayerStats() {
          pID = new JLabel();
          pID.setBounds(1210, 270, 200, 20);
          pID.setFont(new Font("Arial", Font.BOLD, 15)); 
          pID.setVisible(false);
          bPane.add(pID, new Integer(2));

          pDollars = new JLabel();
          pDollars.setBounds(1210, 300, 150, 20);
          pDollars.setFont(new Font("Arial", Font.BOLD, 14));
          pDollars.setVisible(false);
          bPane.add(pDollars, new Integer(2));

          pCredits = new JLabel();
          pCredits.setBounds(1210, 320, 150, 20);
          pCredits.setFont(new Font("Arial", Font.BOLD, 14));
          pCredits.setVisible(false);
          bPane.add(pCredits, new Integer(2));

          pRehearsals = new JLabel();
          pRehearsals.setBounds(1210, 340, 150, 20);
          pRehearsals.setFont(new Font("Arial", Font.BOLD, 14));
          pRehearsals.setVisible(false);
          bPane.add(pRehearsals, new Integer(2));
     }

     private void setFeed() {
          feedTitle = new JLabel("Message Log");
          feedTitle.setBounds(1210, 400, 150, 15);
          bPane.add(feedTitle, new Integer(2));

          message = new JLabel("", SwingConstants.LEFT);
          message.setFont(new Font("Arial", Font.PLAIN, 12));
          message.setBounds(1210, 420, 150, 45);
          message.setVisible(false);
          bPane.add(message, new Integer(2));

          message2 = new JLabel("", SwingConstants.LEFT);
          message2.setFont(new Font("Arial", Font.PLAIN, 12));
          message2.setBounds(1210, 465, 150, 45);
          message2.setVisible(false);
          bPane.add(message2, new Integer(2));

          message3 = new JLabel("", SwingConstants.LEFT);
          message3.setFont(new Font("Arial", Font.PLAIN, 12));
          message3.setBounds(1210, 510, 150, 45);
          message3.setVisible(false);
          bPane.add(message3, new Integer(2));

          message4 = new JLabel("", SwingConstants.LEFT);
          message4.setFont(new Font("Arial", Font.PLAIN, 12));
          message4.setBounds(1210, 555, 150, 45);
          message4.setVisible(false);
          bPane.add(message4, new Integer(2));

          message5 = new JLabel("", SwingConstants.LEFT);
          message5.setFont(new Font("Arial", Font.PLAIN, 12));
          message5.setBounds(1210, 600, 150, 45);
          message5.setVisible(false);
          bPane.add(message5, new Integer(2));
     }

     public void setPlayerDice(Player[] players) {
          String[][] diceColorImgs = { { "b1.png", "b2.png", "b3.png", "b4.png", "b5.png", "b6.png" },
                    { "c1.png", "c2.png", "c3.png", "c4.png", "c5.png", "c6.png" },
                    { "g1.png", "g2.png", "g3.png", "g4.png", "g5.png", "g6.png" },
                    { "o1.png", "o2.png", "o3.png", "o4.png", "o5.png", "o6.png" },
                    { "p1.png", "p2.png", "p3.png", "p4.png", "p5.png", "p6.png" },
                    { "r1.png", "r2.png", "r3.png", "r4.png", "r5.png", "r6.png" },
                    { "v1.png", "v2.png", "v3.png", "v4.png", "v5.png", "v6.png" },
                    { "y1.png", "y2.png", "y3.png", "y4.png", "y5.png", "y6.png" },
                    { "w1.png", "w2.png", "w3.png", "w4.png", "w5.png", "w6.png" } };

          String[] colors = { "Blue", "Cyan", "Green", "Orange", "Pink", "Red", "Violet", "Yellow", "White" };

          playerLabels = new JLabel[players.length];

          for (int i = 0; i < players.length; i++) {
               players[i].setDiceImgs(diceColorImgs[i]);
               players[i].diceColor = colors[i];

               if (players.length < 7)
                    players[i].currDiceImg = diceColorImgs[i][0];
               else
                    players[i].currDiceImg = diceColorImgs[i][1];

               playerLabels[i] = new JLabel();
               playerLabels[i].setIcon(new ImageIcon("./images/dice/" + players[i].currDiceImg));
               playerLabels[i].setOpaque(false);
               bPane.add(playerLabels[i], new Integer(3));
               movePlayer(i, 11);
          }
     }



     /* METHODS FOR UPDATING LABELS AND BUTTONS */

     public void updateMessage(String str) {
          message5.setText(message4.getText());
          message5.setVisible(true);

          message4.setText(message3.getText());
          message4.setVisible(true);

          message3.setText(message2.getText());
          message3.setVisible(true);

          message2.setText(message.getText());
          message2.setVisible(true);

          message.setText(str);
          message.setVisible(true);
     }

     public void updatePlayerStats() {
          pID.setText("PLAYER " + Deadwood.currentPlayer.getID() + " (" + Deadwood.currentPlayer.diceColor.toUpperCase() + ")");
          pID.setVisible(true);
          pDollars.setText("Dollars : " + Deadwood.currentPlayer.getDollars());
          pDollars.setVisible(true);
          pCredits.setText("Credits : " + Deadwood.currentPlayer.getCredits());
          pCredits.setVisible(true);
          pRehearsals.setText("Rehearsals : " + Deadwood.currentPlayer.getRehearsalsDone());
          pRehearsals.setVisible(true);
     }

     public void showActionButtons(boolean bool) {
          actionsLabel.setVisible(bool);
          bAct.setVisible(bool);
          bRehearse.setVisible(bool);
          bMove.setVisible(bool);
          bUpgrade.setVisible(bool);
          bSkip.setVisible(bool);
     }

     public void showRoomButtons(boolean bool) {
          adjRoomsLabel.setVisible(bool);

          if (bool) {
               ArrayList<Room> adjRooms = Deadwood.currentPlayer.getRoom().getAdjacentRooms();
               int offset = 0;

               for (int i = 0; i < adjRooms.size(); i++) {
                    roomButtons[adjRooms.get(i).getRoomNum()].setBounds(1210, 30 + offset, 150, 25);
                    roomButtons[adjRooms.get(i).getRoomNum()].setBackground(Color.white);
                    roomButtons[adjRooms.get(i).getRoomNum()].setVisible(true);
                    offset += 30;
               }

               cancelRoom.setBounds(1210, 30 + offset, 150, 25);
               cancelRoom.setVisible(true);

          } else {
               TSbutton.setVisible(bool);
               SHbutton.setVisible(bool);
               Cbutton.setVisible(bool);
               Hbutton.setVisible(bool);
               MSbutton.setVisible(bool);
               Jbutton.setVisible(bool);
               GSbutton.setVisible(bool);
               Rbutton.setVisible(bool);
               Bbutton.setVisible(bool);
               Sbutton.setVisible(bool);
               Obutton.setVisible(bool);
               Tbutton.setVisible(bool);
               cancelRoom.setVisible(bool);
          }
     }

     public void showRoleButtons(boolean bool) {
          rolesLabel.setVisible(bool);

          if (bool) {
               ArrayList<Role> roles = Deadwood.currentPlayer.getRoom().getAvailableRoles(Deadwood.currentPlayer.getRank());
               int offset = 0;

               for (int i = 0; i < roles.size(); i++) {
                    roleButtons[i].setText(roles.get(i).getTitle());
                    roleButtons[i].setBounds(1210, 30 + offset, 150, 25);
                    roleButtons[i].setBackground(Color.white);
                    roleButtons[i].setVisible(true);
                    offset += 30;
               }

               cancelRole.setBounds(1210, 30 + offset, 150, 25);
               cancelRole.setVisible(true);

          } else {
               bRole1.setVisible(bool);
               bRole2.setVisible(bool);
               bRole3.setVisible(bool);
               bRole4.setVisible(bool);
               bRole5.setVisible(bool);
               bRole6.setVisible(bool);
               bRole7.setVisible(bool);
               cancelRole.setVisible(bool);
          }
     }

     public void showUpgradeButtons(boolean bool) {
          upgradesLabel.setVisible(bool);
          upgradesTitle.setVisible(bool);
          r2.setVisible(bool);
          r3.setVisible(bool);
          r4.setVisible(bool);
          r5.setVisible(bool);
          r6.setVisible(bool);
          r2D.setVisible(bool);
          r3D.setVisible(bool);
          r4D.setVisible(bool);
          r5D.setVisible(bool);
          r6D.setVisible(bool);
          r2C.setVisible(bool);
          r3C.setVisible(bool);
          r4C.setVisible(bool);
          r5C.setVisible(bool);
          r6C.setVisible(bool);
          cancelUpgrade.setVisible(bool);
     }

     public void showRoomScene(int rNum) {
          roomBackLabels[rNum].setVisible(false);
     }

     public void removeShot() {
          Room room = Deadwood.currentPlayer.getRoom();
          ArrayList<JLabel> roomShotLabels = shotLabels.get(room.getRoomNum());

          for (int i = roomShotLabels.size() - 1; i >= 0; i--) {
               if (roomShotLabels.get(i).isVisible()) { 
                    roomShotLabels.get(i).setVisible(false);
                    if (i != 0) return;
                    else break;
               }
          }

          roomLabels[room.getRoomNum()].setVisible(false);
          Deadwood.board.completeRoom(room);

          if (Deadwood.board.getRoomsRemaining() == 1) {
               Deadwood.endDay();
               if (Deadwood.currDay <= Deadwood.numDays) {
                    JOptionPane.showMessageDialog(this, "Day " + (Deadwood.currDay-1) + " has ended. Reseting board and moving all players to trailers.");

                    for (Player player : Deadwood.players) {
                         movePlayer(player.getID() - 1, 11);
                    }     

                    resetRoomBackLabels();
                    resetRoomLabels();
                    resetShotMarkers();

               } else {
                    for (Player player : Deadwood.players) {
                         movePlayer(player.getID() - 1, 11);
                    }

                    Deadwood.calcScores();
                    JOptionPane.showMessageDialog(this, Deadwood.getScoreString());
                    System.exit(0);
               }
          }
     }

     public void resetShotMarkers() {
          for (int i = 0; i < shotLabels.size(); i++) {
               for (int j = 0; j < shotLabels.get(i).size(); j++) {
                    shotLabels.get(i).get(j).setVisible(true);
               }
          }
     }

     public void resetRoomLabels() {
          for (int i = 0; i < roomLabels.length; i++) {
               Scene scene = Deadwood.board.getRooms()[i].getCurrentScene();
               roomLabels[i].setIcon(new ImageIcon("images/cards/" + scene.getSceneImg()));
               roomLabels[i].setVisible(true);
          }
     }

     public void resetRoomBackLabels() {
          for (int i = 0; i < roomBackLabels.length; i++) {
               roomBackLabels[i].setVisible(true);
          }
     }

     public void movePlayer(int pNum, int rNum) {
          int[] coords = moveCoords[pNum][rNum];
          playerLabels[pNum].setBounds(coords[0], coords[1], 46, 46);
     }

     public void movePlayerRole(int pNum) {
          Role role = Deadwood.currentPlayer.getRole();
          int[] area = role.getArea();
          int[] roomArea = Deadwood.currentPlayer.getRoom().getRoomArea();

          if (role.isExtra()) playerLabels[pNum].setBounds(area[0], area[1], 46, 46);
          else playerLabels[pNum].setBounds(area[0] + roomArea[0], area[1] + roomArea[1], 46, 46);

          System.out.println("Player " + Deadwood.players[pNum].getID() + " took role : " + role.getTitle());
     }

     public void upgradePlayer(int pNum, int rank) {
          String[] diceImgs = Deadwood.currentPlayer.getDiceImgs();
          Deadwood.currentPlayer.currDiceImg = diceImgs[rank - 1];
          playerLabels[pNum].setIcon(new ImageIcon("images/dice/" + Deadwood.currentPlayer.currDiceImg));
     }

     // moves to next player's turn
     public void nextPlayer() {
          Deadwood.currentPlayer.validTurn = true;
          Deadwood.currentPlayer.myTurn = false;
          Deadwood.findNextPlayer();
          updatePlayerStats();
     }

}