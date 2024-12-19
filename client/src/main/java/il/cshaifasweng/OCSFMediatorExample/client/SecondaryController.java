package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SecondaryController {

    private String host= "localhost";
    private int port = 3000;
    public App app;
    private Stage stage;


    public void setApp(App app) {
        this.app = app;
    }

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="myIP"
    private TextField myIP; // Value injected by FXMLLoader

    @FXML // fx:id="myPort"
    private TextField myPort; // Value injected by FXMLLoader

    @FXML
    void handleOkButton(ActionEvent event) throws Exception {
        port = Integer.parseInt(myPort.getText());
        host = myIP.getText();
        stage.close();
        app.startGame(port, host);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void initialize() {
        // Set initial values in the text fields
        myPort.setText(Integer.toString(port));
        myIP.setText(host);
    }


}
