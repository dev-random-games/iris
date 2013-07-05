package co.devrandom.model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
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
	private Shape shape;

	public PhysicsObject(World world, Vector position, BodyType type, Shape shape, float density,
			float friction, float restitution) {
		this.shape = shape;

		texAttributes = new TextureAttributes(new TextureList[] { TextureList.SMILEY_MOUTH_1,
				TextureList.SMILEY_MOUTH_2, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_4,
				TextureList.SMILEY_MOUTH_4, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_2,
				TextureList.SMILEY_MOUTH_1 });
		
		texAttributes.setSize(getSize().scale(GameState.SCALE));
		
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
	 * Translates Box2D coordinates to on screen coordinates using
	 * GameState.SCALE
	 */
	public Vector getPosition() {
		Vec2 position = body.getPosition();
		return new Vector(position.x, position.y).scale(GameState.SCALE);
	}

	/**
	 * Attempts to find the minimum bounding box of the physics shape.
	 */
	public Vector getSize() {
		if (shape.m_type == ShapeType.POLYGON) {
			// If the shape is a polygon, find the minimum bounding box.
			PolygonShape shape = (PolygonShape) this.shape;
			
			Vec2 vertex0 = shape.getVertex(0);
			Vector c1 = new Vector(vertex0.x, vertex0.y);
			Vector c2 = new Vector(c1);
			for (Vec2 vertex : shape.getVertices()){
				c1.x = Math.min(c1.x, vertex.x);
				c2.x = Math.max(c2.x, vertex.x);
				c1.y = Math.min(c1.y, vertex.y);
				c2.y = Math.max(c2.y, vertex.y);
			}
			System.out.println(c2.minus(c1));
			return c2.minus(c1);
		} else {
			float dimension = shape.getRadius() * 2;
			return new Vector(dimension, dimension);
		}
	}

	public float getRotation() {
		return (float) (body.getAngle() * 180 / Math.PI);
	}

	public Body getBody() {
		return body;
	}
}
