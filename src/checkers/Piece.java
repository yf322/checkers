package checkers;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class Piece {
	private JButton square;
	private int[] piecePostion;
	private boolean isBlue = true;
	private boolean isOccupied = false;
	private boolean isKing = false;
	
	
	public Piece(int x, int y) {
		piecePostion = new int[]{x ,y};
		square = new JButton();
		square.setPreferredSize(new Dimension(8, 8));
		square.setEnabled(false);
		square.setBackground(Color.WHITE);
		if((x+y)%2 == 1) {
			square.setBackground(Color.BLACK);
		}
	}
	
	public int[] getPiecePosition() {
		return piecePostion;
	}
	
	public JButton getButton() {
		return square;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}
	
	public void setIsOccupied(boolean b) {
		isOccupied = b;
	}
	
	public boolean getIsKing() {
		return isKing;
	}
	
	public void setIsKing(boolean b) {
		isKing = b;
	}
	
	public boolean getIsBlue() {
		return isBlue;
	}
	
	public void setIsBlue(boolean b) {
		isBlue = b;
	}

}
