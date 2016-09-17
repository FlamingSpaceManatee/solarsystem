package main;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

public class Program{
	
	public static boolean HEADLESS = false;
	public static double TIME_SCALE = 1d;
	public static boolean WINDOWED = false;

	private static long TICK_COUNT;
	private static long FRAME_COUNT = 60;
	private static double SECOND_COUNT = 1.0;


	private Screen screen;

	public static void main(String[] args){

		Program p = new Program(args);

	}

	public Program(String[] args){

		for (String s : args){

			if (s.equals("-h")){

				HEADLESS = true;

			}

			if (s.matches("-t\\d*.\\d*")){

				TIME_SCALE = Double.parseDouble(s.substring(2, s.length()));

			}

			if (s.matches("-w")){

				WINDOWED = true;

			}
		}

		if (!HEADLESS){

			Window w = new Window(WINDOWED);
			screen = w.getScreen();

			if (screen == null){

				HEADLESS = true;
				System.out.println("Could not retrieve GraphicsPointer from screen");

			}
		}

		Thread loop = new Thread(new Runnable(){

			public void run(){

				mainLoop();

			}

		});

		loop.start();
	}

	private void mainLoop(){

		final double D_FPS = 60.0;
		final double D_INTERVAL = 1000000000.0 / D_FPS;
		int frames = 0;

		long time = System.nanoTime();
		long lastTime = time;

		boolean running = true;

		while (running){

			//ProcessInputs

			if (!HEADLESS){

				render();

			}
			
			update();
			frames++;

			lastTime = time;
			time = System.nanoTime();

			if (SECOND_COUNT >= 2.0){

				FRAME_COUNT = 60;
				SECOND_COUNT = 1.0;

			}

			/*
				While the time it took to update is less than 1/60 of a second,
				try to sleep for the difference until the time between updates
				is greater or equal to 1/60 of a second.
			*/
			while (time - lastTime <= (D_INTERVAL - 1750000)){

				try {

					Thread.sleep((long)((D_INTERVAL - (time - lastTime)) / 1000000.0));

				} catch (InterruptedException e){}

				time = System.nanoTime();

			}

			SECOND_COUNT += (time - lastTime) / 1000000000.0;
		}
	}

	private void render(){

		Graphics2D g = screen.getGraphics();	//Get Graphics2D object of screen
		Dimension d = screen.getSize();			//Get Dimension of screen

		g.setColor(new Color(0, 0, 0));			//Set Graphics color to black
		g.fillRect(0, 0, d.width, d.height);	//Clear screen with black
		g.setColor(new Color(1.0f, 1.0f, 0.0f));


		//JUST FOR TESTING~~~~~~~~~~~~~
		try {
		
			String s = "Elapsed Time: " + SECOND_COUNT;
			s = s.substring(0, 17);
			s += ", FPS: " + (FRAME_COUNT / SECOND_COUNT);
			s = s.substring(0, 30);
			g.drawString(s, 100, 100);

		} catch (Exception e){

			String s = "Elapsed Time: " + SECOND_COUNT + ", FPS: " + (FRAME_COUNT / SECOND_COUNT);
			g.drawString("ERR: " + s.length(), 100, 100);

		}		//JUST FOR TESTING~~~~~~~~~~~~~


		//DO other rendering here

		screen.flipBuffer();					//Flip the current screen with g
		g.dispose();							//Dispose of g since it isn't needed anymore

		FRAME_COUNT++;							//Increment total frame count by 1

	}

	private void update(){

		TICK_COUNT++;							//Increment total tick count by 1

	}
}