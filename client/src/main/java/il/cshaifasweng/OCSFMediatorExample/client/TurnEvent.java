package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Turn;
public class TurnEvent {
	private Turn current_turn;

	public Turn getTurn() {
		return current_turn;
	}

	public TurnEvent(Turn current_turn) {
		this.current_turn = current_turn;
	}
}
