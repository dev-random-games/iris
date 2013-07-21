package co.devrandom.model.events;

public class EventHandler {
	private Event event;
	private Triggerable trigger;
	
	public EventHandler(Event event, Triggerable trigger) {
		this.event = event;
		this.trigger = trigger;
	}
	
	public boolean checkEvent() {
		if (trigger.isTriggered()){
			event.trigger();
			return true;
		} else
			return false;
	}
}