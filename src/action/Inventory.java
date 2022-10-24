package action;

import java.util.Scanner;

import mob.Character;
import object.Consumable;
import object.Trinket;

public class Inventory {
	
	private Consumable[] consumables;
	private Trinket[] trinkets;
	private Character cha;
	
	public Inventory(Character c) {
		consumables = new Consumable[5];
		trinkets = new Trinket[5];
		this.cha = c;
	}

    //Getters
	public Consumable[] getConsumables() {
		return this.consumables;
	}

	public Trinket[] getTrinkets() {
		return this.trinkets;
	}

    public int getConsumableIndex(Consumable o){
        for(int i = 0; i < this.consumables.length; i++){
            if(this.consumables[i] == o) {
                return i;
            }
        }
        return 0;
    }

    public int getTrinketIndex(Trinket t){
        for(int i = 0; i < this.trinkets.length; i++){
            if(this.trinkets[i] == t) {
                return i;
            }
        }
        return 0;
    }

	//Built-in graphical inventory display
    public void displayInventory() {
        if(this.consumables[0] != null){
            System.out.println("---------------");
            System.out.println("In your consumables inventory you have:");
            int i = 0;
            while(this.consumables[i] != null){
                System.out.println("· " + this.consumables[i].getName() + "(x" + this.consumables[i].getAmount() + ")");
                i++;
            }
        }
        int i = 0;
        System.out.println("---------------");
        System.out.println("In your trinkets inventory you have:");
        while(this.trinkets[i] != null){
            if(this.trinkets[i].getHeldStatus()){
                System.out.println("· " + this.trinkets[i].getName() + " [E]");
            } else {
                System.out.println("· " + this.trinkets[i].getName());
            }
            i++;
        }
        
        System.out.println("---------------");
    }

    //Consumables modifiers
	public void addObject(Consumable o) {
        int i = 0;
        if(this.consumables[4] != null){
            System.out.println("Your inventory is full!");
        } else {
            while((this.consumables[i] != null) && (this.consumables[i].getId() != o.getId())){
                i++;
            }
            if(this.consumables[i].getId() == o.getId()){
                this.consumables[i].boughtAnother();
            } else {
                this.consumables[i] = o;
            }
        }
	}

	public void useObject(Consumable o) {
        if(o.applyObject(this.cha)){
            removeObject(o);
        }

	}
	
    public void removeObject(Object o) {

        int index = 0;
        while(this.consumables[index] != o){
            index++;
        }

		for(int i = 0; i < this.consumables.length; i++) {
			if (i > index) {
                this.consumables[i-1] = this.consumables[i];
			}
		}
        index = this.consumables.length;
        boolean removed = false;
        while(!removed){
            if(this.consumables[index] != null){
                this.consumables[index] = null;
                removed = true;
            }
            index--;
        }
	}
    
    //Trinkets modifiers
    public void addObject(Trinket t) {
        int i = 0;
        while(this.trinkets[i] != null){
            i++;
        }
        this.trinkets[i] = t;
	}
	
	public void useObject(Trinket t) {	
		if(unequipTrinket()) {
			equipTrinket(t);
		}
	}

	public void equipTrinket(Trinket t) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you wish to equip the " + t.getName() + ", \"" + t.getDescription() + "\"?\n[1] Yes\n[2] No\n> ");
		if(sc.nextInt() == 1){
			this.cha.equipTrinket(t);
			System.out.println("You have equipped the " + cha.getTrinketHeld().getName());
		}
	}

	public boolean unequipTrinket(){
		Scanner sc = new Scanner(System.in);
		if(this.cha.getIsHoldingItem()){
			System.out.print("Do you wish to unequip " + this.cha.getTrinketHeld().getName() + "?\n[1] Yes\n[2] No\n> ");
			if(sc.nextInt() == 1){
				System.out.println("You have unequipped the " + cha.getTrinketHeld().getName());
				int life = cha.getStats()[0];
                this.cha.unnequipTrinket();
				if(cha.getStats()[0] <= 5){
                    cha.setLife(life);
				}
				return true;		
			}
			return false;
		}
		return true;
	}

    //Main inventory function for menu
	public void useInventory(){
		Scanner sc = new Scanner(System.in);
		boolean cont = true;

		do{
            if(this.getConsumables()[0] == null && this.getTrinkets()[0] == null){
                System.out.println("Yout inventory is empty!\n---------------");
                cont = false;
            } else {
                displayInventory();
                
                System.out.print("What do you want to do?\n[1] Use consumables\n[2] Equip/Unnequip trinkets\n[3] Exit\n> ");

                switch(sc.nextInt()){
                    case 1:
                        if(this.consumables[0] != null){
                            System.out.println("What consumable do you wish to use?");
                            int i = 0;
                            while(this.consumables[i] != null){
                                System.out.println("[" + (1+i) + "x] " + this.consumables[i].getName() + "(x" +  this.consumables[i].getUses() + ")");
                                i++;
                            }
                            System.out.print("> ");
                            int option = sc.nextInt()-1;
                            if(option <= i){
                                useObject(this.consumables[option]);
                            } else {
                                System.out.println("Incorrect option");
                            }
                        } else {
                            System.out.println("You do not have consumables to use!");
                        }
                        break;
                    case 2:
                        System.out.println("What trinket do you wish to equip?");
                        int i = 0;
                        while(this.trinkets[i] != null){
                            if(this.trinkets[i].getHeldStatus()){
                                System.out.println("[" + (1+i) + "] " + this.trinkets[i].getName() + "(E)");
                            } else {
                                System.out.println("[" + (1+i) + "] " + this.trinkets[i].getName());
                            }
                            i++;
                        }
                        System.out.print("> ");
                        int option = sc.nextInt()-1;
                            if(option <= i){
                                useObject(this.trinkets[option]);
                            } else {
                                System.out.println("Incorrect option");
                            }
                        break;
                    default:
                        cont = false;
                }
            }
		} while(cont);
	}
}
