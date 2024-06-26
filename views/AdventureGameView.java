package views;

import AdventureModel.*;
import AdventureModel.npc.VisitableNPC;
import Controller.MenuController;
import Button.SettingButton;
import Button.InstructionButton;
import Controller.SettingController;
import MovePackage.*;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import AdventureModel.npc.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 *
 */


public class AdventureGameView {

    static AdventureGame model; //model of the game
    static Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons
    Boolean helpToggle = false; //is help on display?
    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    Label npcTextLabel = new Label(); //to hold NPC text
    Label npcNameLabel = new Label(); //to display NPC name

    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
     Setting setting;
     AnchorPane root = new AnchorPane();
    Button player;
    ImageView floor;
    ImageView playerFront;
    ImageView playerBack;
    ImageView playerLeft;
    ImageView playerRight;
    MenuView menuView;
    MenuController menuController;

    Scene gameScene;
    Scene menuScene;
    TextField textField;
    SettingController settingController;
    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing
    private AdventureGame currentGame; // Current game instance

    public VBox battleInterface;
    public Label playerHealthLabel;
    public Label playerManaLabel;
    public Label monsterHealthLabel;
    public Label monsterManaLabel;
    public Button attackButton;
    public Button defendButton;
    public Button escapeButton;
    public Button healButton;
    public Button rapidAttackButton;
    public Monster currentMonster;
    public ProgressBar playerHealthBar;
    public ProgressBar playerManaBar;
    public ProgressBar monsterHealthBar;
    public ProgressBar monsterManaBar;
    public ProgressBar playerDefenseBar;
    public ProgressBar monsterDefenseBar;
    public Label playerDefenseLabel;
    public Label monsterDefenseLabel;
    private static MediaPlayer battleMediaPlayer;

