package Button;

import AdventureModel.Setting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import views.MenuView;

import java.io.IOException;

public class StartButton extends GameButton{
    ImageView imageView;
    ImageView imageViewHover;
    public StartButton(MenuView menuView, Setting setting)
    {
        super(setting);
        this.imageView = new ImageView(new Image("Button/Button.png"));
        this.imageView.setFitWidth(this.setting.buttonWidth);
        this.imageView.setFitHeight(this.setting.buttonHeight);
        this.imageViewHover = new ImageView(new Image("Button/Button_hover.png"));
        this.imageViewHover.setFitHeight(this.setting.buttonHeight);
        this.imageViewHover.setFitWidth(this.setting.buttonWidth);
        this.setGraphic(this.imageView);
        this.setStyle("-fx-background-color: transparent;");
        this.makeButtonAccessible("Start Button", "Click this button to start");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    menuView.getAdventureGameView().intiUI();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.setTranslateY(100);
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                enterHover();
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitHover();
            }
        });
    }
    private void enterHover()
    {
        this.setGraphic(this.imageViewHover);
    }
    private void exitHover()
    {
        this.setGraphic(this.imageView);
    }

}
