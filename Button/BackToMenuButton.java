package Button;

import Controller.SettingController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BackToMenuButton extends GameButton{
    Stage popUpStage;
    Pane root;
    ImageView imageView;
    ImageView imageViewHover;
    SettingController settingController;
    public BackToMenuButton(Stage popUpStage, Pane root, SettingController settingController)
    {
        super();
        this.settingController = settingController;
        this.popUpStage = popUpStage;
        this.root = root;
        this.imageView = new ImageView(new Image("Button/BackButton.png"));
        this.imageViewHover = new ImageView(new Image("Button/BackButtonHover.png"));
        this.setGraphic(this.imageView);
        this.setStyle("-fx-background-color: transparent;");
        this.makeButtonAccessible("Back to menu button", "Click to go back to menu page");

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAction();
            }
        });
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
    private void handleAction()
    {
        popUpStage.hide();


    }
    private void enterHover() {
        this.setGraphic(this.imageViewHover);
        this.setCursor(Cursor.HAND);
    }
    private void exitHover(){
        this.setGraphic(this.imageView);
    }
    public void setPane(Pane pane)
    {
        this.root = pane;
    }
}
