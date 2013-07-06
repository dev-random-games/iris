package co.devrandom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;
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
//		Firework fire0 = new Firework(this, new Vector(-5, 0));
//		Firework fire1 = new Firework(this, new Vector(0, 0));
//		Firework fire2 = new Firework(this, new Vector(5, 0));
		
		for (int x = -30; x <= 30; x += 10){
			Firework fire = new Firework(this, new Vector(x, 0));
			events.add(new LaunchFirework(this, fire, 9000 + x * 300));
			physicsObjects.add(fire);
			events.add(new ExplodeFirework(this, fire, 10000 + x * 300));
		}
		
//		events.add(new LaunchFirework(this, fire0, 0));
//		events.add(new LaunchFirework(this, firei1, 0));
//		events.add(new LaunchFirework(this, fire2, 0));
		
//		physicsObjects.add(fire0);
//		physicsObjects.add(fire1);
//		physicsObjects.add(fire2);
//		
//		events.add(new ExplodeFirework(this, fire0, 500));
//		events.add(new ExplodeFirework(this, fire1, 600));
//		events.add(new ExplodeFirework(this, fire2, 700));
		
		TextureAttributes smile = new TextureAttributes(new TextureList[] { TextureList.SMILEY_MOUTH_1,
				TextureList.SMILEY_MOUTH_2, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_4,
				TextureList.SMILEY_MOUTH_4, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_2,
				TextureList.SMILEY_MOUTH_1 });
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(60, 10);
		
		PhysicsObject po = new PhysicsObject(this, new Vector(0, 10), BodyType.STATIC, ps, new TextureAttributes(TextureList.DOT));
		po.getBody().createFixture(ps, 0);
		
		physicsObjects.add(po);
		
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
