package il.cshaifasweng.OCSFMediatorExample.client;

public class playerMoveEvent
{
	String player;
	String buttonId;
	String message;
	public playerMoveEvent(String message)
	{
		this.player =message.substring(12,13);
		System.out.println(player+"in playerEvent");
		
	}
}


