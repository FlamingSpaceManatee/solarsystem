package ui;

import java.awt.Graphics2D;
import graphics.*;
import component.KeyEventType;
import component.MouseEventType;
import component.KeyComponent;
import component.DrawComponent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class TextBox extends UIElement implements KeyComponent, DrawComponent {
	
	private Font font;
	private ImageContainer image;
	protected boolean focused;
	private int cursorPos;
	private String text;
	private boolean keyPressed;
	private final int MAX_TEXT_LENGHT;
	private final float SCALE;

	public TextBox(int x, int y, int w, int h, Font font){

		super(x, y, w, h);
		this.font = font;
		text = "";
		this.SCALE = 1f;

		MAX_TEXT_LENGHT = (w / (font.getWidth() + 1));

		setReleasedEvent(xxx -> ((TextBox)xxx).focused = true);

	}

	public TextBox(int x, int y, int w, int h, Font font, String backgroundImage){

		super(x, y, w, h);
		this.font = font;
		this.image = ResourceManager.getImageContainer(backgroundImage);
		text = "";
		this.SCALE = 1f;

		MAX_TEXT_LENGHT = (w / (font.getWidth() + 1));

		setReleasedEvent(xxx -> ((TextBox)xxx).focused = true);

	}

	public TextBox(int x, int y, int w, int h, Font font, float scale){

		super(x, y, w, h);
		this.font = font;
		text = "";
		this.SCALE = scale;

		MAX_TEXT_LENGHT = (int)(w / (font.getWidth() + SCALE));

		setReleasedEvent(xxx -> ((TextBox)xxx).focused = true);

	}

	public TextBox(int x, int y, int w, int h, Font font, float scale, String backgroundImage){

		super(x, y, w, h);
		this.font = font;
		this.image = ResourceManager.getImageContainer(backgroundImage);
		text = "";
		this.SCALE = scale;

		MAX_TEXT_LENGHT = (int)(w / (font.getWidth() + SCALE));

		setReleasedEvent(xxx -> ((TextBox)xxx).focused = true);

	}

	public void setText(String t){

		this.text = t;

	}

	public String getText(){

		return text;

	}

	@Override
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){

		if (!inside(e.getPoint()))
			focused = false;

		return super.handleMouseEvent(e, t);

	}

	@Override
	public boolean handleKeyEvent(KeyEvent k, KeyEventType t){

		if (!focused)
			return false;

		switch(t){

			case KEY_PRESSED:
				handleKeyPress(k);
				break;
			case KEY_RELEASED:
				handleKeyRelease(k);
				break;

		}

		return true;

	}

	@Override
	public void handleKeyRelease(KeyEvent k){

		keyPressed = false;

	}

	@Override
	public void handleKeyPress(KeyEvent k){

		if ((k.getKeyCode() == KeyEvent.VK_BACK_SPACE) && (text.length() > 0))
			text = text.substring(0, text.length() - 1);
		else if (reg(k.getKeyCode()) && text.length() < MAX_TEXT_LENGHT)
			text += k.getKeyChar();
		else
			System.out.println("wtf?");

	}

	@Override
	public void draw(Graphics2D g){

		g.setColor(java.awt.Color.BLUE);

		if (image != null)
			g.drawImage(image.getImage(), bounds.x, bounds.y, null);
		else
			//g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

		for (int i = 0; i < text.length(); i++)
			g.drawImage(font.getChar(text.charAt(i), SCALE), bounds.x + i * font.getWidth() + i, bounds.y, null);

	}

	private boolean reg(int n){

		return ((n != KeyEvent.VK_SHIFT) && (n != KeyEvent.VK_CAPS_LOCK) && (n != KeyEvent.VK_ALT) && (n != KeyEvent.VK_TAB));

	}

	public boolean focused(){

		return focused;

	}

	public void setFocused(boolean t){

		focused = t;

	}
}