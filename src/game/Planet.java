package game;

import component.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Planet implements DrawComponent, ClickComponent {
	
	private static final double G = 6.67e-11;
	private static Point 		FOCUS;
	private static double 		SCALE;
	private static int 			centreX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private static int 			centreY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	protected double x, y, m, r, x1, y1;
	private double vX, vY;
	private String name;
	private float[] colour;

	public Planet(double x, double y, double m, double v, double angle){

		this.x = this.x1 = x;
		this.y = this.y1 = y;
		this.m = m;
		this.vX =  v * Math.cos(angle);
		this.vY = -v * Math.sin(angle);
		this.r = 0;
		this.colour = new float[]{1f, 1f, 1f};

	}

	public void setRadius(double r){

		this.r = r;

	}

	public static void setFocus(Point p){

		FOCUS = p;

	}

	public static void setScale(double scale){

		SCALE = scale;

	}

	public void update(double t, ArrayList<Planet> bodies){

		x1 = x;
		y1 = y;

		if (bodies == null || t == 0)
			return;

		for (Planet p : bodies){

			if (this != p){

				double rX = x - p.x1;
				double rY = y - p.y1;

				double rr = ((rX * rX) + (rY * rY));
				double a = ((6.67e-11 * m * p.m) / (rr)) / m;

				double angle = (rX != 0) ? Math.atan(rY / rX) : 0;
				double aX = Math.abs(a * Math.cos(angle));
				double aY = Math.abs(a * Math.sin(angle));

				if (rX > 0)
					aX = -aX;
				if (rY > 0)
					aY = -aY;

				vX += aX * t;
				vY += aY * t;


			}
		}

		x += (t * vX);
		y += (t * vY);

	}

	@Override
	public void draw(Graphics2D g){

		double dX, dY;

		dX = (x * SCALE) - (FOCUS.x * SCALE) + centreX;
		dY = (y * SCALE) - (FOCUS.y * SCALE) + centreY;

		g.setColor(new Color(colour[0], colour[1], colour[2]));

		if (r == 0)
			g.fillOval((int)(dX - 1.5), (int)(dY- 1.5), 3, 3);
		if (r > 0)
			g.fillOval((int)(dX - (r / SCALE)), (int)(dY - (r / SCALE)), (int)(r / SCALE), (int)(r / SCALE));

		System.out.println("Drawing planet @ " + dX + ", " + dY);

	}

	@Override
	public void handleMousePress(MouseEvent e){}

	@Override
	public void handleMouseRelease(MouseEvent e){}

	@Override
	public void handleMouseDrag(MouseEvent e){}

	@Override
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t){ return true; }

	@Override
	public void setReleasedEvent(Consumer<Object> c){}

	@Override
	public void setPressedEvent(Consumer<Object> c){}

}