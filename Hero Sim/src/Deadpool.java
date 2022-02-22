
public class Deadpool extends Hero{
	//protected double healingFactor;
	
	public Deadpool(String name, double health, double strength, double speed, double fightIq){
		super(name, health, strength, speed, fightIq);
	}
	public void attackOptions() {
		double move = Math.random();
		if(move <= 0.75) {
			melee();
		}
		if(move > 0.75) {
			regen();
			
		}
	}

}
