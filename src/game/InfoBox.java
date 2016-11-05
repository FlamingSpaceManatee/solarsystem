package game;

import ui.*;
import component.*;
import graphics.*;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class InfoBox extends Container {
	
	private SolarSystem s;
	private ArrayList<Planet> planets;
	private int focus;
	protected boolean done = false;

	private UIElement mass, name, velocity, angle, radius, red, green, blue, tScale, dScale;

	public InfoBox(SolarSystem s){

		super(0, 0, 500, 200);

		this.s = s;
		this.planets = s.getPlanets();

		Font f = new Font("ascii.png", 16, 15);

		//MASS INFO
		UIElement massLabel = new Label(0, 17, "Mass: ");
		mass = new TextBox(80, 17, 200, 15, f);
		((TextBox)mass).setText("" + planets.get(focus).m);

		//NAME INFO
		name = new TextBox(0, 0, 400, 15, f);
		((TextBox)name).setText(planets.get(focus).name);

		//VELOCITY INFO
		UIElement velocityLabel = new Label(0, 34, "V: ");
		velocity = new TextBox(48, 34, 500, 15, f);
		((TextBox)velocity).setText("" + planets.get(focus).getVelocity());
		UIElement angleLabel = new Label(0, 52, "@: ");
		angle = new TextBox(48, 52, 500, 15, f);
		((TextBox)angle).setText("" + planets.get(focus).getAngle());

		//RADIUS INFO
		UIElement radiusInfo = new Label(0, 70, "R: ");
		radius = new TextBox(48, 70, 500, 15, f);
		((TextBox)radius).setText("" + planets.get(focus).r);

		//COLOUR
		UIElement rLabel = new Label(0, 88, "Red: ");
		red = new TextBox(74, 88, 82, 15, f);
		((TextBox)red).setText("" + planets.get(focus).colour[0]);

		UIElement gLabel = new Label(162, 88, "Green: ");
		green = new TextBox(260, 88, 82, 15, f);
		((TextBox)green).setText("" + planets.get(focus).colour[1]);

		UIElement bLabel = new Label(340, 88, "Blue: ");
		blue = new TextBox(420, 88, 82, 15, f);
		((TextBox)blue).setText("" + planets.get(focus).colour[2]);

		//SCALES
		UIElement tLabel = new Label(0, 106, "Time Scale: ");
		tScale = new TextBox(176, 106, 500, 15, f);
		((TextBox)tScale).setText("" + s.timeScale);

		UIElement dLabel = new Label(0, 124, "Distance Scale: ");
		dScale = new TextBox(240, 124, 500, 15, f);
		((TextBox)dScale).setText("" + Planet.SCALE);

		//TOGGLE TAIL
		UIElement tailButton = new Button(185, 142, 50, 25, "tail.png");

		tailButton.setReleasedEvent(xxx -> {planets.get(focus).tail = !planets.get(focus).tail;});

		//SWITCH PLANETS
		UIElement lastButton = new Button(115, 142, 25, 25, "arrow_left.png");
		UIElement nextButton = new Button(145, 142, 25, 25, "arrow_right.png");

		lastButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() - 1); update(); done = false; } );
		nextButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() + 1); update(); done = false; } );

		UIElement updateButton = new Button(0, 142, 100, 25, "update.png");
		updateButton.setReleasedEvent(xxx -> {

			try{planets.get(focus).m = Double.parseDouble(((TextBox)mass).getText());} catch (Exception e) {((TextBox)mass).setText("" + planets.get(focus).m);};
			try{planets.get(focus).setVelocity(Double.parseDouble(((TextBox)velocity).getText()), Double.parseDouble(((TextBox)angle).getText()));} catch (Exception e) {};
			try{planets.get(focus).setColour(Float.parseFloat(((TextBox)red).getText()), Float.parseFloat(((TextBox)green).getText()), Float.parseFloat(((TextBox)blue).getText()));} catch (Exception e) {};
			try{planets.get(focus).setRadius(Double.parseDouble(((TextBox)radius).getText()));} catch (Exception e) {};
			try{Planet.setScale(Double.parseDouble(((TextBox)dScale).getText()));} catch (Exception e) {};
			try{s.timeScale = Double.parseDouble(((TextBox)tScale).getText());} catch (Exception e) {};

			planets.get(focus).name = ((TextBox)name).getText();
			update();
			done = true;

			});

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
		((TextBox)red).setText("" + planets.get(focus).colour[0]);
		((TextBox)green).setText("" + planets.get(focus).colour[1]);
		((TextBox)blue).setText("" + planets.get(focus).colour[2]);
		((TextBox)radius).setText("" + planets.get(focus).r);

	}

	@Override
	public void setVisible(boolean v){

		super.setVisible(v);
		update();
		if (v != visible()){

			done = !v;
			update();

		}

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
}