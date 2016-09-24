package graphics;

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.Hashtable;

import java.io.InputStream;
import javax.imageio.ImageIO;

public class ResourceManager {
	
	private static GraphicsConfiguration config = 
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		

	private static Hashtable<String, ImageContainer> images = new Hashtable<String, ImageContainer>();

	//Loads image file from filename
	public static void loadImage(String fileName){

		if (images.containsKey(fileName))
			return;

		try {

			BufferedImage b = makeCompatible(ImageIO.read(loadAsStream(fileName)));	//Creates optimised image from InputStream
			images.put(fileName, new ImageContainer(b));							//Puts image in new container and in hashtable

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	//Gets image from file name
	public static BufferedImage getImage(String fileName){

		if (!images.containsKey(fileName))
			loadImage(fileName);

		return images.get(fileName).getImage();
	}

	//Gets image from file name, and scales it
	public static BufferedImage getImage(String fileName, float scale){

		if (!images.containsKey(fileName))
			loadImage(fileName);

		return images.get(fileName).getImage(scale);

	}

	//A class to hold all scaled versions of an image
	private static class ImageContainer {

		private Hashtable<Dimension, BufferedImage> sizes;
		private final Dimension originalSize;

		protected ImageContainer(BufferedImage image){

			originalSize = new Dimension(image.getWidth(), image.getHeight());
			sizes = new Hashtable<Dimension, BufferedImage>();
			sizes.put(originalSize, image);

		}

		//returns source image
		protected BufferedImage getImage(){

			return sizes.get(originalSize);

		}

		//Returns scaled image of source image
		protected BufferedImage getImage(float scale){

			//Find scaled dimensions
			Dimension s = new Dimension((int)(originalSize.width * scale), (int)(originalSize.height * scale));

			//If the scale is 1 or a scaled image was already generated it return that
			if (sizes.containsKey(s) || s.equals(originalSize))
				return sizes.get(s);

			BufferedImage source = sizes.get(originalSize);												//Create Pointer to source image
			BufferedImage b = config.createCompatibleImage(s.width, s.height, source.getTransparency());//Create new image with same dimensions
			AffineTransform t = AffineTransform.getScaleInstance(scale, scale);							//Creates AffineTransform to scale

			((Graphics2D)b.getGraphics()).drawRenderedImage(source, t);
			sizes.put(s, b);

			return b;
		}
	}

	//Optimise BufferedImage for screen capabilities
	private static BufferedImage makeCompatible(BufferedImage b){

		if (b.getColorModel().equals(config.getColorModel()))
			return b;

		BufferedImage b2 = config.createCompatibleImage(b.getWidth(), b.getHeight(), b.getTransparency());
		((Graphics2D)b2.getGraphics()).drawImage(b, 0, 0, null);

		return b2;
	}

	//Creates an InputStream object of the file
	private static InputStream loadAsStream(String fileName){

		try {

			InputStream i = ResourceManager.class.getResourceAsStream("/resources/" + fileName);
			if (i == null)
				System.out.println("Couldn't create InputStream for file: " + fileName);

			return i;

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}
	}
}