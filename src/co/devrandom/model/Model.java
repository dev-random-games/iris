package co.devrandom.model;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;


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
		CircleShape cs = new CircleShape();
		cs.m_radius = 5f;
		
		gameObjects.add(new PhysicsObject(world, 1, 1, BodyType.DYNAMIC, cs, 0.5f, 0.3f, 0.5f));
		gameObjects.add(new PhysicsObject(world, 2, 1, BodyType.DYNAMIC, cs, 0.5f, 0.3f, 0.5f));
		
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()){				
				world.step(GameState.TIME_STEP, GameState.VELOCITY_ITERATIONS, GameState.POSITION_ITERATIONS);

				for (PhysicsObject obj : gameObjects){
					//						System.out.printf("%4.2f %4.2f \n", obj.getBody().getPosition().x, obj.getBody().getPosition().y);
					obj.setRotation(obj.getRotation() + 1);
				}
			}
		}
	}

	public ArrayList<PhysicsObject> getGameObjects() {
		return gameObjects;
	}
}
