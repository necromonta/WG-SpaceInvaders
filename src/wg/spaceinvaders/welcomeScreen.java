package wg.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author anagy
 */
public class welcomeScreen implements ActionListener {
    
    Player currentPlayer;
    ArrayList<Player> players;
    JButton[] menuButtons = new JButton[4];
    String[] menuTexts = {"Play", "Upgrades", "Profile", "Quit"};
    String[] upgradeTexts = {"1000sc", "1000sc", "1000sc", "1000sc"};
    String[] upgradeDescription = {"Gives you 5 dmg | Current: ", "Improves your firerate | Current: ", "Gives you 50 health | Current: ", "Regens 1 hp/sec | Current: "};
    JButton[] upgradeButtons = new JButton[4];
    JLabel[] upgradeLabels = new JLabel[4];
    JPanel upgradePanel = new JPanel();
    JButton back = new JButton();
    JLabel money = new JLabel();
    public static JFrame welcomeScreenFrame = new JFrame();
    
    welcomeScreen(Player currentPlayer, ArrayList<Player> players) throws InterruptedException, IOException {
        
        this.currentPlayer = currentPlayer;
        this.players = players;
        menuButtons();
        
        upgradeProperties();
        
        Update();
        
        frame();
        
    }
    
    public void frame() throws IOException {
        
        welcomeScreenFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("pictures\\welcomeScreenBG.png")))));
        welcomeScreenFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        welcomeScreenFrame.setIconImage(new ImageIcon("spaceship.png").getImage());
        welcomeScreenFrame.setSize(886, 600);
        welcomeScreenFrame.setResizable(false);
        welcomeScreenFrame.setLayout(null);
        welcomeScreenFrame.setVisible(true);
        welcomeScreenFrame.add(back);
        welcomeScreenFrame.add(money);
        upgradePanel.setBounds(0, 0, 886, 600);
        upgradePanel.setVisible(false);
        upgradePanel.setBackground(Color.gray);
        welcomeScreenFrame.add(upgradePanel);
        
        back.setBounds(0, 500, 100, 50);
        back.setText("Back");
        back.setVisible(false);
        upgradePanel.setLayout(null);
        buttonProperties(back);
        
        money.setBounds(600, 500, 200, 50);
        money.setVisible(false);
        
        for (int i = 0; i < menuButtons.length; i++) {
            welcomeScreenFrame.add(menuButtons[i]);
        }
        
    }
    
    public void menuButtons() {
        
        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i] = new JButton();
            menuButtons[i].setBounds(300, 200 + 80 * i, 300, 60);
            menuButtons[i].setText(menuTexts[i]);
            buttonProperties(menuButtons[i]);
        }
        
    }
    
    public void buttonProperties(JButton button) {
        Font font = new Font("", Font.PLAIN, 30);
        
        button.setFont(font);
        button.setForeground(Color.black);
        button.addActionListener(this);
        button.setOpaque(false);
        // button.setContentAreaFilled(false);
        button.setLayout(null);
        button.setBorderPainted(false);
        button.setFocusable(false);
    }
    
    public void upgradeProperties() {
        for (int i = 0; i < upgradeButtons.length; i++) {
            //buttons
            upgradeButtons[i] = new JButton();
            buttonProperties(upgradeButtons[i]);
            upgradeButtons[i].setText(upgradeTexts[i]);
            upgradeButtons[i].setBounds(50, 50 + 80 * i, 180, 50);

            //labels
            upgradeLabels[i] = new JLabel();
            upgradeLabels[i].setBounds(250, 50 + 80 * i, 250, 50);

            //frame
            upgradePanel.add(upgradeButtons[i]);
            upgradePanel.add(upgradeLabels[i]);
            
        }
    }
    
    public void Update() {
        upgradeLabels[0].setText(upgradeDescription[0] + currentPlayer.dmg);
        upgradeLabels[1].setText(upgradeDescription[1] + currentPlayer.firerate);
        upgradeLabels[2].setText(upgradeDescription[2] + currentPlayer.hp);
        upgradeLabels[3].setText(upgradeDescription[3] + currentPlayer.hpRegen);
        money.setText("Space Coins: " + currentPlayer.spaceCoin);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            upgradePanel.setVisible(false);
            back.setVisible(false);
            money.setVisible(false);
            
        }
        for (int i = 0; i < menuButtons.length; i++) {
            if (menuButtons[i] == e.getSource()) {
                switch (i) {
                    case 0:
                        try {
                        new MyFrame(currentPlayer, players);
                    } catch (Exception ex) {
                            System.out.println("problem");
                    }
                    welcomeScreenFrame.setVisible(false);
                    break;
                    case 1:
                        upgradePanel.setVisible(true);
                        money.setText("SpaceCoin:" + currentPlayer.spaceCoin);
                        back.setVisible(true);
                        money.setVisible(true);
                        
                        break;
                    case 2:
                        System.out.println("Profile stats");
                        System.out.println("Coming soon");
                        break;
                    case 3:
                        try {
                        currentPlayer.saveData(players, currentPlayer);                        
                    } catch (IOException ex) {
                        
                    }
                    
                    System.exit(0);
                    break;
                    default:
                        throw new AssertionError();
                }
                
            }
        }
        for (int i = 0; i < upgradeButtons.length; i++) {
            if (upgradeButtons[i] == e.getSource()) {
                switch (i) {
                    case 0:
                        if (currentPlayer.spaceCoin > 999) {
                            currentPlayer.dmg += 5;
                            currentPlayer.spaceCoin -= 1000;
                            Update();
                        }
                        break;
                    case 1:
                        if (currentPlayer.spaceCoin > 999) {
                            if (currentPlayer.firerate == 10) {
                                
                            } else {
                                currentPlayer.firerate -= 10;
                                currentPlayer.spaceCoin -= 1000;
                                Update();
                            }
                            
                        }
                        break;
                    case 2:
                        if (currentPlayer.spaceCoin > 999) {
                            currentPlayer.hp += 50;
                            currentPlayer.spaceCoin -= 1000;
                            Update();
                        }
                        break;
                    case 3:
                        if (currentPlayer.spaceCoin > 999) {
                            currentPlayer.hpRegen += 1;
                            currentPlayer.spaceCoin -= 1000;
                            Update();
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }
        
    }
    
}
