package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Turn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private SimpleClient client;
    private PrimaryController primaryController; // to make changes on event

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
    	//connect to server and get symbol
        client = SimpleClient.getClient();
    	client.openConnection();
        client.sendToServer("add client");

        // load the primary FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent root = fxmlLoader.load();
        primaryController = fxmlLoader.getController();  // will be used to call fxml controller functions later
        primaryController.set_player_symbol(client.get_player_symbol());

        // open game on screen
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    
    // terminate client
    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
        client.sendToServer("remove client");
        client.closeConnection();
		super.stop();
	}

    // receive turn from event bus
    @Subscribe
    public void onTurnEvent(TurnEvent event) {
    	Platform.runLater(() -> {
            primaryController.receiveTurn(event.getTurn());
            if(!event.getTurn().get_player_win().isEmpty()){
                System.out.println(event.getTurn().get_player_win());
            }
        });

    }

	public static void main(String[] args) {
        launch();
    }

}