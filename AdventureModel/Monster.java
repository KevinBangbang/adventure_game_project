package AdventureModel;

import javafx.scene.layout.AnchorPane;
import views.AdventureGameView;

public interface Monster {
    void interactWithPlayer(Player player);
    void takeDamage(int damage);
    void attack(Player player);
    int getDefense();
    boolean isAlive();

    boolean isDefeated();

    int getHealth();

    int getMana();

    void resetState();

    void setDefense(int i);

    String getname();
}
