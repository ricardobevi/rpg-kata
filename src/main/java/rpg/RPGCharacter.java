package rpg;

public class RPGCharacter {

  public static final double MAX_HEALTH = 1000.0;
  public static final double MIN_HEALTH = 0.0;
  public static final int START_LEVEL = 1;

  private int level;
  private double health;

  public RPGCharacter(){
    this(MAX_HEALTH, START_LEVEL);
  }

  public RPGCharacter(double startHealth){
    health = startHealth;
    level = START_LEVEL;
  }

  public RPGCharacter(double startHealth, int startLevel){
    health = startHealth;
    level = startLevel;
  }

  public double getHealth() {
    return health;
  }

  public int getLevel() {
    return level;
  }

  public boolean isAlive() {
    return health > 0;
  }

  public void damage(RPGCharacter otherRpgCharacter, double damageAmount) {
    if(!this.equals(otherRpgCharacter)) {
      otherRpgCharacter.damageWith(damageAmount, this.level);
    }
  }

  public void heal(RPGCharacter otherRpgCharacter) {
    if(this.equals(otherRpgCharacter)) {
      otherRpgCharacter.healWith(MAX_HEALTH);
    }
  }

  private void healWith(double healAmount) {
    health = isAlive() ? Math.max(health, healAmount) : MIN_HEALTH;
  }

  private void damageWith(double damageAmount, int othersLevel) {

    if(othersLevel < level && level - othersLevel >= 5){
      health = Math.max(health - damageAmount / 2, MIN_HEALTH);
    } else if(othersLevel > level && othersLevel - level >= 5){
      health = Math.max(health - damageAmount * 1.5, MIN_HEALTH);
    } else {
      health = Math.max(health - damageAmount, MIN_HEALTH);
    }

  }
}
