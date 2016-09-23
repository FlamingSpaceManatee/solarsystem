package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.event.KeyListener;
import javax.swing.event.MouseInputAdapter;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Iterator;

public class InputListener extends MouseInputAdapter implements KeyListener{
	
	private static Hashtable<InputEvent, Object> events;
	private static InputListener instance = new InputListener();

	public static InputListener getInstance(){

		return instance;

	}

	private InputListener(){

		this.events = new Hashtable<InputEvent, Object>();

	}

	public static InputEvent nextEvent(){

		if (!hasEvents())
			return null;

		return events.keySet().iterator().next();
	}

	public static Object getType(InputEvent e){

		if (!hasEvents())
			return null;

		Object o = events.get(e);
		events.remove(e);
		
		return o;

	}

	public static boolean hasEvents(){

		return events.size() > 0;

	}

	@Override
	public void mouseClicked(MouseEvent e){

		events.put(e, MouseEventType.MOUSE_CLICKED);

	}

	@Override
	public void mouseDragged(MouseEvent e){

		events.put(e, MouseEventType.MOUSE_DRAGGED);

	}

	@Override
	public void mouseMoved(MouseEvent e){

		events.put(e, MouseEventType.MOUSE_MOVED);

	}

	@Override
	public void mousePressed(MouseEvent e){
	
		events.put(e, MouseEventType.MOUSE_PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e){

		events.put(e, MouseEventType.MOUSE_RELEASED);

	}

	@Override
	public void keyReleased(KeyEvent e){

		events.put(e, KeyEventType.KEY_RELEASED);

	}

	@Override
	public void keyPressed(KeyEvent e){

		events.put(e, KeyEventType.KEY_PRESSED);

	}

	@Override
	public void keyTyped(KeyEvent e){

		events.put(e, KeyEventType.KEY_TYPED);

	}
}