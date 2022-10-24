package action;

import object.Consumable;
import java.util.Scanner;
import mob.Character;

public class Shop {
	private Consumable[] inventory;
	private boolean full = false;;
	
	public Shop() {
		if(!full) {
			fillShop();
		}
	}
	
	public void useShop(Character c) {
		
		displayShop(c);
		
		boolean cont = true;
		Scanner sc = new Scanner(System.in);
		String answer;
		int option;
		
		while(cont) {
			System.out.print("What would you like to buy?\n> ");
			answer = sc.nextLine();
			option = translate(answer);
			
			if(option == 7) {
				cont = false;
			} else if(option != 8) {
				if(canAfford(10, c)) {
					buyObject(c, this.inventory[option]);
					cont = false;
				} else {
					System.out.println("You don't have enough money!");
					cont = false;
				}
			} else {
				System.out.println("That ain't here, pal!");
			}
		}
	}

	public Consumable[] getInventory() {
		return this.inventory;
	}
	
	public boolean canAfford(int price, Character c){
		return c.getGold() >= price;
	}

	public void buy(int i, Character c) {
		c.getInventory().addObject(this.inventory[i]);
	}
	
	public int translate(String s) {
		s = s.toUpperCase();
		if (s.equals("E")){
			return 7;
		}
		String[][] answers = {{"HP POTION", "LIFE POTION", "HP", "LIFE", "1"},
							{"ATTACK POTION", "A", "DMG", "DPS", "ATTACK", "ATT", "2"},
							{"DEFENSE POTION", "D", "DF", "DEFENSE", "DEF", "3"},
							{"SPEED POTION", "S", "SPEED", "SPE", "4"},
							{"LUCK POTION", "L", "LUCK", "5"},
							{"DBR", "RDB", "DEBUG RESET", "RESET DEBUG", "6"},
							{"DBM", "MDB", "DEBUG MAX", "MAX DEBUG", "7"}};
		
		for(int i = 0; i < answers.length; i++){
			for(int j = 0; j < answers[i].length; j++){
				if(s.equals(answers[i][j])){
					return i;
				}
			}
		}
		return 8;
	}
	
	
	public void fillShop() {
		int[] efH = {30, 0, 0, 0, 1};
		int[] efA = {0, 5, 0, 0, 1};
		int[] efD = {0, 0, 5, 0, 1};
		int[] efS = {0, 0, 0, 5, 1};
		int[] efL = {-1, -1, -1, -1, 10};
		int[] efR = {-999, -999, -999, -999, -999};
		int[] efM = {999, 999, 999, 999, 999};
		
		this.inventory = new Consumable[7];
		inventory[0] = new Consumable(00, "HP Potion", efH, 3);
		inventory[1] = new Consumable(01, "Attack Potion", efA, 3);
		inventory[2] = new Consumable(02, "Deffense Potion", efD, 3);
		inventory[3] = new Consumable(03, "Speed Potion", efS, 3);
		inventory[4] = new Consumable(04, "Luck Potion", efL, 3);
		inventory[5] = new Consumable(90, "Debug Reset Potion", efR, 11);
		inventory[6] = new Consumable(91, "Debug Max Potion", efM, 11);
		
		this.full = true;
	}
	
	public void displayShop(Character c) {
		System.out.println("Gold: " + c.getGold()
						+ "\nVendor: Greetings, adventurer! Here is what you can buy:");
		for(int i = 0; i < this.inventory.length; i++) {
			System.out.println("[" + (i+1) + "] " + this.inventory[i].getName() + " (10G)");
		}
		System.out.println("[E] Exit");
	}
	
	public void buyObject(Character c, Consumable o) {
		c.getInventory().addObject(o);
        int i = c.getInventory().getConsumableIndex(o);
		if(c.getInventory().getConsumables()[i].getAmount() > 1){
			System.out.println(o.getName() + " added to your inventory! Now you have " + (c.getInventory().getConsumables()[i].getAmount()));
		} else {
			System.out.println(o.getName() + " added to your inventory!");
		}
		c.remGold(10);
	}
	
}
