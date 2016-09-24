package ui;

import main.MouseEventType;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.function.Consumer;

public abstract class UIElement implements Clickable {
	
	protected Rectangle bounds;
	protected Consumer<Object> onClick;

	protected boolean clicked;
	protected boolean draggable;
	protected boolean dragged;

	private Point clickedPoint;

	public UIElement(int x, int y, int w, int h){

		bounds = new Rectangle(x, y, w, h);
		onClick = null;

		clicked = false;
		draggable = false;
		dragged = false;

	}

	public UIElement(int x, int y, int w, int h, boolean draggable){

		bounds = new Rectangle(x, y, w, h);
		onClick = null;

		clicked = false;
		this.draggable = draggable;
		dragged = false;

	}

	public void handleMousePress(MouseEvent e){

		clicked = true;
		dragged = false;
		clickedPoint = e.getPoint();

	}

	public void handleMouseRelease(MouseEvent e){

		clicked = false;
		dragged = false;

		if (onClick != null)
			onClick.accept(this);

	}

	public void handleMouseDrag(MouseEvent e){

		if (draggable) {

			dragged = true;
			bounds.translate(e.getPoint().x - clickedPoint.x, e.getPoint().y - clickedPoint.y);
			clickedPoint = e.getPoint();

		}

	}

	public void handleMouseEvent(MouseEvent e, MouseEventType t){

		if (!inside(e.getPoint()) && !dragged) {

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
				if (clicked)
					handleMouseDrag(e);
				break;
		}

	}

	public void setOnClick(Consumer<Object> c){

		this.onClick = c;

	}

	public boolean inside(Point p){

		return bounds.contains(p);

	}

	public abstract void draw(Graphics2D g);
}