package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.ResourceManager;

public class Button extends UIElement {
	
	private BufferedImage image;

	public Button(int x, int y, int w, int h, String imageName){

		super(x, y, w, h);
		image = ResourceManager.getImage(imageName);

	}

	@Override
	public void draw(Graphics2D g){

		g.drawImage(image, bounds.x, bounds.y, null);

	}
}