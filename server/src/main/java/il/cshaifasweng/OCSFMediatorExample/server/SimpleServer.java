package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.Turn;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import java.io.IOException;
import java.util.ArrayList;


import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	// for game management well keep updated board and current player turn
	private String[][] game_board = new String[3][3];
	private String current_player = "O";
	public SimpleServer(int port) {
		super(port);
		//reset game_board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game_board[i][j] = "";
			}
		}
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		if (msg instanceof Turn) {
			Turn turn = (Turn) msg;
			manage_turn(turn);

		} else {
			String msgString = (String) msg;
			if (msgString.startsWith("add client")) {
					for (SubscribedClient subscribedClient : SubscribersList) {
						if (subscribedClient.getClient().equals(client)) {
							SubscribersList.remove(subscribedClient);
							break;
						}
					}
					return;
				}
				SubscribedClient connection = new SubscribedClient(client);
				SubscribersList.add(connection);
				try {//assign each client snd its player Symbol
                    client.sendToClient(current_player);
                    current_player = "X";
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else if (msgString.startsWith("remove client")) {
				if (!SubscribersList.isEmpty()) {
					for (SubscribedClient subscribedClient : SubscribersList) {
						if (subscribedClient.getClient().equals(client)) {
							SubscribersList.remove(subscribedClient);
							break;
						}
					}
				}
			    if(SubscribersList.isEmpty()){// the game is over
                    this.close();
                }
            }
		}

	}

	//manages turn
	private void manage_turn(Turn turn) {
		if(!turn.get_player_symbol().equals(current_player)){// check if it's the players turn
			return;
		}
		//put the new input on the board
		if(game_board[turn.getY()][turn.getX()].isEmpty()){// if the place is empty
			game_board[turn.getY()][turn.getX()] = current_player;// update board

			//check for win
			if(checkIfWin()) {
				turn.set_player_win(current_player); // the current player won
				System.out.println(current_player);
			}else if(checkIfFull()){
				turn.set_player_win("d"); // board is full and nobody won = draw
				System.out.println(current_player);
			}

			//change turns
			if(current_player.equals("O")){
				current_player = "X";
			}
			else{
				current_player = "O";
			}
			sendToAllClients(turn); // send the turn to the players to update their screens
		}

	}

	// check if the board is full
	private boolean checkIfFull(){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(game_board[i][j].isEmpty()){ // return false if there is an empty cell
					return false;
				}
			}
		}
		return true;
	}

	// check if player won
	private boolean checkIfWin() {
		//check rows
		for (int i = 0; i < 3; i++) {
			if (game_board[i][0].equals(current_player) && game_board[i][1].equals(current_player) && game_board[i][2].equals(current_player)) {
				return true;
			}
		}

		//check columns
		for (int i = 0; i < 3; i++) {
			if (game_board[0][i].equals(current_player) && game_board[1][i].equals(current_player) && game_board[2][i].equals(current_player)) {
				return true;
			}
		}

		//check diagonals
		if (game_board[0][0].equals(current_player) && game_board[1][1].equals(current_player) && game_board[2][2].equals(current_player)) {
			return true; //main diagonal
		}
		if (game_board[0][2].equals(current_player) && game_board[1][1].equals(current_player) && game_board[2][0].equals(current_player)) {
			return true; //anti-diagonal
		}

		//player hasn't won
		return false;
	}


	public void sendToAllClients(Turn message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
