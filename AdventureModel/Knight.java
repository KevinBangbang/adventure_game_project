package AdventureModel;

import java.util.List;

public class Knight extends BasicMonster {

    public Knight(String name, int health, int attack, int defense,int mp, List<SpecialAbility> specialAbilities) {
        super(name,health, attack, defense, mp,specialAbilities);
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Specific interaction logic for Knight
        if (this.health < 50 && this.mp >= 15) {
            this.defense += (int)(this.defense * 0.5);
            this.mp -= 15;
        }
//        for (SpecialAbility ability : this.getSpecialAbilities()) {
//            applySpecialAbility(ability,player);
//        }
    }
    private List<SpecialAbility> getSpecialAbilities() {return this.specialAbilities;
    }

    // Knight-specific methods...
    private void applySpecialAbility(SpecialAbility ability,Player player) {
        switch (ability.getEffect()) {
            case DAMAGE:
                health = (int) (0.3 * player.getHealth());
                player.setHealth(health);
            case BUFF:
                this.setDefense(this.getDefense() + 20);
                break;
            // Handle other cases...
        }
    }
}
