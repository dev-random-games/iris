package co.devrandom.model;

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
