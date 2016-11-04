package ui;

import graphics.Font;

import java.awt.Graphics2D;

public class Label extends UIElement {
	
	private String text;
	private Font font;

	public Label(int x, int y, String text){

		super(x, y, 1920, 1080);

		this.text = text;
		font = new Font("ascii.png", 16, 15);

	}

	public void setText(String text){

		this.text = text;

	}

	public String getText(){

		return text;

	}

	@Override
	public void draw(Graphics2D g){

		for (int i = 0; i < text.length(); i++)
			g.drawImage(font.getChar(text.charAt(i)), bounds.x + i * font.getWidth() + i, bounds.y, null);

	}
}