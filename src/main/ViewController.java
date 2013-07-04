package main;

import org.lwjgl.LWJGLException;
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
			
			// render OpenGL here
			
			Display.update();
		}
		
		Display.destroy();
	}
}
