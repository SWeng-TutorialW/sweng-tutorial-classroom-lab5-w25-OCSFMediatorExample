package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.application.Platform;

public class SecondaryController {


    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b4;

    @FXML
    private Button b5;

    @FXML
    private Button b6;

    @FXML
    private Button b7;

    @FXML
    private Button b8;

    @FXML
    private Button b9;


    // List of buttons
    private List<Button> buttons;

    @Subscribe
    public void startTheGame(String msg){
        if(!msg.equals("Start")){
            return;
        }
        Platform.runLater(() -> {
            removeTextFromButtons();
            // Enable buttons
            for (Button button : buttons) {
                button.setDisable(false);
            }
        });
    }

    @FXML
    // Method to remove text from all buttons
    public void removeTextFromButtons() {
        // Removes text from buttonS
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
    }


    @FXML
    void initialize(){
        buttons = List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9);
        // Buttons should be disabled till the game starts
        for (Button button : buttons) {
            button.setDisable(true);
        }
        EventBus.getDefault().register(this);
    }
}