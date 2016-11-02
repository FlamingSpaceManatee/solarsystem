package game;

import component.*;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SolarSystem implements DrawComponent, ClickComponent {
	
	private ArrayList<Planet> bodies;
	private double timeScale;
	private Point focus;
	private int focusI;

	public SolarSystem(){

		//Good Solar System scale : 5e11 ( 2 * Distance from Sun to Mars)
		Planet.setScale(5e9);
		timeScale = 1;

		bodies = new ArrayList<Planet>();
		bodies.add(new Planet(0, 0, 1.989e30, 0, 0.0)); //Sun
		bodies.add(new Planet(0, 57.91e9, 0.330e24, 47400, 0.0)); //Mercury
		bodies.add(new Planet(-108.2e9, 0, 4.867e24, 35220, 0.0)); //Venus
		bodies.add(new Planet(149.6e9, 0, 5.972e24, 29800, 0.0)); //Earth
		//addPlanet(149.6e9 + 0.4055e9, 0, 0.07346e24, 30864, 90.0); //Moon
		bodies.add(new Planet(0, -227.99e9, 6.42e23, 24060, 0.0)); //Mars
		bodies.add(new Planet(778.6e9, 0, 1898e24, 13100, 0.0)); //Jupiter
		bodies.add(new Planet(-1433.5e9, 0, 568e24, 9700, 0.0)); //Saturn
		bodies.add(new Planet(0, 2872.5e9, 86.8e24, 6800, 0.0)); //Uranus
		bodies.add(new Planet(0, -4495.1e9, 102e24, 5400, 0.0)); //Neptune

		updateFocus(0);
		Planet.setFocus(focus);


	}

	public void update(double t){

		for (Planet p : bodies)
			p.update(t * timeScale, bodies);

	}

	@Override
	public void draw(Graphics2D g){

		for (Planet p : bodies)
			p.draw(g);

	}

	public void updateFocus(int focusI){

		this.focusI = focusI;
		this.focus = new Point((int)bodies.get(focusI).x, (int)bodies.get(focusI).y);

	}

	@Override
	public void handleMousePress(MouseEvent e){}

	@Override
	public void handleMouseRelease(MouseEvent e){}

	@Override
	public void handleMouseDrag(MouseEvent e){}

	@Override
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){ return true; }

	@Override
	public void setReleasedEvent(Consumer<Object> c){}

	@Override
	public void setPressedEvent(Consumer<Object> c){}
}