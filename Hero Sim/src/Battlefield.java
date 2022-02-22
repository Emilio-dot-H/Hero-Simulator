
public class Battlefield {
	public static double spiderwins;
	public static double deadwins;
	public static double h1wins;
	public static double h2wins;
	
	
	protected Spiderman spiderman;
	protected Deadpool deadpool;
	protected Hero h1;
	protected Hero h2;
	
//	public Battlefield(Spiderman h1, Deadpool h2) {
//		this.spiderman = h1;
//		this.deadpool = h2;
//		spiderwins = 0;
//		deadwins = 0;
//	}
	public Battlefield(Hero h1, Hero h2) {
		this.h1 = h1;
		this.h2 = h2;
		h1wins = 0;
		h2wins = 0;
	}
	/*
	 * Fighting will be seperated into 4 sections: h1-attack, h1-defense, h2-attack, h2-defense
	 * */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Spiderman peter = new Spiderman("Peter Parker", 2000, 90, 90, 40);
//		Deadpool wade = new Deadpool("Wade Wilson", 3500, 30, 30, 85);
//		Battlefield b = new Battlefield(peter, wade);
//		//b.fight();
//		for(int i = 0; i<1; i++) {
//			b.fight();
//		}
//		System.out.println(getChampion());
		Spiderman hero1 = new Spiderman("Peter Parker", 1500, 90, 90, 30);
		Deadpool hero2 = new Deadpool("Wade Wilson", 2000, 25, 25, 80);
		Battlefield b = new Battlefield(hero1, hero2);
		for(int i = 0; i < 1; i++) {
			b.fight(hero1, hero2);
		}
		System.out.println(b.getWinner());
		
	}
	public void fight(Hero h1, Hero h2) {
		double coinflip = Math.random();
		if(coinflip>0.50) {
			h1.isFighting = true;
			h2.isFighting= false;
		}
		else {
			h1.isFighting = false;
			h2.isFighting= true;
		}
		while(h1.health>0 && h2.health>0) {
			if(h1.isFighting) {
				h1.attackOptions();
				h2.dodge(h1.comboMap, h2.oddsToDodge());
				double oddsToBeat = Math.random();
				//assign deadpool's turn
				if(oddsToBeat > h1.nextTurnOdds()) {
					h1.isFighting = false;
					h2.isFighting = true;
				}
			}
			if(h2.isFighting) {
				h2.attackOptions();
				h1.dodge(h2.comboMap, h1.oddsToDodge());
				double oddsToBeat = Math.random();
				//assign deadpool's turn
				if(oddsToBeat > h2.nextTurnOdds()) {
					h2.isFighting = false;
					h1.isFighting = true;
				}
			}
			h1.loseHealth(h2.getDmg() - h1.recoverHealth());
			h2.loseHealth(h1.getDmg() - h2.recoverHealth());
			h1.resetHitStatus();
			h2.resetHitStatus();
		}
		setWinner(h1, h2);
		h1.resetAll();
		h2.resetAll();
	}
	public void setWinner(Hero h1, Hero h2) {
		if(h1.health>h2.health) {
			h1wins++;
			System.out.println(h1.getName() + "  wins");
		}
		else {
			h2wins++;
			System.out.println(h2.getName() + "  wins");
		}
	}
	public String getWinner() {
		String champ;
		double winrate;
		if(h1wins>h2wins) {
			winrate = (h1wins/(h1wins+h2wins))*100;
			champ = h1.getName() + " won " + h1wins + " to " +h2wins+ " winrate: " + winrate+"%";
			return champ;
		}
		else {
			winrate = (h2wins/(h2wins+h1wins))*100;
			champ = h2.getName() + " won " + h2wins + " to " +h1wins+ " winrate: " + winrate+"%";
		}
		return champ;
	}

	

}
