package co.devrandom.model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.util.Vector;

public class ExplodeFirework extends TimedEvent {
	private Model model;
	private PhysicsObject firework;
	
	public ExplodeFirework(long timeUntil, Model model, PhysicsObject firework) {
		super(model, timeUntil);
		
		this.model = model;
		this.firework = firework;
	}
	
	@Override
	public void onTrigger() {
		for (int i = 0; i < 10; i++) {
			PolygonShape cs = new PolygonShape();
			cs.setAsBox(1, 1);
			
			model.addGameObject((new PhysicsObject(model.getWorld(), new Vector(firework.getBody().getPosition().x, firework.getBody().getPosition().y), BodyType.DYNAMIC, cs)));
		}
	}
}
