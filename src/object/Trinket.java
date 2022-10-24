package object;

public class Trinket {
	private int id;
	private String description;
	private String name;
	private boolean held;
	private int[] effects;
	
	
	public Trinket(int num, String nombre, int[] effects, String description) {
		this.id = num;
		this.name = nombre;
		this.effects = effects;
		this.held = false;
		this.description = description;
	}
	
    //Getters
    public int getId(){
		return this.id;
	}

    public String getName(){
		return this.name;
	}
	
    public int[] getEffects() {
		return this.effects;
	}

    public boolean getHeldStatus() {
        return this.held;
    }

    public String getDescription() {
        return this.description;
    }

	public void heldChange() {
		this.held = !this.held;
	}

    public void changeHeldStatus() {
        this.held = !this.held;
    }

}
