package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.SpriteSheet;
import graphics.ResourceManager;
import component.DrawComponent;

public class Button extends UIElement implements DrawComponent {
	
	private SpriteSheet image;
	private float scale;

	public Button(int x, int y, int w, int h, String imageName){

		super(x, y, w, h);
		image = new SpriteSheet(imageName, w, h);
		scale = 1f;

	}

	public Button(int x, int y, int w, int h, String imageName, int imageW, int imageH){

		super(x, y, w, h);
		image = new SpriteSheet(imageName, imageW, imageH);
		scale = (float)(w / imageW);

	}

	@Override
	public void draw(Graphics2D g){

		if (clicked)
			g.drawImage(image.getImage(0, scale), bounds.x, bounds.y, null);
		else
			g.drawImage(image.getImage(1, scale), bounds.x, bounds.y, null);

	}
}