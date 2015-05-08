package ija.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

public class PreparePanel extends JPanel implements ActionListener{
	
	public PreparePanel(){
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		JButton butStart = new JButton("Start");
		butStart.setActionCommand("start");
		butStart.setBounds(600, 500, 150, 50);
		
		JButton butBack = new JButton("Back");
		butBack.setActionCommand("back");
		butBack.setBounds(50, 500, 150, 50);
		
		butStart.addActionListener(this);
		butBack.addActionListener(this);
		
		add(butStart);
		add(butBack);
		
		JRadioButton p2, p3, p4;
		
		p2 = new JRadioButton("2");
		p2.setBackground(UIManager.getColor("Button.disabledShadow"));
		p2.setActionCommand("p2");
		p2.setBounds(450, 180, 40, 30);
		
		p3 = new JRadioButton("3");
		p3.setBackground(UIManager.getColor("Button.disabledShadow"));
		p3.setActionCommand("p3");
		p3.setBounds(500, 180, 40, 30);
		
		p4 = new JRadioButton("4");
		p4.setBackground(UIManager.getColor("Button.disabledShadow"));
		p4.setActionCommand("p4");
		p4.setBounds(550, 180, 40, 30);
		
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(p2);
		group1.add(p3);
		group1.add(p4);

		add(p2);
		add(p3);
		add(p4);
		
		p2.addActionListener(this);
		p3.addActionListener(this);
		p4.addActionListener(this);
		
		JRadioButton s5, s7, s9, s11;
		
		s5 = new JRadioButton("5");
		s5.setBackground(UIManager.getColor("Button.disabledShadow"));
		s5.setActionCommand("s5");
		s5.setBounds(450, 280, 40, 30);
		
		s7 = new JRadioButton("7");
		s7.setBackground(UIManager.getColor("Button.disabledShadow"));
		s7.setActionCommand("s7");
		s7.setBounds(500, 280, 40, 30);
		
		s9 = new JRadioButton("9");
		s9.setBackground(UIManager.getColor("Button.disabledShadow"));
		s9.setActionCommand("s9");
		s9.setBounds(550, 280, 40, 30);
		
		s11 = new JRadioButton("11");
		s11.setBackground(UIManager.getColor("Button.disabledShadow"));
		s11.setActionCommand("s11");
		s11.setBounds(600, 280, 40, 30);
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(s5);
		group2.add(s7);
		group2.add(s9);
		group2.add(s11);
		
		add(s5);
		add(s7);
		add(s9);
		add(s11);
		
		s5.addActionListener(this);
		s7.addActionListener(this);
		s9.addActionListener(this);
		s11.addActionListener(this);
		
		JRadioButton c12, c24;
		
		c12 = new JRadioButton("12");
		c12.setBackground(UIManager.getColor("Button.disabledShadow"));
		c12.setActionCommand("c12");
		c12.setBounds(450, 380, 40, 30);
		
		c24 = new JRadioButton("24");
		c24.setBackground(UIManager.getColor("Button.disabledShadow"));
		c24.setActionCommand("c24");
		c24.setBounds(500, 380, 40, 30);
		
		ButtonGroup group3 = new ButtonGroup();
		group3.add(c12);
		group3.add(c24);
		
		add(c12);
		add(c24);
		
		c12.addActionListener(this);
		c24.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.BOLD, 32));
		g.drawString("Number of players:", 100, 200);
		g.drawString("Size of board:", 100, 300);
		g.drawString("Number of treasures:", 100, 400);
	}
	
	public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
        	Game.preparePanel.revalidate(); 
        	Game.gamePanel = new BoardPanel();
        	Game.mainFrame.setContentPane(Game.gamePanel);
        	
        } else if ("back".equals(e.getActionCommand())) {
        	Game.preparePanel.revalidate();
        	Game.mainFrame.setContentPane(Game.menuPanel);
        	
        } else if ("p2".equals(e.getActionCommand())) {
        	Game.playersCount = 2;
        	
        }else if ("p3".equals(e.getActionCommand())) {
        	Game.playersCount = 3;
        	
        }else if ("p4".equals(e.getActionCommand())) {
        	Game.playersCount = 4;
        	
        }else if ("s5".equals(e.getActionCommand())) {
        	Game.boardSize = 5;
        }else if ("s7".equals(e.getActionCommand())) {
        	Game.boardSize = 7;
        }else if ("s9".equals(e.getActionCommand())) {
        	Game.boardSize = 9;
        }else if ("s11".equals(e.getActionCommand())) {
        	Game.boardSize = 11;
        }else if ("c12".equals(e.getActionCommand())) {
        	Game.cardSize = 12;
        }else if ("c24".equals(e.getActionCommand())) {
        	Game.cardSize = 24;
        }
	
    }

}
