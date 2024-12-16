
/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class PrimaryController {

	@FXML // fx:id="Button00"
	private Button Button00; // Value injected by FXMLLoader

	@FXML // fx:id="Button01"
	private Button Button01; // Value injected by FXMLLoader

	@FXML // fx:id="Button02"
	private Button Button02; // Value injected by FXMLLoader

	@FXML // fx:id="Button10"
	private Button Button10; // Value injected by FXMLLoader

	@FXML // fx:id="Button11"
	private Button Button11; // Value injected by FXMLLoader

	@FXML // fx:id="Button12"
	private Button Button12; // Value injected by FXMLLoader

	@FXML // fx:id="Button20"
	private Button Button20; // Value injected by FXMLLoader

	@FXML // fx:id="Button21"
	private Button Button21; // Value injected by FXMLLoader

	@FXML // fx:id="Button22"
	private Button Button22; // Value injected by FXMLLoader

	@FXML // fx:id="XOMatrix"
	private GridPane XOMatrix; // Value injected by FXMLLoader

	@FXML // fx:id="playerTurn"
	private TextField playerTurn; // Value injected by FXMLLoader

	@FXML // fx:id="Player"
	private Label Player; // Value injected by FXMLLoader

	@FXML // fx:id="root"
	private AnchorPane root; // Value injected by FXMLLoader
	@FXML // fx:id="root"



	private Map<String,Button> buttonStringMap = new HashMap<String,Button>();

	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
			System.out.println("in primary contrler Warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void initialize(){
		System.out.println("initialize");
		try {
			EventBus.getDefault().register(this);
			for(Node node : XOMatrix.getChildren())
			{
				buttonStringMap.put(node.getId(),(Button)node);
			}
			SimpleClient.getClient().sendToServer("add client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ButtonClicked(ActionEvent event) throws IOException {
		// Get the button that was clicked
		Button clickedButton = (Button) event.getSource();
		// Get the button's ID (fx:id set in FXML)
		String buttonId = clickedButton.getId();
		Integer row = GridPane.getRowIndex(clickedButton); // Get the row index
		Integer col = GridPane.getColumnIndex(clickedButton); // Get the column index

		// Handle null cases (if no row or column is set explicitly)
		row = (row == null) ? 0 : row;
		col = (col == null) ? 0 : col;
		//SimpleClient.getClient().sendButtonInfo(buttonId, row, col);
		String msg="#Button"+","+"ButtonID-"+buttonId+","+"row-"+row+","+"col-"+col+","+"playerID-"+SimpleClient.getClient().getID();
		System.out.println(msg);
		try {
				SimpleClient.getClient().sendToServer(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Button getButton(String buttonId){
		Button check=buttonStringMap.get("Button00");
		System.out.println(check.getId());
		 if(buttonStringMap.get(buttonId)!=null){
			 return buttonStringMap.get(buttonId);
		 }
		 else {
			 System.out.println("Button not found");
			 return null;
		 }
    }
	@Subscribe
	public void onPlayerMove(playerMoveEvent event) {
		Platform.runLater(()-> {
			Button button = buttonStringMap.get(event.buttonId);
			playerTurn.setText("Turn: "+event.turn);
			button.setText(event.player);
			System.out.println("turn- "+event.turn);
			System.out.println("player- "+SimpleClient.getClient().getID());
			if(event.turn.equals(SimpleClient.getClient().getID()))
			{
				System.out.println("in primary contrler PlayerMove");
				buttonStringMap.forEach((key, value) ->{
					value.setDisable(false); // Disable the button
				});
			}
			else
			{
				buttonStringMap.forEach((key, value) ->{
					value.setDisable(true); // Disable the button
				});
			}
		}
		);
	}
	@Subscribe
	public void onNewGameEvent(newGameEvent event) {
		Platform.runLater(()-> {
					Player.setText("Player: "+event.player);
				}
		);
		if(SimpleClient.getClient().getID().equals("X"))
		{
			buttonStringMap.forEach((key, value) ->{
				value.setDisable(true); // Disable the button
			});
		}
	}

}
