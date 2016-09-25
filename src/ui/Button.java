package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.SpriteSheet;
import graphics.ResourceManager;

public class Button extends UIElement {
	
	private SpriteSheet image;

	public Button(int x, int y, int w, int h, String imageName){

		super(x, y, w, h);
		image = new SpriteSheet(imageName, w, h);

	}

	@Override
	public void draw(Graphics2D g){

		if (clicked)
			g.drawImage(image.getImage(0), bounds.x, bounds.y, null);
		else
			g.drawImage(image.getImage(1), bounds.x, bounds.y, null);

	}
}