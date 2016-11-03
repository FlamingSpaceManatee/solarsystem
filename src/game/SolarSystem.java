package game;

import component.*;
import main.InputListener;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SolarSystem implements DrawComponent, KeyComponent {
	
	private ArrayList<Planet> bodies;
	private double timeScale;
	private boolean paused;
	private Point focus;
	private int focusI;

	public SolarSystem(){

		bodies = new ArrayList<Planet>();
		
		//Our Solar System
		/*
		//Good Solar System scale : 1e9
		Planet.setScale(2e9);
		timeScale = 864000;
		
		bodies = new ArrayList<Planet>();
		bodies.add(new Planet(0, 0, 1.989e30, 0, 0.0)); //Sun
		getPlanet(0).setColour(1f, 1f, 0f);
		bodies.add(new Planet(149.6e9, 0, 5.972e24, 29800, 90.0)); //Earth
		getPlanet(1).setColour(0f, 0f, 1f);

		bodies.add(new Planet(0, 57.91e9, 0.330e24, 47400, 0.0)); //Mercury
		getPlanet(2).setColour(1f, 0f, 0f);
		bodies.add(new Planet(-108.2e9, 0, 4.867e24, 35220, 270.0)); //Venus
		getPlanet(3).setColour(0f, 1f, 0.5f);
		bodies.add(new Planet(0, -227.99e9, 6.42e23, 24060, 180.0)); //Mars
		getPlanet(4).setColour(1f, 0.25f, 0.1f);
		bodies.add(new Planet(778.6e9, 0, 1898e24, 13100, 90.0)); //Jupiter
		getPlanet(5).setColour(1f, 0.25f, 0f);
		bodies.add(new Planet(-1433.5e9, 0, 568e24, 9700, 270.0)); //Saturn
		getPlanet(6).setColour(0.75f, 0.25f, 0f);
		bodies.add(new Planet(0, 2872.5e9, 86.8e24, 6800, 0.0)); //Uranus
		getPlanet(7).setColour(0f, 0.25f, 1f);
		bodies.add(new Planet(0, -4495.1e9, 102e24, 5400, 180.0)); //Neptune
		getPlanet(8).setColour(0f, 0f, 1f);
		 */
		
		Planet.setScale(1e9);
		timeScale = 864000;
		
		bodies.add(new Planet(0, 0, 1.989e30, 29800, 270.0)); //Sun
		getPlanet(0).setColour(1f, 1f, 0f);
		bodies.add(new Planet(149.6e9, 0, 2e30, 29800, 90.0)); //Sun 2
		getPlanet(1).setColour(1f, 0.75f, 0f);
		
		updateFocus(0);
		Planet.setFocus(focus);


	}

	public void update(double t){

		double scaledTime = (paused) ? 0 :  t * timeScale;

		for (Planet p : bodies)
			p.update(scaledTime, bodies);

		for (Planet p : bodies)
			p.updatePos(t);
		
	}

	@Override
	public void draw(Graphics2D g){
		
		for (Planet p : bodies)
			p.drawLine(g);
		
		for (Planet p : bodies)
			p.draw(g);

	}

	public void updateFocus(int focusI){

		this.focusI = focusI;
		this.focus = new Point((int)bodies.get(focusI).x, (int)bodies.get(focusI).y);

	}

	private Planet getPlanet(int i){

		return bodies.get(i);

	}

	public void handleEvent(InputEvent x){

		if (x instanceof KeyEvent){

			KeyEvent e = (KeyEvent)x;
			KeyEventType t = (KeyEventType)InputListener.getType(x);
			handleKeyEvent(e, t);

		}

		if (x instanceof MouseEvent){

			MouseEvent e = (MouseEvent)x;
			MouseEventType t = (MouseEventType)InputListener.getType(x);
			
			for (Planet p : bodies)
				p.handleMouseEvent(e, t);

		}

	}

	public boolean handleKeyEvent(KeyEvent k, KeyEventType t){

		switch (t) {

			case KEY_PRESSED:
				handleKeyPress(k);
				break;
			case KEY_RELEASED:
				handleKeyRelease(k);
				break;

		}

		return true;

	}
	public void handleKeyPress(KeyEvent k){



	}

	public void handleKeyRelease(KeyEvent k){

		if (k.getKeyCode() == KeyEvent.VK_SPACE)
			paused = !paused;

	}
}