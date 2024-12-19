package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Turn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private SecondaryController secondaryController; // to make changes on event
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        EventBus.getDefault().register(this);

        // open initiation gui
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Parent secondaryRoot = fxmlLoader.load();
        secondaryController = fxmlLoader.getController();  // will be used to call fxml controller functions later
        secondaryController.setApp(this);

        // open on screen
        scene = new Scene(secondaryRoot, 200, 200);
        stage.setScene(scene);
        secondaryController.setStage(primaryStage);
        stage.show();

    }
    public void startGame(int port, String host) throws Exception {
        //connect to server and get symbol
        client = SimpleClient.startClient(port, host);
        client.openConnection();
        client.sendToServer("add client");

        // load the primary FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent primaryRoot = fxmlLoader.load();
        primaryController = fxmlLoader.getController();  // will be used to call fxml controller functions later
        primaryController.set_player_symbol(client.get_player_symbol());

        // open game on screen
        scene = new Scene(primaryRoot, 200, 200);
        primaryStage.setScene(scene);
        primaryController.setStage(primaryStage); // to close on finish
        primaryStage.show();
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
        client.sendToServer("remove client") ;
        client.closeConnection();
		super.stop();
	}

    // receive turn from event bus
    @Subscribe
    public void onTurnEvent(TurnEvent event) {// I could have added the controller to the event bus, but its working and you didn't ask for it
    	Platform.runLater(() -> {
            primaryController.receiveTurn(event.getTurn());
            if(!event.getTurn().get_player_win().isEmpty()){// game is over
                primaryController.game_is_over(event.getTurn().get_player_win());// enable finish game button
            }

        });
    }

	public static void main(String[] args) {
        launch();
    }

}