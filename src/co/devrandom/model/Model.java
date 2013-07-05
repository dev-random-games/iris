package co.devrandom.model;

import java.util.ArrayList;

import co.devrandom.main.GameState;


public class Model implements Runnable{
	
	private static final int SLEEP_TIME = 10;

	private ArrayList<GameObject> gameObjects;
	
	public Model() {
		gameObjects = new ArrayList<>();
	}

	public void run() {
		gameObjects.add(new GameObject());
		
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()){
				for (GameObject obj : gameObjects){
					obj.setRotation(obj.getRotation() + 1);
				}
			}
		}
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
}
