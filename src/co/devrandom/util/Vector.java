package co.devrandom.util;

public class Vector {
	public double x, y;
	
	public Vector() {
		x = y = 0;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}

	public double dot(Vector v) {
		return x * v.x + y * v.y;
	}

	public double cross(Vector v) {
		return x * v.y - y * v.x;
	}

	public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	public void addInPlace(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void setInPlace(Vector v) {
		x = v.x;
		y = v.y;
	}

	public Vector scale(double scalar) {
		return new Vector(x * scalar, y * scalar);
	}
	
	public void scaleInPlace(double scalar) {
		x *= scalar;
		y *= scalar;
	}

	public Vector minus(Vector v) {
		return plus(v.scale(-1));
	}
	
	public void subtractInPlace(Vector v) {
		addInPlace(v.scale(-1));
	}

	public double mag() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector norm() {
		return scale(1 / mag());
	}

	public Vector rot(double deg) {
		double cos = Math.cos(deg);
		double sin = Math.sin(deg);
		return new Vector(cos * x - sin * y, sin * x + cos * y);
	}

	public Vector projection(Vector v) {
		return v.scale(dot(v) / Math.pow(v.mag(), 2));
	}
	
	public Vector perp(Vector v) {
		return minus(projection(v));
	}
	
	public String toString() {
		return x + ", " + y;
	}
}