    private SettingButton settingButton;
    private HBox npcTextBox;
    private InstructionButton instructionButton;
    private Button monsterButton;
    private MediaPlayer mainMedia;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) throws IOException {
        this.model = model;
        this.stage = stage;
        this.currentGame = model;
        this.stage.setTitle("group2's Adventure Game");
        this.setting = new Setting(this.model.getDirectoryName());
        this.menuView = new MenuView(this.menuScene, this.model.getDirectoryName(), this.setting, this.stage, this);
        this.menuController = new MenuController(this.model, this.menuView, this.setting);
        this.menuController.showMenu();
        this.settingButton = this.menuController.getSettingButton();
        this.settingController = this.settingButton.getSettingController();

        Media sound = new Media(new File(this.model.getDirectoryName()+"/bgm/MainTheme.mp3").toURI().toString());
        mainMedia = new MediaPlayer(sound);
        this.settingController.addMedia(mainMedia);

        mainMedia.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mainMedia.seek(Duration.ZERO);
            }
        });
        mainMedia.play();

        this.stage.setResizable(false);
    }

    /**
     * Initialize the UI
     */
    public void intiUI() throws IOException {

        // setting up the stage
        this.stage.setTitle("group2's Adventure Game");
        this.gameScene = new Scene(root, this.setting.width, this.setting.height);
        this.playerFront = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-front.png"));
        this.playerFront.setFitWidth(1.1*setting.playerWidth);
        this.playerFront.setFitHeight(1.1*setting.playerHeight);

        this.playerBack = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-back.png"));
        this.playerBack.setFitWidth(setting.playerWidth);
        this.playerBack.setFitHeight(setting.playerHeight);

        this.playerLeft = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-left.png"));
        this.playerLeft.setFitWidth(setting.playerWidth);
        this.playerLeft.setFitHeight(setting.playerHeight);

        this.playerRight = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-right.png"));
        this.playerRight.setFitWidth(setting.playerWidth);
        this.playerRight.setFitHeight(setting.playerHeight);

        this.floor = new ImageView(new Image(this.model.getDirectoryName()+"/room-images/1.png"));
        this.floor.setFitHeight(this.setting.height);
        this.floor.setFitWidth(this.setting.width);
        this.root.getChildren().add(this.floor);
        this.player = new Button();
        makeButtonAccessible(this.player, "Player", "This is a player button.");
        this.player.setGraphic(this.playerFront);
        this.player.setPrefSize(setting.playerWidth, setting.playerHeight);
        this.player.setStyle("-fx-background-color: transparent;");
        this.player.setLayoutX(this.model.getPlayer().getCurrentPos()[0]);
        this.player.setLayoutY(this.model.getPlayer().getCurrentPos()[1]);
        this.root.getChildren().add(player);

        this.inputTextField = new TextField();
        this.inputTextField.requestFocus();
        this.root.getChildren().add(this.inputTextField);
        this.inputTextField.setLayoutY(setting.height-70);
        this.inputTextField.setLayoutX(33);
        this.inputTextField.setPrefSize(setting.width-70, 20);
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_FIELD);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play. Enter a command and hit enter to continue.");
        addTextHandlingEvent();
        updateItems();


        ImageView settingImage = new ImageView(new Image("Button/SettingIcon.png"));
        settingImage.setFitWidth(45);
        settingImage.setFitHeight(45);
        ImageView settingImageHover = new ImageView(new Image("Button/SettingIconHover.png"));
        settingImageHover.setFitHeight(45);
        settingImageHover.setFitWidth(45);

        this.settingController.addPane(root);
        this.settingController.updateBrightness();
        this.settingButton.setImageView(settingImage);
        this.settingButton.setImageViewHover(settingImageHover);
        this.settingButton.setPane(this.root);
        this.settingButton.setStage(this.stage);
        this.settingButton.setPrefSize(45, 45);
        this.settingButton.setLayoutX(setting.width-100);
        this.settingButton.setLayoutY(420);
        makeButtonAccessible(this.settingButton, "Instruction Button", "Click to show instruction");
        showSettingButton();
        this.instructionButton = this.menuController.getInstructionButton();
        ImageView instructionImage = new ImageView(new Image("Button/instructionIcon.png"));
        instructionImage.setFitWidth(45);
        instructionImage.setFitHeight(45);
        ImageView instructionImageHover = new ImageView(new Image("Button/instructionIconHover.png"));
        instructionImageHover.setFitHeight(45);
        instructionImageHover.setFitWidth(45);
        this.instructionButton.setImageView(instructionImage);
        this.instructionButton.setImageViewHover(instructionImageHover);
        this.instructionButton.setLayoutX(setting.width-150);
        this.instructionButton.setLayoutY(345);
        makeButtonAccessible(this.instructionButton, "Instruction Button", "Click to show instruction");
        showInstructionButton();

        playerHealthBar = new ProgressBar(model.getPlayer().getHealth() / 100.0);
        playerManaBar = new ProgressBar(model.getPlayer().getMana() / 100.0);
        playerDefenseBar = new ProgressBar(model.getPlayer().getDefense() / 100.0);
        playerHealthLabel = new Label("Player Health: "+Integer.toString(this.model.getPlayer().getHealth()), playerHealthBar);
        playerManaLabel = new Label("Player Mana: "+Integer.toString(this.model.getPlayer().getMana()), playerManaBar);
        playerDefenseLabel = new Label("Player Defense: "+Integer.toString(this.model.getPlayer().getDefense()), playerDefenseBar);
        VBox playerStats = new VBox(playerHealthLabel, playerHealthBar, playerManaLabel, playerManaBar, playerDefenseLabel, playerDefenseBar);
        root.setLeftAnchor(playerStats, 10.0);
        root.getChildren().add(playerStats);
        this.gameScene.getStylesheets().add(getClass().getResource("font.css").toExternalForm());

        articulateRoomDescription();
        listenMove(this.gameScene);
        this.stage.setScene(this.gameScene);
        this.stage.show();
        //Inventory + Room items

    }

    public void showInstructionButton(){
        this.root.getChildren().add(this.instructionButton);
    }
    public void showSettingButton()
    {
        this.root.getChildren().add(this.settingButton);
    }
    public void listenMove(Scene scene){
        MoveExecution moveExecution = new MoveExecution();
        Move up = new MoveUp(this.model.getPlayer(), this.player);
        Move down = new MoveDown(this.model.getPlayer(), this.player);
        Move left = new MoveLeft(this.model.getPlayer(), this.player);
        Move right = new MoveRight(this.model.getPlayer(), this.player);




        ImageView frontImage = this.playerFront;
        ImageView backImage = this.playerBack;
        ImageView leftImage = this.playerLeft;
        ImageView rightImage = this.playerRight;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode()==KeyCode.W)
                {
                    moveExecution.execute(up);
                    movePlayer();
                    updatePlayer(backImage);

                }
                else if (keyEvent.getCode()==KeyCode.S)
                {
                    moveExecution.execute(down);
                    movePlayer();
                    updatePlayer(frontImage);

                }
                if (keyEvent.getCode()==KeyCode.A)
                {
                    moveExecution.execute(left);
                    movePlayer();
                    updatePlayer(leftImage);

                }
                if (keyEvent.getCode()==KeyCode.D)
                {
                    moveExecution.execute(right);
                    movePlayer();
                    updatePlayer(rightImage);

                }
            }
        });
    }

    public void updatePlayer(ImageView image){
        this.player.setGraphic(image);
        this.player.setLayoutX(this.model.getPlayer().getCurrentPos()[0]);
        this.player.setLayoutY(this.model.getPlayer().getCurrentPos()[1]);

    }

    public void movePlayer()
    {
        if (this.model.getPlayer().getCurrentPos()[1]<0){

            if(this.model.getPlayer().getCurrentRoom().getMotionTable().optionExists("NORTH")) {
                this.model.getPlayer().setCurrentPos(this.model.getPlayer().getCurrentPos()[0], setting.height - setting.playerHeight);
            }
            submitEvent("N");
            System.out.println(this.model.getPlayer().getCurrentRoom().getRoomNumber());
        }
        else if (this.model.getPlayer().getCurrentPos()[1]>setting.height-setting.playerHeight)
        {

            if(this.model.getPlayer().getCurrentRoom().getMotionTable().optionExists("SOUTH")) {
                this.model.getPlayer().setCurrentPos(this.model.getPlayer().getCurrentPos()[0], 0);
            }
            submitEvent("S");
        }
        else if (this.model.getPlayer().getCurrentPos()[0]<0)
        {

            if(this.model.getPlayer().getCurrentRoom().getMotionTable().optionExists("WEST")) {
                this.model.getPlayer().setCurrentPos(setting.width - setting.playerWidth, this.model.getPlayer().getCurrentPos()[1]);
            }
            submitEvent("W");
        }
        else if (this.model.getPlayer().getCurrentPos()[0]>setting.width-setting.playerWidth)
        {

            if(this.model.getPlayer().getCurrentRoom().getMotionTable().optionExists("EAST")) {
                this.model.getPlayer().setCurrentPos(setting.playerWidth, this.model.getPlayer().getCurrentPos()[1]);
            }
            submitEvent("E");
        }
    }

    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name        ARIA name
     * @param shortString ARIA accessible text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }
    public void makeButtonAccessible(Button inputButton, String name, String shortString)
    {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     * <p>
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     * <p>
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String command = inputTextField.getText().trim();
                if (!command.isEmpty()) {
                    submitEvent(command);
                }
            } else if (event.getCode() == KeyCode.TAB) {
                root.requestFocus(); // Move focus to the GridPane or any other node.
            }
        });
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {
        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("status"))
        {
            status("Player", model.getPlayer().getHealth(), model.getPlayer().getMana(), model.getPlayer().getDefense());
        }
        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP") && !output.startsWith("NPC"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            updateScene(""); // 更新显示以显示当前房间的图像
            updateItems(); // 更新物品

            PauseTransition delay = new PauseTransition(Duration.seconds(5)); // 暂停5秒
            delay.setOnFinished(event -> {
                // 在这里，您可以添加代码以在FORCED方向上过渡到房间
                String forcedDirection = model.getPlayer().getCurrentRoom().getMotionTable().getDirection().get(0).getDirection();
                model.movePlayer(forcedDirection);

                updateScene(""); // 移动到新房间后更新显示
                updateItems(); // 更新物品
            });
            delay.play();
        } else if (output.startsWith("NPC")) {
            updateSceneNPC(output.substring(4));
        }
    }


    /**
     * showCommands
     * __________________________
     * <p>
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        List<String> exits = model.getPlayer().getCurrentRoom().getExits();
        Set<String> uniqueExits = new HashSet<>(exits); // 使用HashSet来去除重复项

        StringBuilder commandString = new StringBuilder("You can: ");
        for (String exit : uniqueExits) {
            commandString.append(exit).append(", ");
        }
        if (!uniqueExits.isEmpty()) {
            commandString.setLength(commandString.length() - 2); // 移除最后的逗号和空格
        }

        roomDescLabel.setText(commandString.toString());
    }


    /**
     * updateScene
     * __________________________
     * <p>
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {
        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        updateSceneNPC(null);

//        formatText(textToDisplay); //format the text to display
        AnchorPane pane = new AnchorPane();
        this.settingController.addPane(pane);
        this.settingController.updateBrightness();
        pane.getChildren().add(this.roomImageView);
        pane.getChildren().add(this.inputTextField);
        playerHealthBar = new ProgressBar(model.getPlayer().getHealth() / 100.0);
        playerManaBar = new ProgressBar(model.getPlayer().getMana() / 100.0);
        playerDefenseBar = new ProgressBar(model.getPlayer().getDefense() / 100.0);
        playerHealthLabel = new Label("Player Health: "+Integer.toString(this.model.getPlayer().getHealth()), playerHealthBar);
        playerManaLabel = new Label("Player Mana: "+Integer.toString(this.model.getPlayer().getMana()), playerManaBar);
        playerDefenseLabel = new Label("Player Defense: "+Integer.toString(this.model.getPlayer().getDefense()), playerDefenseBar);
        VBox playerStats = new VBox(playerHealthLabel, playerHealthBar, playerManaLabel, playerManaBar, playerDefenseLabel, playerDefenseBar);
        pane.setLeftAnchor(playerStats, 10.0);
        pane.getChildren().add(playerStats);

        // Deploy monster if there are any
        Monster m = this.model.getPlayer().getCurrentRoom().getMonster();
        if (m!=null)
        {
            monsterButton = new Button();
            ImageView monsterImage = new ImageView(new Image(this.model.getDirectoryName()+"/monster-images/"+m.getname()+".png"));
            monsterButton.setPrefSize(150, 300);
            monsterImage.setFitWidth(150);
            monsterImage.setFitHeight(300);
            monsterButton.setGraphic(monsterImage);
            monsterButton.setStyle("-fx-background-color: transparent;");
            monsterButton.setOnMouseEntered(
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            monsterImage.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.WHITE, 10, 0.75, 1, 1));
                        }
                    }
            );
            monsterButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    monsterImage.setEffect(null);
                }
            });
            monsterButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stopArticulation();
                    showBattleInterface(m);
                    model.setCurrentMonster(m);
                    model.setInBattle(true);
                }
            });
            monsterButton.setLayoutX(setting.width/2-75);
            monsterButton.setLayoutY(setting.height/2-150);
            pane.getChildren().add(monsterButton);
        }
        Scene scene = new Scene(pane, setting.width, setting.height);
        scene.getStylesheets().add(getClass().getResource("font.css").toExternalForm());
        this.stage.setScene(scene);
        this.root = pane;
        showSettingButton();
        showInstructionButton();

        pane.getChildren().add(player);
        listenMove(scene);
        stage.sizeToScene();
        if (this.model.getPlayer().getCurrentRoom().getRoomNumber()!=7&&
                this.model.getPlayer().getCurrentRoom().getRoomNumber()!=8){
        articulateRoomDescription();}
        //finally, articulate the description
//        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    public HBox createNPCTextBox(String textToDisplay) {
        formatTextNPC(textToDisplay); //format the text to display

        //chat box holding NPC icon replaces roomDescLabel
        HBox npcTextBox = new HBox();
        npcTextBox.setStyle("-fx-background-color: #000000;-fx-border-color: white;-fx-border-width: 5px;");
        npcTextBox.setPrefHeight(200);
        npcTextBox.setPrefWidth(600);
        npcTextBox.setLayoutX(1280/4);
        npcTextBox.setLayoutY(720 - 300);
        //npcTextBox.setAlignment(Pos.BOTTOM_CENTER);

        npcTextLabel.setPrefWidth(400);
        npcTextLabel.setPrefHeight(200);
        npcTextLabel.setAlignment(Pos.CENTER);

        npcNameLabel.setPrefWidth(200);
        npcNameLabel.setPrefHeight(100);
        npcNameLabel.setAlignment(Pos.TOP_LEFT);

        //obtain the image of the npc the player is currently interacting with
        VisitableNPC npc = this.model.player.getInteractingNPC();
        System.out.println(npc.getImagePath());
        Image npcImageFile = new Image(npc.getImagePath());
        ImageView npcImage = new ImageView(npcImageFile);
        npcImage.setPreserveRatio(true);
        npcImage.setFitWidth(200);
        npcImage.setFitHeight(200);

        //add text and image to text box
        npcTextBox.getChildren().add(npcImage);

        VBox npcTextArea = new VBox();
        npcTextArea.getChildren().add(npcNameLabel);
        npcTextArea.getChildren().add(npcTextLabel);
        npcTextBox.getChildren().add(npcTextArea);


        return npcTextBox;
    }

    /**
     * updateSceneNPC
     * __________________________
     * <p>
     * Show the current room, and have the NPC say some text below it.
     * If the input parameter is not null, the NPC will say it
     * below the image.
     * @param textToDisplay the text the NPC will say below the image.
     */
    public void updateSceneNPC(String textToDisplay) {

        root.getChildren().remove(npcTextBox);

        if (textToDisplay != null&&this.settingController.getMode()) {
            npcTextBox = createNPCTextBox(textToDisplay);
            root.getChildren().add(npcTextBox);
        }
    }

    /**
     * formatText
     * __________________________
     * <p>
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty())
                roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * formatTextNPC
     * __________________________
     * <p>
     * Format text for display in NPC text box.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatTextNPC(String textToDisplay) {
        npcTextLabel.setText(textToDisplay);
        npcTextLabel.setStyle("-fx-text-fill: white;");
        npcTextLabel.setFont(new Font("Arial", 16));
        npcTextLabel.setTextOverrun(OverrunStyle.CLIP);
        npcTextLabel.setWrapText(true);

        npcNameLabel.setText(model.player.getInteractingNPC().getName());
        npcNameLabel.setStyle("-fx-text-fill: white;");
        npcNameLabel.setFont(new Font("Arial", 16));
        npcNameLabel.setTextOverrun(OverrunStyle.CLIP);
        npcNameLabel.setWrapText(true);
    }

    /**
     * getRoomImage
     * __________________________
     * <p>
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {
        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
//        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(setting.width);
        roomImageView.setFitHeight(setting.height);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     * <p>
     * This method is partially completed, but you are asked to finish it off.
     * <p>
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * <p>
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {
        updateBag();
        // Clear previous objects
        // Update objects in the current room
        Room currentRoom = model.getPlayer().getCurrentRoom();
        ArrayList<AdventureObject> roomObjects = currentRoom.objectsInRoom;
        for (AdventureObject obj : roomObjects) {
            Button objectButton = new Button();
            objectButton.setLayoutX(obj.getCoordinates().get(0));
            objectButton.setLayoutY(obj.getCoordinates().get(1));
            String objectName = obj.getName();
            objectButton.setGraphic(obj.getImageView());
            objectButton.setOnMouseEntered(
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            obj.getImageView().setEffect(new DropShadow(BlurType.GAUSSIAN, Color.WHITE, 10, 0.75, 1, 1));
                        }
                    }
            );
            objectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    obj.getImageView().setEffect(null);
                }
            });


            makeButtonAccessible(objectButton, objectName, "Click to take"+objectName, obj.getDescription());
            objectButton.setStyle("-fx-background-color: transparent;");
            this.root.getChildren().add(objectButton);
            objectButton.setOnAction(event -> {
                String result = model.interpretAction("TAKE " + objectName);
                updateItems();
                System.out.println(result);
                roomDescLabel.setText(result);
                root.getChildren().remove(objectButton);

            });


        }
    }

        public void updateBag() {
            // Clear previous objects
            // Update objects in the current room
            ImageView imageView = new ImageView(new Image(model.getDirectoryName()+"/objectImages/Bag.png"));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            Room location = model.getRooms().get(3); //WILL HAVE TO CHANGE THIS METHOD

            AdventureObject obj = new AdventureObject("Bag", "a bag for holding", location, new ArrayList<Integer>(0), imageView);
            Button objectButton = new Button();
            makeButtonAccessible(objectButton, "BAG", "A Bag where you can store more items.");
            objectButton.setLayoutX(1150);
            objectButton.setLayoutY(0);
            String objectName = obj.getName();
            objectButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    obj.getImageView().setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GREEN, 10, 0.75, 1, 1));
                }
            });
            objectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    obj.getImageView().setEffect(null);
                }
            });

            objectButton.setGraphic(obj.getImageView());
            makeButtonAccessible(objectButton, objectName, objectName, objectName);
            objectButton.setStyle("-fx-background-color: transparent;");
            this.root.getChildren().add(objectButton);
            objectButton.setOnAction(event -> {
                String result = model.interpretAction("OPEN BAG");
                updateItems();
                System.out.println(result);
                roomDescLabel.setText(result);
                openInventoryGrid();
            });


        }

    // Method to open a new gridpane with images of adventure objects in the player's inventory
    private void openInventoryGrid() {
        // Create a new GridPane for the inventory
        GridPane inventoryGrid = new GridPane();
        inventoryGrid.setStyle("-fx-background-color: white;"); // Set white background

        // Get the player's inventory
        ArrayList<AdventureObject> playerInventory = model.getPlayer().inventory;

        // Populate the inventoryGrid with buttons for each adventure object
        int column = 0;
        int row = 0;
        for (AdventureObject adventureObject : playerInventory) {
            Button objectButton = new Button();
            ImageView image = new ImageView(adventureObject.getImageView().getImage());
            image.setFitHeight(125);
            image.setFitWidth(125);
            objectButton.setGraphic(image);
            objectButton.setPrefSize(150, 150);

            // Set button action to drop the object to the ground
            objectButton.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) { //LEFT CLICK TO DROP
                    inventoryGrid.getChildren().remove(objectButton);
                    model.getPlayer().inventory.remove(adventureObject); // Remove from the inventory
                    adventureObject.changeLocation(model.player.getCurrPos());
                    model.getPlayer().getCurrentRoom().addGameObject(adventureObject);
                    updateItems();
                    roomDescLabel.setText("Dropped " + adventureObject.getName() + " to the ground.");
                    System.out.println("Dropped " + adventureObject.getName() + " to the ground.");
                }// Close the inventory window if needed
                if (event.getButton() == MouseButton.SECONDARY){ //RIGHT CLICK TO USE
                    if (Objects.equals(adventureObject.getName(), "KEY") || Objects.equals(adventureObject.getName(), "BIRD")|| Objects.equals(adventureObject.getName(), "PENCIL")) {
                    }else if(Objects.equals(adventureObject.getName(), "MAP")) {
                        useObject("MAP");
                    } else {
                        useObject(adventureObject.getName());
                        inventoryGrid.getChildren().remove(objectButton);
                        model.getPlayer().inventory.remove(adventureObject); // Remove from the inventory
                        roomDescLabel.setText("Used " + adventureObject.getName() + ", it is now gone");
                        System.out.println("Used " + adventureObject.getName() + ", it is now gone");
                    }
                }
            });


            // Add the object button to the inventoryGrid
            inventoryGrid.add(objectButton, column, row);

            // Increase the column, and move to the next row if necessary
            column++;
            if (column > 3) {
                column = 0;
                row++;
            }
        }

        // Create a new scene for the inventoryGrid
        Scene inventoryScene = new Scene(inventoryGrid, 600, 600);

        // Create a new stage for the inventoryScene
        Stage inventoryStage = new Stage();
        inventoryStage.setTitle("Inventory");
        inventoryStage.setScene(inventoryScene);

        // Show the inventoryStage
        inventoryStage.show();
    }


    private void useObject(String objName) {
        if (objName.equals("HEALTH")) {
            this.model.player.currentHp = this.model.player.getMax();
        } else if (objName.equals("SANDWICH")) {
            this.model.player.currentHp += 25;
        } else if (objName.equals("MANA")) {
            this.model.player.mp = 100;
        } else if (objName.equals("SWORD")) {
            this.model.player.attack += 20;
        } else if (objName.equals("MAP")) {
            // Load map image
            Image mapImage = new Image(model.getDirectoryName() + "/MAP.png");

            // Create a new window
            Stage stage = new Stage();
            stage.setTitle("Map Window");

            // Create ImageView and set the map image
            ImageView imageView = new ImageView(mapImage);

            // Create a layout for the new window
            StackPane layout = new StackPane();
            layout.getChildren().add(imageView);

            // Create a scene and set it on the stage
            Scene scene = new Scene(layout, mapImage.getWidth(), mapImage.getHeight());
            stage.setScene(scene);

            // Show the stage
            stage.show();
        }
        updateStatus();
    }


        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";





    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (!helpToggle) {
            try {
                String directoryName = this.model.getDirectoryName();
                // 读取 help.txt 文件的内容
                File file = new File(directoryName + "/help.txt");
                Scanner scanner = new Scanner(file);
                StringBuilder helpTextBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    helpTextBuilder.append(scanner.nextLine()).append("\n");
                }
                scanner.close();

                // 创建带有黑色背景的 VBox 来显示帮助文本
                VBox helpTextContainer = new VBox();
                helpTextContainer.setStyle("-fx-background-color: black;"); // 设置黑色背景
                Label helpTextLabel = new Label(helpTextBuilder.toString());
                helpTextLabel.setStyle("-fx-text-fill: white;");
                helpTextLabel.setFont(new Font("Arial", 16));
                helpTextLabel.setWrapText(true);
                helpTextContainer.getChildren().add(helpTextLabel);

                // 在 gridPane 中显示 VBox
                gridPane.add(helpTextContainer, 1, 1);

                helpToggle = true;
            } catch (FileNotFoundException e) {
                System.out.println("Error reading help.txt: " + e.getMessage());
            }
        } else {
            // 移除帮助文本并重新显示房间的图像和描述
            updateScene(null);
            helpToggle = false;
        }
    }

    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = Integer.toString(this.model.getPlayer().getCurrentRoom().getRoomNumber());


        musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + ".mp3";


        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        this.settingController.addMedia(mediaPlayer);
        this.settingController.updateVolume();
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    public void setCurrentGame(AdventureGame loadedGame, Label selectGameLabel) {
        try {
            // 尝试使用已加载的游戏设置当前游戏
            if (loadedGame == null) {
                throw new Exception("No game loaded.");
            }

            this.currentGame = loadedGame;
            this.model = loadedGame;

            updateUI();

            // 更新标签，通知用户游戏已成功加载
            selectGameLabel.setText("Successfully loaded the game.");

        } catch (Exception e) {
            // 如果加载游戏时出错，我们记录异常，并通过selectGameLabel通知用户
            selectGameLabel.setText("Failed to set the loaded game. Starting TinyGame instead...");

            // 开始一个叫做“TinyGame”的新游戏
            try {
                this.currentGame = new AdventureGame("TinyGame");
                // 假设AdventureGame的构造函数在出错时会抛出异常
            } catch (Exception ex) {
                // 处理与启动新的“TinyGame”相关的异常
                selectGameLabel.setText("Failed to start TinyGame. Please check the game files.");
                // 在此处，你也可能需要禁用与游戏相关的UI元素，直到有效的游戏被加载。
            }
        }
    }

    private void updateStatus()
    {
        this.playerHealthBar.setProgress(model.getPlayer().getHealth() / 100.0);
        this.playerManaBar.setProgress(model.getPlayer().getMana() / 100.0);
        this.playerDefenseBar.setProgress(model.getPlayer().getDefense() / 100.0);

        this.playerHealthLabel.setText("Player Health: "+Integer.toString(this.model.getPlayer().getHealth()));
        this.playerManaLabel.setText("Player Mana: "+Integer.toString(this.model.getPlayer().getMana()));
        this.playerDefenseLabel = new Label("Player Defense: "+Integer.toString(this.model.getPlayer().getDefense()), playerDefenseBar);
    }

    public AdventureGame getCurrentGame() {
        return this.currentGame;
    }
    private void status(String player, int health, int mana, int defend)
    {
        String s = player+"health is "+Integer.toString(health)+" and Mana is "+Integer.toString(mana)+" and Defense is "+Integer.toString(defend);
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager vm = VoiceManager.getInstance();
        Voice v = vm.getVoice("kevin16");
        v.setVolume((float)settingController.getVolume()/100);
        v.allocate();
        v.speak(s);
        v.deallocate();
    }
    public void updateUI() {
        updateScene(null); // 使用 null 更新场景，这将显示当前房间的描述
        updateItems();     // 更新房间和背包中的物品
//        articulateRoomDescription(); // 播放关于当前房间的音频描述
    }
    public void SetMonster(Monster monster){ this.currentMonster = monster;
    }
    public void showBattleInterface(Monster monster) {
        this.mainMedia.pause();
        SetMonster(monster);
        AnchorPane battleRoot = new AnchorPane();
        this.settingController.addPane(battleRoot);
        this.settingController.updateBrightness();
        //monster.showBattle(battleRoot, this.setting, this.model, this);
        ImageView battleBackground = new ImageView(new Image(model.getDirectoryName() + "/background/battlewith"+monster.getname().toLowerCase()+".png"));
        battleBackground.setFitHeight(setting.height);
        battleBackground.setFitWidth(setting.width);
        battleRoot.getChildren().add(battleBackground);

        // 设置玩家的角色图像
        ImageView playerImage = new ImageView(new Image(model.getDirectoryName() + "/player-images/character-front.png"));
        playerImage.setFitWidth(setting.playerWidth);
        playerImage.setFitHeight(setting.playerHeight);
        playerImage.setLayoutX(350); // 例如: 100像素距离左边界
        playerImage.setLayoutY(setting.height / 2-75); // 垂直居中
        battleRoot.getChildren().add(playerImage);

        // 设置怪物的图像
        ImageView monsterImage = new ImageView(new Image(model.getDirectoryName() + "/monster-images/"+currentMonster.getname().toUpperCase()+".png"));
        monsterImage.setFitWidth(1.5*setting.playerWidth);
        monsterImage.setFitHeight(1.5*setting.playerHeight);
        monsterImage.setLayoutX(setting.width - 150 - monsterImage.getFitWidth()); // 例如: 100像素距离右边界
        monsterImage.setLayoutY((setting.height - monsterImage.getFitHeight()) / 2); // 垂直居中
        battleRoot.getChildren().add(monsterImage);

        //BorderPane battleLayout = new BorderPane();
        //battleLayout.setPrefSize(AdventureGameView.setting.width, AdventureGameView.setting.height); // Set to full window size

        // Health and Mana Bars
        playerHealthBar = new ProgressBar(model.getPlayer().getHealth() / 100.0);
        playerManaBar = new ProgressBar(model.getPlayer().getMana() / 100.0);
        monsterHealthBar = new ProgressBar(monster.getHealth() / 100.0);
        monsterManaBar = new ProgressBar(monster.getMana() / 100.0);
        playerDefenseBar = new ProgressBar(model.getPlayer().getDefense() / 100.0);
        monsterDefenseBar = new ProgressBar(monster.getDefense() / 100.0);

        // 显示玩家和怪物的健康和魔法条
        //playerHealthLabel = new Label("Health: " + model.getPlayer().getHealth());
        //playerManaLabel = new Label("Mana: " + model.getPlayer().getMana());
        //monsterHealthLabel = new Label("Monster Health: " + monster.getHealth());
        //monsterManaLabel = new Label("Monster Mana: " + monster.getMana());
        // 创建状态标签
        playerHealthLabel = new Label("Player Health: "+Integer.toString(this.model.getPlayer().getHealth()), playerHealthBar);
        playerManaLabel = new Label("Player Mana: "+Integer.toString(this.model.getPlayer().getMana()), playerManaBar);
        playerDefenseLabel = new Label("Player Defense: "+Integer.toString(this.model.getPlayer().getDefense()), playerDefenseBar);
        monsterHealthLabel = new Label("Monster Health: "+Integer.toString(monster.getHealth()), monsterHealthBar);
        monsterManaLabel = new Label("Monster Mana: "+Integer.toString(monster.getMana()), monsterManaBar);
        monsterDefenseLabel = new Label("Monster Defense: "+Integer.toString(monster.getDefense()), monsterDefenseBar);

        // 创建用于放置状态条的容器
        VBox playerStats = new VBox(playerHealthLabel, playerHealthBar, playerManaLabel, playerManaBar, playerDefenseLabel, playerDefenseBar);
        VBox monsterStats = new VBox(monsterHealthLabel, monsterHealthBar, monsterManaLabel, monsterManaBar, monsterDefenseLabel, monsterDefenseBar);

        // 将状态条容器放置在BorderPane的相应位置
        //battleLayout.setLeft(playerStats);
        //battleLayout.setRight(monsterStats);

        // 创建战斗按钮
        attackButton = new Button("Attack");
        makeButtonAccessible(attackButton, "Attack", "Using this button to attack monster");
        defendButton = new Button("Defend");
        makeButtonAccessible(defendButton, "Defend", "Using this button to defend monster");
        escapeButton = new Button("Escape");
        makeButtonAccessible(escapeButton, "Escape", "Using this button to escape");
        healButton = new Button("Heal");
        makeButtonAccessible(healButton, "Heal", "Using this button to heal for 20% of your max health");
        rapidAttackButton = new Button("Rapid Attack");
        makeButtonAccessible(rapidAttackButton, "Rapid Attack", "Using this button to perform special attack");

        // 添加按钮事件处理
        attackButton.setOnAction(e -> handleAttack(battleRoot, playerImage, monsterImage, attackButton));
        defendButton.setOnAction(e -> handleDefend(battleRoot, playerImage, monsterImage, defendButton));
        escapeButton.setOnAction(e -> handleEscape());
        healButton.setOnAction(e -> handleHeal(battleRoot, playerImage, monsterImage, healButton));
        rapidAttackButton.setOnAction(e -> handleRapidAttack(battleRoot, playerImage, monsterImage, rapidAttackButton));

        // 创建一个水平容器放置按钮
        HBox buttonBox = new HBox(100, attackButton, defendButton,healButton, rapidAttackButton, escapeButton);
        buttonBox.setAlignment(Pos.CENTER);


        battleRoot.getChildren().addAll(playerStats, monsterStats, buttonBox);

        // 设置元素在战斗界面中的位置
        AnchorPane.setLeftAnchor(playerStats, 10.0);
        AnchorPane.setRightAnchor(monsterStats, 10.0);
        AnchorPane.setBottomAnchor(buttonBox, 10.0);

        Scene battleScene = new Scene(battleRoot, setting.width, setting.height);
        battleScene.getStylesheets().add(getClass().getResource("font.css").toExternalForm());

        stage.setScene(battleScene);
        String musicFile = model.getDirectoryName() + "/bgm/"+monster.getname().toLowerCase()+".mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        battleMediaPlayer = new MediaPlayer(sound);
        this.settingController.addMedia(battleMediaPlayer);
        this.settingController.updateVolume();
        battleMediaPlayer.play();

    }
    private void disableAll()
    {
        attackButton.setDisable(true);
        defendButton.setDisable(true);
        healButton.setDisable(true);
        rapidAttackButton.setDisable(true);
    }
    private void enableAll()
    {
        attackButton.setDisable(false);
        defendButton.setDisable(false);
        healButton.setDisable(false);
        rapidAttackButton.setDisable(false);
    }
    // 新增方法：处理攻击动作
    public void handleAttack(Pane battleRoot, ImageView playerImage, ImageView monsterImage, Button attackButton) {
        // 在这里添加攻击逻辑
        model.attackMonster(currentMonster);
        TranslateTransition translate = new TranslateTransition();
        disableAll();
        translate.setNode(playerImage);
        translate.setDuration(Duration.seconds(2));
        translate.setCycleCount(2);
        translate.setByX(450);
        translate.setAutoReverse(true);
        translate.play();
        Media soundAttack = new Media(new File(this.model.getDirectoryName()+"/fightingSound/attack.mp3").toURI().toString());
        MediaPlayer battleMediaPlayerAttack = new MediaPlayer(soundAttack);
        this.settingController.addMedia(battleMediaPlayerAttack);
        this.settingController.updateVolume();
        battleMediaPlayerAttack.play();
        translate.setOnFinished(new
        EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enableAll();
                battleMediaPlayerAttack.stop();
                updateBattleInterface();
                if (!settingController.getMode()) {
                    status("Player", model.getPlayer().getHealth(), model.getPlayer().getMana(), model.getPlayer().getDefense());
                    status(currentMonster.getname(), currentMonster.getHealth(), currentMonster.getMana(), currentMonster.getDefense());
                }
                checkBattleEnd();
            }
        });
    }

    // 新增方法：处理防御动作
    public void handleDefend(Pane battleRoot, ImageView playerImage, ImageView monsterImage, Button defendButton) {
        // 在这里添加防御逻辑
        model.playerDefend();
        TranslateTransition translate = new TranslateTransition();
        disableAll();
        translate.setNode(monsterImage);
        translate.setDuration(Duration.seconds(1));
        translate.setCycleCount(2);
        translate.setByX(-450);
        translate.setAutoReverse(true);
        translate.play();
        Media soundDefend = new Media(new File(this.model.getDirectoryName()+"/fightingSound/defend.mp3").toURI().toString());
        MediaPlayer battleMediaPlayerDefend = new MediaPlayer(soundDefend);
        this.settingController.addMedia(battleMediaPlayerDefend);
        this.settingController.updateVolume();
        battleMediaPlayerDefend.play();
        translate.setOnFinished(new
                                        EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                enableAll();
                                                battleMediaPlayerDefend.stop();
                                                updateBattleInterface();
                                                if (!settingController.getMode()) {
                                                    status("Player", model.getPlayer().getHealth(), model.getPlayer().getMana(), model.getPlayer().getDefense());
                                                    status(currentMonster.getname(), currentMonster.getHealth(), currentMonster.getMana(), currentMonster.getDefense());
                                                }
                                                checkBattleEnd();

                                            }
                                        });

    }
    public void handleHeal(Pane battleRoot, ImageView playerImage, ImageView monsterImage, Button healButton) {
        // 在这里添加攻击逻辑
        Label l = new Label("Not enough Mana!!");
        ImageView image = new ImageView(new Image(this.model.getDirectoryName()+"/fightingAnimation/heal.png"));
        disableAll();
        Media soundHeal = new Media(new File(this.model.getDirectoryName()+"/fightingSound/heal.mp3").toURI().toString());
        MediaPlayer battleMediaPlayerHeal = new MediaPlayer(soundHeal);
        this.settingController.addMedia(battleMediaPlayerHeal);
        this.settingController.updateVolume();
        if(model.Heal()){
            image.setFitWidth(45);
            image.setFitHeight(45);
            image.setLayoutX(400);
            image.setLayoutY(260);
            battleRoot.getChildren().add(image);
            TranslateTransition translateHeal = new TranslateTransition();
            translateHeal.setNode(image);
            translateHeal.setDuration(Duration.seconds(0.5));
            translateHeal.setCycleCount(1);
            translateHeal.setByY(-40);
            translateHeal.play();
            translateHeal.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateBattleInterface();
                }
            });
            battleMediaPlayerHeal.play();
        }
        else {
            if (settingController.getMode()) {
                l.setStyle("-fx-text-fill: blue;");
                l.setLayoutX(setting.width / 2);
                l.setLayoutY(setting.height / 2);
                battleRoot.getChildren().add(l);
            }
            else {
                speak("Not Enough Mana");
            }
        }
        PauseTransition p = new PauseTransition(Duration.seconds(2));
        p.play();
        p.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                battleRoot.getChildren().remove(l);
                battleMediaPlayerHeal.stop();
                battleRoot.getChildren().remove(image);
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(monsterImage);
                translate.setDuration(Duration.seconds(1));
                translate.setCycleCount(2);
                translate.setByX(-450);
                translate.setAutoReverse(true);
                translate.play();
                Media sound = new Media(new File(model.getDirectoryName()+"/fightingSound/defend.mp3").toURI().toString());
                MediaPlayer battleMediaPlayerHeal = new MediaPlayer(sound);
                settingController.addMedia(battleMediaPlayerHeal);
                settingController.updateVolume();
                battleMediaPlayerHeal.play();
                translate.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        enableAll();
                        battleMediaPlayerHeal.stop();
                        currentMonster.attack(model.getPlayer());
                        updateBattleInterface();
                        if (!settingController.getMode()) {
                            status("Player", model.getPlayer().getHealth(), model.getPlayer().getMana(), model.getPlayer().getDefense());
                            status(currentMonster.getname(), currentMonster.getHealth(), currentMonster.getMana(), currentMonster.getDefense());
                        }
                        checkBattleEnd();
                    }
                });
            }
        });


    }
    public void handleRapidAttack(Pane battleRoot, ImageView playerImage, ImageView monsterImage, Button rapidAttackButton) {
        // 在这里添加攻击逻辑
        Label l = new Label("Not enough Mana!!");
        if(model.RapidAttack()) {
            TranslateTransition translate = new TranslateTransition();
            disableAll();
            translate.setNode(playerImage);
            translate.setDuration(Duration.seconds(1));
            translate.setCycleCount(2);
            translate.setByX(450);
            translate.setAutoReverse(true);
            translate.play();
            Media soundRapid = new Media(new File(this.model.getDirectoryName() + "/fightingSound/rapid.mp3").toURI().toString());
            MediaPlayer battleMediaPlayerRapid = new MediaPlayer(soundRapid);
            this.settingController.addMedia(battleMediaPlayerRapid);
            this.settingController.updateVolume();
            battleMediaPlayerRapid.play();

            translate.setOnFinished(new
                                            EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    enableAll();
                                                    battleMediaPlayerRapid.stop();
                                                    updateBattleInterface();
                                                    if (!settingController.getMode()) {
                                                        status("Player", model.getPlayer().getHealth(), model.getPlayer().getMana(), model.getPlayer().getDefense());
                                                        status(currentMonster.getname(), currentMonster.getHealth(), currentMonster.getMana(), currentMonster.getDefense());
                                                    }
                                                    checkBattleEnd();
                                                }
                                            });
        }
        else {
            if (settingController.getMode()) {
                l.setStyle("-fx-text-fill: blue;");
                l.setLayoutX(setting.width / 2);
                l.setLayoutY(setting.height / 2);
                battleRoot.getChildren().add(l);
            }
             else {
                speak("Not Enough Mana");
            }
        }
        PauseTransition p = new PauseTransition(Duration.seconds(2));
        p.play();
        p.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                battleRoot.getChildren().remove(l);
            }
        });

    }

    private void speak(String s)
    {
        VoiceManager vm = VoiceManager.getInstance();
        Voice v = vm.getVoice("kevin16");
        v.setVolume((float)settingController.getVolume()/100);
        v.allocate();
        v.speak(s);
        v.deallocate();
    }

    // 新增方法：处理逃跑动作
    public void handleEscape() {
        // 在这里添加逃跑逻辑
        this.model.setInBattle(false);
        updateScene("");
        updateItems();
        battleMediaPlayer.stop();
        mainMedia.play();
    }

    // 新增方法：更新战斗界面
    private void updateBattleInterface() {

        if (playerHealthBar != null && playerManaBar != null &&
                monsterHealthBar != null && monsterManaBar != null &&
                playerDefenseBar != null && monsterDefenseBar != null) {
            // 更新玩家和怪物的生命值、魔法值和防御值进度条
            playerHealthBar.setProgress(model.getPlayer().getHealth() / 100.0);
            playerManaBar.setProgress(model.getPlayer().getMana() / 100.0);
            playerDefenseBar.setProgress(model.getPlayer().getDefense() / 100.0);

            playerHealthLabel.setText("Player Health: "+Integer.toString(this.model.getPlayer().getHealth()));
            playerManaLabel.setText("Player Mana: "+Integer.toString(this.model.getPlayer().getMana()));
            playerDefenseLabel = new Label("Player Defense: "+Integer.toString(this.model.getPlayer().getDefense()), playerDefenseBar);


            if (currentMonster.isDefeated()) {
                monsterHealthBar.setProgress(0);
            } else {
                monsterHealthBar.setProgress(currentMonster.getHealth() / 100.0);
            }
            monsterManaBar.setProgress(currentMonster.getMana() / 100.0);
            monsterDefenseBar.setProgress(currentMonster.getDefense() / 100.0);
            monsterHealthLabel.setText("Monster Health: "+Integer.toString(this.currentMonster.getHealth()));
            monsterManaLabel.setText("Monster Mana: "+Integer.toString(this.currentMonster.getMana()));
            monsterDefenseLabel.setText("Monster Defense: "+Integer.toString(this.currentMonster.getDefense()));


        }
    }

    // 新增方法：检查战斗是否结束
    private void checkBattleEnd() {
        if (currentMonster.getHealth() <= 0 || model.getPlayer().getHealth() <= 0) {
            // 战斗结束，移除战斗界面
            if (battleMediaPlayer != null) {
                battleMediaPlayer.stop();
            }

            if (model.getPlayer().getHealth() <= 0) {
                // 玩家死亡，处理游戏结束逻辑
                handleGameOver();
            }
            if (currentMonster.isDefeated()){
                // 怪物死亡，处理胜利逻辑
                handleVictory();
            }

        }
    }

    // 新增方法：处理游戏结束
    private void handleGameOver() {
        // 显示游戏结束信息，可能需要切换到游戏结束场景
        this.model.getPlayer().setCurrentRoom(this.model.getRooms().get(8));
        updateScene("");
        updateItems();
        this.root.getChildren().remove(1, this.root.getChildren().size());
        Media soundLose = new Media(new File(this.model.getDirectoryName() + "/bgm/Lose.mp3").toURI().toString());
        MediaPlayer Lose = new MediaPlayer(soundLose);
        this.settingController.addMedia(Lose);
        this.settingController.updateVolume();
        Lose.play();

    }

    // 新增方法：处理胜利
    private void handleVictory() {
        // 显示胜利信息，可能需要切换到胜利场景
        if (this.currentMonster.getname().equalsIgnoreCase("boss")){

            updatePlayer(playerFront);
            System.out.println("Congratulation! you win the game");
            Media soundVic = new Media(new File(this.model.getDirectoryName() + "/bgm/Victory.mp3").toURI().toString());
            MediaPlayer Vic = new MediaPlayer(soundVic);
            this.model.getPlayer().setCurrentRoom(this.model.getRooms().get(7));
            this.settingController.addMedia(Vic);
            this.settingController.updateVolume();
            Vic.play();
        }
        updateScene("");
        updateItems();
        this.monsterButton.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent event) {
                                               System.out.println("You have defeated this monster.");
                                           }
                                       }
        );

    }
}
