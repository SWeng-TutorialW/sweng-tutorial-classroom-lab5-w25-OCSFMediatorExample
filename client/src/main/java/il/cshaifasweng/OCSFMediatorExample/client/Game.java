package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class Game {
    private static Game game = null;
    @FXML
    private Label statusLabel;

    @FXML
    private Button button00, button01, button02;
    @FXML
    private Button button10, button11, button12;
    @FXML
    private Button button20, button21, button22;

    private String[][] board = new String[3][3]; // Tracks the game state
    private Button lastButton = null;
    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        lastButton  =   clickedButton;
        // Get row and column indices from the button's fx:id
        String buttonId = clickedButton.getId();
        try {
            SimpleClient.getClient().sendToServer(buttonId.charAt(6)+buttonId.charAt(7));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Game getGame() {
        return game;
    }

    public void setGame(int row, int col,String operation) {
        board[row][col] = operation;
        lastButton.setText(operation);
        return;
    }

    public void disableBoard(String msg) {
        // Disable all buttons
        statusLabel.setText(msg);
        button00.setDisable(true);
        button01.setDisable(true);
        button02.setDisable(true);
        button10.setDisable(true);
        button11.setDisable(true);
        button12.setDisable(true);
        button20.setDisable(true);
        button21.setDisable(true);
        button22.setDisable(true);
    }

}

