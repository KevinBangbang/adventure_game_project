package Button;

import Controller.SettingController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BrightnessSlider extends AdoptedSlider{
    SettingController settingController;
    Label label;
    public BrightnessSlider(Label label, SettingController settingController, AnchorPane root)
    {
        super(label, settingController);
        this.settingController = settingController;
        this.label = label;
        this.setValue(settingController.getVolume());
        this.label.setText(Integer.toString(this.settingController.getVolume()));
        this.setTranslateY(70);
        this.label.setTranslateY(70);
        Label bri = new Label("Brightness:");
        bri.setLayoutX(85);
        bri.setLayoutY(278);
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
        this.settingController.setBrightness((int) this.getValue());
        this.settingController.updateBrightness();
        this.label.setText(Integer.toString((int)this.settingController.getBrighness()));
    }
}
