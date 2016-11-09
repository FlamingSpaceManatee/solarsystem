package game;

public class DoublePoint {
	
	public double x, y;

	public DoublePoint(){

		x = y = 0;

	}

	public DoublePoint(double x, double y){

		this.x = x;
		this.y = y;

	}

	public double distance(double x, double y){

		double dx = this.x - x;
		double dy = this.y - y;

		return Math.sqrt(dx * dx + dy * dy);

	}
}