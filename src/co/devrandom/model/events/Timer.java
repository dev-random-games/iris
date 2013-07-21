package co.devrandom.model.events;

public class Timer implements Triggerable {
	private long timeCreated;
	private long timeTotal;
	
	public Timer(long timeUntil) {
		this.timeTotal = timeUntil;
		timeCreated = System.currentTimeMillis();
	}

	public boolean isTriggered() {
		return (System.currentTimeMillis() - timeCreated) < timeTotal;
	}

	public void onTrigger() {

	}
}
