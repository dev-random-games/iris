package co.devrandom.model.events;

import co.devrandom.model.Model;
import co.devrandom.model.objects.Firework;

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
