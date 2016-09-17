package main;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Graphics2D;

public class Window extends JFrame{
	
	private Screen screen;

	public Window(boolean windowed){

		super("window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screen = new Screen();
		this.add(screen);

		if (windowed){

			this.setVisible(true);													//Make Window visible
			//this.setLocationRelativeTo(null);										//Centre Window
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);	//Maximizes window to screen
			this.setResizable(false);												//Lock Window size
			screen.setPreferredSize(this.getContentPane().getSize());				//Set Canvas size to size inside window

		} else {

			this.setUndecorated(true);												//Remove window decorations
			this.setVisible(true);													//Make window visible
			this.setResizable(false);												//Lock window size
			screen.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());	//Set canvas size to screen size
			this.pack();															//Wrap window around canvas
			this.setLocationRelativeTo(null);										//Centre window

		}

		screen.createBuffer();
		//screen.start();

	}

	public Screen getScreen(){

		return screen;

	}
}