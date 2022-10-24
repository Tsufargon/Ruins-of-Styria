package main;

import java.io.FileNotFoundException;
import java.util.Scanner;
import mob.Character;
import action.Fight;
import action.Menu;
import action.Shop;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(System.in);
		
		System.out.print("Introduce the hero's name: ");
		Character MC = new Character(sc.nextLine());
		
		Shop shop = new Shop();
		Fight fight = new Fight();
		Menu menu = new Menu(MC);

		menu.startGame(MC, fight, shop);

		sc.close();
	} 
}
