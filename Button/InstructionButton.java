package Button;

import AdventureModel.AdventureGame;
import AdventureModel.Setting;
import Controller.SettingController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import views.MenuView;

import java.util.Set;

public class InstructionButton extends GameButton{
    ImageView imageView;
    ImageView imageViewHover;
    MenuView menuView;
    Scene instructionScene;
    Stage popUpStage;
    VBox instructionRoot;
    GameButton backButton;

    public InstructionButton(MenuView menuView, Setting setting, SettingController settingController, AdventureGame adventureGame)
    {
        super(setting);
        this.menuView = menuView;
        this.imageView = new ImageView(new Image("Button/InstructionButton.png"));
        this.imageView.setFitWidth(this.setting.buttonWidth);
        this.imageView.setFitHeight(this.setting.buttonHeight);
        this.imageViewHover = new ImageView(new Image("Button/InstructionButtonHover.png"));
        this.imageViewHover.setFitHeight(this.setting.buttonHeight);
        this.imageViewHover.setFitWidth(this.setting.buttonWidth);
        this.setGraphic(this.imageView);
        this.makeButtonAccessible("Instruction Button", "Click to examine the instruction for the game");
        this.setStyle("-fx-background-color: transparent;");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInstructionDetail();
            }
        });
        this.setTranslateY(250);
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


        //Create scene for instruction
        this.instructionRoot = new VBox(5);
        this.popUpStage = new Stage(StageStyle.TRANSPARENT);
        this.popUpStage.initOwner(this.menuView.getStage());
        this.popUpStage.initModality(Modality.APPLICATION_MODAL);
        TextArea instructionText = new TextArea();
        String instruction = adventureGame.getInstructions();
        instructionText.setText(instruction);
        instructionText.setWrapText(true);
        instructionText.setPrefSize(setting.instructionWidth, setting.instructionHeight);
        this.instructionRoot.setPrefSize(setting.instructionWidth, setting.instructionHeight);
        instructionText.setEditable(false);
        this.instructionRoot.getChildren().add(instructionText);
        this.instructionRoot.setAlignment(Pos.CENTER);
        this.backButton = new BackToMenuButton(this.popUpStage, this.menuView.getStackPane(), settingController);
        this.instructionRoot.getChildren().add(this.backButton);
        settingController.addPane(this.instructionRoot);
        settingController.updateBrightness();
        this.instructionScene = new Scene(this.instructionRoot, Color.TRANSPARENT);
        this.instructionScene.getStylesheets().add(getClass().getResource("Instruction.css").toExternalForm());




    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        this.setGraphic(imageView);
    }
    public void setImageViewHover(ImageView imageView)
    {
        this.imageViewHover = imageView;
    }
    private void enterHover()
    {
        this.setGraphic(this.imageViewHover);
    }
    private void exitHover()
    {
        this.setGraphic(this.imageView);
    }
    private void showInstructionDetail()
    {
        this.popUpStage.setScene(this.instructionScene);
        this.popUpStage.show();
    }
}
