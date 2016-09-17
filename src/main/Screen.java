package main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas{
	
	private BufferStrategy buffer;

	public Screen(){

	}

	public void createBuffer(){

		while (this.buffer == null){

			this.createBufferStrategy(2);
			this.buffer = this.getBufferStrategy();
		}
	}

	public Graphics2D getBufferGraphics(){

		return ((Graphics2D)buffer.getDrawGraphics());

	}

	public void flipBuffer(){

		buffer.show();

	}
}