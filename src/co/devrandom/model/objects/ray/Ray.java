package co.devrandom.model.objects.ray;

import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.util.Vector;

public class Ray {
	private PhysicsObject source, dest;
	private Vector origin, end;
	
	public Ray(Vector origin, Vector end, PhysicsObject source, PhysicsObject dest){
		this.origin = origin;
		this.end = end;
		this.source = source;
		this.dest = dest;
	}
	
	public PhysicsObject getSource() {
		return source;
	}
	
	public PhysicsObject getDest() {
		return dest;
	}
	
	public Vector getOrigin() {
		return origin;
	}
	
	public Vector getEnd() {
		return end;
	}
}
