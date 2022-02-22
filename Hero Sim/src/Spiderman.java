public class Spiderman extends Hero{

	protected double totalWebDmg;
	protected double totalMeleeDmg;
	public Spiderman(String name, double health, double strength, double speed ,double fightIq){
		super(name, health, strength, speed, fightIq);
		totalWebDmg = 0;
		totalMeleeDmg = 0;
	}
	
	/*
	 * Does 1 of 2 things:
	 * 1) Mobility/Escape/Swing: Little to no damage but almost guarantees a follow-up move (90%)
	 * 2) Sticky/Spray: Quarter damage but 45% of follow-up move
	*/
	
	public double fightSense() {
		double fightsense = 0;
		double spideyAdvtg = 0;
		double trigger = 0;
		//the chance of spideysense and fightsense is (0.85*0.51): 43%
		if(Math.random() > 0.15) {
			spideyAdvtg = rand(0, spideySense);
			trigger++;
		}
		if(Math.random() > 0.49 - fightIqAdvtg()) {
			fightsense = fightIqAdvtg() + speedAdvtg() - staminaDisadvtg() + spideyAdvtg;
			trigger++;
		}
		if(trigger == 2) {
			System.out.println("SPIDEY SENSE");
		}
		return fightsense;
	}
	public void webSling() {
		System.out.println("Spiderman is web slinging: ");
		double move = Math.random();
		double dmg = 0;
		double nextTurnOdds = 1;
		
		resetHitStatus();
		//swing evasive tactic
		if(move < 0.25) {
			nextTurnOdds = 0.85;
			System.out.println("Webswing");
			regen();
			stamina += 2;
		}
		//colateral damage - web uses nearby object to inflict damage
		if(move >= 0.25 && move < 0.5) {
			dmg = rand(0.30, 0.80) * strength;
			nextTurnOdds = 0.5;
			System.out.println("Web-Collateral: "+dmg);
			stamina += 3;
		}
		//disarm does 0-10% damage and is used to escape confrontation
		if(move >= 0.5 && move < 0.75) {
			dmg = rand(0, 0.10) * strength;
			nextTurnOdds = 0.9;
			System.out.println("Web-Disarm: "+dmg);
			regen();
			stamina += 2;
		}
		//webstrike does between 10-30% of full strength damage
		if(move >=0.75){
			dmg = rand(0.35, 0.55) * strength;
			nextTurnOdds = 0.75;
			System.out.println("Webstrike: "+ dmg);
			stamina += 3;
		}
		addWebDamage(dmg);
		hit[0] = dmg;
		hit[1] = nextTurnOdds - staminaDisadvtg();
	}
	public void addWebDamage(double damage) {
		totalWebDmg += damage;
	}
	public double totalDamage(){
		return totalWebDmg + totalMeleeDmg;
	}
	public void attackOptions() {
		double move = Math.random();
		if(move < 0.5) {
			melee();
		}
		if(move >= 0.5) {
			webSling();
		}
	}
	
}
