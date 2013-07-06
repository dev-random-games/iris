package co.devrandom.model.objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.main.GameState;
import co.devrandom.model.Model;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;

public class PhysicsObject {
	private Model model;
	private TextureAttributes texAttributes;
	private BodyDef bd;
	private Body body;

	public PhysicsObject(Model model, BodyDef bd, FixtureDef[] fixtures, TextureAttributes texAttributes) {
		this.model = model;

		body = model.getWorld().createBody(bd);
		
		for (FixtureDef fd : fixtures) {
			body.createFixture(fd);	
		}
		
		this.texAttributes = texAttributes;
		texAttributes.setSize(getSize().scale(GameState.SCALE));
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

	private Vector getSize() {
		return getSize(body.getFixtureList().getShape());
	}
	
	/**
	 * Attempts to find the minimum bounding box of the physics shape.
	 */
	private Vector getSize(Shape shape) {
		if (shape.m_type == ShapeType.POLYGON) {
			// If the shape is a polygon, find the minimum bounding box.
			PolygonShape polyShape = (PolygonShape) shape;
			
			Vec2 vertex0 = polyShape.getVertex(0);
			Vector c1 = new Vector(vertex0.x, vertex0.y);
			Vector c2 = new Vector(c1);

			for (Vec2 vertex : polyShape.getVertices()) {
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

	public BodyDef getBD() {
		return bd;
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
