package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.event.KeyListener;
import javax.swing.event.MouseInputAdapter;

import java.awt.Point;
import java.util.ArrayList;

public class InputListener extends MouseInputAdapter implements KeyListener{
	
	private ArrayList<InputEvent> events;

	public InputListener(){

		this.events = new ArrayList<InputEvent>();

	}

	public InputEvent nextEvent(){

		if (events.size() == 0)
			return null;

		InputEvent e = events.get(0);
		events.remove(0);

		return e;

	}

	public boolean hasEvents(){

		return events.size() > 0;

	}

	@Override
	public void mouseClicked(MouseEvent e){

		events.add(e);

	}

	@Override
	public void mouseDragged(MouseEvent e){

		events.add(e);

	}

	@Override
	public void mouseMoved(MouseEvent e){

		events.add(e);

	}

	@Override
	public void mousePressed(MouseEvent e){
	
		events.add(e);
	}

	@Override
	public void mouseReleased(MouseEvent e){

		events.add(e);

	}

	@Override
	public void keyReleased(KeyEvent e){

		events.add(e);

	}

	@Override
	public void keyPressed(KeyEvent e){

		events.add(e);

	}

	@Override
	public void keyTyped(KeyEvent e){

		events.add(e);

	}
}