package MovePackage;

import AdventureModel.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MoveDown implements Move{
    Player player;
    Button button;
    public MoveDown(Player player, Button button){
        this.player = player;
        this.button = button;
    }
    public void execute(){
        if (this.player.getCurrentPos()[1]<=setting.height- setting.playerHeight){
        this.player.getCurrentPos()[1] += setting.moveSpeed;
        transition.setByY(setting.moveSpeed);
        transition.play();}
    }
}
