import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rpg.RPGCharacter;

public class RPGTest {

  public static final double MAX_HEALTH = 1000.0;
  private RPGCharacter rpgCharacter;
  private RPGCharacter otherRpgCharacter;

  @Test
  @DisplayName("When creating a character should have 1000 health")
  void createACharacterWith1000Health(){
    givenACharacter();
    assertEquals(MAX_HEALTH, rpgCharacter.getHealth());
  }

  @Test
  @DisplayName("When creating a character should have level 1")
  void createACharacterWithLevel1(){
    givenACharacter();
    assertEquals(1, rpgCharacter.getLevel());
  }

  @Test
  @DisplayName("When creating a character should be alive")
  void whenCreatingCharacterShouldBeAlive(){
    givenACharacter();
    assertTrue(rpgCharacter.isAlive());
  }

  @Test
  @DisplayName("Characters can Deal Damage to Characters")
  void charactersCanDealDamageToOtherCharacters(){
    givenACharacter();
    givenOtherCharacter();

    rpgCharacter.damage(otherRpgCharacter, 100);

    assertEquals(900.0, otherRpgCharacter.getHealth());
  }

  @Test
  @DisplayName("When damage received exceeds current Health, Health becomes 0 and the character dies")
  void whenDamageExceedsCurrentHealthCharacterDies(){
    givenACharacter();
    givenOtherCharacter();

    rpgCharacter.damage(otherRpgCharacter, 1001);

    thenOtherCharacterIsDead();
  }

  @Test
  @DisplayName("Healing cannot raise health above 1000")
  void healingCannotExceedMaxHealth(){
    rpgCharacter = new RPGCharacter(500.0);

    rpgCharacter.heal(rpgCharacter);

    assertEquals(MAX_HEALTH, rpgCharacter.getHealth());
  }

  @Test
  @DisplayName("Dead characters cannot be healed")
  void deadCharactersCannotBeHealed(){
    givenACharacter();
    givenOtherCharacter();

    rpgCharacter.damage(otherRpgCharacter, MAX_HEALTH);
    rpgCharacter.heal(otherRpgCharacter);

    thenOtherCharacterIsDead();
  }

  @Test
  @DisplayName("A Character cannot Deal Damage to itself")
  void characterCannotDealDamageToItself(){
    givenACharacter();

    rpgCharacter.damage(rpgCharacter, MAX_HEALTH);

    assertEquals(MAX_HEALTH, rpgCharacter.getHealth());
  }

  @Test
  @DisplayName("A Character can Heal itself")
  void characterCanHealItself(){
    rpgCharacter = new RPGCharacter(500.0);

    rpgCharacter.heal(rpgCharacter);

    assertEquals(MAX_HEALTH, rpgCharacter.getHealth());
  }

  @Test
  @DisplayName("A Character cannot heal other")
  void characterCannotHealOther(){
    givenACharacter();
    otherRpgCharacter = new RPGCharacter(500.0);

    rpgCharacter.heal(otherRpgCharacter);

    assertEquals(500.0, otherRpgCharacter.getHealth());
  }

  @Test
  @DisplayName("If the target is 5 or more Levels above the attacker, Damage is reduced by 50%")
  void target5OrMoreLevelsBelowDamageIs50Less(){
    RPGCharacter target = new RPGCharacter(500.0, 6);
    RPGCharacter attacker = new RPGCharacter(500.0, 1);

    attacker.damage(target, 100);

    assertEquals(450, target.getHealth());
  }

  @Test
  @DisplayName("If the target is 5 or more levels below the attacker, Damage is increased by 50%")
  void target5OrMoreLevelsAboveDamageIs50More(){
    RPGCharacter target = new RPGCharacter(500.0, 1);
    RPGCharacter attacker = new RPGCharacter(500.0, 6);

    attacker.damage(target, 100);

    assertEquals(350, target.getHealth());
  }

  private void givenOtherCharacter() {
    otherRpgCharacter = new RPGCharacter();
  }

  private void givenACharacter() {
    rpgCharacter = new RPGCharacter();
  }

  private void thenOtherCharacterIsDead() {
    assertAll(
        () -> assertEquals(0, otherRpgCharacter.getHealth()),
        () -> assertFalse(otherRpgCharacter.isAlive())
    );
  }
}
