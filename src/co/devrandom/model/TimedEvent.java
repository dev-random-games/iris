package co.devrandom.model;

public class TimedEvent implements Triggerable, Comparable<TimedEvent> {
	private long start;
	private long end;
	
	public TimedEvent(long timeUntil) {
		start = System.currentTimeMillis();
		end = start + timeUntil;
	}
	
	public long getEnd() {
		return end;
	}
	
	@Override
	public int compareTo(TimedEvent other) {
		return (int) (this.getEnd() - other.getEnd());
	}

	@Override
	public void onTrigger() {

	}

	@Override
	public boolean isTriggered() {
		return System.currentTimeMillis() >= end;
	}
}
