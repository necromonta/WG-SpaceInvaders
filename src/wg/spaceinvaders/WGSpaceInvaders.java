package wg.spaceinvaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author anagy
 */
public class WGSpaceInvaders {

    public static void main(String[] args) throws InterruptedException, IOException {
        new Login(loadsData());
    }

    public static ArrayList<Player> loadsData() throws FileNotFoundException {
        ArrayList<Player> players = new ArrayList();
        File file = new File("data.txt");

        if (file.length() > 0) {

            String line;
            FileReader r = new FileReader(file);

            Scanner sc = new Scanner(r);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                Player temp = new Player(line.split(",")[0], line.split(",")[1], line.split(",")[2], line.split(",")[3], line.split(",")[4], line.split(",")[5], line.split(",")[6], line.split(",")[7], line.split(",")[8]);
                players.add(temp);

            }

        }

        return players;
    }

}
