package main;

public class GameState {
	private static final GameState gameState = new GameState(State.MAIN_MENU);
	
	private enum State {
		RUNNING,
		PAUSED,
		MAIN_MENU;
	}
	
	private static State currentState;
	
	private GameState(State initializeState) {
		currentState = initializeState;
	}
	
	public static GameState getInstance() {
		return gameState;
	}
	
	public static boolean isModelRunning() {
		return currentState == State.RUNNING;
	}
}
