package co.devrandom.model;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class Particle extends PhysicsObject {
	private static final float DENSITY = 0.01f;
	private static final float FRICTION = 0.3f;
	private static final float RESTITUTION = 0.5f;
	private static final float GRAVITY = 1f;
	private static final Shape SHAPE = PhysicsObject.makeCircle(0.5f);
	private static final TextureAttributes TEX_ATTRIBUTES = 
			new TextureAttributes(TextureList.SPARKLE);
	
	public Particle(Model model, Vector position, long duration) {
		super(model, position, BodyType.DYNAMIC,
				SHAPE, DENSITY, FRICTION, RESTITUTION, GRAVITY, false, TEX_ATTRIBUTES);
		
		this.getModel().addTimedEvent(new DestroyPhysicsObject(model, duration, this));
	}
}
