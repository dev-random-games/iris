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
import co.devrandom.model.events.ExplodeFirework;
import co.devrandom.model.events.LaunchFirework;
import co.devrandom.model.events.TimedEvent;
import co.devrandom.model.objects.Firework;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class Model implements Runnable{	
	private static final int SLEEP_TIME = 10;
	
	private long elapsedTime;
	private long lastFrame;
	private List<PhysicsObject> physicsObjects;
	private World world;
	private PriorityBlockingQueue<TimedEvent> events;

	public Model() {
		elapsedTime = 0l;
		lastFrame = System.currentTimeMillis();
		physicsObjects = Collections.synchronizedList(new ArrayList<PhysicsObject>());
		world = new World(GameState.DEFAULT_GRAVITY);
		events = new PriorityBlockingQueue<TimedEvent>();
	}

	public void run() { 
		for (int x = -30; x <= 30; x += 10){
			Firework fire = new Firework(this, new Vector(x, 0));
			events.add(new LaunchFirework(this, fire, 9000 + x * 300));
			physicsObjects.add(fire);
			events.add(new ExplodeFirework(this, fire, 10000 + x * 300));
		}

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(60, 10);
		
		BodyDef groundBody = new BodyDefBuilder().position(new Vector(0, 10))
				.type(BodyType.STATIC)
				.build();
		
		FixtureDef groundFixture = new FixtureDefBuilder().shape(ps)
				.build();
		
		PhysicsObject ground = new PhysicsObject(this, groundBody,
				new FixtureDef[] { groundFixture },
				new TextureAttributes(TextureList.DOT));
		
		physicsObjects.add(ground);
		
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
				world.step(GameState.TIME_STEP, GameState.VELOCITY_ITERATIONS, GameState.POSITION_ITERATIONS);
			
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
