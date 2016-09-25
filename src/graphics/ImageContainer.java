package graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.Hashtable;

public class ImageContainer {

	private Hashtable<Float, BufferedImage> sizes;

	public ImageContainer(BufferedImage image){

		sizes = new Hashtable<Float, BufferedImage>();
		sizes.put(1f, image);

	}

	//returns source image
	public BufferedImage getImage(){

		return sizes.get(1f);

	}

	//Returns scaled image of source image
	public BufferedImage getImage(float scale){

		//Find scaled dimensions
		Dimension s = new Dimension((int)(sizes.get(1f).getWidth() * scale), (int)(sizes.get(1f).getHeight() * scale));

		//If the scale is 1 or a scaled image was already generated it return that
		if (scale == 1f || sizes.containsKey(scale))
			return sizes.get(scale);

		BufferedImage source = sizes.get(1f);																	//Create Pointer to source image
		BufferedImage b = ResourceManager.CONFIG.createCompatibleImage(s.width, s.height, source.getTransparency());	//Create new image with same dimensions
		AffineTransform t = AffineTransform.getScaleInstance(scale, scale);												//Creates AffineTransform to scale

		((Graphics2D)b.getGraphics()).drawRenderedImage(source, t);
		sizes.put(scale, b);

		return b;
	}

	public Dimension getSize(){

		return new Dimension(sizes.get(1f).getWidth(), sizes.get(1f).getHeight());

	}
}