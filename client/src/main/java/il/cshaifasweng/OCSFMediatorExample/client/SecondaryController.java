package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SecondaryController {

    private String port= "localhost";
    private int ip=3000;

    @FXML // fx:id="ipTF"
    private TextField ipTF; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="portTF"
    private TextField portTF; // Value injected by FXMLLoader

    @FXML
    void handleOkButton(ActionEvent event) {
        port = portTF.getText();
        ip = Integer.parseInt(ipTF.getText());
    }

    @FXML
    public void initialize() {
        // Set initial values in the text fields
        portTF.setText(port);
        ipTF.setText(Integer.toString(ip));
    }


}
