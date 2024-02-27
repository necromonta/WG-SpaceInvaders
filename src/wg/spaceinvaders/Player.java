package wg.spaceinvaders;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author anagy
 */
public class Player {

    String username;
    String password;
    int spaceCoin;
    int dmg;
    int firerate;
    int projectileSpeed;
    int hp;
    int hpRegen;
    int wave;
    boolean canShoot;

    public Player(String username, String password, String spaceCoin, String dmg, String firerate, String projectileSpeed, String hp, String hpRegen, String wave) {
        this.username = username;
        this.password = password;
        this.spaceCoin = Integer.valueOf(spaceCoin);
        this.dmg = Integer.valueOf(dmg);
        this.firerate = Integer.valueOf(firerate);
        this.projectileSpeed = Integer.valueOf(projectileSpeed);
        this.hp = Integer.valueOf(hp);
        this.hpRegen = Integer.valueOf(hpRegen);
        this.wave = Integer.valueOf(wave);
    }

    public Player() {
        this.spaceCoin = 5000;
        this.dmg = 300;
        this.firerate = 1000;
        this.projectileSpeed = 10;
        this.hp = 500;
        this.hpRegen = 0;
        this.canShoot = false;
        this.wave = 0;
    }

    String data() {
        return username + "," + password + "," + spaceCoin + "," + dmg + "," + firerate + "," + projectileSpeed + "," + hp + "," + hpRegen + "," + wave + "\n";
    }

    public void saveData(ArrayList<Player> players, Player currentPlayer) throws IOException {

        //first acc
        if (players.isEmpty()) {
            FileWriter w = new FileWriter("data.txt");
            w.write(data());
            w.close();
        }

        //If acc exists
        boolean alreadyExists = false;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).username.equals(currentPlayer.username)) {
                players.get(i).dmg = currentPlayer.dmg;
                players.get(i).hp = currentPlayer.hp;

                players.get(i).spaceCoin = currentPlayer.spaceCoin;
                players.get(i).firerate = currentPlayer.firerate;
                players.get(i).projectileSpeed = currentPlayer.projectileSpeed;
                players.get(i).hpRegen = currentPlayer.hpRegen;
                players.get(i).wave = currentPlayer.wave;
                alreadyExists = true;
                break;
            }
        }
        //If new acc
        if (!alreadyExists) {
            players.add(currentPlayer);
        }

        //Array to txt
        FileWriter w = new FileWriter("data.txt");
        for (int j = 0; j < players.size(); j++) {
            w.write(players.get(j).data());
        }
        w.close();

    }

}
