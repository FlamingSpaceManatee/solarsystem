package ui;

import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;

import graphics.*;
import component.KeyComponent;
import component.KeyEventType;
import component.MouseEventType;
	
//UIELEMENT SHOULD JUST IMPLEMENT TYPEABLE ITSELF

public class Container extends UIElement implements KeyComponent {
	
	private ArrayList<UIElement> elements;

	//BACKGROUND IMAGE
	private ImageContainer image;
	private float imageScale;

	public Container(int x, int y, int w, int h){

		super(x, y, w, h);
		elements = new ArrayList<UIElement>();

	}

	public Container(int x, int y, int w, int h, boolean draggable){

		super(x, y, w, h, draggable);
		elements = new ArrayList<UIElement>();

		elements.add(new Button(x + w - 24, y - 8, 16, 16, "button_close.png"));
		elements.get(0).setEventElement(this);
		elements.get(0).setReleasedEvent(eee -> {

			((UIElement)eee).delete = true;

		});

	}

	public Container(int x, int y, int w, int h, String image){

		super(x, y, w, h);
		elements = new ArrayList<UIElement>();
		this.image = ResourceManager.getImageContainer(image);
		this.imageScale = w / this.image.getSize().width;

	}

	public Container(int x, int y, int w, int h, String image, boolean draggable){

		super(x, y, w, h, draggable);

		elements = new ArrayList<UIElement>();
		elements.add(new Button(x + w - 24, y - 8, 16, 16, "button_close.png"));
		elements.get(0).setEventElement(this);
		elements.get(0).setReleasedEvent(eee -> {

			((UIElement)eee).delete = true;

		});

		this.image = ResourceManager.getImageContainer(image);
		this.imageScale = w / this.image.getSize().width;

	}

	public void addElement(UIElement e){

		if (!elements.contains(e))
			elements.add(e);

	}

	public void removeElement(UIElement e){

		elements.remove(e);

	}

	public UIElement getElement(int index){

		return elements.get(index);

	}

	@Override
	public void handleMouseDrag(MouseEvent e){

		if (draggable)
			for (UIElement el : elements){
				el.translate(e.getPoint().x - clickedPoint.x, e.getPoint().y - clickedPoint.y);
			}

		super.handleMouseDrag(e);

	}

	@Override
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){

		boolean x = false;
		for (UIElement el : elements)
			x = (x | el.handleMouseEvent(e, t));
		if (!x)
			return super.handleMouseEvent(e, t);

		return !x;

	}

	@Override
	public void draw(Graphics2D g){

		if (image == null){
		
			//g.setColor(java.awt.Color.RED);
			//g.draw(bounds);

		} else {

			g.drawImage(image.getImage(imageScale), bounds.x, bounds.y, null);

		}


		for (UIElement e : elements)
			e.draw(g);

	}

	@Override
	public boolean handleKeyEvent(KeyEvent k, KeyEventType t){

		boolean x = false;
		for (UIElement el : elements)
			if (el instanceof KeyComponent)
				x = (x | ((KeyComponent)el).handleKeyEvent(k, t));

		return x;

	}

	@Override
	public void handleKeyPress(KeyEvent k){}

	@Override
	public void handleKeyRelease(KeyEvent k){}
}