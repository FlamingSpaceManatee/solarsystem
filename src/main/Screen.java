package main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas{
	
	private BufferStrategy buffer;

	public Screen(){



	}

	public void createBuffer(){

		this.createBufferStrategy(2);
		this.buffer = this.getBufferStrategy();

	}

	public Graphics2D getGraphics(){

		return ((Graphics2D)buffer.getDrawGraphics());

	}

	public void flipBuffer(){

		buffer.show();

	}
}