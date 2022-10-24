package mob;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy {
	private String name;
	private int[] stats;
	private int id;
	private String title;
	private int goldCarried;
	
	public Enemy(Character c) throws FileNotFoundException {
		this.stats = randomStats(c);
		this.name = randomName();
	}
	
	public Enemy(String n, Character c) {
		this.name = n;
		this.stats = randomStats(c);		
	}

	public Enemy(int[] s) throws FileNotFoundException{
		this.name = randomName();
		this.stats = s;
	}

	public String getEnemyName(){
		return this.name;
	}

	public int[] getEnemyStats(){
		return this.stats;
	}

	public int getEnemyID(){
		return this.id;
	}

	public int getEnemyGold() {
		return this.goldCarried;
	}
	
	public void recalcLife(int h) {
		this.stats[0] -= h;
		if(this.stats[0] < -1) {
			this.stats[0] = -1;
		}
	}
	
	public boolean isDead() {
		return this.stats[0] < 0;
	}
	
	public void setLife(int h) {
		this.stats[0] = h;
	}

	public int[] randomStats(Character c){
		int[] newStats = new int[5];
		int[][] posStats = enemyClass(c.getLevel());

		newStats[0] = ThreadLocalRandom.current().nextInt(posStats[1][0], posStats[1][1]); // HP
		newStats[1] = ThreadLocalRandom.current().nextInt(posStats[2][0], posStats[2][1]); //DMG
		newStats[2] = ThreadLocalRandom.current().nextInt(posStats[3][0], posStats[3][1]); //DEF
		newStats[3] = ThreadLocalRandom.current().nextInt(posStats[4][0], posStats[4][1]); //SPE
		newStats[4] = ThreadLocalRandom.current().nextInt(posStats[5][0], posStats[5][1]); //LUCK

		getClassName(posStats[0][0]);

		return newStats;
	}

	public  String randomName() throws FileNotFoundException{
		java.net.URL url = getClass().getResource("names.txt");
		File names = new File(url.getPath());
	    String result = null;
		int rand = ThreadLocalRandom.current().nextInt(0, 18240);
		int n = 0;
		Scanner sc;
	    for(sc = new Scanner(names); sc.hasNext(); ){
	        String line = sc.nextLine();
	        if(rand == n){
	           result = line;
			}   
			n++;
	    }
		sc.close();
	    return (this.title + " " + result);      
	}

	private int[][] enemyClass(int level) {
		int numG = ThreadLocalRandom.current().nextInt(1, 7);
		int[][] statsC;


		switch(level){
			case 1:
				statsC = new int[][] {{0}, {25, 50}, {7, 10}, {7, 10}, {7, 10}, {0, 2}};
				break;
			case 2:
				if(numG <= 3){
					statsC = new int[][] {{0}, {35, 60}, {7, 10}, {7, 10}, {7, 10}, {0, 2}};
				} else if(numG <= 5){
					statsC = new int[][] {{1}, {60, 80}, {8, 13}, {8, 13}, {8, 13}, {0, 3}};
				} else {
					statsC = new int[][] {{2}, {85, 105}, {12, 16}, {12, 16}, {9, 16}, {1, 4}};
				}
				break;
			case 3:
				if(numG <= 2){
					statsC = new int[][] {{0}, {45, 70}, {7, 10}, {7, 10}, {7, 10}, {0, 2}};
				} else if(numG <= 4){
					statsC = new int[][] {{1}, {70, 90}, {8, 13}, {8, 13}, {8, 13}, {0, 3}};
				} else {
					statsC = new int[][] {{2}, {90, 110}, {12, 16}, {12, 16}, {9, 16}, {1, 4}};
				}
				break;
			case 4:
				if(numG == 1){
					statsC = new int[][] {{0}, {55, 80}, {7, 10}, {7, 10}, {7, 10}, {0, 2}};
				} else if(numG <= 3){
					statsC = new int[][] {{1}, {80, 100}, {8, 13}, {8, 13}, {8, 13}, {0, 3}};
				} else {
					statsC = new int[][] {{2}, {110, 120}, {12, 16}, {12, 16}, {9, 16}, {1, 4}};
				}
				break;
			default:
				if(numG <= 2){
					statsC = new int[][] {{1}, {95, 116}, {8, 13}, {8, 13}, {8, 13}, {0, 3}};
				} else {
					statsC = new int[][] {{2}, {125, 151}, {12, 16}, {12, 16}, {9, 16}, {1, 4}};
				}

		}
		return statsC;
	}

	public void getClassName(int n){
		switch(n){
			case 0:
			this.title = "Novice";
			this.id = 0;
			this.goldCarried = 5;
			break;
		case 2:
			this.title = "Grand Warrior";
			this.id = 2;
			this.goldCarried = 10;
			break;
		default:
			this.title = "Warrior";
			this.id = 1;
			this.goldCarried = 7;
		}
	}
}
