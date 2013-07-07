package co.devrandom.vc.controller;

import org.lwjgl.input.Keyboard;

public enum KeyPress {
	FORWARD(Keyboard.KEY_W),
	BACKWARD(Keyboard.KEY_S),
	RIGHT(Keyboard.KEY_D),
	LEFT(Keyboard.KEY_A),
	SPRINT(Keyboard.KEY_LSHIFT),
	PING(Keyboard.KEY_P),
	PAUSE(Keyboard.KEY_SPACE),
	START(Keyboard.KEY_SPACE);
	
	private int keyID;
	
	private KeyPress(int keyID){
		this.keyID = keyID;
	}
	
	public int getKeyID(){
		return keyID;
	}
	
	public boolean isDown() {
		return Keyboard.isKeyDown(getKeyID());
	}
}
