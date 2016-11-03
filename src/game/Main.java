package game;

import java.awt.Graphics2D;
import java.awt.event.InputEvent;

public class Main {
	
	private SolarSystem s;

	public Main(){

		s = new SolarSystem();

	}

	public void update(double t){

		s.update(t);

	}

	public void draw(Graphics2D g){

		s.draw(g);

	}

	public void handleEvent(InputEvent x){

		s.handleEvent(x);

	}
}