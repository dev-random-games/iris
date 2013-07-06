package co.devrandom.model.events;

import co.devrandom.model.Model;
import co.devrandom.model.objects.Firework;

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
