package MovePackage;

import AdventureModel.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class MoveUp implements Move{

    Player player;
    Button button;

    public MoveUp(Player player, Button button){
        this.player = player;
        this.button = button;
    }
    public void execute(){
        if (this.player.getCurrentPos()[1]+20>=0){
        this.player.getCurrentPos()[1] -= setting.moveSpeed;
        transition.setByY(-setting.moveSpeed);
        transition.play();}

    }


}
