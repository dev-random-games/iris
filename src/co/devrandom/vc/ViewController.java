package co.devrandom.vc;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import co.devrandom.model.Model;

public class ViewController implements Runnable{
	
	Model model;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;	
	
	public ViewController(Model model){
		this.model = model;
	}
	
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Initialize OpenGL and LWJGL
		{
			GL11.glViewport(0, 0, WIDTH, HEIGHT);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
			GL11.glClearColor(1, 1, 1, 1);
			GL11.glLineWidth(3);
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR );
		}
		
		while (!Display.isCloseRequested()) {
			setCamera();

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
	
	/**
	 * Initialize the camera for the next frame of OpenGL. Positions the center of the camera
	 * at controller.getCameraPosition().
	 */
	private void setCamera() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// TODO: Matrix transforms here
	    GL11.glOrtho(0.0f, WIDTH, HEIGHT, 0.0f, 0.0f, 1.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
}
