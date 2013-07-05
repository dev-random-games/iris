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
	
	private TextureAttributes texAttributes;
	private BodyDef bd;
	private float rotation;
	private Body body;
	
	public PhysicsObject(World world, float x, float y, BodyType type, Shape shape, float density, float friction, float restitution) {
		texAttributes = new TextureAttributes(TextureList.EVIL_SMILEY);
		
		bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = type;

		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;       
		fd.restitution = restitution;
		
		body = world.createBody(bd);
		body.createFixture(fd);
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
		return rotation;
	}
	
	public void setRotation(float newRot) {
		rotation = newRot;
	}
	
	public Body getBody() {
		return body;
	}
}
