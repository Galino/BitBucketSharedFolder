package ija.gui.window;

import ija.MainFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener{
	
	private JButton butNewGame, butLoadGame, butExit;
	
	/*
	 * Constructor
	 */
	public MenuPanel(){
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		butNewGame = new JButton("Start New Game");
		butNewGame.setActionCommand("start");
		butNewGame.setBounds(300, 300, 200, 25);
		
		butLoadGame = new JButton("Load Game");
		butLoadGame.setActionCommand("load");
		butLoadGame.setBounds(300, 330, 200, 25);
		
		butExit = new JButton("Exit Game");
		butExit.setActionCommand("exit");
		butExit.setBounds(300, 360, 200, 25);
		
		butNewGame.addActionListener(this);
		butLoadGame.addActionListener(this);
		butExit.addActionListener(this);
		
		add(butNewGame);
		add(butLoadGame);
		add(butExit);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.BOLD, 60));
		g.drawString("The Labyrinth", 200, 200);
	}
	
	public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
        	if(Game.preparePanel == null){
        		Game.preparePanel = new PreparePanel();
        	}
        	Game.menuPanel.revalidate();  
        	Game.mainFrame.setContentPane(Game.preparePanel);
        } else if ("load".equals(e.getActionCommand())) {
            System.out.println("Load Game");
        } else if ("exit".equals(e.getActionCommand())) {
        	System.exit(0);
        }
    }
}
