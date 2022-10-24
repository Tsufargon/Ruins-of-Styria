package object;
import mob.Character;

public class Consumable {
	int id;
	int uses;
	int amount;
	String name;
	int[] effects;
	
	
	public Consumable(int num, String name, int[] efectos, int uses) {
		this.id = num;
		this.name = name;
		this.effects = efectos;
		this.uses = uses;
		this.amount = 1;
	}

    //Getters
    public int getId(){
		return this.id;
	}
    
    public int getUses(){
		return this.uses;
	}
	
    public int getAmount() {
		return this.amount;
	}

    public String getName(){
		return this.name;
	}
	
    public int[] getEffects() {
		return this.effects;
	}


    //Amount modifier
    public void boughtAnother() {
		this.amount++;
	}
	
	public boolean applyObject(Character c) {
        c.addStats(this.effects);
        return used();
	}

    public boolean used() {
		this.amount--;
		return this.amount == 0;
	}
}