package run;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import action.Fight;
import action.Shop;
import mob.Character;
import object.Trinket;

public class Run {

    private Room[] run;
    private int length;
    private Trinket[] trinkets;

    public Run(int lvl) {
        run = genRun(runDifficulty(lvl));
        this.length = run.length;



    }

    public Room[] getRun(){
        return this.run;
    }

    private int[] runDifficulty(int lvl) {
        int[] rooms = new int[5];
        boolean thereIsShop = false;
        int twoEnemies = 0;
        boolean thereIsEmpty = false;
        boolean thereIsBoss = false;
        int i = 0;

        while(i < rooms.length){
            int poss_number = ThreadLocalRandom.current().nextInt(1, 101);

            while((twoEnemies == 2) && (poss_number <= 20)){
                poss_number += ThreadLocalRandom.current().nextInt(1, 101);
            }
            while(thereIsShop && ((poss_number > 20) && (poss_number < 31))){
                poss_number += ThreadLocalRandom.current().nextInt(1, 101);
            }
            while(thereIsEmpty && ((poss_number > 30) && (poss_number < 36))){
                poss_number += ThreadLocalRandom.current().nextInt(1, 101);
            }

            if( poss_number <= 100){

                if(poss_number < 21){
                    rooms[i] = 2;
                    twoEnemies++;
                } else if(poss_number < 31) {
                    rooms[i] = 4;
                    thereIsShop = true;
                } else if( poss_number < 36) {
                    rooms[i] = 0;
                    thereIsEmpty = true;
                }

                switch(lvl){
                    case 1:
                        if((poss_number > 35) && (poss_number < 96)) {
                            rooms[i] = 1;
                        } else {
                            rooms[i] = 3;
                        }
                        break;
                    case 2:
                        if((poss_number > 35) && (poss_number < 91)) {
                            rooms[i] = 1;
                        } else {
                            rooms[i] = 3;
                        }
                        break;
                    case 3:
                    case 4: {
                        if((poss_number > 35) && (poss_number < 86)) {
                            rooms[i] = 1;
                        } else if((poss_number > 85) && (poss_number < 96)) {
                            rooms[i] = 3;
                        } else if(thereIsBoss){
                            rooms[i] = 0;
                        } else {
                            rooms[i] = 5;
                            thereIsBoss = true;
                        }
                        break;
                    }
                    case 5:
                        if((poss_number > 35) && (poss_number < 86)) {
                            rooms[i] = 1;
                        } else if((poss_number > 85) && (poss_number < 90)){
                            rooms[i] = 3;
                        } else if(thereIsBoss){
                            rooms[i] = 0;
                        } else {
                            rooms[i] = 5;
                            thereIsBoss = true;
                        }
                }
                i++;
            }
        }
        return rooms;
    }

    private Room[] genRun(int[] a) {
        Room[] run = new Room[a.length];

        for(int i = 0; i < a.length; i++) {
            run[i] = new Room(a[i]);
        }

        return run;
    }

    public void startRun(Character c, Fight f, Shop s) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int floors = this.length;

        System.out.print("Would you like to use the shop?\n> ");
        if(sc.nextLine().toLowerCase().equals("y")){
            s.useShop(c);
        }
        for(int i = 0; i < floors; i++){
            System.out.println("You are in floor " + (i+1));
            this.run[i].eventInRoom(c, f, s);
            
            if(c.isDead()){
                break;
            }
            
            System.out.println("Press enter to continue exploring...");
            sc.nextLine();
        }
    }


}
