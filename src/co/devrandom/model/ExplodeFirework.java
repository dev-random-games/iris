package co.devrandom.model;


public class ExplodeFirework extends TimedEvent {
	private Model model;
	private Firework firework;

	public ExplodeFirework(Model model, Firework firework, long timeUntil) {
		super(model, timeUntil);

		this.model = model;
		this.firework = firework;
	}

	@Override
	public void onTrigger() {
		firework.explode();
	}
}
