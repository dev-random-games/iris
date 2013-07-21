package co.devrandom.model.events;

import co.devrandom.model.Model;
import co.devrandom.model.objects.PhysicsObject;

public class DestroyPhysicsObject implements Triggerable{

	private PhysicsObject object;
	private Model model;
	
	public DestroyPhysicsObject(Model model, PhysicsObject object) {
		this.object = object;
		this.model = model;
	}

	public void trigger() {
		model.removePhysicsObject(object);
	}

	@Override
	public boolean isTriggered() {
		return model.getGameObjects().contains(object);
	}
}
