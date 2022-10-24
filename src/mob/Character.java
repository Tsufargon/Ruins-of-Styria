package mob;

import java.util.Scanner;

import action.Inventory;
import object.Consumable;
import object.Trinket;

public class Character {
	private String name;
	private int[] stats;
	private Inventory inventory;
	private int gold, exp, level;
	private boolean hasTrinketHeld;
	private Trinket trinketHeld;
	private int maxHP;
	private int[][][] activeEffects;
	
	public Character(String nombre) {
		this.name = nombre;
		int[] tempStats = {100, 10, 10, 10, 1};
		this.stats = tempStats;
		this.inventory = new Inventory(this);
		this.gold = 10;
		this.level = 1;
		this.exp = 0;
		this.hasTrinketHeld = false;
        this.trinketHeld = null;
		this.maxHP = this.stats[0];
		this.activeEffects = new int[5][5][1];
	}

	//Getters
	public String getName() {
		return this.name;
	}

	public int[] getStats() {
		return this.stats;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public int getGold() {
		return this.gold;
	}

	public int getLevel() {
		return level;
	}

	public boolean getIsHoldingItem() {
		return this.hasTrinketHeld;
	}

	public Trinket getTrinketHeld() {
		return this.trinketHeld;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

    //Build-in graphical stats display
    public void showStats() {
		int xp = (int)(Math.pow(2, this.level) - this.exp);
 		String state = String.format("%s\nLVL: %s\t\tExp: %s (%s to next level)\n%s G\nHP: %s/%s\nAtt: %s\nDef: %s\nSpe: %s\nLu: %s", this.name, this.level, this.exp, xp, this.gold, this.stats[0], this.maxHP, this.stats[1], this.stats[2], this.stats[3], this.stats[4]);
		System.out.println(state + "\n-------------------");
    }
    
    //Gold modifiers
    public void addGold(int n) {
		this.gold += n;
	}
	
	public void remGold(int n) {
		this.gold -= n;
	}

    //Exp/LVL modifier
	public void addExp() {
		this.exp++;
		if(this.exp == (int) Math.pow(2, level)){
			this.level++;
			this.exp = 0;
		}
	}

    //Personal Consumable inventory modifiers
    public void applyActiveEffect(Consumable o) {
		for(int i = 0; i < this.stats.length; i++){
			this.stats[i] += o.getEffects()[i];
		}
	}

	public void unapplyActiveEffect(Consumable o){
		for(int i = 0; i < this.stats.length; i++){
			if(this.stats[i] - o.getEffects()[i] > 0){
				this.stats[i] -= o.getEffects()[i];
			}
		}
	}

    public void newActiveEffect(Consumable o) {
		for(int i = 0; i < this.activeEffects.length; i++){
			if(this.activeEffects[i][0] == o.getEffects()){
				this.activeEffects[i][1][0] += o.getUses();
			} else{
				if(this.activeEffects[i][0] == null){
					this.activeEffects[i][0] = o.getEffects();
					this.activeEffects[i][1][0] = o.getUses();
					applyActiveEffect(o);
				}
			}
		}
	}

	public void removeActiveEffect(Consumable o) {

		for(int i = 0; i < this.activeEffects.length; i++){
			if(this.activeEffects[i][0] == o.getEffects()){
				this.activeEffects[i][0] = null;
				this.activeEffects[i][1][0] = 0;
				unapplyActiveEffect(o);
			}
		}
	}

    //Personal Trinket inventory modifiers

	public void equipTrinket(Trinket t){
        this.hasTrinketHeld = true;
		this.trinketHeld = t;
        this.trinketHeld.changeHeldStatus();
        addStats(t.getEffects());
	}

	public void unnequipTrinket() {
		this.hasTrinketHeld = false;
		this.trinketHeld.changeHeldStatus();
        int[] negStats = new int[] {-this.trinketHeld.getEffects()[0], -this.trinketHeld.getEffects()[1], -this.trinketHeld.getEffects()[2], -this.trinketHeld.getEffects()[3], -this.trinketHeld.getEffects()[4]};
        addStats(negStats);
        this.trinketHeld = null;
	}
	
    //Stats modifiers

	/*public void reCalcStats(int[] stats) {
		for(int i = 1; i < stats.length; i++) {
			if(stats[i] < 0) {
				stats[i] = 1;
			} else if(stats[i] > 20) {
				stats[i] = 20;
			}
		}
		
		if(stats[0] < -1) {
			stats[0] = -1;
		} else if(stats[0] > 150) {
			stats[0] = 150;
		}
		this.stats = stats;
	}
    */

    public void addStats(int[] stats){
        for(int i = 0; i < stats.length; i++){
            this.stats[i] += stats[i];
        }
        if(stats[0] < -1) {
			stats[0] = -1;
		} else if(stats[0] > 150) {
			stats[0] = 150;
		}
    }

    public void recalcLife(int h) {
		this.stats[0] -= h;
		if(this.stats[0] < -1) {
			this.stats[0] = -1;
		}
	}

    public void setLife(int h) {
		this.stats[0] = h;
	}
    //Avoiding cheesing HP with potions
	public void checkLife(){
		if(this.stats[0] > this.maxHP){
			this.stats[0] = maxHP;
		}
	}
	
	
	public boolean isDead() {
		return this.stats[0] < 0;
	}

	//DEBUG
	public void resetChar(){
		int[] tempStats = {100, 10, 10, 10, 1};
		this.stats = tempStats;
		this.gold = 10;
	}

	public void setStats(int[] newStats) {
		this.stats = newStats;
		this.maxHP = this.stats[0];
	}

	public void menuUseObject() {
		if(this.inventory.getConsumables().length == 0) {
			System.out.println("You have nothing in your inventory!");
		} else {
			System.out.println("What item do you want to use?");
			for(int i = 0; i < this.inventory.getConsumables().length; i++) {
				System.out.println("[" + (i+1) + "] " + this.inventory.getConsumables()[i].getName() + "(x" + this.inventory.getConsumables()[i].getUses() + ")");
			}
			Scanner sc = new Scanner(System.in);
			int whatObject = sc.nextInt()-1;
			this.inventory.useObject(this.inventory.getConsumables()[whatObject]);
		}
	}

	

	
	

}
