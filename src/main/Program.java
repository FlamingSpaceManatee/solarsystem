package main;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.RenderingHints;
import java.awt.Polygon;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

import component.MouseEventType;
import component.KeyEventType;
import ui.*;
import graphics.*;
import game.Main;

public class Program{
	
	public static boolean HEADLESS = false;
	public static double TICK_SCALE = 1d;
	public static boolean WINDOWED = false;

	private static long TICK_COUNT;
	private static long FRAME_COUNT;
	private static double SECOND_COUNT = 1.0;

	private Screen screen;

	private Main m;

	public static void main(String[] args){

		Program p = new Program(args);

	}

	public Program(String[] args){

		m = new Main();

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

		if (!HEADLESS){

			Window w = new Window(WINDOWED);
			screen = w.getScreen();

			screen.addKeyListener(InputListener.getInstance());
			screen.addMouseListener(InputListener.getInstance());
			screen.addMouseMotionListener(InputListener.getInstance());

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

			while(InputListener.hasEvents()){

				handleEvent(InputListener.nextEvent());

			}

			if (!HEADLESS && (time - lastRenderTime) >= (D_F_INTERVAL - 1750000)) {

				render();
				lastRenderTime = time;

			}
			
			update((time - lastUpdateTime) / 1000000000.0);

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

		Graphics2D g = screen.getBufferGraphics();																		//Get Graphics2D object of screen
		Dimension d = screen.getSize();																					//Get Dimension of screen

		g.setColor(new Color(0, 0, 0));																					//Set Graphics color to black
		g.fillRect(0, 0, d.width, d.height);																			//Clear screen with black

		Map<Object, Object> hints = new HashMap<Object, Object>();
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setRenderingHints(hints);

		g.setColor(new Color(1.0f, 1.0f, 1.0f));

		//c.draw(g);
		//Do other rendering here
		m.draw(g);

		screen.flipBuffer();																							//Flip the current screen with g
		g.dispose();																									//Dispose of g since it isn't needed anymore

		FRAME_COUNT++;																									//Increment total frame count by 1

	}

	private void update(double time){

		TICK_COUNT++;																									//Increment total tick count by 1
		m.update(time);

	}

	private void handleEvent(InputEvent x){

		m.handleEvent(x);
		
		/*
		if (x instanceof KeyEvent){

			KeyEvent e = (KeyEvent)x;
			KeyEventType t = (KeyEventType)InputListener.getType(x);

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE &&
				t == KeyEventType.KEY_RELEASED)
				System.exit(0);

			//HANDLE KEY EVENTS
			//c.handleKeyEvent(e, t);

		}

		if (x instanceof MouseEvent){

			MouseEvent e = (MouseEvent)x;
			MouseEventType t = (MouseEventType)InputListener.getType(x);
			//c.handleMouseEvent(e, t);

		}
		*/
	}
}