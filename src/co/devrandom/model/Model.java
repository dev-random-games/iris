package co.devrandom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;
import co.devrandom.model.events.TimedEvent;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureListBuilder;
import co.devrandom.util.Vector;
import co.devrandom.vc.view.TextureAttributes;
import co.devrandom.vc.view.TextureList;

public class Model implements Runnable {
	private static final int SLEEP_TIME = 10;

	private long elapsedTime;
	private long lastFrame;
	private List<PhysicsObject> physicsObjects;
	private World world;
	private PriorityBlockingQueue<TimedEvent> events;
	private Player player;

	public Model() {
		elapsedTime = 0l;
		lastFrame = System.currentTimeMillis();
		physicsObjects = Collections.synchronizedList(new ArrayList<PhysicsObject>());
		world = new World(GameState.DEFAULT_GRAVITY);
		events = new PriorityBlockingQueue<TimedEvent>();
	}

	public void run() {

		{
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 20; y++) {
					BodyDef weightBody = new BodyDefBuilder().position(new Vector( x * .5f + y * .1f, -1 -y * .25f))
							.type(BodyType.DYNAMIC).build();
				
					FixtureDef[] weightFixtures = new FixtureListBuilder().shape(new float[] {-8f, -8f, -16f, -8f, -16f, 8f, -8f, 8f})
																		  .shape(new float[] {8f, -8f, 16f, -8f, 16f, 8f, 8f, 8f})
																		  .shape(new float[] {-8f, -2f, 8f, -2f, 8f, 2f, -8f, 2f})
																		  .size(new Vector(.25f, .125f)).density(10).build();
					
					PhysicsObject weight = new PhysicsObject(this, weightBody, weightFixtures,
							new TextureAttributes(TextureList.WEIGHT));
		
					physicsObjects.add(weight);
				}
			}
		}

		player = new Player(this, new Vector(0, 0));		
		physicsObjects.add(player);
		
		while (true) {
			lastFrame = System.currentTimeMillis();

			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()) {
				checkEvents();
				try {
					world.step(GameState.TIME_STEP, GameState.VELOCITY_ITERATIONS,
							GameState.POSITION_ITERATIONS);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

				elapsedTime += System.currentTimeMillis() - lastFrame;
			}
		}
	}

	public void checkEvents() {
		synchronized (events) {
			Iterator<TimedEvent> i = events.iterator();

			while (i.hasNext()) {
				TimedEvent currentEvent = (TimedEvent) i.next();

				if (currentEvent.isTriggered()) {
					currentEvent.onTrigger();
					i.remove();
				} else {
					break;
				}
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public ArrayList<PhysicsObject> getGameObjects() {
		return new ArrayList<PhysicsObject>(physicsObjects);
	}

	public void addPhysicsObject(PhysicsObject object) {
		this.physicsObjects.add(object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		this.physicsObjects.remove(object);
		world.destroyBody(object.getBody());
	}

	public void addTimedEvent(TimedEvent event) {
		this.events.add(event);
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}
