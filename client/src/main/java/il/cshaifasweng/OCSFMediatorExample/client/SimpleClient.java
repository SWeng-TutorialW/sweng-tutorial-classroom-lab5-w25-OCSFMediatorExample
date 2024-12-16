package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;
	private String ID;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		String message = msg.toString();
		if (message.equals("X") || message.equals("O")) {
			getClient().setID(message);
			System.out.format("My ID is :%s%n", ID);
		}
		if(message.contains("is winner"))
		{
			String winner=message.substring(0,0);
			System.out.format("the winner is :%s%n", winner);
			EventBus.getDefault().post(new WinEvent(winner));
		}
		if(message.startsWith("#continue"))
		{
			System.out.println("in continue");
			EventBus.getDefault().post(new playerMoveEvent(message));
		}
			System.out.println(message);
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	public String getID()
	{
		return ID;
	}
	public void setID(String id)
	{
		ID=id;
	}
}