package game;

import component.*;
import ui.*;
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
	private InfoBox infoBox;
	protected double timeScale;
	private boolean paused;
	protected Point focus;
	protected int focusI;
	protected int lastFocus;
	private ArrayList<Planet> queued = new ArrayList<Planet>();

	public SolarSystem(){

		Planet.setSolarSystem(this);
		bodies = new ArrayList<Planet>();


		//Our Solar System
	
		//Good Solar System scale : 1e9
		Planet.setScale(5e8);
		timeScale = 864000;
		
		bodies = new ArrayList<Planet>();
		bodies.add(new Planet(0, 0, 1.989e30, 0, 0.0)); //Sun
		getPlanet(0).setColour(1f, 1f, 0f);
		getPlanet(0).name = "Sun";

		bodies.add(new Planet(0, 57.91e9, 0.330e24, 47400, 0.0)); //Mercury
		getPlanet(1).setColour(1f, 0f, 0f);
		getPlanet(1).setRadius(2439700);
		getPlanet(1).name = "Mercury";
		bodies.add(new Planet(-108.2e9, 0, 4.867e24, 35220, 270.0)); //Venus
		getPlanet(2).setColour(0f, 1f, 0.5f);
		getPlanet(2).setRadius(6051800);
		getPlanet(2).name = "Venus";
		bodies.add(new Planet(149e9, 0, 5.972e24, 15000, 90.0)); //Earth
		getPlanet(3).setColour(0f, 0f, 1f);
		getPlanet(3).setRadius(6371000);
		getPlanet(3).name = "Earth";
		bodies.add(new Planet(0, -227.99e9, 6.42e23, 24060, 180.0)); //Mars
		getPlanet(4).setColour(1f, 0.25f, 0.1f);
		getPlanet(4).setRadius(3396200);
		getPlanet(4).name = "Mars";
		bodies.add(new Planet(778.6e9, 0, 1898e24, 13100, 90.0)); //Jupiter
		getPlanet(5).setColour(1f, 0.25f, 0f);
		getPlanet(5).setRadius(71492000);
		getPlanet(5).name = "Jupiter";
		bodies.add(new Planet(-1433.5e9, 0, 568e24, 9700, 270.0)); //Saturn
		getPlanet(6).setColour(0.75f, 0.25f, 0f);
		getPlanet(6).setRadius(60268000);
		getPlanet(6).name = "Saturn";
		bodies.add(new Planet(0, 2872.5e9, 86.8e24, 6800, 0.0)); //Uranus
		getPlanet(7).setColour(0f, 0.25f, 1f);
		getPlanet(7).setRadius(25559000);
		getPlanet(7).name = "Uranus";
		bodies.add(new Planet(0, -4495.1e9, 102e24, 5400, 180.0)); //Neptune
		getPlanet(8).setColour(0f, 0f, 1f);
		getPlanet(8).setRadius(24764000);
		getPlanet(8).name = "Neptune";
		 
		/*
		Planet.setScale(1e9);
		timeScale = 864000;
		
		bodies.add(new Planet(0, 0, 1.989e30, 29800, 270.0)); //Sun
		getPlanet(0).setColour(1f, 1f, 0f);
		bodies.add(new Planet(149.6e9, 0, 2e30, 29800, 90.0)); //Sun 2
		getPlanet(1).setColour(1f, 0.75f, 0f);
		*/

		updateFocus(0);

		infoBox = new InfoBox(this);

	}

	public void update(double t){

		double scaledTime = (paused) ? 0 :  t * timeScale;

		for (Planet p : bodies)
			p.update(scaledTime, bodies);

		for (Planet p : bodies)
			p.updatePos(t);
		
		for (Planet p : queued)
			bodies.add(p);

		for (int i = 0; i < bodies.size(); i++)
			if (bodies.get(i).boom)
				bodies.remove(i);

		if (queued.size() > 0)
			queued = new ArrayList<Planet>();

	}

	@Override
	public void draw(Graphics2D g){
		
		for (Planet p : bodies)
			p.drawLine(g);
		
		for (Planet p : bodies)
			p.draw(g);

		infoBox.draw(g);

	}

	public void updateFocus(int f){

		if (f >= bodies.size())
			f = 0;
		if (f < 0)
			f = bodies.size() - 1;

		this.focusI = f;

	}

	public int getFocus(){

		return focusI;

	}

	protected Planet getPlanet(int i){

		return bodies.get(i);

	}

	public ArrayList<Planet> getPlanets(){

		return bodies;

	}

	public void addPlanet(double x, double y, double m, double v, double angle){

		bodies.add(new Planet(x, y, m, v, angle));

	}

	public void queuePlanet(Planet p){

		queued.add(p);

	}

	public void removePlanet(Planet p){

		bodies.remove(p);

	}

	public void handleEvent(InputEvent x){

		if (x instanceof KeyEvent){

			KeyEvent e = (KeyEvent)x;
			KeyEventType t = (KeyEventType)InputListener.getType(x);
			handleKeyEvent(e, t);
			infoBox.handleKeyEvent(e, t);

		}

		if (x instanceof MouseEvent){

			MouseEvent e = (MouseEvent)x;
			MouseEventType t = (MouseEventType)InputListener.getType(x);
			
			for (Planet p : bodies)
				p.handleMouseEvent(e, t);
				infoBox.handleMouseEvent(e, t);

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

		if (k.getKeyCode() == KeyEvent.VK_SPACE){

			if (!paused){

				paused = true;
				infoBox.setVisible(true);

			} else if (!infoBox.elementFocused()){

				paused = false;
				infoBox.updatePlanets.accept(this);
				infoBox.setVisible(false);

			}

		}

	}
}