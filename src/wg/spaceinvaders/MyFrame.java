package wg.spaceinvaders;

//Imports
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import static wg.spaceinvaders.welcomeScreen.welcomeScreenFrame;

//Class
public class MyFrame implements ActionListener, KeyListener, MouseListener {

    //Variables
    ArrayList<Projectile> projectiles = new ArrayList();
    ArrayList<Enemy> enemies = new ArrayList();
    JFrame frame = new JFrame();
    JLayeredPane layer = new JLayeredPane();
    JLabel bg = new JLabel();
    JPanel gui = new JPanel();
    JLabel guiLabel = new JLabel();
    JLabel characterLabel = new JLabel();
    JPanel menu = new JPanel();
    JButton[] menuButtons = new JButton[4];
    String[] menuTexts = {"Next wave", "Upgrade", "Menu", "Exit and save"};
    Player currentPlayer;
    ArrayList<Player> players;
    Robot bot;
    int time = 0;
    int enemiesDestroyed = 0;
    int enemiesCreated = 0;
    int tempHp=2;
    int waveLength = 30;
    int timeBetweenSpawns = 4;
    boolean bossFight = false;
    boolean botToggle = false;

    //Main method
    MyFrame(Player currentPlayer, ArrayList<Player> players) throws IOException, UnsupportedAudioFileException, LineUnavailableException, AWTException {
        this.players = players;
        this.currentPlayer = currentPlayer;
        tempHp = currentPlayer.hp;
        bot = new Robot();

        objects();

        timers();

    }

    //Complex methods
    public void objects() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        player();

        gui();

        bgPic();

        menu();

        bgMusic();

        layer();

