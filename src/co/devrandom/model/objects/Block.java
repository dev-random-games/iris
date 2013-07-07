package co.devrandom.model.objects;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.model.Model;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;
import co.devrandom.vc.view.TextureAttributes;
import co.devrandom.vc.view.TextureList;

public class Block extends PhysicsObject {
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 1f;
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
	
	public Block(Model model, Vector position, Vector size) {
		super(model, BodyDefBuilder.setPosition(BD, position),
				FD.shape(PhysicsObject.makeBoxShape(size)).build(),
				 new TextureAttributes(TextureList.BLOCK));
	}
}
