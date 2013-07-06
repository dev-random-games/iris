package co.devrandom.util;

import org.jbox2d.common.Vec2;

public class Vector {
	public float x, y;
	
	public Vector() {
		x = y = 0;
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}

	public float dot(Vector v) {
		return x * v.x + y * v.y;
	}

	public float cross(Vector v) {
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

	public Vector scale(float scalar) {
		return new Vector(x * scalar, y * scalar);
	}
	
	public void scaleInPlace(float scalar) {
		x *= scalar;
		y *= scalar;
	}

	public Vector minus(Vector v) {
		return plus(v.scale(-1));
	}
	
	public void subtractInPlace(Vector v) {
		addInPlace(v.scale(-1));
	}

	public float mag() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vector norm() {
		return scale(1 / mag());
	}

	public Vector rot(float deg) {
		float cos = (float) Math.cos(deg);
		float sin = (float) Math.sin(deg);
		return new Vector(cos * x - sin * y, sin * x + cos * y);
	}

	public Vector projection(Vector v) {
		return v.scale(dot(v) / (float) Math.pow(v.mag(), 2));
	}
	
	public Vector perp(Vector v) {
		return minus(projection(v));
	}
	
	public String toString() {
		return x + ", " + y;
	}
	
	public Vec2 toVec2() {
		return new Vec2(x, y);
	}
}
