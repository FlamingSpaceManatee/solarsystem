package graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	protected ImageContainer image;
	private Dimension tileSize;

	public SpriteSheet(String fileName, int tileX, int tileY){

		image = ResourceManager.getImageContainer(fileName);
		tileSize = new Dimension(tileX, tileY);

	}

	public BufferedImage getImage(int index){

		int tilesWide = image.getSize().width / tileSize.width;
		int tilesHigh = image.getSize().height / tileSize.height;

		int indexX = index % tilesWide;
		int indexY = (int)Math.floor((double)index / tilesHigh);

		return image.getImage().getSubimage(indexX * tileSize.width, indexY * tileSize.height, tileSize.width, tileSize.height);

	}
}