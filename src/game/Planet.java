package game;

import component.*;
import ui.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Planet extends UIElement implements DrawComponent {
	
	private static final double G = 6.67e-11;
	private static Point 		FOCUS;
	protected static double 	SCALE;
	private static int 			centreX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private static int 			centreY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	private static Random       rand = new Random();
	private static SolarSystem	ss;
	protected double x, y, x1, y1, m, r, s;
	private double vX, vY;
	protected String name;
	protected float[] colour;
	private ArrayList<Point> path;
	protected boolean boom = false;
	protected Point centre;
	protected boolean tail;

	public Planet(double x, double y, double m, double v, double angle){

		super((int)x, (int)y, 0, 0, true);

		this.x = this.x1 = x;
		this.y = this.y1 = y;
		this.m = m;
		this.vX =  v * Math.cos(Math.toRadians(angle));
		this.vY = -v * Math.sin(Math.toRadians(angle));
		this.colour = new float[]{1f, 1f, 1f};
		this.path = new ArrayList<Point>();
		this.name = "Planet";
		this.r = -1;
		this.s = 1.0d;
		this.centre = new Point((int)x, (int)y);
		this.tail = true;

	}

	public static void setSolarSystem(SolarSystem s){

		Planet.ss = s;

	}

	public static void setFocus(Point p){

		FOCUS = p;

	}

	public static void setScale(double scale){

		SCALE = scale;

	}

	private double getRoche(Planet p){

		if (r == -1)
			return -1d;

		return (1.26 * r * Math.pow(p.m / m, 1.0/3.0)) * 50;

	}

	public void setRadius(double r){

		this.r = r;

	}

	public double getVelocity(){

		return Math.sqrt(vX * vX + vY * vY);

	}

	public double getAngle(){

		double a = Math.abs(Math.toDegrees(Math.atan(vY / vX)));

		if 			(vY > 0 && vX < 0){

			return 180.0 + a;

		} else if 	(vY < 0 && vX < 0){

			return 180.0 - a;

		} else if 	(vY > 0 && vX > 0){

			return 360.0 - a;

		}

		return a;
	}

	public void setVelocity(double v, double a){

		this.vX =  v * Math.cos(Math.toRadians(a));
		this.vY = -v * Math.sin(Math.toRadians(a));

	}

	public void setColour(float r ,float g, float b){

		colour[0] = Math.min(r, 1f);
		colour[1] = Math.min(g, 1f);
		colour[2] = Math.min(b, 1f);

	}

	public void update(double t, ArrayList<Planet> bodies){

		if (bodies == null || t == 0 || dragged)
			return;

		for (Planet p : bodies){

			if (this != p){

				double rX = x - p.x;
				double rY = y - p.y;

				double rr = ((rX * rX) + (rY * rY));
				double d = Math.sqrt(rr);

				if (d <= getRoche(p)){

					System.out.println("roche");
					for (int i = 0; i < 10; i++){

						Planet n = new Planet(this.x + 1e8 * rand.nextDouble(), this.y + 1e8 * rand.nextDouble(), m * rand.nextFloat(), getVelocity() - rand.nextFloat() * 5e4, getAngle());
						n.s = 0.75;
						n.setPath((ArrayList<Point>)path.clone());
						n.setColour(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
						n.tail = false;
						n.name = name + " [Fragment #" + i + "]";
						ss.queuePlanet(n);
						ss.updateFocus(ss.focusI - 1);

					}
					boom = true;
				}

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
		
		double d = path.get(path.size() - 1).distance(px, py);

		if (d > 5.00){
			
			path.add(new Point(px, py));
			
		}
		
		while (path.size() > 500){
			
			path.remove(0);
			path.remove(0);
			
		}
	}

	public void enableInfo(boolean show){

		if (!show)
			return;

	}

	protected void setPath(ArrayList<Point> path){

		this.path = path;

	}

	@Override
	public boolean inside(Point p){

		int fx = (int)(ss.getPlanet(ss.focusI).x / SCALE);
		int fy = (int)(ss.getPlanet(ss.focusI).y / SCALE);

		double x0 = (x / SCALE) + centreX - fx;
		double y0 = (y / SCALE) + centreY - fy;

		return (p.x > (x0 - 5) && 
				p.x < (x0 + 5) &&
				p.y > (y0 - 5) &&
				p.y < (y0 + 5));

	}

	@Override
	public void translate(int dx, int dy){

		x += dx * SCALE;
		y += dy * SCALE;

		centre.setLocation((int)x, (int)y);

	}

	@Override
	public void handleMouseRelease(MouseEvent e){

		super.handleMouseRelease(e);

		int fx = (int)(ss.getPlanet(ss.focusI).x / SCALE);
		int fy = (int)(ss.getPlanet(ss.focusI).y / SCALE);

		path = new ArrayList<Point>();
		path.add(new Point(e.getPoint().x - centreX + fx, e.getPoint().y - centreY + fy));

	}

	@Override
	public void handleMouseDrag(MouseEvent e){

		if (ss.getPlanet(ss.focusI) == this)
			return;

		dragged = true;

		translate((e.getPoint().x) - clickedPoint.x, (e.getPoint().y) - clickedPoint.y);
		clickedPoint = new Point(e.getPoint().x, e.getPoint().y);

	}

	@Override
	public void draw(Graphics2D g){

		int dx, dy, fx, fy; // dx, y draw points, px, y real points

		fx = (int)(ss.getPlanet(ss.focusI).x / SCALE);
		fy = (int)(ss.getPlanet(ss.focusI).y / SCALE);

		dx = ((int)(x / SCALE)) + centreX - fx;
		dy = ((int)(y / SCALE)) + centreY - fy;

		g.setColor(new Color(colour[0], colour[1], colour[2]));
		g.fillOval((int)(dx - 2 * s), (int)(dy - 2 * s), (int)(s * 4), (int)(s * 4));

	}
	
	public void drawLine(Graphics2D g){
		
		if (!tail)
			return;

		g.setColor(new Color(colour[0], colour[1], colour[2]));
		
		int fx, fy;

		fx = (int)(ss.getPlanet(ss.focusI).x / SCALE);
		fy = (int)(ss.getPlanet(ss.focusI).y / SCALE);

		for (int i = 1; i < path.size(); i += 2){
			
			g.drawLine(path.get(i - 1).x + centreX - fx,
				 path.get(i - 1).y + centreY - fy, 
				 path.get(i).x + centreX - fx, 
				 path.get(i).y + centreY - fy);
			
		}
	}
}