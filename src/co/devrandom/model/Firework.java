package co.devrandom.model;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.audio.AudioList;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class Firework extends PhysicsObject {
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 1f;
	private static final float RESTITUTION = 0.0001f;
	private static final float GRAVITY = 1f;
	
	private static final Shape SHAPE = PhysicsObject.makeBoxShape(0.1f, 1f);

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
	
	private static final TextureAttributes TEX_ATTRIBUTES = new TextureAttributes(
			TextureList.DOT);

	public Firework(Model model, Vector position) {
		super(model, BodyDefBuilder.setPosition(BD, position), new FixtureDef[] { FD }, TEX_ATTRIBUTES);
	}

	public void launch() {
		AudioList.SWISH.getAudio().playAsSoundEffect(1f, 1f, false);

		this.getBody().applyForceToCenter(new Vec2(0f, -70f));
	}

	public void explode() {
		AudioList.SMALL_EXPLOSION.getAudio().playAsSoundEffect(1f, 1f, false);

		Vec2 pos = this.getBody().getPosition();

		for (int i = 0; i < 50; i++) {
			Vector rand = new Vector((float) Math.random() - 0.5f, (float) Math.random() - 0.5f).scale(20.0f);
			Particle part = new Particle(this.getModel(), new Vector(pos.x, pos.y).plus(rand), (long) (Math.random() * 1000 +  500));

			part.getBody().applyForceToCenter(rand.toVec2());

			this.getModel().addPhysicsObject(part);
		}

		this.getModel().getWorld().destroyBody(this.getBody());
		this.getModel().removePhysicsObject(this);
	}
}
