package Button;

import Controller.SettingController;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class AdoptedSlider extends Slider {
    Label label;
    SettingController settingController;

    public AdoptedSlider(Label label, SettingController settingController)
    {
        this.settingController = settingController;
        this.label = label;
        this.setMin(0);
        this.setMax(100);
        this.setPrefSize(300, 50);
        this.setLayoutX(200);
        this.setLayoutY(200);
        this.setBlockIncrement(1);
        this.label.setLayoutX(500);
        this.label.setLayoutY(202);


    }


}
