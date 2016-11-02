package ui;

import component.MouseEventType;
import component.ClickComponent;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.function.Consumer;

public abstract class UIElement implements ClickComponent {
	
	protected Rectangle bounds;
	protected Consumer<Object> onClick;
	protected UIElement clickElement;

	protected boolean clicked;
	protected boolean draggable;
	protected boolean dragged;

	protected boolean delete;

	protected Point clickedPoint;

	public UIElement(int x, int y, int w, int h){

		bounds = new Rectangle(x, y, w, h);
		onClick = null;

		clicked = false;
		draggable = false;
		dragged = false;

		delete = false;

		clickElement = this;

	}

	public UIElement(int x, int y, int w, int h, boolean draggable){

		bounds = new Rectangle(x, y, w, h);
		onClick = null;

		clicked = false;
		this.draggable = draggable;
		dragged = false;

		delete = false;

		clickElement = this;

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
			onClick.accept(clickElement);

	}

	public void handleMouseDrag(MouseEvent e){

		dragged = true;
		translate(e.getPoint().x - clickedPoint.x, e.getPoint().y - clickedPoint.y);
		clickedPoint = e.getPoint();

	}

	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){

		if (!inside(e.getPoint()) && !dragged) {

			clicked = false;
			return false;

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
				if (clicked && draggable)
					handleMouseDrag(e);
				break;
		}

		return true;
	}

	@Override
	public void setReleasedEvent(Consumer<Object> c){

		this.onClick = c;

	}

	@Override
	public void setPressedEvent(Consumer<Object> c){



	}

	public boolean inside(Point p){

		return bounds.contains(p);

	}

	public void translate(int dx, int dy){

		bounds.translate(dx, dy);

	}

	public boolean isDeleted(){

		return delete;

	}

	public void setEventElement(UIElement e){

		clickElement = e;

	}

	public abstract void draw(Graphics2D g);
}