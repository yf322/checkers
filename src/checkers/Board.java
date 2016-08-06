package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Board extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int Row = 8;
	private int Col = 8;
	private Piece[][] squares = new Piece[Row][Col];
	private Move move = new Move(squares);
	private Piece selectedPiece;
	private Image image1;
	private Image image2;
	private Image image3;
	private Image image4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board frame = new Board();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Board() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		deploy();
		refresh();
	}
	
	public void deploy() {
		try {
			image1 = ImageIO.read(ClassLoader.getSystemResource("Black.png"));
			image2 = ImageIO.read(ClassLoader.getSystemResource("White.png"));
			image3 = ImageIO.read(ClassLoader.getSystemResource("BlackKing.png"));
			image4 = ImageIO.read(ClassLoader.getSystemResource("WhiteKing.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paintBoard();
		init();
		topBar();
	}
	
	public void refresh() {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		for(int i = 0; i < Row; i++) {
			for(int j = 0; j < Col; j++) {
				int[] temp = move.detectJumpable(i, j);
				if(temp != null){
					positions.add(temp);
				}
			}
		}
		if(!positions.isEmpty()) {
			for(int[] position : positions) {
				try {
					squares[position[0]][position[1]].getButton().setEnabled(true);
					squares[position[0]][position[1]].getButton().setBackground(Color.GREEN);
				} catch (Exception e) {
					
				}
				
			}
		}
		else {
			for(int i = 0; i < Row; i++) {
				for(int j = 0; j < Col; j++) {
					int[] temp = move.detectMovable(i, j);
					if(temp != null){
						positions.add(temp);
					}
				}
			}
			for(int[] position : positions) {
				try {
					squares[position[0]][position[1]].getButton().setEnabled(true);
					squares[position[0]][position[1]].getButton().setBackground(Color.GREEN);
				} catch (Exception e) {
					
				}
				
			}
		}
	}
	
	public void init() {
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 8; j++) {
				if((i+j)%2 == 0){
					squares[i][j].getButton().setIcon(new ImageIcon(image1));
					squares[i][j].getButton().setDisabledIcon(new ImageIcon(image1));
					squares[i][j].setIsOccupied(true);
				}
				
			}
		}
		
		for(int i = 5; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i+j)%2 == 0){
					squares[i][j].getButton().setIcon(new ImageIcon(image2));
					squares[i][j].getButton().setDisabledIcon(new ImageIcon(image2));
					squares[i][j].setIsOccupied(true);
					squares[i][j].setIsBlue(false);
				}
				
			}
		}
	}
	
	public void paintBoard() {
		JPanel board = new JPanel(new GridLayout(0, Row));
		board.setPreferredSize(new Dimension(640, 640));
		Piece square;
		
		ActionListener onClick = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] position = ((JButton)e.getSource()).getName().split(",");
				if(squares[Integer.parseInt(position[0])][Integer.parseInt(position[1])].getIsOccupied()) {
					selectedPiece = squares[Integer.parseInt(position[0])][Integer.parseInt(position[1])];
					if(!move.showJumpable(Integer.parseInt(position[0]), Integer.parseInt(position[1]))) {
						move.showMovable(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
					}
				}
				else {
					Piece middle = getMiddlePiece(selectedPiece, squares[Integer.parseInt(position[0])][Integer.parseInt(position[1])]);
					if(middle == null) {
						move.movePiece(selectedPiece, squares[Integer.parseInt(position[0])][Integer.parseInt(position[1])]);
						move.setTurnIsBlue(!move.getTurnIsBlue());
						checkKing();
						refresh();
					}
					else {
						middle.getButton().setIcon(null);
						middle.getButton().setDisabledIcon(null);
						middle.setIsOccupied(false);
						move.movePiece(selectedPiece, squares[Integer.parseInt(position[0])][Integer.parseInt(position[1])]);
						checkKing();
						if(!move.showJumpable(Integer.parseInt(position[0]), Integer.parseInt(position[1]))) {
							move.setTurnIsBlue(!move.getTurnIsBlue());
						}
						move.deColor();
						refresh();
					}
				}
			}
		};
		
		for(int i = 0; i < Row; i++) {
			for(int j = 0; j < Col; j++) {
				square = new Piece(i, j);
				square.getButton().setName(i+","+j);
				square.getButton().addActionListener(onClick);
				board.add(square.getButton(), BorderLayout.CENTER);
				squares[i][j] = square;
			}
		}
		
		contentPane.add(board, BorderLayout.CENTER);
	}
	
	public void checkKing() {
		for(int i = 0; i < Col; i++) {
			if(squares[0][i].getIsOccupied() && !squares[0][i].getIsKing() && !squares[0][i].getIsBlue()) {
					squares[0][i].setIsKing(true);
					squares[0][i].getButton().setIcon(new ImageIcon(image4));
					squares[0][i].getButton().setDisabledIcon(new ImageIcon(image4));
			}
			else if (squares[7][i].getIsOccupied() && !squares[7][i].getIsKing() && squares[7][i].getIsBlue()) {
					squares[7][i].setIsKing(true);
					squares[7][i].getButton().setIcon(new ImageIcon(image3));
					squares[7][i].getButton().setDisabledIcon(new ImageIcon(image3));
			}
		}
	}
	
	public Piece getMiddlePiece(Piece init, Piece dest) {
		if(Math.abs(init.getPiecePosition()[0] - dest.getPiecePosition()[0]) != 1) {
			int midX = (init.getPiecePosition()[0] + dest.getPiecePosition()[0])/2;
			int midY = (init.getPiecePosition()[1] + dest.getPiecePosition()[1])/2;
			return squares[midX][midY];
		}
		return null;
	}
	
	public void topBar() {
		JPanel topBar = new JPanel();
		JButton quit = new JButton("QUIT");
		JLabel uniqueId = new JLabel("Unique ID : ");
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		topBar.add(quit, BorderLayout.WEST);
		topBar.add(uniqueId, BorderLayout.EAST);
		contentPane.add(topBar, BorderLayout.NORTH);
	}

}
