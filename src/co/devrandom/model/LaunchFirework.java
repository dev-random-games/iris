package co.devrandom.model;

public class LaunchFirework extends TimedEvent {
	private Firework firework;

	public LaunchFirework(Model model, Firework firework, long timeUntil) {
		super(model, timeUntil);

		this.firework = firework;
	}

	@Override
	public void onTrigger() {
		firework.launch();
	}
}
