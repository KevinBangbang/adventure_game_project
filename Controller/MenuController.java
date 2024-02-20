package Controller;

import AdventureModel.AdventureGame;
import AdventureModel.Setting;
import Button.GameButton;
import Button.InstructionButton;
import Button.SettingButton;
import Button.StartButton;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import views.MenuView;

public class MenuController {

    AdventureGame gameModel;
    MenuView menuView;
    Setting setting;
    SettingButton settingButton;
    InstructionButton instructionButton;

    public MenuController(AdventureGame gameModel, MenuView menuView, Setting setting)
    {
        this.gameModel = gameModel;
        this.menuView = menuView;
        this.setting = setting;
    }

    public void showStart()
    {
        GameButton startButton = new StartButton(this.menuView, this.setting);
        this.menuView.getStackPane().getChildren().add(startButton);
    }
    public void showInstruction()
    {
        InstructionButton instructionButton = new InstructionButton(this.menuView, this.setting, settingButton.getSettingController(), this.gameModel);
        this.menuView.getStackPane().getChildren().add(instructionButton);
        this.instructionButton = instructionButton;
    }
    public void showSetting()
    {
        ImageView imageView = new ImageView(new Image("Button/SettingButton.png"));
        imageView.setFitWidth(this.setting.buttonWidth);
        imageView.setFitHeight(this.setting.buttonHeight);
        ImageView imageViewHover = new ImageView(new Image("Button/SettingButtonHover.png"));
        imageViewHover.setFitHeight(this.setting.buttonHeight);
        imageViewHover.setFitWidth(this.setting.buttonWidth);
        SettingButton settingButton = new SettingButton(this.setting, this.menuView.getStage(), this.menuView.getStackPane(), imageView, imageViewHover);
        this.settingButton = settingButton;
        this.menuView.getStackPane().getChildren().add(settingButton);
    }
    public void showMenu()
    {
        this.menuView.show();
        this.showStart();
        this.showSetting();
        this.showInstruction();

    }
    public SettingButton getSettingButton()
    {
        return this.settingButton;

    }
    public InstructionButton getInstructionButton(){
        return this.instructionButton;
    }
}
