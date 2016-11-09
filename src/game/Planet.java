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
import java.awt.BasicStroke;

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
	private ArrayList<DoublePoint> path;
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
		this.path = new ArrayList<DoublePoint>();
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
						n.setPath((ArrayList<DoublePoint>)path.clone());
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
		
		if (path.size() == 0){
			
			path.add(new DoublePoint(x, y));
			return;
			
		}
		
		double d = path.get(path.size() - 1).distance(x, y) / 1e9;

		if (d > 5.00){
			
			path.add(new DoublePoint(x, y));
			
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

	protected void setPath(ArrayList<DoublePoint> path){

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

		double fx = ss.getPlanet(ss.focusI).x;
		double fy = ss.getPlanet(ss.focusI).y;

		if (dragged){
			
			path = new ArrayList<DoublePoint>();
			path.add(new DoublePoint((e.getPoint().x * SCALE) + fx - (centreX * SCALE), (e.getPoint().y * SCALE) + fy - (centreY / SCALE)));

		}
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

		int r = (int)Math.max((1e9 / SCALE) * 4, 4);
		int dr = r / 2;

		g.setColor(new Color(colour[0], colour[1], colour[2]));
		g.fillOval((int)(dx - dr * s), (int)(dy - dr * s), (int)(s * r), (int)(s * r));

	}
	
	public void drawLine(Graphics2D g){
		
		if (!tail)
			return;

		g.setStroke(new BasicStroke((int)Math.min(1e9 / SCALE, 1), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(new Color(colour[0], colour[1], colour[2]));
		
		int fx, fy, dx0, dx1, dy0, dy1;

		fx = (int)(ss.getPlanet(ss.focusI).x / SCALE);
		fy = (int)(ss.getPlanet(ss.focusI).y / SCALE);

		for (int i = 1; i < path.size(); i += 2){
			
			dx0 = (int)((path.get(i - 1).x / SCALE) + centreX - fx);
			dy0 = (int)((path.get(i - 1).y / SCALE) + centreY - fy);
			dx1 = (int)((path.get(i).x / SCALE) + centreX - fx);
			dy1 = (int)((path.get(i).y / SCALE) + centreY - fy);

			g.drawLine(dx0, dy0, dx1, dy1);
			
		}

		g.setStroke(new BasicStroke(1));
	}
}