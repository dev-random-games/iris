package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class ViewController implements Runnable{
	
	Model model;
	
	public ViewController(Model model){
		this.model = model;
	}
	
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// init OpenGL here
		
		while (!Display.isCloseRequested()) {
			

			handleInput();
			
			// render OpenGL here
			
			Display.update();
		}
		
		Display.destroy();
	}
	
	public void handleInput() {
		while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == KeyPress.FORWARD.getKeyID()) {
		        	System.out.println("forward");
		        } else if (Keyboard.getEventKey() == KeyPress.BACKWARD.getKeyID()) {
		        	System.out.println("backward");
		        } else if (Keyboard.getEventKey() == KeyPress.LEFT.getKeyID()) {
		        	System.out.println("left");
		        } else if (Keyboard.getEventKey() == KeyPress.RIGHT.getKeyID()) {
		        	System.out.println("right");
		        }
		    } else {
		        if (Keyboard.getEventKey() == KeyPress.FORWARD.getKeyID()) {
		        	System.out.println("forward released");
		        } else if (Keyboard.getEventKey() == KeyPress.BACKWARD.getKeyID()) {
		        	System.out.println("backward released");
		        } else if (Keyboard.getEventKey() == KeyPress.LEFT.getKeyID()) {
		        	System.out.println("left released");
		        } else if (Keyboard.getEventKey() == KeyPress.RIGHT.getKeyID()) {
		        	System.out.println("right released");
		        }
		    }
		}	
	}
}
