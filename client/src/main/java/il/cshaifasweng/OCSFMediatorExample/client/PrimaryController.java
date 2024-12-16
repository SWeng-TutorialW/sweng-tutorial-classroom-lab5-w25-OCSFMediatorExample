
/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;

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

	@FXML // fx:id="root"
	private AnchorPane root; // Value injected by FXMLLoader

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
		String msg="#Button"+","+buttonId+","+row+","+col+","+SimpleClient.getClient().getID();
		System.out.println(msg);
		try {
				SimpleClient.getClient().sendToServer(msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateMatrix(playerMoveEvent event) throws IOException {

	}

}
