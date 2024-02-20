package Button;

import Controller.SettingController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class VolumeSlider extends AdoptedSlider{
    SettingController settingController;
    Label label;
    public VolumeSlider(Label label, SettingController settingController, AnchorPane root)
    {
        super(label, settingController);
        this.settingController = settingController;
        this.label = label;
        this.setValue(settingController.getVolume());
        this.label.setText(Integer.toString(this.settingController.getVolume()));
        Label bri = new Label("Volume:");
        bri.setLayoutX(85);
        bri.setLayoutY(208);
        bri.setStyle("-fx-font-size: 20px;");
        root.getChildren().add(bri);
        this.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                slide();
            }
        });

    }
    private void slide(){
        this.settingController.setVolume((int) this.getValue());
        this.label.setText(Integer.toString((int)this.settingController.getVolume()));
        this.settingController.updateVolume();
    }
}
