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
		} else {

			System.out.println(message);
		}
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
//	public static void sendButtonInfo(String buttonID,int row,int col)
//	{
//		System.out.println("Getting button cell with ID: " + buttonID);
//		String msg="#Button"+" "+buttonID+" "+row+" "+col+" "+ID;
//		try {
//				getClient().sendToServer(msg);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public String getID()
	{
		return ID;
	}
	public void setID(String id)
	{
		ID=id;
	}
}