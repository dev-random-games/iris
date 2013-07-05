package co.devrandom.model;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class PhysicsObject {
	private static final float DEFAULT_DENSITY = 1.0f;
	private static final float DEFAULT_FRICTION = 0.3f;
	private static final float DEFAULT_RESTITUTION = 0.5f;
	
	private TextureAttributes texAttributes;
	private BodyDef bd;
	private Body body;
	
	public PhysicsObject(World world, Vector position, BodyType type, Shape shape, float density, float friction, float restitution) {
		texAttributes = new TextureAttributes(new TextureList[] {TextureList.SMILEY_MOUTH_1,
				 TextureList.SMILEY_MOUTH_2,
				 TextureList.SMILEY_MOUTH_3,
				 TextureList.SMILEY_MOUTH_4,
				 TextureList.SMILEY_MOUTH_4,
				 TextureList.SMILEY_MOUTH_3,
				 TextureList.SMILEY_MOUTH_2,
				 TextureList.SMILEY_MOUTH_1});

		bd = new BodyDef();
		bd.position.set(position.x, position.y);
		bd.type = type;

		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;       
		fd.restitution = restitution;
		
		body = world.createBody(bd);
		body.createFixture(fd);
	}
	
	public PhysicsObject(World world, Vector position, BodyType type, Shape shape) {
		this(world, position, type, shape, DEFAULT_DENSITY, DEFAULT_FRICTION, DEFAULT_RESTITUTION);
	}

	public TextureAttributes getTexAttributes() {
		return texAttributes;
	}
	
	/**
	 * Translates Box2D coordinates to on screen coordinates using GameState.SCALE
	 */
	public Vector getPosition() {
		Vec2 position = body.getPosition();
		return new Vector(position.x, position.y).scale(GameState.SCALE);
	}
	
	public float getRotation() {
		return body.getAngle();
	}
	
	public Body getBody() {
		return body;
	}
}
