package co.devrandom.model.events;

import co.devrandom.model.Model;
import co.devrandom.model.objects.PhysicsObject;

public class DestroyPhysicsObject extends TimedEvent{

	private PhysicsObject object;
	
	public DestroyPhysicsObject(Model model, long timeUntil, PhysicsObject object) {
		super(model, timeUntil);
		
		this.object = object;
	}

	public void onTrigger() {
		getModel().removePhysicsObject(object);
	}
}
