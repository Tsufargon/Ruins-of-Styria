package action;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import mob.Character;
import object.Trinket;
import run.Room;
import run.Run;

public class Menu {
        private String separator;

    public Menu(Character c) {
        this.separator = "-------------------";

    }

    public void startGame(Character c, Fight f, Shop s) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        Trinket[] trinketsAvailable = allTrinkets();
        c.getInventory().addObject(trinketsAvailable[ThreadLocalRandom.current().nextInt(0, 5)]);

        do {
        System.out.print("You are in a safe room... What do you want to do?\n[1] Enter the dungeon\n[2] Check stats\n[3] Check inventory\n[4] Leave\n> ");
        
        switch(sc.nextInt()){
            case 1:
                Run run = new Run(c.getLevel());
                run.startRun(c, f, s);
                break;
            case 2:
                c.showStats();
                System.out.println(this.separator);
                break;
            case 3:
                c.getInventory().useInventory();
                break;
            case 4:
                continuar = false;
                break;
            default:
                System.out.println(this.separator);
            }
     } while(!c.isDead() && continuar);

    }

    public Trinket[] allTrinkets(){
        int[] effH = new int[] {15, 0, 0, 0, 0};
        int[] effA = new int[] {0, 5, 0, 0, 3};
        int[] effD = new int[] {5, 0, 5, 0, 0};
        int[] effS = new int[] {0, 3, 0, 5, 1};
        int[] effL = new int[] {1, 1, 1, 1, 5};

        Trinket[] trinkets = new Trinket[]{new Trinket(10, "Gold necklace", effH, "A golden necklace that grants HP to the wielder"), 
                                           new Trinket(11, "Longsword", effA, "A strong weapon that empowers your Attack"), 
                                           new Trinket(12, "Sturdy Armour", effD, "An impenetrable suit of armour that will help you endure more attacks"), 
                                           new Trinket(13, "Light Boots", effS, "A pair of boots capable of granting speed to whoever wears them"), 
                                           new Trinket(14, "Silver Ring", effL, "A mysterious ring that it's said to bring good fortune..."), };
        
        return trinkets;
    }
}
