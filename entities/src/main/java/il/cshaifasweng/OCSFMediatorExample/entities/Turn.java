package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Turn implements Serializable {
	private String player_symbol;
	private int x;
	private int y;
	private String player_win = ""; // will get the wining player's symbol or "d" if draw

	public Turn(String player_symbol, int x, int y) {
		this.player_symbol = player_symbol;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public String get_player_symbol() {
		return player_symbol;
	}

	public int getY() {
		return y;
	}

	public void set_player_win(String player_win) {
		this.player_win = player_win;
	}
	public String get_player_win() {
		return player_win;
	}
}
