package graphics;

import java.awt.image.BufferedImage;

public class Font extends SpriteSheet {
	
	public Font(String fileName, int tileW, int tileH){

		super(fileName, tileW, tileH);

	}

	public BufferedImage getChar(char letter){

		return getImage(letter);

	}

	public BufferedImage getChar(char letter, float scale){

		return getImage(letter, scale);

	}

	public int getWidth(){

		return tileSize.width;

	}

	public int getHeight(){

		return tileSize.height;

	}
}