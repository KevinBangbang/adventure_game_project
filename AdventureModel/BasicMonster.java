package AdventureModel;

import java.util.List;

public class BasicMonster implements Monster {
    protected int mp; //mana of a monster
    protected int health; //health of a monster
    protected int attack; //attack of a monster
    protected int defense; //defense of a monster
    protected List<SpecialAbility> specialAbilities; //list of special ability

    private int originalHealth; //the max health of monster
    private int originalMana;//the max mana of monster

    public String name; //name of a monster
    /**
     * BasicMonster Constructor
     * __________________________
     * Initializes attributes
     *
     * @param name the name of the monster
     * @param health health of the monset
     * @param
     * @param
     * @
     */
    public BasicMonster(String name,int health, int attack, int defense, int mp, List<SpecialAbility> specialAbilities) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAbilities = specialAbilities;
        this.mp = mp;
        this.originalHealth = this.health/* 初始生命值 */;
        this.originalMana = this.mp;
        this.name = name;
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Default interaction logic, can be overridden by subclasses
    }

    // Common methods like takeDamage(), performAttack(), etc.
    @Override
    public void attack(Player player) {
        // Implement attack logic
        int damageDealt = this.attack - player.getDefense();
        if (damageDealt < 0){
            player.setDefense(-damageDealt);}
        else { player.setDefense(0);
        }
        if (damageDealt < 0) damageDealt = 0;
        player.takeDamage(damageDealt);
        //int damageDealt = this.attack - player.getDefense();
        //if (damageDealt < 0) damageDealt = 0;
        //player.takeDamage(damageDealt);
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) this.health = 0;
    }

    @Override
    public boolean isDefeated() {
        return this.health <= 0;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getMana() {
        return this.mp;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }
    @Override
    public boolean isAlive() {
        return this.health > 0;
    }
    @Override
    public void resetState() {
        this.health = this.originalHealth;
        this.mp = this.originalMana;
    }


    public void setDefense(int i) {this.defense = i;
    }

    @Override
    public String getname() {
        return this.name;
    }

    protected void setAttack(int i) {this.attack = i;
    }
}
