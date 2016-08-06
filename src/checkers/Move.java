package checkers;

import java.awt.Color;
import java.util.ArrayList;

public class Move {
	private int Row = 8;
	private int Col = 8;
	private Piece[][] pieces;
	private int[][] directions = new int[][]{{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
	private boolean turnIsBlue = false;
	
	public Move(Piece[][] squares) {
		pieces = squares;
	}
	
	public void movePiece(Piece init, Piece dest) {
		deColor();
		dest.getButton().setIcon(init.getButton().getIcon());
		dest.getButton().setDisabledIcon(init.getButton().getIcon());
		dest.setIsOccupied(true);
		dest.setIsBlue(init.getIsBlue());
		dest.setIsKing(init.getIsKing());
		
		init.getButton().setEnabled(false);
		init.getButton().setIcon(null);
		init.setIsOccupied(false);
		init.setIsKing(false);
	}
	
	public int[] detectMovable(int i, int j) {
		if(turnIsBlue){
			if(pieces[i][j].getIsOccupied()) {
				if(pieces[i][j].getIsBlue()) {
					return blueMovable(i, j);
				}
			}
		}
		else {
			if(pieces[i][j].getIsOccupied()) {
				if(!pieces[i][j].getIsBlue()) {
					return redMovable(i, j);
				}
			}
		}
		return null;
	}
	
	public int[] detectJumpable(int i, int j) {
		if(turnIsBlue){
			if(pieces[i][j].getIsOccupied()) {
				if(pieces[i][j].getIsBlue()) {
					return blueJumpable(i, j);
				}
			}
		}
		else {
			if(pieces[i][j].getIsOccupied()) {
				if(!pieces[i][j].getIsBlue()) {
					return redJumpable(i, j);
				}
			}
		}
		return null;
	}
	
	public int[] blueMovable(int i, int j) {
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x <= 7 && x >= 0) {
					if(!pieces[x][y].getIsOccupied()) {
						return new int[]{i, j};
					}
				}
			}
		}
		else {
			for(int a = 0; a < 2; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x <= 7) {
					if(!pieces[x][y].getIsOccupied()) {
						return new int[]{i, j};
					}
				}
			}
		}
		return null;
	}
	
	public int[] redMovable(int i, int j) {
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x >= 0 && x <= 7) {
					if(!pieces[x][y].getIsOccupied()) {
						return new int[]{i, j};
					}
				}
			}
		}
		else {
			for(int a = 2; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x >= 0) {
					if(!pieces[x][y].getIsOccupied()) {
						return new int[]{i, j};
					}
				}
			}
		}
		return null;
	}
	
	public int[] blueJumpable(int i, int j) {
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 <= 7 && x2 >= 0 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && !pieces[x1][y1].getIsBlue()) {
						return new int[]{i, j};
					}
				}
			}
		}
		else {
			for(int a = 0; a < 2; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 <= 7 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && !pieces[x1][y1].getIsBlue()) {
						return new int[]{i, j};
					}
				}
			}
		}
		return null;
	}
	
	public int[] redJumpable(int i, int j) {
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 >= 0 && x2 <= 7) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && pieces[x1][y1].getIsBlue()) {
						return new int[]{i, j};
					}
				}
			}
		}
		else {
			for(int a = 2; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 >= 0 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && pieces[x1][y1].getIsBlue()) {
						return new int[]{i, j};
					}
				}
			}
		}
		return null;
	}
	
	public ArrayList<int[]> blueToMove(int i, int j) {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x <= 7 && x >= 0) {
					if(!pieces[x][y].getIsOccupied()) {
						positions.add(new int[]{x, y});
					}
				}
			}
		}
		else {
			for(int a = 0; a < 2; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x <= 7) {
					if(!pieces[x][y].getIsOccupied()) {
						positions.add(new int[]{x, y});
					}
				}
			}
		}
		return positions;
	}
	
	public ArrayList<int[]> blueToJump(int i, int j) {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 <= 7 && x2 >= 0) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && !pieces[x1][y1].getIsBlue()) {
						positions.add(new int[]{x2, y2});
					}
				}
			}
		}
		else {
			for(int a = 0; a < 2; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 <= 7 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && !pieces[x1][y1].getIsBlue()) {
						positions.add(new int[]{x2, y2});
					}
				}
			}
		}
		return positions;
	}
	
	public ArrayList<int[]> redToMove(int i, int j) {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x >=0 && x<= 7) {
					if(!pieces[x][y].getIsOccupied()) {
						positions.add(new int[]{x, y});
					}
				}
			}
		}
		else {
			for(int a = 2; a < 4; a++) {
				int x = directions[a][0] + i;
				int y = directions[a][1] + j;
				if(y >= 0 && y <= 7 && x >= 0) {
					if(!pieces[x][y].getIsOccupied()) {
						positions.add(new int[]{x, y});
					}
				}
			}
		}
		return positions;
	}
	
	public ArrayList<int[]> redToJump(int i, int j) {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		if(pieces[i][j].getIsKing()) {
			for(int a = 0; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 >= 0 & x2 <= 7 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && pieces[x1][y1].getIsBlue()) {
						positions.add(new int[]{x2, y2});
					}
				}
			}
		}
		else {
			for(int a = 2; a < 4; a++) {
				int x1 = directions[a][0] + i;
				int y1 = directions[a][1] + j;
				int x2 = directions[a][0] * 2 + i;
				int y2 = directions[a][1] * 2 + j;
				if(y2 >= 0 && y2 <= 7 && x2 >= 0 ) {
					if(!pieces[x2][y2].getIsOccupied() && pieces[x1][y1].getIsOccupied() && pieces[x1][y1].getIsBlue()) {
						positions.add(new int[]{x2, y2});
					}
				}
			}
		}
		return positions;
	}
	
	public void deColor() {
		for(int i = 0; i < Row; i++) {
			for(int j = 0; j < Col; j++) {
				if(pieces[i][j].getButton().getBackground().equals(Color.GREEN)) {
					pieces[i][j].getButton().setEnabled(false);
					pieces[i][j].getButton().setBackground(Color.WHITE);
				}
			}
		}
	}
	
	public void showMovable(int x, int y) {
		deColor();
		if(turnIsBlue){
			for(int[] position : blueToMove(x, y)) {
				pieces[position[0]][position[1]].getButton().setEnabled(true);
				pieces[position[0]][position[1]].getButton().setBackground(Color.GREEN);
			}
		}
		else {
			for(int[] position : redToMove(x, y)) {
				pieces[position[0]][position[1]].getButton().setEnabled(true);
				pieces[position[0]][position[1]].getButton().setBackground(Color.GREEN);
			}
		}
	}
	
	public boolean showJumpable(int x, int y) {
		deColor();
		if(turnIsBlue){
			ArrayList<int[]> positions = blueToJump(x, y);
			if(!positions.isEmpty()) {
				for(int[] position : positions) {
					pieces[position[0]][position[1]].getButton().setEnabled(true);
					pieces[position[0]][position[1]].getButton().setBackground(Color.GREEN);
				}
			}
			else return false;
		}
		else {
			ArrayList<int[]> positions = redToJump(x, y);
			if(!positions.isEmpty()) {
				for(int[] position : positions) {
					pieces[position[0]][position[1]].getButton().setEnabled(true);
					pieces[position[0]][position[1]].getButton().setBackground(Color.GREEN);
				}
			}
			else return false;
		}
		return true;
	}
	
	public boolean getTurnIsBlue() {
		return turnIsBlue;
	}
	
	public void setTurnIsBlue(boolean b) {
		turnIsBlue = b;
	}

}
