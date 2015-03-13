//Imports for Java Window
import java.awt.*;
import java.awt.event.*;
import java.awt.Image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class gameFrame extends JPanel{
		
		private JFrame frame;
		private JTextArea newText;
		
		public void createAndShowGUI(){
		frame = new JFrame("Sequence-Like Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar;
		JMenu gameMenu, aboutMenu;
		JMenuItem gameMenuItemPlay;
		JMenuItem aboutMenutItemAbout;
		
		menuBar = new JMenuBar();
		
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		gameMenu.getAccessibleContext().setAccessibleDescription(
				"Game Menu");
		menuBar.add(gameMenu);
		
		aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic(KeyEvent.VK_A);
		aboutMenu.getAccessibleContext().setAccessibleDescription(
				"About Menu");
		menuBar.add(aboutMenu);
		
		gameMenuItemPlay = new JMenuItem("Play", KeyEvent.VK_P);
		gameMenuItemPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		gameMenuItemPlay.getAccessibleContext().setAccessibleDescription("Play Sequence!");
		gameMenu.add(gameMenuItemPlay);
		
		aboutMenutItemAbout = new JMenuItem("About", KeyEvent.VK_A);
		aboutMenutItemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		aboutMenutItemAbout.getAccessibleContext().setAccessibleDescription("About this Game");
		aboutMenu.add(aboutMenutItemAbout);
		
		frame.setJMenuBar(menuBar);
		frame.setLayout(new FlowLayout());
		
		frame.pack();
		frame.setVisible(true);
	}
		public void displayText(String text, String color)
		{
			newText = new JTextArea(text, 5, 10);
			newText.setPreferredSize(new Dimension(400,400));
			JScrollPane scrollPane = new JScrollPane(newText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			newText.setLineWrap(true);
			newText.setWrapStyleWord(true);
			newText.setEditable(false);
						
			switch(color)
			{
			case "RED":		newText.setForeground(new Color(0xff0000));
							break;
			case "BLUE":	newText.setForeground(new Color(0x0000ff));
							break;
			case "GREEN":	newText.setForeground(new Color(0x00ff00));
							break;
			case "WHITE":	newText.setForeground(new Color(0xffffff));
							break;
			case "BLACK":   newText.setForeground(new Color(0x000000));
							break;
			default:		newText.setForeground(new Color(0x000000));
							break;
			}
			
			frame.add(newText);
			frame.add(scrollPane);
			frame.pack();
			frame.setVisible(true);			
		}
}
