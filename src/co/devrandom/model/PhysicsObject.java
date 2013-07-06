package co.devrandom.model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.main.GameState;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;

public class PhysicsObject {
	private static final float DEFAULT_DENSITY = 0.5f;
	private static final float DEFAULT_FRICTION = 0.3f;
	private static final float DEFAULT_RESTITUTION = 0.5f;
	private static final float DEFAULT_LINEAR_DAMPING = 1f;

	private Model model;
	private TextureAttributes texAttributes;
	private BodyDef bd;
	private Body body;
	private Shape shape;

	public PhysicsObject(Model model, Vector position, BodyType type, Shape shape, float density,
			float friction, float restitution, float gravity, TextureAttributes texAttributes) {
		
		this.model = model;
		
		this.shape = shape;
		
		this.texAttributes = texAttributes;
		texAttributes.setSize(getSize().scale(GameState.SCALE));
		
		bd = new BodyDef();
		bd.position.set(position.x, position.y);
		bd.type = type;
		bd.gravityScale = gravity;

		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;

		body = model.getWorld().createBody(bd);
		body.createFixture(fd);
	}

	public PhysicsObject(Model model, Vector position, BodyType type, Shape shape, TextureAttributes texAttributes) {
		this(model, position, type, shape, 
				DEFAULT_DENSITY, DEFAULT_FRICTION, DEFAULT_RESTITUTION, DEFAULT_LINEAR_DAMPING, 
				texAttributes);
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
			
			for (Vec2 vertex : shape.getVertices()) {
				c1.x = Math.min(c1.x, vertex.x);
				c2.x = Math.max(c2.x, vertex.x);
				c1.y = Math.min(c1.y, vertex.y);
				c2.y = Math.max(c2.y, vertex.y);
			}

			return c2.minus(c1);
		} else {
			float dimension = shape.getRadius() * 2;
			return new Vector(dimension, dimension);
		}
	}
	
	public Model getModel() {
		return this.model;
	}

	public float getRotation() {
		return (float) (body.getAngle() * 180 / Math.PI);
	}

	public Body getBody() {
		return body;
	}
	
	/**
	 * Creates a new box with the specified width and height
	 */
	protected static Shape makeBoxShape(float w, float h) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w, h);
		return shape;
	}

	/**
	 * Creates a new box with the specified width and height
	 */
	protected static Shape makeCircle(float r) {
		CircleShape shape = new CircleShape();
		shape.m_radius = r;
		return shape;
	}
}
