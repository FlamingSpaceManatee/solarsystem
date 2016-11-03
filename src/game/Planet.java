package game;

import component.*;
import ui.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Planet extends UIElement implements DrawComponent {
	
	private static final double G = 6.67e-11;
	private static Point 		FOCUS;
	private static double 		SCALE;
	private static int 			centreX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private static int 			centreY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	protected double x, y, x1, y1, m;
	private double vX, vY;
	private String name;
	private float[] colour;
	private boolean infoShown = false;
	private ArrayList<Point> path;

	public Planet(double x, double y, double m, double v, double angle){

		super((int)x, (int)y, 0, 0, true);

		this.x = this.x1 = x;
		this.y = this.y1 = y;
		this.m = m;
		this.vX =  v * Math.cos(Math.toRadians(angle));
		this.vY = -v * Math.sin(Math.toRadians(angle));
		this.colour = new float[]{1f, 1f, 1f};
		this.path = new ArrayList<Point>();

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
	
	public void updatePos(double t){
		
		if (dragged)
			return;
		
		//ADD POINT TO PATH
		
		int px = (int)(x / SCALE);
		int py = (int)(y / SCALE);
		
		if (path.size() == 0){
			
			path.add(new Point(px, py));
			return;
			
		}
		
		if (path.get(path.size() - 1).distance(px, py) > 2.50){
			
			path.add(new Point(px, py));
			
		}
		
		if (path.size() > 1000){
			
			path.remove(0);
			
		}
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

		int dx, dy; // dx, y draw points, px, y real points
		
		dx = (int)((x / SCALE) - (FOCUS.x / SCALE) + centreX);
		dy = (int)((y / SCALE) - (FOCUS.y / SCALE) + centreY);

		g.setColor(new Color(colour[0], colour[1], colour[2]));
		g.fillOval((int)(dx - 2), (int)(dy- 2), 4, 4);

	}
	
	public void drawLine(Graphics2D g){
		
		g.setColor(new Color(colour[0], colour[1], colour[2]));
		
		for (int i = 1; i < path.size(); i++){
			
			g.drawLine(path.get(i - 1).x + centreX, path.get(i - 1).y + centreY, path.get(i).x + centreX, path.get(i).y + centreY);
			
		}
	}
}