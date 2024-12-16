package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	String[][] XOMatrix=new String[3][3];
	boolean turn=false;
	public SimpleServer(int port) {
		super(port);
		
	}
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if(msgString.startsWith("add client"))
		{
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			try {
				if (SubscribersList.size() == 2) {

					SubscribersList.getFirst().getClient().sendToClient("X");
					SubscribersList.getLast().getClient().sendToClient("O");
					//add notify all to enable buttons
				} else {
					SubscribersList.getFirst().getClient().sendToClient("waiting for another player");//add a messagehandle in client that is recived wauting message disable buttons
				}
			}
			catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			try {
				client.sendToClient("client added successfully");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if(msgString.startsWith("remove client")){
			if(!SubscribersList.isEmpty()){
				for(SubscribedClient subscribedClient: SubscribersList){
					if(subscribedClient.getClient().equals(client)){
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
		if(msgString.startsWith("#Button"))
		{
			System.out.println(msgString);
			String buttonid=msgString.substring(8,16);
			String col=msgString.substring(19,20);
			String row=msgString.substring(17,18);
			String player=msgString.substring(21,22);
			updateMatrix(player,Integer.parseInt(row),Integer.parseInt(col));
			System.out.println("in server after matrix update");
			if(isWinner(Integer.parseInt(row),Integer.parseInt(col),player))
			{
				sendToAllClients(player+" is the winner");
				return;
			}
			else
			{
				turn=!turn;
				System.out.println("in server winner->else");
				sendToAllClients("#continue"+","+player+","+turn+","+buttonid);
				return;
			}
		}
	}
	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	private void updateMatrix(String player,int row,int col)
	{
		System.out.println("in matrix update");
		XOMatrix[row][col]=player;
	}
	private boolean isWinner(int row,int col,String player)
	{
		// Check the row of the last move
		int size=3;
		boolean rowWin = true;
		for (int c = 0; c < size; c++) {
			if (!XOMatrix[row][c].equals(player)) {
				rowWin = false;
				break;
			}
		}
		if (rowWin) return true;

		// Check the column of the last move
		boolean colWin = true;
		for (int r = 0; r < size; r++) {
			if (!XOMatrix[r][col].equals(player)) {
				colWin = false;
				break;
			}
		}
		if (colWin) return true;

		// Check the main diagonal (if applicable)
		if (row == col) { // The move is on the main diagonal
			boolean diagWin = true;
			for (int i = 0; i < size; i++) {
				if (!XOMatrix[i][i].equals(player)) {
					diagWin = false;
					break;
				}
			}
			if (diagWin) return true;
		}

		// Check the anti-diagonal (if applicable)
		if (row + col == size - 1) { // The move is on the anti-diagonal
			boolean antiDiagWin = true;
			for (int i = 0; i < size; i++) {
				if (!XOMatrix[i][size - 1 - i].equals(player)) {
					antiDiagWin = false;
					break;
				}
			}
			if (antiDiagWin) return true;
		}

		return false; // No winner found
	}
}
