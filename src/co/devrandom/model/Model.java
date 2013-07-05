package co.devrandom.model;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;
import co.devrandom.util.Vector;


public class Model implements Runnable{	
	private static final int SLEEP_TIME = 10;
	private static final Vec2 DEFAULT_GRAVITY = new Vec2(0.0f, 9.8f);

	private ArrayList<PhysicsObject> gameObjects;
	private World world;

	public Model() {
		gameObjects = new ArrayList<>();
		world = new World(DEFAULT_GRAVITY);
	}

	public void run() {
		PolygonShape cs = new PolygonShape();
		cs.setAsBox(1f, 1f);  
		
		for (int i = 0; i < 100; i++) {
			gameObjects.add(new PhysicsObject(world, new Vector(1, 9-i*2), BodyType.DYNAMIC, cs));
		}
		
		//gameObjects.add(new PhysicsObject(world, new Vector(1, 1), BodyType.DYNAMIC, cs));
		//gameObjects.add(new PhysicsObject(world, new Vector(1.5f, 2), BodyType.DYNAMIC, cs));
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(10, 10);
		
		PhysicsObject po = new PhysicsObject(world, new Vector(0, 20), BodyType.STATIC, ps);
		po.getBody().createFixture(ps, 0);
		
		gameObjects.add(po);
		
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()){				
				world.step(GameState.TIME_STEP, GameState.VELOCITY_ITERATIONS, GameState.POSITION_ITERATIONS);
			}
		}
	}

	public ArrayList<PhysicsObject> getGameObjects() {
		return gameObjects;
	}
}
