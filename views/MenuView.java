package views;

import AdventureModel.Setting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView{
    Scene scene;
    String dir;
    Setting setting;
    StackPane stackPane;
    Stage stage;
    AdventureGameView adventureGameView;
    ImageView backImage;
    public MenuView(Scene scene, String dir, Setting setting, Stage stage, AdventureGameView adventureGameView) {
        this.scene = scene;
        this.dir = dir;
        this.setting = setting;
        this.stage = stage;
        this.adventureGameView = adventureGameView;

        this.backImage = new ImageView(new Image(this.dir+"/background/menuBackground.png"));
        this.backImage.setFitWidth(this.setting.width);
        this.backImage.setFitHeight(this.setting.height);

        this.stackPane = new StackPane(backImage);
        this.stackPane.setPrefSize(this.setting.width, this.setting.height);

        this.scene = new Scene(this.stackPane);
    }
    public void show()
    {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public StackPane getStackPane()
    {
        return this.stackPane;
    }

    public AdventureGameView getAdventureGameView()
    {
        return this.adventureGameView;
    }
    public Stage getStage()
    {
        return this.stage;
    }


}
