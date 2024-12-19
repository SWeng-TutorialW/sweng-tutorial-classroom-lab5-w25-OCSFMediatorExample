/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Turn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryController {
	private String player_symbol = ""; // the player's symbol for game management

	@FXML // fx:id="game_over"
	private Button game_over; // Value injected by FXMLLoader

	@FXML // fx:id="board"
	private AnchorPane board; // Value injected by FXMLLoader

	@FXML // fx:id="lower_left"
	private Button lower_left; // Value injected by FXMLLoader

	@FXML // fx:id="lower_middle"
	private Button lower_middle; // Value injected by FXMLLoader

	@FXML // fx:id="lower_right"
	private Button lower_right; // Value injected by FXMLLoader

	@FXML // fx:id="middle_left"
	private Button middle_left; // Value injected by FXMLLoader

	@FXML // fx:id="middle_middle"
	private Button middle_middle; // Value injected by FXMLLoader

	@FXML // fx:id="middle_right"
	private Button middle_right; // Value injected by FXMLLoader

	@FXML // fx:id="upper_left"
	private Button upper_left; // Value injected by FXMLLoader

	@FXML // fx:id="upper_middle"
	private Button upper_middle; // Value injected by FXMLLoader

	@FXML // fx:id="upper_right"
	private Button upper_right; // Value injected by FXMLLoader
	private Stage stage; // to close the window

	/////////////////////////////// methods

	//sets the player symbol according to what was received from the server
	public void set_player_symbol(String player_symbol) {
		this.player_symbol = player_symbol;
		if(player_symbol.equals("X")){// X plays first
			enableButtons();
		}
		else{
			disableButtons();
		}
		game_over.setVisible(false); // the game over button is invisible
	}
	public void game_is_over(String result){
		disableButtons();// disable all buttons except game over when game is over
		game_over.setVisible(true);
		game_over.setDisable(false);
		if(result.equals("d")){
			game_over.setText("draw");
		}
		else{
			game_over.setText(result + " won");
		}
	}

	@FXML // close the window
	void close_game(ActionEvent event) {
		if (stage != null && stage.isShowing()) {
			stage.close();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	// after press, send the player press to the server
	public void sendTurn(String button_pressed) throws IOException {
		int x = 0;
		int y = 0;
		// get coordinates from button
		switch (button_pressed) {
			case "upper_left":
				x = 0;
				y = 0;
				break;
			case "upper_middle":
				x = 1;
				y = 0;
				break;
			case "upper_right":
				x = 2;
				y = 0;
				break;
			case "middle_left":
				x = 0;
				y = 1;
				break;
			case "middle_middle":
				x = 1;
				y = 1;
				break;
			case "middle_right":
				x = 2;
				y = 1;
				break;
			case "lower_left":
				x = 0;
				y = 2;
				break;
			case "lower_middle":
				x = 1;
				y = 2;
				break;
			case "lower_right":
				x = 2;
				y = 2;
				break;
				
		}
		SimpleClient.getClient().sendToServer(new Turn(player_symbol, x, y)); // returns the turn that will be sent to the sever
	}


	// update screen according to recieved turn
	public void receiveTurn(Turn turn) {
		// find the button to update according to the coordinates
		if(turn.getX() == 0 && turn.getY() == 0){
			upper_left.setText(turn.get_player_symbol());
		} else if (turn.getX() == 1 && turn.getY() == 0) {
			upper_middle.setText(turn.get_player_symbol());
		} else if (turn.getX() == 2 && turn.getY() == 0) {
			upper_right.setText(turn.get_player_symbol());
		} else if(turn.getX() == 0 && turn.getY() == 1){
			middle_left.setText(turn.get_player_symbol());
		} else if (turn.getX() == 1 && turn.getY() == 1) {
			middle_middle.setText(turn.get_player_symbol());
		} else if (turn.getX() == 2 && turn.getY() == 1) {
			middle_right.setText(turn.get_player_symbol());
		} else if(turn.getX() == 0 && turn.getY() == 2){
			lower_left.setText(turn.get_player_symbol());
		} else if (turn.getX() == 1 && turn.getY() == 2) {
			lower_middle.setText(turn.get_player_symbol());
		} else if (turn.getX() == 2 && turn.getY() == 2) {
			lower_right.setText(turn.get_player_symbol());
		}
		// disable all buttons, since the opponent should play next
		if(turn.get_player_symbol().equals(player_symbol)){
			disableButtons();
		}
		else{ // the opponent played now it's the players turn
			enableButtons();
		}
	}
	
	// disable all buttons
	private void disableButtons() {
		for (Node node : board.getChildren()) {
			if (node instanceof Button) {
				((Button) node).setDisable(true);
			}
		}
	}

	// enable all the empty buttons
	private void enableButtons() {
		for (Node node : board.getChildren()) {
			if (node instanceof Button) {
				if(((Button) node).textProperty().getValue().isEmpty()){
					((Button) node).setDisable(false);
				}
			}
		}
	}

	@FXML
	void lower_left_press(ActionEvent event) throws IOException {
		sendTurn("lower_left");
	}

	@FXML
	void lower_middle_press(ActionEvent event) throws IOException {
		sendTurn("lower_middle");
	}

	@FXML
	void lower_right_press(ActionEvent event) throws IOException {
		sendTurn("lower_right");
	}

	@FXML
	void middle_left_press(ActionEvent event) throws IOException {
		sendTurn("middle_left");
	}

	@FXML
	void middle_middle_press(ActionEvent event) throws IOException {
		sendTurn("middle_middle");
	}

	@FXML
	void middle_right_press(ActionEvent event) throws IOException {
		sendTurn("middle_right");
	}

	@FXML
	void upper_left_press(ActionEvent event) throws IOException {
		sendTurn("upper_left");
	}

	@FXML
	void upper_middle_press(ActionEvent event) throws IOException {
		sendTurn("upper_middle");
	}

	@FXML
	void upper_right_press(ActionEvent event) throws IOException {
		sendTurn("upper_right");
	}

}
