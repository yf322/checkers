package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;

public class Board extends JFrame {

	private JPanel contentPane;
	private int Row = 8;
	private int Col = 8;
	private JButton[][] squares;

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
	}
	
	public void deploy() {
		paintBoard();
		init();
		topBar();
	}
	
	public void init() {
		Image image;
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 8; i++) {
				if((i+j)%2 == 0){
					try {
						image = ImageIO.read(ClassLoader.getSystemResource("Black.png"));
						squares[j][i].setIcon(new ImageIcon(image));
						squares[j][i].setEnabled(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
		for(int j = 5; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				if((i+j)%2 == 0){
					try {
						image = ImageIO.read(ClassLoader.getSystemResource("White.png"));
						squares[j][i].setIcon(new ImageIcon(image));
						squares[j][i].setEnabled(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		}
	}
	
	public void paintBoard() {
		JPanel board = new JPanel(new GridLayout(0, Row));
		board.setPreferredSize(new Dimension(640, 640));
		JButton square;
		squares = new JButton[Row][Col];
		
		for(int i = 0; i < Row; i++) {
			for(int j = 0; j < Col; j++) {
				square = new JButton();
				square.setPreferredSize(new Dimension(8, 8));
				square.setEnabled(false);
				square.setBackground(Color.WHITE);
				if((i+j)%2 == 1) {
					square.setBackground(Color.BLACK);
				}
				board.add(square, BorderLayout.CENTER);
				squares[i][j] = square;
			}
		}
		
		contentPane.add(board, BorderLayout.CENTER);
	}
	
	public void topBar() {
		JPanel topBar = new JPanel();
		JButton quit = new JButton("QUIT");
		JLabel uniqueId = new JLabel("Unique ID : ");
		
		topBar.add(quit, BorderLayout.WEST);
		topBar.add(uniqueId, BorderLayout.EAST);
		contentPane.add(topBar, BorderLayout.NORTH);
	}

}
