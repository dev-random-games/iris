package co.devrandom.model.events;

public class Timer implements Triggerable {
	private long timeCreated;
	private long timeTotal;
	private Triggerable trigger;
	
	public Timer(long timeUntil, Triggerable trigger) {
		this.timeTotal = timeUntil;
		timeCreated = System.currentTimeMillis();
		this.trigger = trigger;
	}

	public boolean isTriggered() {
		return (System.currentTimeMillis() - timeCreated) < timeTotal;
	}

	public void trigger() {
		trigger.trigger();
	}
}
