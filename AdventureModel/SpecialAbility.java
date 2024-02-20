package AdventureModel;

public class SpecialAbility {
    private String name;
    private Effect effect;

    public SpecialAbility(String name, Effect effect) {
        this.name = name;
        this.effect = effect;
    }

    public Effect getEffect() {return effect;
    }

    // Getters and setters...
}
