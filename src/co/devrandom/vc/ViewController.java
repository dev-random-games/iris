package co.devrandom.vc;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import co.devrandom.main.GameState;
import co.devrandom.model.Model;

public class ViewController implements Runnable{
	Model model;
	
	public ViewController(Model model){
		this.model = model;
	}
	
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(GameState.WINDOW_WIDTH, GameState.WINDOW_HEIGHT));
			Display.setTitle(GameState.NAME);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Initialize OpenGL and LWJGL
		{
			glViewport(0, 0, GameState.WINDOW_WIDTH, GameState.WINDOW_HEIGHT);
			glEnable(GL_DEPTH_TEST);
			glDepthFunc(GL_LEQUAL);
			//glShadeModel(GL_SMOOTH);
			glEnable(GL_LINE_SMOOTH);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glEnable(GL_BLEND);
			glEnable(GL_ALPHA_TEST);
			glAlphaFunc(GL_GREATER, 0.1f);
			glEnable(GL_TEXTURE_2D);
			glClearColor(0, 0, 0, 1);
			glLineWidth(3);
			glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST );
		}
		
		while (!Display.isCloseRequested()) {
			setCamera();

			handleInput();
			
			glPushMatrix();
			
			// Put all world matrix transforms here.
			glTranslatef((float) GameState.WINDOW_WIDTH / 2, (float) GameState.WINDOW_HEIGHT / 2, 0);
			glScalef(.5f, .5f, .5f);
			
			TextureList.EVIL_SMILEY.bindTexture();
			
			//glColor3f(0, 0, 0);
			
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(0, 0);
			glTexCoord2f(1,0);
			glVertex2f(100, 0);
			glTexCoord2f(1,1);
			glVertex2f(100, 100);
			glTexCoord2f(0,1);
			glVertex2f(0, 100);
			glEnd();
			
			glPopMatrix();
			
			// render OpenGL here
			
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
	    glOrtho(0.0f, GameState.WINDOW_WIDTH, GameState.WINDOW_HEIGHT, 0.0f, 0.0f, 1.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
}
