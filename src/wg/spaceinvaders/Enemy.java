package wg.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author anagy
 */
public class Enemy {
    JLabel shape;
    JProgressBar bar;
    int width;
    int height;
    int hp;
    int maxHp;
    String type;
    int wave;
    boolean direction;

    public Enemy(int wave) throws IOException {
         shape = new JLabel();
           this.wave = wave;
           this.direction=false;
        if (wave%3==0&&wave!=0) {
            boss();
        }else if(wave%5==0&&wave!=0){
           String chances[] = {"king","queen", "knight", "bishop","rook"};
           
        type = chances[(int) (Math.random() * (chances.length))];
        switch (type) {
            case "king":
                king();
                break;
            case "knight":
                 knight();
                break;
            case "bishop":
                bishop();
                break;
                case "rook":
                rook();
                break;
                case "queen":
                queen();
                break;
            default:
                System.out.println("Error chess enemies");
                System.out.println(type);
        } 
        }
        else{
                              
           String chances[] = {"strong", "strong", "scout", "scout", "normal", "normal", "normal", "normal", "normal", "normal"};
        type = chances[(int) (Math.random() * (chances.length))];
        switch (type) {
            case "strong":
                strong();
                break;
            case "scout":
                scout();
                break;
            case "normal":
                normal();
                break;
            default:
                System.out.println("Error defa enemies");
                System.out.println(type);
        } 
        }
        shape.setBounds(((int) (Math.random() * (7) )) * 250, 0, width, height);
        maxHp = hp;
        bar();
        scalesImage();
    }
 
    public Enemy(String type,int wave) throws IOException{
        this.type=type;
        shape = new JLabel();
        this.wave = wave;
        pawn();
        maxHp = hp;
         this.direction=false;
         shape.setBounds(((int) (Math.random() * (7) )) * 250, 10, width, height);
        bar();
        scalesImage();
    }
    
    private void normal() {
        width = ((int) (Math.random() * (19) + 15)) * 10;
        height = ((int) (Math.random() * (19) + 15)) * 10;
        hp = (width + height) * 2 + wave * 25;
    }

    private void strong() {
        width = ((int) (Math.random() * (25) + 20)) * 10;
        height = ((int) (Math.random() * (25) + 20)) * 10;
        hp = (width + height) * 3 + wave * 50;
    }

    private void scout() {
        width = ((int) (Math.random() * (14) + 10)) * 10;
        height = ((int) (Math.random() * (14) + 10)) * 10;
        hp = width + height + wave * 10;
    }
    
    private void boss(){
            width=200;
            height=300;
            hp=5000+wave*300;;
            type="boss";
           
    }  
    
    private void rook(){
            width=100;
            height=200;
            hp=750+wave*100;
}
    
    private void king(){
            width=137;
            height=275;
            hp=1000+wave*50;        
    }
    
    private void bishop(){
            width=100;
            height=200;
            hp=750+wave*100;   
   }
    
    private void knight(){
            width=100;
            height=200;
            hp=750+wave*50;
    }
    
    private void queen(){
             width=60;
            height=150;
            hp=400+wave*50;   
    }
    
    private void pawn(){
            width=70;
            height=125;
            hp=150+wave*100;
            type="pawn";
    }
    
    public void bossSpecial(){
        int rng = 300;
                    if (shape.getX() - rng < 0) {
                        shape.setLocation(shape.getX() + 600, shape.getY());

                    } else {
                        shape.setLocation(shape.getX() - rng, shape.getY());
                    }
    }
    
    public void rookSpecial(){
        int rng = ((int) (Math.random() * (2) ));
                    if (rng==0) {
                        shape.setLocation(0, shape.getY());
                    } else {
                        shape.setLocation(1920-width, shape.getY());
                    }
    }
    
    private void bar() {
        bar = new JProgressBar(0, hp);
        bar.setValue(0);
        bar.setBorderPainted(false);
        bar.setBounds(0, 0, width, 30);
        bar.setStringPainted(true);
        if (width<100) {
            bar.setFont(new Font("MV Boli", Font.BOLD, 10)); 
        }else{
           bar.setFont(new Font("MV Boli", Font.BOLD, 15)); 
        }
        
        bar.setForeground(Color.black);
        shape.add(bar);
    }
    
    public void move(){
        shape.setLocation(shape.getX(),shape.getY() + 1);
    }
    
    private void scalesImage() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("pictures\\" + type + ".png"));
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        shape.setIcon(icon);
    }

}
