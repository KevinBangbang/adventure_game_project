package AdventureModel;

import java.util.List;

public class Mage extends BasicMonster {

    public Mage(String name,int health, int attack, int defense,int mp, List<SpecialAbility> specialAbilities) {
        super(name,health, attack, defense, mp, specialAbilities);
    }

    @Override
    public void interactWithPlayer(Player player) {
        if (this.mp >= 25) {
            this.attack += (int)(this.attack * 0.3);
            this.mp -= 25;
        }
        // Specific interaction logic for Mage
    }

    // Mage-specific methods...
}
