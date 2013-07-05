package co.devrandom.model;

public interface Triggerable {	
	
	/**
	 * Returns whether or not the event should be triggered at the moment
	 */
	public boolean isTriggered();
	
	/**
	 * Called when the event is triggered
	 */
	public void onTrigger();
}
