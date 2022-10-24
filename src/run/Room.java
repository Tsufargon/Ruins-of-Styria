package run;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import action.Fight;
import action.Shop;
import mob.Character;

public class Room {
    private int number;
    private int type;
    private int numEnemies;
    private boolean boss;
    private boolean shop;
    private boolean object;
    private boolean completed;

    public Room(){
        this.completed = false;
        this.numEnemies = 0;
        this.boss = false;
        this.shop = false;
        this.object = false;
    }

    public Room(int t) {
        buildRoom(t);
    }

    public int getPosition() {
        return this.number;
    }

    public int getType() {
        // 0 - Empty
        // 1 - 1 Enemy
        // 2 - 2 Enemies
        // 3 - Object
        // 4 - Shop
        // 5 - Boss
        return this.type;
    }

    public boolean getStatus() {
        return this.completed;
    }

    public void setStatus() {
        this.completed = true;
    }

    public void setType(int t) {
        this.type = t;
    }

    private void buildRoom(int t) {
        this.type = t;
        this.completed = false;

        switch(t) {
            case(1):
                this.numEnemies = 1;
                break;
            case(2):
                this.numEnemies = 2;
                break;
            case(3):
                this.object = true;
                break;
            case(4):
                this.shop = true;
                break;
            case(5):
                this.boss = true;
                break;
            default:
                break;
        }
    }

    public boolean eventInRoom(Character c, Fight f, Shop s) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        switch (this.type){
            case 1:
                System.out.println("An enemy attacks!");
                f.runFight(c);
                if(!c.isDead()){
                    this.completed = true;
                }
                break;
            case 2:
                System.out.println("Two enemies attack!");
                f.runFight(c);
                if(!c.isDead()){
                    this.completed = true;
                } else {
                    break;
                }
                f.runFight(c);
                if(!c.isDead()){
                    this.completed = true;
                }
                break;
            case 3:
                System.out.println("There could be an object here...");
                this.completed = true;
                break;
            case 4:
                System.out.print("There's a shop next to the door!\nDo you want to use it?\n> ");
                if(sc.nextLine().toLowerCase().equals("y")){
                    s.useShop(c);
                }   
                this.completed = true;
                break;
            case 5:
                System.out.println("An ominous presence fills the room.");
                this.completed = true;
                break;
            default:
                System.out.println("This room appears to be empty...");
                this.completed = true;
        }
        return this.completed;
    }
    
}