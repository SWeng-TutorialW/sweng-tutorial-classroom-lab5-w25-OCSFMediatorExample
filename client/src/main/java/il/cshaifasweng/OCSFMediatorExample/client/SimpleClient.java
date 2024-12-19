package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Turn;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	private String player_symbol;
	private SimpleClient(String host, int port) {

		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Turn.class)) { // msg is turn, so publish it to players through event bus
			EventBus.getDefault().post(new TurnEvent((Turn) msg));
		}
		else{
			player_symbol = (String)msg;// get player number from server
		}
	}

	public String get_player_symbol() {
		return player_symbol;
	}

	public static SimpleClient startClient(int port, String host) {
		if (client == null) {
			client = new SimpleClient(host, port);
		}
		return client;

	}
	public static SimpleClient getClient() {
		return client;
	}
}
