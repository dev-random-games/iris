package main;

import org.lwjgl.input.Keyboard;

public enum KeyPress {
	FORWARD(Keyboard.KEY_W),
	BACKWARD(Keyboard.KEY_S),
	RIGHT(Keyboard.KEY_D),
	LEFT(Keyboard.KEY_A);
	
	private int keyID;
	
	private KeyPress(int keyID){
		this.keyID = keyID;
	}
	
	public int getKeyID(){
		return keyID;
	}
}
