package Button;

import AdventureModel.Setting;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class GameButton extends Button {
    Setting setting;
    ImageView imageView;
    ImageView imageViewHover;
    public GameButton(Setting setting)
    {
        this.setting = setting;
    }
    public GameButton()
    {}

    public void makeButtonAccessible(String name, String shortString) {
        this.setAccessibleRole(AccessibleRole.BUTTON);
        this.setAccessibleRoleDescription(name);
        this.setAccessibleText(shortString);
        this.setFocusTraversable(true);
    }

}
