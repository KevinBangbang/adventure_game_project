package MovePackage;


import AdventureModel.Setting;
import javafx.animation.TranslateTransition;

public interface Move {
    public void execute();
    Setting setting = new Setting();
    TranslateTransition transition = new TranslateTransition();

}
