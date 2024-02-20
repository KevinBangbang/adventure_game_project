package Button;

import Controller.SettingController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class AccessibilityCheckBox extends ToggleButton {
    SettingController settingController;
    Label label;
    AnchorPane root;
        public AccessibilityCheckBox(SettingController settingController, Label label, AnchorPane root){
            this.setLayoutX(85);
            this.setLayoutY(380);
            this.settingController = settingController;
            this.label = label;
            this.label.setText("Non-sight");
            this.label.setLayoutY(380);
            this.label.setLayoutX(150);
            this.root = root;
            this.root.getChildren().add(this.label);
            this.settingController = settingController;
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    settingController.changeAccess();
                }
            });
        }
}
