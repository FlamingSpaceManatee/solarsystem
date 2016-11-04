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

	private UIElement mass, name, velocity, angle;

	public InfoBox(SolarSystem s){

		super(0, 0, 500, 200);

		this.s = s;
		this.planets = s.getPlanets();
		this.setVisible(false);

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

		//SWITCH PLANETS
		UIElement lastButton = new Button(115, 115, 25, 25, "arrow_left.png");
		UIElement nextButton = new Button(145, 115, 25, 25, "arrow_right.png");

		lastButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() - 1); update(); done = false; } );
		nextButton.setReleasedEvent(xxx -> {s.updateFocus(s.getFocus() + 1); update(); done = false; } );

		UIElement updateButton = new Button(0, 115, 100, 25, "update.png");
		updateButton.setReleasedEvent(xxx -> {

			try{planets.get(focus).m = Double.parseDouble(((TextBox)mass).getText());} catch (Exception e) {((TextBox)mass).setText("" + planets.get(focus).m);};
			try{planets.get(focus).setVelocity(Double.parseDouble(((TextBox)velocity).getText()), Double.parseDouble(((TextBox)angle).getText()));} catch (Exception e) {};
			planets.get(focus).name = ((TextBox)name).getText();
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

	}

	public void update(){

		focus = s.getFocus();

		((TextBox)mass).setText("" + planets.get(focus).m);
		((TextBox)name).setText(planets.get(focus).name);
		((TextBox)velocity).setText("" + planets.get(focus).getVelocity());
		((TextBox)angle).setText("" + planets.get(focus).getAngle());

	}

	@Override
	public void setVisible(boolean v){

		super.setVisible(v);
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