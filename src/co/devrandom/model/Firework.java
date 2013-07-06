package co.devrandom.model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class Firework extends PhysicsObject {
	private static final float DENSITY = 1.0f;
	private static final float FRICTION = 0.3f;
	private static final float RESTITUTION = 0.5f;
	private static final Shape SHAPE = makeBoxShape(0.02f, 0.3f);
	private static final TextureAttributes TEX_ATTRIBUTES = new TextureAttributes(
			new TextureList[] { TextureList.SMILEY_MOUTH_1, TextureList.SMILEY_MOUTH_2, 
					TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_4,
			TextureList.SMILEY_MOUTH_4, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_2,
			TextureList.SMILEY_MOUTH_1 });
	
	public Firework(World world, Vector position) {
		super(world, position, BodyType.DYNAMIC, SHAPE, DENSITY, FRICTION, RESTITUTION, TEX_ATTRIBUTES);
	}
	
	public void launch() {
		this.getBody().applyForceToCenter(new Vec2(0f, -10f));
	}
	
	private static Shape makeBoxShape(float w, float h) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w, h);
		return shape;
	}
}
