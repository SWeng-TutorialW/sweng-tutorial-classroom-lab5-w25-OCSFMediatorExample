package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import java.io.IOException;

public class SimpleClient extends AbstractClient {
	public static String ip = "127.0.0.1";
	public static int port = 3000;
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else if (msg.toString().startsWith("Start")) {
			try {
				SecondaryController.switchTogame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (msg.toString().startsWith("Player")) {
			String msg2 = msg.toString().substring(0,14);
			int row = Character.getNumericValue(msg.toString().charAt(14)); // e.g., button00 -> 0
			int col = Character.getNumericValue(msg.toString().charAt(15)); // e.g., button00 -> 0
			String operation = msg.toString().charAt(7)+"";
			Game.getGame().setGame(row, col, operation);
			Game.getGame().disableBoard(msg2);
		} else if (msg.toString().startsWith("It's a Draw!")) {
			String msg2 = msg.toString().substring(0,12);
			int row = Character.getNumericValue(msg.toString().charAt(12)); // e.g., button00 -> 0
			int col = Character.getNumericValue(msg.toString().charAt(13)); // e.g., button00 -> 0
			String operation = msg.toString().charAt(14)+"";
			Game.getGame().setGame(row, col, operation);
			Game.getGame().disableBoard(msg2);
		}
		else {
			int row = Character.getNumericValue(msg.toString().charAt(0)); // e.g., button00 -> 0
			int col = Character.getNumericValue(msg.toString().charAt(1)); // e.g., button00 -> 0
			String operation = msg.toString().charAt(2)+"";
			Game.getGame().setGame(row, col, operation);
			return;
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient(ip, port);
		}
		return client;
	}

}
