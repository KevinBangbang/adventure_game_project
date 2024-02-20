package Button;

import Controller.SettingController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class NormalCheckBox extends ToggleButton {
    SettingController settingController;
    ToggleButton other;
    Label label;
    AnchorPane root;
    public NormalCheckBox(SettingController settingController, Label label, AnchorPane root){
        this.setLayoutX(85);
        this.setLayoutY(320);
        this.setSelected(true);
        this.label = label;
        this.label.setText("Normal");
        this.label.setLayoutY(320);
        this.label.setLayoutX(150);
        this.root = root;
        this.root.getChildren().add(this.label);
        this.settingController = settingController;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                settingController.changeNormal();
            }
        });

    }

}