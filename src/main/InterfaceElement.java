package main;

import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class InterfaceElement implements Clickable{
	
	protected Point location_0;	//Top-left corner
	protected Point location_1;	//Bottom-right corner
	protected Dimension size;	//Size

	protected Point mouseClickLocation;	//If clicked, where the click happened

	protected boolean clicked;
	protected boolean draggable;

	public InterfaceElement(){}

	public InterfaceElement(int x, int y, int w, int h){

		location_0 = new Point(x, y);
		location_1 = new Point(x + w, y + h);
		size = new Dimension(w, h);
		clicked = false;
		draggable = false;

	}

	public void handleMousePress(MouseEvent e){

		clicked = true;
		mouseClickLocation = e.getPoint();

	}

	public void handleMouseRelease(MouseEvent e){

		if (clicked)
			mouseClickAction();

		clicked = false;
		mouseClickLocation = null;

	}

	public void handleMouseDrag(MouseEvent e){

		if (clicked && draggable){

			int dX = e.getPoint().x - mouseClickLocation.x;
			int dY = e.getPoint().y - mouseClickLocation.y;

			location_0.translate(dX, dY);
			location_1.translate(dX, dY);

			mouseClickLocation = e.getPoint();
		}

	}

	public void handleMouseEvent(MouseEvent e){

		if ((e.getModifiersEx() & (MouseEvent.BUTTON1_DOWN_MASK)) == MouseEvent.BUTTON1_DOWN_MASK) //If left-click
			if (clicked)
				handleMouseDrag(e);
			else
				handleMousePress(e);
		else if (clicked)
			handleMouseRelease(e);

	}

	protected boolean inside(Point p){

		return (p.x > location_0.x && p.y > location_0.y &&
				p.x < location_1.x && p.y < location_1.y);

	}

	protected void mouseClickAction(){}

	protected void draw(Graphics2D g){

		g.setColor(java.awt.Color.RED);
		g.fillRect(location_0.x, location_0.y, size.width, size.height);

	}
}