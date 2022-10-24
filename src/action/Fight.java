package action;
import mob.Enemy;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import mob.Character;

public class Fight {
	

	public int runFight(Character c) throws FileNotFoundException {
		int turns = 0;
		Enemy e = new Enemy(c);
		Scanner sc = new Scanner(System.in);

		while(true) {

			do{
				if(turns > 0) {
					System.out.println("-------------------\n");
				}
				turns++;
				String player = String.format("%s, HP: %s", c.getName(), c.getStats()[0]);
				String enemy = String.format("%s, HP: %s", e.getEnemyName(), e.getEnemyStats()[0]);
				System.out.println(player + "\n" + enemy);

				System.out.println("(1) Attack | (2) Use object");
				int option = sc.nextInt();

				if(option == 1) {
					bothTurns(e, c);
				} else {
					if(useObject(c)){
						turnEnemy(e, c);
					} else {
						System.out.println("You have nothing in your inventory!");
						bothTurns(e, c);
					}
				}
			} while(!c.isDead() && !e.isDead());

			if(c.isDead()) {
				System.out.println("You died...");
				return 0;
			} else {
				int gold = goldWon(e, c);
				System.out.println("¡You won!\n+" + gold + " Gold\n\n");
				c.addExp();
				c.addGold(gold);
				return 1;
			}
		}
	}

	private int goldWon(Enemy e, Character c) {
		switch(c.getStats()[4]){
			case 0:
				return ThreadLocalRandom.current().nextInt(e.getEnemyGold(), (int)(e.getEnemyGold()*1.5));
			case 1:
			case 2:
			case 3:
				return ThreadLocalRandom.current().nextInt(e.getEnemyGold(), (int)(e.getEnemyGold()*1.2)) + 1;
			case 4:
			case 5:
			case 6:
			case 7:
				return ThreadLocalRandom.current().nextInt(e.getEnemyGold(), (int)(e.getEnemyGold()*1.5)) + 2;
			case 8:
			case 9:
				return ThreadLocalRandom.current().nextInt(e.getEnemyGold(), (int)(e.getEnemyGold()*1.75)) + 3;
			default:
				return ThreadLocalRandom.current().nextInt(e.getEnemyGold(), (int)(e.getEnemyGold()*2)) + 5;
		}	
	}

	public int priority(Enemy e, Character c) {
		if(e.getEnemyStats()[3] > c.getStats()[3]) {
			return e.getEnemyID();
		} else if (c.getStats()[3] > e.getEnemyStats()[3]) {
			return e.getEnemyID();
		} else if(e.getEnemyStats()[4] > c.getStats()[4]) {
			return e.getEnemyID();
		} else if(c.getStats()[4] > e.getEnemyStats()[4]) {
			return 0;
		}
		
		if(Math.random() >= 0.5) {
			return 0;
		}
		return e.getEnemyID();
		}
	
	public int playerAttack(Enemy e, Character c) {
		int dmg;

		dmg = (int) ((ThreadLocalRandom.current().nextInt(8, 16)*0.1)*(c.getStats()[1]-(e.getEnemyStats()[2]*0.2) + 0.1*(c.getStats()[4] - e.getEnemyStats()[4])));
		if(dmg < 2) {
			dmg = 1;
		}
		e.recalcLife(dmg);
		return dmg;
	}
	
	public int enemyAttack(Enemy e, Character c) {
		int dmg;
		dmg = (int) ((ThreadLocalRandom.current().nextInt(8, 16)*0.1)*(e.getEnemyStats()[1]-(c.getStats()[2]*0.2) + 0.1*(e.getEnemyStats()[4] - c.getStats()[4])));
		if(dmg < 2) {
			dmg = 1;
		}
		c.recalcLife(dmg);
		return dmg;
	}
	
	public void bothTurns(Enemy e, Character c) {
		if(priority(e, c) == 0) {
			turnPlayer(e, c);
			if(!e.isDead()) {
				turnEnemy(e, c);
			}
		} else {
			turnEnemy(e, c);
			if(!c.isDead()) {
				turnPlayer(e, c);
			}
		}
	}
	
	public void turnPlayer(Enemy e, Character c) {
		System.out.println(c.getName() + " attacks! Damage: " + playerAttack(e, c));
	}
	
	public void turnEnemy(Enemy e, Character c) {
			System.out.println(e.getEnemyName() + " attacks! Damage: " + enemyAttack(e, c));
	}
	
	public boolean useObject(Character c) {
		if(c.getInventory().getConsumables().length == 0) {
			return true;
		}

		System.out.println("What do you want to use?");
		Scanner sc = new Scanner(System.in);
			for(int i = 0; i < c.getInventory().getConsumables().length; i++) {
				System.out.println("[" + (i+1) + "] " + c.getInventory().getConsumables()[i].getName() + "(x" + c.getInventory().getConsumables()[i].getUses() + ")");
			}
			System.out.print("> ");
			int whatObject = sc.nextInt();
			c.getInventory().useObject(c.getInventory().getConsumables()[whatObject-1]);
		return false;
	}

}
