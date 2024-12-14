package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private static int clientCounter = 0;
	private String[][] board = new String[3][3];
	private boolean isXTurn = true;
	private String currentPlayer;
	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(msgString.startsWith("add client")){
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			try {
				client.sendToClient("client added successfully");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else if(msgString.startsWith("remove client")){
			if(!SubscribersList.isEmpty()){
				for(SubscribedClient subscribedClient: SubscribersList){
					if(subscribedClient.getClient().equals(client)){
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		} else if (msgString.startsWith("ip and socket changed")) {
			System.out.format("ip and socket changed successfully for client %s\n", client.getInetAddress().getHostAddress());
			if(clientCounter==0){
				clientCounter++;
				return ;
			} else if (clientCounter==1) {
				clientCounter++;
				sendToAllClients("Start");
				System.out.format("the game started\n");
				return ;
			}
		} else{
			int row = Character.getNumericValue(msgString.charAt(0)); // e.g., button00 -> 0
			int col = Character.getNumericValue(msgString.charAt(1)); // e.g., button00 -> 0
			// If the button is already clicked, do nothing
			if(isXTurn){
				if(!client.getInetAddress().getHostAddress().equals(SubscribersList.getFirst().getClient().getInetAddress().getHostAddress())){
					return;
				}
				currentPlayer = "X";
			}else{
				if(!client.getInetAddress().getHostAddress().equals(SubscribersList.get(1).getClient().getInetAddress().getHostAddress())){
					return;
				}
				currentPlayer = "O";
			}
			if (board[row][col] != null) return;
			if (checkWinner(currentPlayer)) {
				sendToAllClients("Player " + currentPlayer + " Wins!"+msgString.charAt(0)+msgString.charAt(1));
				return;
			} else if (isBoardFull()) {
				sendToAllClients("It's a Draw!"+msgString.charAt(0)+msgString.charAt(1)+currentPlayer);
				return;
			}
			sendToAllClients(msgString.charAt(0)+msgString.charAt(1)+currentPlayer);
			board[row][col] = currentPlayer;
			return;
		}
	}
	private boolean checkWinner(String player) {
		// Check rows, columns, and diagonals for a win
		for (int i = 0; i < 3; i++) {
			// Check rows
			if (player.equals(board[i][0]) && player.equals(board[i][1]) && player.equals(board[i][2])) return true;
			// Check columns
			if (player.equals(board[0][i]) && player.equals(board[1][i]) && player.equals(board[2][i])) return true;
		}
		// Check diagonals
		if (player.equals(board[0][0]) && player.equals(board[1][1]) && player.equals(board[2][2])) return true;
		if (player.equals(board[0][2]) && player.equals(board[1][1]) && player.equals(board[2][0])) return true;

		return false;
	}

	private boolean isBoardFull() {
		// Check if all cells are filled
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) return false;
			}
		}
		return true;
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

}
