package graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	protected ImageContainer image;
	protected Dimension tileSize;

	public SpriteSheet(String fileName, int tileWidth, int tileHeight){

		image = ResourceManager.getImageContainer(fileName);
		tileSize = new Dimension(tileWidth, tileHeight);

	}

	//Get source image at default resolution from ImageContainer
	public BufferedImage getImage(){

		return image.getImage();

	}

	//Get subimage at index based on tile width and height
	public BufferedImage getImage(int index){

		//System.out.println(image.getSize().width + " image width " + tileSize.width + " tile width");
		//System.out.println(image.getSize().height + " image height " + tileSize.height + " tile height");

		int tilesWide = image.getSize().width / tileSize.width;
		int tilesHigh = image.getSize().height / tileSize.height;

		//System.out.println(tilesWide + " tiles wide and " + tilesHigh + " tiles high");

		int indexX = index % tilesWide;
		int indexY = (index / tilesWide);

		//System.out.println("getting subimage index: " + index + ", [x: " + indexX * tileSize.width + ", y: " + indexY * tileSize.height + "]");
		//System.out.println("[x2: " + (indexX + 1) * tileSize.width + ", y2: " + (indexY + 1) * tileSize.height + "]");

		return image.getImage().getSubimage(indexX * tileSize.width, indexY * tileSize.height, tileSize.width, tileSize.height);

	}

	//Get scaled subimage at index based on tile width and height
	public BufferedImage getImage(int index, float scale){

		if (scale == 1f)
			return getImage(index);

		int tilesWide = image.getSize().width / tileSize.width;
		int tilesHigh = image.getSize().height / tileSize.height;

		int indexX = index % tilesWide;
		int indexY = (index / tilesWide);

		//System.out.println("getting subimage index: " + index + ", [x: " + indexX * tileSize.width * scale + ", y: " + indexY * tileSize.height * scale + "]");
		//System.out.println("[x2: " + (indexX + 1) * tileSize.width * scale + ", y2: " + (indexY + 1) * tileSize.height * scale + "]");

		return image.getImage(scale).getSubimage((int)(indexX * tileSize.width * scale), 
												(int)(indexY * tileSize.height * scale), 
												(int)(tileSize.width * scale), 
												(int)(tileSize.height * scale));

	}
}