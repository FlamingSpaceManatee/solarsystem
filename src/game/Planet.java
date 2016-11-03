package game;

import component.*;
import ui.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Planet extends UIElement implements DrawComponent {
	
	private static final double G = 6.67e-11;
	private static Point 		FOCUS;
	private static double 		SCALE;
	private static int 			centreX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private static int 			centreY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	protected double x, y, m;
	private double vX, vY;
	private String name;
	private float[] colour;
	private boolean infoShown = false;

	public Planet(double x, double y, double m, double v, double angle){

		super((int)x, (int)y, 0, 0, true);

		this.x = x;
		this.y = y;
		this.m = m;
		this.vX =  v * Math.cos(Math.toRadians(angle));
		this.vY = -v * Math.sin(Math.toRadians(angle));
		this.colour = new float[]{1f, 1f, 1f};

	}

	public static void setFocus(Point p){

		FOCUS = p;

	}

	public static void setScale(double scale){

		SCALE = scale;

	}

	public void setColour(float r ,float g, float b){

		colour[0] = r;
		colour[1] = g;
		colour[2] = b;

	}

	public void update(double t, ArrayList<Planet> bodies){

		if (bodies == null || t == 0 || dragged)
			return;

		for (Planet p : bodies){

			if (this != p){

				double rX = x - p.x;
				double rY = y - p.y;

				double rr = ((rX * rX) + (rY * rY));
				double a = (((G * m * p.m) / (rr)) / m);

				double angle = Math.atan(rY / rX);
				double aX = Math.abs(a * Math.cos(angle));
				double aY = Math.abs(a * Math.sin(angle));

				if (rX >= 0)
					aX = -aX;
				if (rY >= 0)
					aY = -aY;

				vX += (aX * t);
				vY += (aY * t);


			}
		}

		x += (t * vX);
		y += (t * vY);

	}

	public void enableInfo(boolean show){

		if (!show)
			return;

	}

	@Override
	public boolean inside(Point p){

		double x0 = (x / SCALE) - (FOCUS.x / SCALE) + centreX;
		double y0 = (y / SCALE) - (FOCUS.y / SCALE) + centreY;

		return (p.x > (x0 - 5) && 
				p.x < (x0 + 5) &&
				p.y > (y0 - 5) &&
				p.y < (y0 + 5));

	}

	@Override
	public void translate(int dx, int dy){

		x += dx * SCALE;
		y += dy * SCALE;

	}

	@Override
	public void draw(Graphics2D g){

		double dX, dY;

		dX = (x / SCALE) - (FOCUS.x / SCALE) + centreX;
		dY = (y / SCALE) - (FOCUS.y / SCALE) + centreY;

		g.setColor(new Color(colour[0], colour[1], colour[2]));
		g.fillOval((int)(dX - 2), (int)(dY- 2), 4, 4);

	}
}