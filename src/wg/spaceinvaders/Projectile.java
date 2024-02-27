package wg.spaceinvaders;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author anagy
 */
public class Projectile {

    JPanel shape;
    int tempLocation;
    int move;
    boolean hit;

    public Projectile(int a) {
        this.shape = new JPanel();
        this.tempLocation = a;
        this.move = 0;
        this.hit = false;
        shape.setBackground(Color.red);
        shape.setBounds(2000, 2000, 10, 15);
    }

}
