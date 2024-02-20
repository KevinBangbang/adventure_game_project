package AdventureModel;

import java.util.List;

public class MonsterBuilder {
    private int health;
    private int attack;
    private int defense;
    private List<SpecialAbility> specialAbilities;

    private String name;

    private int mp;

    public MonsterBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    // Other setters...

    public BasicMonster buildBasicMonster() {
        return new BasicMonster(name,health, attack, defense, mp, specialAbilities);
    }

    // Methods for building other types of monsters...
}
