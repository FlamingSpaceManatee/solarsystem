package game;

import ui.*;
import component.*;
import graphics.*;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import java.util.function.Consumer;

public class InfoBox extends Container {
	
	private SolarSystem s;
	private ArrayList<Planet> planets;
	private int focus;
	protected Consumer<Object> updatePlanets;

	private UIElement mass, name, velocity, angle, radius, red, green, blue, tScale, dScale;

	public InfoBox(SolarSystem s){

		super(0, 0, 500, 200);

		this.s = s;
		this.planets = s.getPlanets();

		Font f = new Font("ascii.png", 16, 15);
		Font y = new Font("ascii-yellow.png", 16, 15);

		//MASS INFO
		UIElement massLabel = new Label(0, 17, "Mass: ");
		mass = new TextBox(80, 17, 200, 15, y);
		((TextBox)mass).setText("" + planets.get(focus).m);

		//NAME INFO
		name = new TextBox(0, 0, 400, 15, y);
		((TextBox)name).setText(planets.get(focus).name);

		//VELOCITY INFO
		UIElement velocityLabel = new Label(0, 34, "V: ");
		velocity = new TextBox(32, 34, 500, 15, y);
		((TextBox)velocity).setText("" + planets.get(focus).getVelocity());
		UIElement angleLabel = new Label(0, 52, "@: ");
		angle = new TextBox(32, 52, 500, 15, y);
		((TextBox)angle).setText("" + planets.get(focus).getAngle());

		//RADIUS INFO
		UIElement radiusInfo = new Label(0, 70, "R: ");
		radius = new TextBox(32, 70, 500, 15, y);
		((TextBox)radius).setText("" + planets.get(focus).r);

		//COLOUR
		UIElement rLabel = new Label(0, 88, "Red: ");
		red = new TextBox(74, 88, 82, 15, y);
		((TextBox)red).setText("" + planets.get(focus).colour[0]);

		UIElement gLabel = new Label(162, 88, "Green: ");
		green = new TextBox(260, 88, 82, 15, y);
		((TextBox)green).setText("" + planets.get(focus).colour[1]);

		UIElement bLabel = new Label(340, 88, "Blue: ");
		blue = new TextBox(420, 88, 82, 15, y);
		((TextBox)blue).setText("" + planets.get(focus).colour[2]);

		//SCALES
		UIElement tLabel = new Label(0, 106, "Time Scale: ");
		tScale = new TextBox(192, 106, 500, 15, y);
		((TextBox)tScale).setText("" + s.timeScale);

		UIElement dLabel = new Label(0, 124, "Distance Scale: ");
		dScale = new TextBox(256, 124, 500, 15, y);
		((TextBox)dScale).setText("" + Planet.SCALE);

		//TOGGLE TAIL
		UIElement tailButton = new Button(185 + 25, 142, 50, 25, "tail.png");

		tailButton.setReleasedEvent(xxx -> {planets.get(focus).tail = !planets.get(focus).tail;});

		//SWITCH PLANETS
		UIElement lastButton = new Button(115 + 25, 142, 25, 25, "arrow_left.png");
		UIElement nextButton = new Button(145 + 25, 142, 25, 25, "arrow_right.png");

		lastButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() - 1); update(); } );
		nextButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() + 1); update(); } );

		updatePlanets = xxx -> {

			try{planets.get(focus).m = Double.parseDouble(((TextBox)mass).getText());} catch (Exception e) {((TextBox)mass).setText("" + planets.get(focus).m);};
			try{planets.get(focus).setVelocity(Double.parseDouble(((TextBox)velocity).getText()), Double.parseDouble(((TextBox)angle).getText()));} catch (Exception e) {};
			try{planets.get(focus).setColour(Float.parseFloat(((TextBox)red).getText()), Float.parseFloat(((TextBox)green).getText()), Float.parseFloat(((TextBox)blue).getText()));} catch (Exception e) {};
			try{planets.get(focus).setRadius(Double.parseDouble(((TextBox)radius).getText()));} catch (Exception e) {};
			try{Planet.setScale(Double.parseDouble(((TextBox)dScale).getText()));} catch (Exception e) {};
			try{s.timeScale = Double.parseDouble(((TextBox)tScale).getText());} catch (Exception e) {};

			planets.get(focus).name = ((TextBox)name).getText();
			update();

		};

		UIElement updateButton = new Button(25, 142, 100, 25, "update.png");
		updateButton.setReleasedEvent(updatePlanets);

		addElement(massLabel);
		addElement(mass);
		addElement(updateButton);
		addElement(nextButton);
		addElement(lastButton);
		addElement(name);
		addElement(angleLabel);
		addElement(angle);
		addElement(velocityLabel);
		addElement(velocity);
		addElement(rLabel);
		addElement(red);
		addElement(gLabel);
		addElement(green);
		addElement(bLabel);
		addElement(blue);
		addElement(radiusInfo);
		addElement(radius);
		addElement(tailButton);
		addElement(tScale);
		addElement(dScale);
		addElement(tLabel);
		addElement(dLabel);

		this.setVisible(false);

	}

	public void update(){

		focus = s.getFocus();

		((TextBox)mass).setText("" + planets.get(focus).m);
		((TextBox)name).setText(planets.get(focus).name);
		((TextBox)velocity).setText("" + planets.get(focus).getVelocity());
		((TextBox)angle).setText("" + planets.get(focus).getAngle());

		String r, g, b;

		r = "" + planets.get(focus).colour[0];
		r = r.substring(0, Math.min(r.length(), 5));

		g = "" + planets.get(focus).colour[1];
		g = g.substring(0, Math.min(g.length(), 5));

		b = "" + planets.get(focus).colour[2];
		b = b.substring(0, Math.min(b.length(), 5));

		((TextBox)red).setText(r);
		((TextBox)green).setText(g);
		((TextBox)blue).setText(b);
		((TextBox)radius).setText("" + planets.get(focus).r);

	}

	@Override
	public void setVisible(boolean v){

		super.setVisible(v);
		update();

	}

	@Override
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){

		if (!visible())
			return false;

		return super.handleMouseEvent(e, t);

	}

	@Override
	public boolean handleKeyEvent(KeyEvent e, KeyEventType t){

		if (!visible())
			return false;

		return super.handleKeyEvent(e, t);

	}

	@Override
	public void draw(Graphics2D g){

		if (!visible())
			return;

		super.draw(g);

	}

	public boolean elementFocused(){

		return (((TextBox)mass).focused() || 
			((TextBox)name).focused() || 
			((TextBox)velocity).focused() || 
			((TextBox)angle).focused() || 
			((TextBox)radius).focused() || 
			((TextBox)red).focused() || 
			((TextBox)green).focused() || 
			((TextBox)blue).focused() || 
			((TextBox)tScale).focused() || 
			((TextBox)dScale).focused());
	
	}
}