        frame();
    }

    //Complicated methods
    public void shooting() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        soundEffect("shoot");

        Projectile temp = new Projectile(characterLabel.getX() + 5);
        projectiles.add(temp);
        layer.add(projectiles.get(projectiles.size() - 1).shape, Integer.valueOf(1));

    }

    //Simple methods
    public void layer() {
        layer.setBounds(0, 0, 1920, 1080);
        layer.add(characterLabel, Integer.valueOf(1));
        layer.add(gui, Integer.valueOf(1));
        layer.add(bg, Integer.valueOf(0));
        layer.add(menu, Integer.valueOf(2));
    }

    public void menu() {
        Border blackline = BorderFactory.createLineBorder(Color.red, 15);
        int x = 100;
        int y = 0;
        for (int i = 0; i < 4; i++) {
            menuButtons[i] = new JButton();
            menuButtons[i].setBounds(x, 50 + 200 * y, 250, 150);
            menuButtons[i].setBorder(blackline);
            menuButtons[i].setText((i + 1) + "." + menuTexts[i]);
            menuButtons[i].setLayout(null);
            menu.add(menuButtons[i]);
            buttonProperties(menuButtons[i]);
            y++;
            if (i == 1) {
                x += 350;
                y = 0;
            }

        }

        menu.setLayout(null);
        menu.setVisible(false);
        menu.setBorder(blackline);
        menu.setBackground(Color.green);
        menu.setBounds(480, 270, 960, 540);
    }

    public void bgPic() {
        ImageIcon pic = new ImageIcon("pictures\\bg2.png");
        bg.setBounds(0, 0, 1920, 4320);
        bg.setIcon(pic);
    }

    public void bgMusic() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File file = new File("sounds\\backgroundMusic.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void player() throws IOException {
        ImageIcon spaceship = new ImageIcon("pictures\\spaceship.png");
     
        characterLabel.setIcon(spaceship);
        characterLabel.setBounds(1000, 880, 250, 250);
        characterLabel.addKeyListener(this);
    }

    public void gui() {
        Font font = new Font("", Font.PLAIN, 30);
        gui.setBounds(0, 1030, 1920, 50);
        gui.setBackground(Color.red);
        guiLabel.setFont(font);
        gui.add(guiLabel);
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

    public void savesScreenshot() throws IOException, AWTException {
        Robot bot = new Robot();
        try {
            Thread.sleep(120);

            // It saves screenshot to desired path
            String path = "item.jpg";

            Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            BufferedImage Image = bot.createScreenCapture(capture);

            ImageIO.write(Image, "jpg", new File(path));
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
        }

    }

    public void bindings(KeyEvent e) {
        switch (e.getKeyCode()) {

            case 27:

                break;

        }
    }

    public void timers() {
        TimerTask task1 = new Time();
        TimerTask task2 = new Movement();
        TimerTask task4 = new FireRate();
        TimerTask task5 = new Game();

        Timer timer = new Timer();
        Timer timer2 = new Timer();
        Timer timer4 = new Timer();
        Timer timer5 = new Timer();

        timer.schedule(task1, 0, 1000);
        timer2.schedule(task2, 0, 1);
        timer4.schedule(task4, 0, currentPlayer.firerate);
        timer5.schedule(task5, 0, 10);
        
     
    }

    public void handlesSpawning() throws IOException {
        if (currentPlayer.wave % 3 == 0 && currentPlayer.wave != 0) {
            bossFight = true;
            spawnsEnemy();
        } else {
            if (time < waveLength) {
                if (time % timeBetweenSpawns == 0) {
                    spawnsEnemy();
                }
            }
        }

    }

    public void spawnsEnemy() throws IOException {
        enemies.add(new Enemy(currentPlayer.wave));
        layer.add(enemies.get(enemies.size() - 1).shape, Integer.valueOf(1));
        enemiesCreated++;
    }

    public void soundEffect(String sound) throws UnsupportedAudioFileException, LineUnavailableException, LineUnavailableException, IOException {
        File file = new File("sounds\\" + sound + ".wav");
        AudioInputStream audioStream;
        audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void frame() throws IOException {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SpaceInvader");
        frame.setLayout(null);
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setCursor(1);
        frame.add(layer);
    }

    public void movesBG(int counter) {
        if (counter % 2 == 0) {
            if (bg.getY() == -3239) {
                bg.setLocation(0, 0);
            } else {
                bg.setLocation(0, bg.getY() - 1);
            }
        }

    }

    public void enemyGetsHit(int i, int counter) {

        for (int k = 0; k < projectiles.size(); k++) {
            //Detects hit
            boolean x = false;
            boolean y = false;
            for (int b = 0; b < 10; b++) {
                for (int j = 0; j < 20; j++) {
                    if (projectiles.get(k).shape.getY() + j > enemies.get(i).shape.getY() - 1 && projectiles.get(k).shape.getY() + j < enemies.get(i).shape.getY() + enemies.get(i).height) {
                        y = true;
                    }
                    if (projectiles.get(k).shape.getX() + b > enemies.get(i).shape.getX() - 1 && projectiles.get(k).shape.getX() + b < enemies.get(i).shape.getX() + enemies.get(i).width) {
                        x = true;
                    }
                }
            }

            if (x && y) {
                try {
                    soundEffect("hit");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                enemies.get(i).hp -= currentPlayer.dmg;
                projectiles.get(k).shape.setVisible(false);
                projectiles.get(k).hit = true;
            } else {
                projectiles.get(k).shape.setLocation(projectiles.get(k).tempLocation + 19, 935 - projectiles.get(k).move * 10);
                projectiles.get(k).move++;

            }
            if (projectiles.get(k).shape.getY() < -30 || projectiles.get(k).hit) {
                projectiles.remove(k);
            }
        }

    }

    public void enemyGetsDestroyed(int i) {
        if (enemies.get(i).hp < 1) {
            //Sound effect
            try {
                soundEffect("destroyed");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Money
            currentPlayer.spaceCoin += currentPlayer.wave * 300;
            switch (enemies.get(i).type) {
                case "strong":
                    currentPlayer.spaceCoin += (enemies.get(i).height + enemies.get(i).width) * 3;

                    break;
                case "scout":
                    currentPlayer.spaceCoin += (enemies.get(i).height + enemies.get(i).width) / 2;

                    break;
                case "normal":
                    currentPlayer.spaceCoin += enemies.get(i).height + enemies.get(i).width;

                    break;
                case "boss":
                    currentPlayer.spaceCoin += 25000;
                    currentPlayer.dmg += 25;
                    break;
                case "king":
                    currentPlayer.spaceCoin += 5000;
                    currentPlayer.hp += 50;
                    break;
                case "queen":
                    currentPlayer.spaceCoin += 4000;
                    currentPlayer.hpRegen += 3;
                    break;
                case "bishop":
                    currentPlayer.spaceCoin += 3000;
                    break;
                case "rook":
                    currentPlayer.spaceCoin += 3000;
                    break;
                case "knight":
                    currentPlayer.spaceCoin += 3000;
                    break;
                case "pawn":
                    currentPlayer.spaceCoin += 500;
                    break;
                default:
                    System.out.println("Unknown type");
            }

            enemiesDestroyed++;
            if (time > waveLength - 1 && time != 0 && enemiesCreated == enemiesDestroyed) {
                frame.setCursor(0);
                currentPlayer.wave++;
                menu.setVisible(true);
            } else if (enemies.size() == 1 && enemies.get(i).type.equals("boss")) {
                bossFight = false;
                frame.setCursor(0);
                currentPlayer.wave++;
                menu.setVisible(true);
            }

            enemies.get(i).shape.setVisible(false);
            enemies.remove(i);
        }

    }

    public void enemyHitsBase(int i) {
        if (enemies.get(i).shape.getY() + enemies.get(i).height == gui.getY()) {
            tempHp -= enemies.get(i).hp;
            enemiesDestroyed++;
            enemies.get(i).shape.setVisible(false);
            enemies.get(i).hp = 0;
        }
    }

    public void movesProjectiles(int counter) {
        if (counter % currentPlayer.firerate == 0) {
            for (int k = 0; k < projectiles.size(); k++) {
                projectiles.get(k).shape.setLocation(projectiles.get(k).tempLocation + 19, 935 - projectiles.get(k).move * 10);
                projectiles.get(k).move++;
            }
        }
    }

    public void displaysEnemyhp(int i) {
        enemies.get(i).bar.setValue(enemies.get(i).hp);
        enemies.get(i).bar.setString(enemies.get(i).hp + "/" + enemies.get(i).maxHp + "HP");
    }

    public void autoAims() throws AWTException {

    }

    public void movesEnemies(int counter, int i) {
        // 1 sec = 100 counter
        switch (enemies.get(i).type) {
            case "strong":
                if (counter % 4 == 0) {
                    enemies.get(i).move();
                }
                break;
            case "scout":
                enemies.get(i).move();
                break;
            case "normal":
                if (counter % 2 == 0) {
                    enemies.get(i).move();
                }
                break;
            case "boss":
                if (counter % 4 == 0) {
                    enemies.get(i).move();
                }
                if (counter % 250 == 0) {
                    enemies.get(i).bossSpecial();
                }
                break;
            case "rook":
                if (counter % 350 == 0) {
                    enemies.get(i).rookSpecial();
                } else {
                    enemies.get(i).move();
                }
                break;
            case "bishop":

                if (enemies.get(i).shape.getX() + 1 + enemies.get(i).width > 1920) {
                    enemies.get(i).direction = true;
                }
                if (enemies.get(i).shape.getX() - 1 + enemies.get(i).width < 1) {
                    enemies.get(i).direction = false;
                }

                if (enemies.get(i).direction) {
                    enemies.get(i).shape.setLocation(enemies.get(i).shape.getX() - 2, enemies.get(i).shape.getY() + 1);
                } else {
                    enemies.get(i).shape.setLocation(enemies.get(i).shape.getX() + 2, enemies.get(i).shape.getY() + 1);
                }

                break;
            case "king":
                if (counter % 10 == 0) {
                    enemies.get(i).move();
                }
                if (counter % 400 == 0) {
                    for (int j = 0; j < 2; j++) {
                        try {
                            enemies.add(new Enemy("pawn", currentPlayer.wave));
                        } catch (IOException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        layer.add(enemies.get(enemies.size() - 1).shape, Integer.valueOf(1));
                        enemiesCreated++;
                    }

                }

                break;
            case "pawn":
                if (counter % 2 == 0) {
                    enemies.get(i).move();
                }
                break;
            case "queen":
                if (counter % 10 == 0) {
                    enemies.get(i).move();
                }
                if (counter % 50 == 0) {
                    for (int j = 0; j < enemies.size(); j++) {
                        if (enemies.get(j).hp < enemies.get(j).maxHp) {
                            enemies.get(j).hp += 50;
                        }
                    }
                }

                break;
            case "knight":
                if (counter % 150 == 0) {
                    if (enemies.get(i).shape.getX() + 200 + enemies.get(i).width > 1920) {
                        enemies.get(i).direction = true;
                    }
                    if (enemies.get(i).shape.getX() - 200 + enemies.get(i).width < 1) {
                        enemies.get(i).direction = false;
                    }

                    if (enemies.get(i).direction) {
                        enemies.get(i).shape.setLocation(enemies.get(i).shape.getX() - 200, enemies.get(i).shape.getY() + 50);
                    } else {
                        enemies.get(i).shape.setLocation(enemies.get(i).shape.getX() + 200, enemies.get(i).shape.getY() + 50);
                    }

                }
                break;
            default:
                System.out.println("Error enemy unknown");
        }
    }

    //Timer methods
    class Movement extends TimerTask {

        @Override
        public void run() {
            characterLabel.setLocation((int) MouseInfo.getPointerInfo().getLocation().getX() - 30, 860);

            if (botToggle) {

                for (int i = 0; i < enemies.size(); i++) {
                    bot.mouseMove(enemies.get(i).shape.getX() + 50, 800);
                    bot.delay(60);
                    if (currentPlayer.canShoot) {
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                }

                if (menu.isVisible()) {
                    bot.mouseMove(680, 423);
                    bot.delay(50);
                    currentPlayer.dmg += 5;
                    currentPlayer.hp += 50;
                    currentPlayer.hpRegen += 1;
                    currentPlayer.firerate -= 10;
                    currentPlayer.spaceCoin -= 4000;
                    while (currentPlayer.spaceCoin - 1000 > -1) {
                        currentPlayer.dmg += 5;
                        currentPlayer.spaceCoin -= 1000;
                    }
                    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }

            }
        }
    }

    class Time extends TimerTask {

        public void run() {
            if (!bossFight && menu.isVisible() == false) {
                try {
                    handlesSpawning();
                } catch (IOException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Stats
            guiLabel.setText("Time: " + time + " ,Destroyed: " + enemiesDestroyed + " ,Health: " + tempHp + "/" + currentPlayer.hp + " ,Damage: " + currentPlayer.dmg + " ,Coins: " + currentPlayer.spaceCoin + ", Waves: " + currentPlayer.wave);

            //Lose
            if (tempHp < 1&&welcomeScreenFrame.isVisible()==false) {
                tempHp=currentPlayer.hp;
                welcomeScreenFrame.setVisible(true);
                frame.dispose();
            }

            if (currentPlayer.hp > tempHp + currentPlayer.hpRegen) {
                tempHp += currentPlayer.hpRegen;
            }

            time++;

        }
    }

    class Game extends TimerTask {

        int counter = 0;

        public void run() {;

            movesBG(counter);

            for (int i = 0; i < enemies.size(); i++) {
                if (counter != 0) {
                    displaysEnemyhp(i);
                    movesEnemies(counter, i);
                    enemyGetsHit(i, counter);
                    enemyHitsBase(i);
                    enemyGetsDestroyed(i);
                    movesProjectiles(counter);
                }

            }
            counter++;

        }
    }

    class FireRate extends TimerTask {

        public void run() {
            currentPlayer.canShoot = true;
        }
    }

    //Listener methods
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < menuButtons.length; i++) {
            if (e.getSource() == menuButtons[i]) {
                switch (i) {
                    //next wave
                    case 0:
                        time = 0;
                        frame.setCursor(1);
                        menu.setVisible(false);
                        tempHp=currentPlayer.hp;
                        break;
                    //upgrades
                    case 1:
                        welcomeScreenFrame.setVisible(true);
                        frame.dispose();
                        break;
                    //menu
                    case 2:
                        welcomeScreenFrame.setVisible(true);
                        frame.dispose();
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
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
                    //esc
                    case 27:
                         try {
                        currentPlayer.saveData(players, currentPlayer);
                    } catch (IOException ex) {

                    }
                System.exit(0);
                break;
            //j
            case 74:
                botToggle = !botToggle;
                System.out.println("Toggled: "+botToggle);
                break;
            //k
            case 75:
                currentPlayer.spaceCoin += 300000;

                break;
            //l
            case 76:
                    try {
                savesScreenshot();
            } catch (IOException | AWTException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            default:

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentPlayer.canShoot) {
            currentPlayer.canShoot = false;
            try {
                shooting();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // frame.setCursor(12);
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
