package AdventureModel;

import java.util.List;

import static AdventureModel.Effect.BUFF;
import static AdventureModel.Effect.CUSTOM;

public class Boss extends BasicMonster {

    public String name;

    public Boss(String name,int health, int attack, int defense,int mp, List<SpecialAbility> specialAbilities) {
        super(name,health, attack, defense, mp,specialAbilities);

    }

    @Override
    public void interactWithPlayer(Player player) {
        // Specific interaction logic for Boss
        if (this.health < 30 && this.mp >= 50) {
            this.attack *= 2;
            this.mp -= 50;
        }
        // Check and apply special abilities here
//        for (SpecialAbility ability : this.getSpecialAbilities()) {
//            applySpecialAbility(ability);
//        }
    }

    private List<SpecialAbility> getSpecialAbilities() {return this.specialAbilities;
    }

    private void applySpecialAbility(SpecialAbility ability) {
        switch (ability.getEffect()) {
            case BUFF:
                // Increase defense by 20
                this.setDefense(this.getDefense() + 20);
                break;
            case CUSTOM:
                // If HP < 30, set attack to 200
                if (this.getHealth() < 70) {
                    this.setAttack(70);
                }
                break;
            // Handle other cases...
        }
    }


    // Boss-specific methods...
}
