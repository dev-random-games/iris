package co.devrandom.model.objects;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.model.Model;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;
import co.devrandom.vc.view.TextureAttributes;
import co.devrandom.vc.view.TextureList;

public class Player extends PhysicsObject {
	private static final float MOVEMENT_SPEED = 1f;
	
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 1f;
	private static final float RESTITUTION = 0.0001f;
	private static final float GRAVITY = 1f;
	private static final float LINEAR_DAMPING = 4f;
	
	private static final Shape SHAPE = PhysicsObject.makeBoxShape(0.3f, 0.3f);

	private static final BodyDef BD = new BodyDefBuilder()
		.type(BodyType.DYNAMIC)
		.gravityScale(GRAVITY)
		.linearDamping(LINEAR_DAMPING)
		.build();
	
	private static final FixtureDef FD = new FixtureDefBuilder()
		.shape(SHAPE)
		.density(DENSITY)
		.friction(FRICTION)
		.restitution(RESTITUTION)
		.build();
	
	private static final TextureAttributes TEX_ATTRIBUTES = new TextureAttributes(
			TextureList.PLAYER);

	public Player(Model model, Vector position) {
		super(model, BodyDefBuilder.setPosition(BD, position), new FixtureDef[] { FD }, TEX_ATTRIBUTES);
	}
	
	public void moveForward() {
		this.getBody().applyForceToCenter(new Vec2(0, -MOVEMENT_SPEED));
	}
	
	public void moveBackward() {
		this.getBody().applyForceToCenter(new Vec2(0, MOVEMENT_SPEED));
	}

	public void moveLeft() {
		this.getBody().applyForceToCenter(new Vec2(-MOVEMENT_SPEED, 0));
	}	
	
	public void moveRight() {
		this.getBody().applyForceToCenter(new Vec2(MOVEMENT_SPEED, 0));
	}
	
	public void haltX() {
		this.getBody().m_linearVelocity.x = 0;
	}
	
	public void haltY() {
		
	}
}
