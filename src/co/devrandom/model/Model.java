package co.devrandom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

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
	private static final Vec2 DEFAULT_GRAVITY = new Vec2(0.0f, 9.8f);
	
	private long elapsedTime;
	private long lastFrame;
	private List<PhysicsObject> gameObjects;
	private World world;
	private PriorityQueue<TimedEvent> events;

	public Model() {
		elapsedTime = 0l;
		lastFrame = System.currentTimeMillis();
		gameObjects = Collections.synchronizedList(new ArrayList<PhysicsObject>());
		world = new World(DEFAULT_GRAVITY);
		events = new PriorityQueue<TimedEvent>();
	}

	public void run() {
		PolygonShape cs = new PolygonShape();
		cs.setAsBox(.3f, .3f);  
		
		TextureAttributes smile = new TextureAttributes(new TextureList[] { TextureList.SMILEY_MOUTH_1,
				TextureList.SMILEY_MOUTH_2, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_4,
				TextureList.SMILEY_MOUTH_4, TextureList.SMILEY_MOUTH_3, TextureList.SMILEY_MOUTH_2,
				TextureList.SMILEY_MOUTH_1 });
		
//		for (int i = 0; i < 100; i++) {
//			gameObjects.add(new PhysicsObject(world, new Vector(1, -i*2), BodyType.DYNAMIC, cs, smile.clone()));
//		}
		
//		PhysicsObject firework = new PhysicsObject(world, new Vector(4, 1), BodyType.DYNAMIC, cs, smile.clone());
//		firework.getBody().applyForceToCenter(new Vec2(0f, -300f));
//		
		Firework fire0 = new Firework(world, new Vector(-1, 0));
		Firework fire1 = new Firework(world, new Vector(0, 0));
		Firework fire2 = new Firework(world, new Vector(1, 0));
		
		fire0.launch();
		fire1.launch();
		fire2.launch();
		
		gameObjects.add(fire0);
		gameObjects.add(fire1);
		gameObjects.add(fire2);
		
//		events.add(new ExplodeFirework(this, firework, 2000));
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(10, 10);
		
		PhysicsObject po = new PhysicsObject(world, new Vector(0, 10), BodyType.STATIC, ps, smile.clone());
		po.getBody().createFixture(ps, 0);
		
		gameObjects.add(po);
		
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
	
	public World getWorld() {
		return world;
	}
	
	public ArrayList<PhysicsObject> getGameObjects() {
		return new ArrayList<PhysicsObject>(gameObjects);
	}
	
	public void addGameObject(PhysicsObject object) {
		this.gameObjects.add(object);
	}
	
	public void addTimedEvent(TimedEvent event) {
		this.events.add(event);
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}
