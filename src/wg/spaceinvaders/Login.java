package wg.spaceinvaders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author anagy
 */
public class Login extends JFrame implements ActionListener {

    ArrayList<Player> players;
    Player currentPlayer=new Player();
    JTextField un = new JTextField();
    JTextField pw = new JTextField();
    JComboBox box;
    JButton button = new JButton();
    boolean login = true;

    Login(ArrayList<Player> players) throws IOException {
        this.players = players;
        objects();
    }

    final void objects() throws IOException {
        //textfield
        un.setText("username");
        un.setBounds(25, 50, 200, 50);
        pw.setText("password");
        pw.setBounds(25, 150, 200, 50);

        //button
        button.addActionListener(this);
        button.setBounds(250, 150, 100, 50);
        button.setText("Submit");

        //combobox
        String[] options = {"Login", "Registration"};
        box = new JComboBox(options);
        box.addActionListener(this);
        box.setSelectedIndex(0);
        box.setBounds(250, 50, 100, 50);

        //frame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("pictures\\loginBG.png")))));
        this.setIconImage(new ImageIcon("pictures\\icon.png").getImage());
        this.setBounds(800,500,400, 300);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.add(un);
        this.add(pw);
        this.add(button);
        this.add(box);
    }

    void register() throws InterruptedException, IOException {
        
         
        if (usernameAlreadyExists()||un.getText().equals("username")) {
            un.setText("Username already exists");
        } else {
            currentPlayer.username=un.getText();
             currentPlayer.password=pw.getText();
            new welcomeScreen(currentPlayer,players); 
            this.dispose();
        }

    }
    
    boolean usernameAlreadyExists(){
       boolean exists=false;
         for (int i = 0; i < players.size(); i++) {
             if (players.get(i).username.equals(un.getText())) {
                 exists=true;
                 break;
             }
        }
         return exists;
    }

    void login() throws InterruptedException, IOException {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).username.equals(un.getText())&&players.get(i).password.equals(pw.getText())) {
                currentPlayer = players.get(i);
                    new welcomeScreen(currentPlayer,players); 
                    this.dispose();
            }
        }
         un.setText("Invalid username/");
        pw.setText("/Invalid password");
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (un.getText().equals("username")) {
            }else{
               try {
                    
                    if (box.getSelectedItem().equals("Login")) {
                    login();
                    }else {
                    register(); 
                     }
                    
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
                
            }

        }

    }

