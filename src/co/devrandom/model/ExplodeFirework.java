package co.devrandom.model;


public class ExplodeFirework extends TimedEvent {
	private Firework firework;

	public ExplodeFirework(Model model, Firework firework, long timeUntil) {
		super(model, timeUntil);

		this.firework = firework;
	}

	@Override
	public void onTrigger() {
		firework.explode();
	}
}
