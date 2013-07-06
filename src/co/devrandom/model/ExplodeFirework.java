package co.devrandom.model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class ExplodeFirework extends TimedEvent {
	private Model model;
	private PhysicsObject firework;

	public ExplodeFirework(Model model, PhysicsObject firework, long timeUntil) {
		super(model, timeUntil);

		this.model = model;
		this.firework = firework;
	}

	@Override
	public void onTrigger() {
		for (int i = 0; i < 10; i++) {
			PolygonShape cs = new PolygonShape();
			cs.setAsBox(1, 1);

			TextureAttributes attr = new TextureAttributes(TextureList.DOT);

			model.addGameObject((new PhysicsObject(model.getWorld(), new Vector(firework.getBody()
					.getPosition().x, firework.getBody().getPosition().y), BodyType.DYNAMIC, cs,
					attr)));
		}
	}
}
