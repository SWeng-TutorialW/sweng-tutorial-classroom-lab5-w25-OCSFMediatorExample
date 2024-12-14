package il.cshaifasweng.OCSFMediatorExample.client;

public class playerMoveEvent
{
	String message;
	String playerID;
	String gridIndex;
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	public String getMessage() {
		return message;
	}
	public void setGridIndex(String gridIndex) {
		this.gridIndex = gridIndex;
	}
	public playerMoveEvent(String message) {
		this.message = message;
		if(message.contains("X"))
		{
			setPlayerID("X");
		}
		else
		{
			setPlayerID("O");
		}
		int ButtonIndex=message.indexOf("B");
		setGridIndex(message.substring(ButtonIndex));
	}

}


