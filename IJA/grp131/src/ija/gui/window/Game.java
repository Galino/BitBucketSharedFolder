package ija.gui.window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Create top-level container of gui
 */
public class Game {
	
	public static JFrame mainFrame;
	public static JPanel menuPanel;
	public static JPanel preparePanel;
	public static JPanel gamePanel;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public static int playersCount=2;
	public static int boardSize=7;
	public static int cardSize=12;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
	}
	
	private static void createAndShowGUI(){
		mainFrame = new JFrame("The Labyrinth");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		
		menuPanel = new MenuPanel();
		mainFrame.add(menuPanel);
		
	}

}
