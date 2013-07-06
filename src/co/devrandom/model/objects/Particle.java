package co.devrandom.model.objects;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.model.Model;
import co.devrandom.model.events.DestroyPhysicsObject;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class Particle extends PhysicsObject {
	private static final float DENSITY = 0.01f;
	private static final float FRICTION = 0.3f;
	private static final float RESTITUTION = 0.5f;
	private static final float GRAVITY = 1f;
	
	private static final Shape SHAPE = PhysicsObject.makeCircle(0.5f);
	
	private static final BodyDef BD = new BodyDefBuilder()
		.type(BodyType.DYNAMIC)
		.gravityScale(GRAVITY)
		.build();
	
	private static final FixtureDef FD = new FixtureDefBuilder()
		.shape(SHAPE)
		.density(DENSITY)
		.friction(FRICTION)
		.restitution(RESTITUTION)
		.build();
	
	private static final TextureAttributes TEX_ATTRIBUTES = 
			new TextureAttributes(TextureList.SPARKLE);
	
	public Particle(Model model, Vector position, long duration) {
		super(model, BodyDefBuilder.setPosition(BD, position), new FixtureDef[] { FD }, TEX_ATTRIBUTES);
		
		model.addTimedEvent(new DestroyPhysicsObject(model, duration, this));
	}
}
