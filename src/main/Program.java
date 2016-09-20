package main;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Program{
	
	public static boolean HEADLESS = false;
	public static double TICK_SCALE = 1d;
	public static boolean WINDOWED = false;

	private static long TICK_COUNT;
	private static long FRAME_COUNT;
	private static double SECOND_COUNT = 1.0;

	private InputListener input;
	private Screen screen;

	InterfaceElement xxx = new InterfaceElement(300, 300, 256, 64){

		@Override
		protected void mouseClickAction(){

			System.out.println("fiuck");

		}

	};

	public static void main(String[] args){

		Program p = new Program(args);

	}

	public Program(String[] args){

		for (String s : args){

			if (s.equals("-h")){

				HEADLESS = true;

			}

			if (s.matches("-t\\d*.\\d*")){

				TICK_SCALE = Double.parseDouble(s.substring(2, s.length()));

			}

			if (s.matches("-w")){

				WINDOWED = true;

			}
		}

		input = new InputListener();

		if (!HEADLESS){

			Window w = new Window(WINDOWED);
			screen = w.getScreen();

			screen.addKeyListener(input);
			screen.addMouseListener(input);
			screen.addMouseMotionListener(input);

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
		final double D_UPS = 60.0 * TICK_SCALE;
		final double D_F_INTERVAL = 1000000000.0 / D_FPS;
		final double D_T_INTERVAL = 1000000000.0 / D_UPS;

		TICK_COUNT = (long) D_UPS;
		FRAME_COUNT = (long) D_FPS;

		long time = System.nanoTime();
		long lastUpdateTime = time;
		long lastRenderTime = time;

		boolean running = true;

		while (running){

			while(input.hasEvents()){

				handleEvent(input.nextEvent());

			}

			if (!HEADLESS && (time - lastRenderTime) >= (D_F_INTERVAL - 1750000)) {

				render();
				lastRenderTime = time;

			}
			
			update();

			time = System.nanoTime();
			lastUpdateTime = time;

			/*
				While the time it took to update is less than desired interval,
				try to sleep for the difference until the time between updates
				is greater or equal to 1/60 of a second.
			*/
			while (time - lastUpdateTime < (D_T_INTERVAL - 1250000)){

				try {

					Thread.sleep((long)((D_T_INTERVAL - (time - lastUpdateTime)) / 1000000.0));

				} catch (InterruptedException e){}

				time = System.nanoTime();

			}

			SECOND_COUNT += (time - Math.max(lastRenderTime, lastUpdateTime)) / 1000000000.0;
		}
	}

	private void render(){

		Graphics2D g = screen.getBufferGraphics();	//Get Graphics2D object of screen
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
			s += ", UPS: " + (TICK_COUNT / SECOND_COUNT);
			g.drawString(s, 100, 100);

		} catch (Exception e){

			String s = "Elapsed Time: " + SECOND_COUNT + ", FPS: " + (FRAME_COUNT / SECOND_COUNT);
			g.drawString("ERR: " + s.length(), 100, 100);

		}
		//JUST FOR TESTING~~~~~~~~~~~~~

		xxx.draw(g);

		//Do other rendering here

		screen.flipBuffer();					//Flip the current screen with g
		g.dispose();							//Dispose of g since it isn't needed anymore

		FRAME_COUNT++;							//Increment total frame count by 1

	}

	private void update(){

		TICK_COUNT++;							//Increment total tick count by 1

	}

	private void handleEvent(InputEvent e){

		if (e instanceof KeyEvent){

			if (((KeyEvent)e).getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0);

		}

		if (e instanceof MouseEvent)
			xxx.handleMouseEvent((MouseEvent)e);
		
	}
}