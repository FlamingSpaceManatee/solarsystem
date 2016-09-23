package ui;

import main.MouseEventType;

import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.function.Consumer;

public class UIElement implements Clickable {
	
	protected Rectangle bounds;
	protected Consumer<Clickable> onClick;

	protected boolean clicked;
	protected boolean draggable;

	public UIElement(int x, int y, int w, int h){

		bounds = new Rectangle(x, y, w, h);
		onClick = (t) -> {};

	}

	public void handleMousePress(MouseEvent e){

		clicked = true;

	}

	public void handleMouseRelease(MouseEvent e){

		clicked = false;
		onClick.accept(this);

	}

	public void handleMouseDrag(MouseEvent e){

		//if (draggable)
			//Do Whatever

	}

	public void handleMouseEvent(MouseEvent e, MouseEventType t){

		if (!inside(e.getPoint())) {

			clicked = false;
			return;

		}

		switch (t){

			case MOUSE_PRESSED:
				if (!clicked)
					handleMousePress(e);
				break;

			case MOUSE_RELEASED:
				if (clicked)
					handleMouseRelease(e);
				break;

			case MOUSE_DRAGGED:
				
				handleMouseDrag(e);
				break;
		}

	}

	public void setOnClick(Consumer<Clickable> c){

		this.onClick = c;

	}

	public boolean inside(Point p){

		return bounds.contains(p);

	}

	public void draw(Graphics2D g){

		g.setColor((clicked)? java.awt.Color.RED : java.awt.Color.BLUE);
		g.fill(bounds);

	}
}