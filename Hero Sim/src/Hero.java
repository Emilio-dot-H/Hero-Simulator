

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Hero {
	protected String name;
	protected double health;
	protected double strength;
	protected double speed;
	protected double fightIq;
	
	protected double stamina;
	protected double[] hit;
	protected double lostHealth;
	protected HashMap<Integer, ArrayList<Double>> comboMap;
	protected boolean isFighting;
	protected double totalMeleeDmg;
	
	protected ArrayList<Double> jabHits;
	protected ArrayList<Double> crossHits;
	protected ArrayList<Double> hookHits;
	protected ArrayList<Double> uppercutHits;
	protected ArrayList<Double> kickHits;
	
	protected double originalHealth;
	protected double originalStrength;
	protected double originalSpeed;
	
	//unique attributes
	protected double spideySense = 0;
	protected double healingFactor;
	
	public Hero(String name, double health, double strength, double speed, double fightIq) {
		this.name = name;
		this.health = health;
		this.strength = strength;
		this.speed = speed;
		this.fightIq = fightIq;
		hit = new double[3];
		totalMeleeDmg = 0;
		stamina = 0;
		lostHealth = 0;
		//the Ogs for reset
		originalHealth = health;
		originalStrength = strength;
		originalSpeed = speed;
		
		comboMap = new HashMap<Integer,ArrayList<Double>>();
		jabHits = new ArrayList<Double>();
		crossHits = new ArrayList<Double>();
		hookHits = new ArrayList<Double>();
		uppercutHits = new ArrayList<Double>();
		kickHits = new ArrayList<Double>();
		
		if(this.getClass() == Spiderman.class) {
			this.spideySense = 0.30;
		}
		if(this.getClass() == Deadpool.class) {
			this.healingFactor = 0.25;
		}
		
	}
	//return Hero's name
	public String getName() {
		return name;
	}
	//return hit array [dmg, oddNxtTrn, hlthRecovered]
	public double[] getHitStatus() {
		return hit;
	}
	public String printHitStatus() {
		String hitStatus = Arrays.toString(hit);
		return hitStatus;
	}
	//return damage
	public void setDmg(double dmg) {
		hit[0] += dmg;
	}
	public double getDmg() {
		return hit[0];
	}
	//return nextTurnOdds
	public double nextTurnOdds() {
		System.out.println(getName()+"'s NTO called: "+hit[1]);
		return hit[1];
	}
	
	public double recoverHealth() {
		return hit[2];
	}
	//reset hit array
	public void resetHitStatus() {
		hit[0] = 0;	//dmg
		hit[1] = 0;	//next turn odds
		hit[2] = 0;	//recovered health
	}
	public void resetHitAndOdds() {
		hit[0] = 0;	//dmg
		hit[1] = 0;	//next turn odds
	}
	public void loseHealth(double dmg) {
		//System.out.println(getName()+ " lost: "+ dmg+" health");
		health -= dmg;
		lostHealth += dmg;
	}
	public static double rand(double min, double max) {
		return ((Math.random()*((max - min) )) + min);
	}
	//increases the ability to sneak in an extra combo
	public double speedAdvtg() {
		double speedadvg = speed/1000;
		return speedadvg;
	}
	//increases the amount of damage, especially for a more critical combo hit
	public double fightIqAdvtg() {
		double fightadvg = fightIq/1000;
		return fightadvg;
	}
	//staminaDis - diminishes impact of strength and speed and affects percentages in odds
	public double staminaDisadvtg() {
		
		double staminaDisadvantage = stamina/20000;
		strength -= staminaDisadvantage;
		speed -= staminaDisadvantage;
		
		if(strength <= originalStrength/2) {
			strength = rand(originalStrength*0.5,originalStrength*0.75);
		}
		if(speed <= originalSpeed/2) {
			speed = rand(originalSpeed*0.5,originalSpeed*0.75);
		}
		//make sure it doesn't affect more than 14%
		if(stamina >= 2500) {stamina = rand(1250,2000);}
		return staminaDisadvantage;
	}
	
	public void addMeleeDamage(double damage) {
		totalMeleeDmg += damage;
	}
	public double totalMeleeDamage(){
		return totalMeleeDmg;
	}
	public void resetAll() {
		resetHitStatus();
		resetMeleeCombos();
		stamina = 0;
		lostHealth = 0;
		health = originalHealth;
		strength = originalStrength;
		speed = originalSpeed;
	}
	public void resetMeleeCombos() {
		comboMap.clear();
		jabHits.clear();
		crossHits.clear();
		hookHits.clear();
		uppercutHits.clear();
		kickHits.clear();
	}
	public void attackOptions() {
		melee();
	}

	public void melee() {
		System.out.println(getName() +" is meeleing: ");
		int combo = 0; //keeps track of combos
		double move = Math.random();
		double dmg = 0;
		double comboOdds = 0; //odds of performing a combination
		boolean fight = true;
		resetHitAndOdds();
		resetMeleeCombos();
		
		while(fight) {
			//jab is 40% likely
			if(move<.40) {
				//damage produced by jab is in range of 0-25% of full strength + fightIQ
				dmg = (fightIqAdvtg() + rand(0, 0.25)) * strength;
				System.out.println("Jab is thrown: "+ dmg +"dmg");
				comboOdds = rand(0.75,0.85) + speedAdvtg();
				stamina += 2;
				jabHits.add(dmg);
				comboMap.put(1,  jabHits);
			}
			//cross is 25% likely
			if(move < 0.65 && move >= 0.40) {
				//damage produced by jab is in range of 35-55% of full strength
				dmg = (fightIqAdvtg() + rand(0.35, 0.55)) * strength;
				System.out.println("Cross is thrown: "+ dmg +"dmg");
				//punchStats(dmg, fightIqAdvtg(), strength);
				comboOdds = rand(0.65,0.75) + speedAdvtg();
				stamina += 3;
				crossHits.add(dmg);
				comboMap.put(2,  crossHits);
			}
			//hook is 12.5% likely
			if(move >= 0.65 && move < 0.775) {
				//damage produced by jab is in range of 50-80% of full strength
				dmg = (fightIqAdvtg() + rand(0.5, 0.8)) * strength;
				System.out.println("Hook is thrown: "+ dmg+"dmg");
				//punchStats(dmg, fightIqAdvtg(), strength);
				comboOdds = rand(0.55,0.65) + speedAdvtg();
				stamina += 4;
				hookHits.add(dmg);
				comboMap.put(3,  hookHits);
			}
			//uppercut is 12.5% likely
			if(move >= 0.775 && move < 0.9) {
				//damage produced by jab is in range of 75-100% of full strength
				dmg = (fightIqAdvtg() + rand(0.75, 1)) * strength;
				System.out.println("Uppercut is thrown: "+ dmg+"dmg");
				//punchStats(dmg, fightIqAdvtg(), strength);
				comboOdds = rand(0.4, 0.5) + speedAdvtg();
				stamina += 6;
				uppercutHits.add(dmg);
				comboMap.put(4,  uppercutHits);
			}
			//kick is 10% likely
			if(move >= 0.9) {
				dmg = (fightIqAdvtg() + rand(0.85, 1)) * strength;
				System.out.println("Kick is thrown: "+ dmg+"dmg");
				//punchStats(dmg, fightIqAdvtg(), strength);
				comboOdds = rand(0.35,0.45) + speedAdvtg();
				stamina += 8;
				kickHits.add(dmg);
				comboMap.put(5,  kickHits);
			}
			//calculating likely hood of another punch thrown.
			combo++;
			comboOdds = comboOdds - combo*0.05 - staminaDisadvtg();
			//comboStats(comboOdds, combo, staminaDisadvtg());
			setDmg(dmg);
			addMeleeDamage(dmg);
			
			if(Math.random() > comboOdds) {
				fight = false;
			}
			//odds of next move in combo
			move = Math.random();
			//damage
			
		}
//		if(Math.random() > 0.5) {
//			staminaDiminish();
//		}
		//likelihood of getting another turn
		hit[1] = 0.35 - combo*0.03;
	}
	public double fightSense() {
		double fightsense = 0;
		if(Math.random() > 0.49 - fightIqAdvtg()) {
			fightsense = fightIqAdvtg() + speedAdvtg() - staminaDisadvtg();
		}
		return fightsense;
	}
	public double oddsToDodge() {
		double fs = fightSense();
		double total = rand(0,0.5) + fs;
		return total;
	}
	public double siftHit(ArrayList<Double> combo) {
		int pd = 0;
		if(combo.size() == 1) {
			System.out.println("punches dodged: "+ 1);
			return combo.get(0);
		}
		double dmg = 0;
		double odds = 1.0/(combo.size());
		for(int i = 0; i < combo.size(); i++) {
			if(Math.random() <= odds) {
				pd++;
				dmg += combo.get(i);
			}
			odds = 1.0 / (combo.size() - i);
		}
		System.out.println("punches dodged: "+ pd);
		return dmg;
	}
	public void dodge(HashMap<Integer,ArrayList<Double>> comboMap, double otd) {
		hit[2] = 0;
		double move;
		double oddsToDodge = otd;
		System.out.println(getName()+" oddsToDodge: "+ oddsToDodge);
		
		while(!comboMap.isEmpty() && oddsToDodge > 0.33) {
			move = Math.random();
			//JAB DODGE
			if(comboMap.containsKey(1)) {
				if(move + fightSense() > 0.35) {
					System.out.println("Dodged jab");
					hit[2] += siftHit(comboMap.get(1));
					stamina += 1;
				}
				move = Math.random();
			}
			//CROSS DODGE
			if(comboMap.containsKey(2)) {
				if(move + fightSense() > 0.45) {
					System.out.println("Dodged cross");
					hit[2] += siftHit(comboMap.get(2));
					stamina += 1.2;
				}
				move = Math.random();
			}
			//HOOK DODGE
			if(comboMap.containsKey(3)) {
				if(move + fightSense() > 0.65) {
					System.out.println("Blocked hook");
					hit[2] += siftHit(comboMap.get(3));
					stamina += 2;
				}
				move = Math.random();
			}
			//UPPERCUT DODGE
			if(comboMap.containsKey(4)) {
				if(move + fightSense() > 0.65) {
					System.out.println("Dodged uppercut");
					hit[2] += siftHit(comboMap.get(4));
					stamina += 3;
				}
				move = Math.random();
			}
			//KICK DODGE
			if(comboMap.containsKey(5)) {
				if(move + fightSense() > 0.65) {
					System.out.println("Blocked kick");
					hit[2] += siftHit(comboMap.get(5));
					stamina += 4;
				}
				move = Math.random();
			}
			//clear comboHits once we've cycled through all possiblities
			System.out.println(hit[2]);
			comboMap.clear();
		}
		
	}
	public void regen(){
		double regen = 0;
		resetHitStatus();
		
		//is you've lost more health than you have: You can only recover up to 5
		if(lostHealth >= originalHealth/2) {
			regen = lostHealth  * (rand(0.01 , 0.05) + healingFactor);
		}
		//else you can recover up to 10%
		else {
			regen = lostHealth * (rand(0.01, 0.10) + healingFactor);
		}
		strength += (originalStrength - strength)*(rand(0.01,0.10) + healingFactor);;
		speed += (originalSpeed - speed)*(rand(0.01,0.10) + healingFactor);
		System.out.println(getName() +" is REGENERATING by: " + regen + " health points");
		stamina += 30;
		lostHealth -= regen;
		health += regen;
		hit[1] = 0.4 - staminaDisadvtg();
	}

}
