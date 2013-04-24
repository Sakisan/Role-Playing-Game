package rpgGame;

import java.util.HashMap;

public class RpgGame {
	
	private static Hero hero;
	
	private static Monster monster;

	public static void main(String[] args) {

		demoHero();
		demoMonster();
		demoFight();
		
	}

	private static void demoHero(){

		if(hero == null){
			Weapon sword = new Weapon(95, new Weight(7));
			Weapon hammer = new Weapon(95, new Weight(20), new DukatAmount(50));
			Backpack backpack = new Backpack();
			Backpack backpack2 = new Backpack();
	
			hammer.moveTo(backpack);
			backpack2.moveTo(backpack);
	
			HashMap<LocationEquipment,Item> gear = new HashMap<LocationEquipment,Item>();
			gear.put(LocationEquipment.Back, backpack);
			gear.put(LocationEquipment.LeftHand, sword);
	
			hero = new Hero("Frankenstein", 300, 15, gear);
		}
		
		System.out.println("The hero's inventory: \n");
		int i = 1;
		for(LocationEquipment location : LocationEquipment.values()){
			try {
				System.out.print(i+". "+location+": \t");
				Item item = hero.getItem(location);
				if(item == null)
					System.out.println("empty");
				else 
					if(item.getClass() == Backpack.class)
						System.out.println(structuredBackpack(item.toString(),2));
					else System.out.println(item);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			i++;
		}
		System.out.println();
		System.out.println("Total value of the inventory:\t"+hero.getPrice());
		System.out.println();

	}


	private static void demoMonster(){

		if(monster == null){
			int numberOfAnchors = 16;
			monster = new Monster("Het monster van Frankenstein", 120, 30, numberOfAnchors);
		}
		
		System.out.println("The monster's inventory: \n");
		for(int i = 0; i < 21; i++){
			try {
				Item item = monster.getItem(i);
				System.out.print(i+".\t");
				if(item == null)
					System.out.println("empty");
				else 
					if(item.getClass() == Backpack.class)
						System.out.println(structuredBackpack(item.toString(),2));
					else System.out.println(item);
			} catch (IllegalArgumentException e) {
			}
		}

		System.out.println();
		System.out.println("Total value of the inventory:\t"+monster.getPrice());
	}
	
	private static void demoFight(){
		
		Actor first, second;
		
		if(Math.random() < 0.5){
			first = hero;
			second = monster;
		}
		else{
			first = monster;
			second = hero;
		}
		
		System.out.println();
		System.out.println(hero);
		System.out.println(monster);
		System.out.println();
		
		while(!first.isTerminated() && !second.isTerminated()){
			System.out.println(first.getName()+" hits "+second.getName()+".");
			first.hit(second);
			System.out.println(second.getName()+" has "+second.getHitPoints()+"/"+ second.getMaxHitpoints() +" hitpoints.");
			if(!second.isTerminated()){
				System.out.println(second.getName()+" hits "+first.getName()+".");
				second.hit(first);
				System.out.println(first.getName()+" has "+first.getHitPoints()+"/"+ first.getMaxHitpoints() +" hitpoints.");
				
			}
		}
		
		System.out.println();
		if(first.getHitPoints() == 0)
			System.out.println(second.getName()+" wins the fight !");
		else System.out.println(first.getName()+" wins the fight !");
		System.out.println();
		System.out.println(hero);
		System.out.println(monster);
	}

	/**
	 * Transforms the output of the Backpack.toString() method to a more structured output,
	 * to make it more readable.
	 * 
	 * @param  string
	 * 			A string originating from the toString() method of a backpack.
	 * @param	tabs
	 * 			The number of tabs to be added in front.
	 * @return 	A string with the same information, but a more readable structure, 
	 * 			for printing purposes.
	 */
	private static String structuredBackpack(String string, int tabs){		
		String temp = "";
		int extraTabs = 0;
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			if( c != '[' && c != ']' && c != '{')
				temp += c;
			else {
				if(c == '[')
					extraTabs++;
				if(c == ']') 
					extraTabs--;
				if(extraTabs == 1){
					if(c == '[' || c == '{')
						temp += "\n"+tabs(tabs + extraTabs) + c;
					if(c == ']') 
						temp += c +"\n"+tabs(tabs + extraTabs);
				}
				else if (extraTabs == 2){
					if(c == '[' || c == '{')
						temp += "\t"+ c;
					if(c == ']') 
						temp += c +"\n"+tabs(tabs + extraTabs );
				}
				else
					temp += c;
			}
		}
		return temp;
	}

	/**
	 * Returns a string with the specified amount of tabs.
	 * 
	 * @param 	tabs
	 * 			The amount of tabs to be in the returned String.
	 */
	private static String tabs(int tabs){
		String temp = "";
		for(int i = 0; i < tabs; i++){
			temp += "\t";
		}
		return temp;
	}
}
