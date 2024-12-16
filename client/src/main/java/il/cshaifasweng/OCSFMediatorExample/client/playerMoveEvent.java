package il.cshaifasweng.OCSFMediatorExample.client;

public class playerMoveEvent
{
	String player;
	String buttonId;
	String message;
	public playerMoveEvent(String message)
	{
		this.message = message;
		this.player = message.substring(9,10);
		this.buttonId = message.substring(11,19);
		
	}
}


