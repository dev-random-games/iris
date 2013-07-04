package co.devrandom.model;

import java.util.ArrayList;

import co.devrandom.main.GameState;


public class Model implements Runnable{
	
	private static final int SLEEP_TIME = 10;

	protected ArrayList<GameObject> gameObjects;
	
	public Model() {
		gameObjects = new ArrayList<>();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()){
				
			}
		}
	}

}
