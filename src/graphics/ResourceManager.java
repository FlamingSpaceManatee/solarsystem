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
	
	public static GraphicsConfiguration CONFIG = 
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		

	private static Hashtable<String, ImageContainer> images = new Hashtable<String, ImageContainer>();

	//Loads image file from file name
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

	public static ImageContainer getImageContainer(String fileName){

		if (!images.containsKey(fileName))
			loadImage(fileName);

		return images.get(fileName);
	}

	//Optimise BufferedImage for screen capabilities
	private static BufferedImage makeCompatible(BufferedImage b){

		if (b.getColorModel().equals(CONFIG.getColorModel()))
			return b;

		BufferedImage b2 = CONFIG.createCompatibleImage(b.getWidth(), b.getHeight(), b.getTransparency());
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