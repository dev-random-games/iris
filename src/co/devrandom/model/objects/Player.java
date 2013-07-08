package co.devrandom.model.objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.assets.textures.TextureAttributes;
import co.devrandom.assets.textures.TextureList;
import co.devrandom.model.Model;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;

public class Player extends PhysicsObject {
	private static final float MOVEMENT_SPEED = 1f;
	private static final float SPRINT_MULTIPLIER = 2f;
	
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 0.3f;
	private static final float RESTITUTION = 0.0001f;
	private static final float GRAVITY = 1f;

	private static final BodyDef BD = new BodyDefBuilder()
		.type(BodyType.DYNAMIC)
		.gravityScale(GRAVITY)
		.build();
	
	private final static FixtureDefBuilder FD = new FixtureDefBuilder()
		.density(DENSITY)
		.friction(FRICTION)
		.restitution(RESTITUTION);
	
	public Player(Model model, Vector position, Vector size) {
		super(model, BodyDefBuilder.setPosition(BD, position),
				FD.shape(PhysicsObject.makeBoxShape(size)).build(),
				 new TextureAttributes(TextureList.PLAYER));
	}

	public void friction(boolean on) {
		if (on) {
			getBody().getFixtureList().setFriction(FRICTION);
		} else {
			getBody().getFixtureList().setFriction(0.0f);
		}
	}

	public void moveForward(boolean sprinting) {
		this.getBody().applyForceToCenter(new Vec2(0, (sprinting ? SPRINT_MULTIPLIER : 1) * -MOVEMENT_SPEED));
	}
	
	public void moveBackward(boolean sprinting) {
		this.getBody().applyForceToCenter(new Vec2(0, (sprinting ? SPRINT_MULTIPLIER : 1) * MOVEMENT_SPEED));
	}

	public void moveLeft(boolean sprinting) {
		this.getBody().applyForceToCenter(new Vec2((sprinting ? SPRINT_MULTIPLIER : 1) * -MOVEMENT_SPEED, 0));
	}	
	
	public void moveRight(boolean sprinting) {
		this.getBody().applyForceToCenter(new Vec2((sprinting ? SPRINT_MULTIPLIER : 1) * MOVEMENT_SPEED, 0));
	}
}
