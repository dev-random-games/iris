package co.devrandom.vc;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import co.devrandom.audio.AudioList;
import co.devrandom.main.GameState;
import co.devrandom.model.Model;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.ray.Ray;
import co.devrandom.model.objects.ray.RayCaster;
import co.devrandom.util.Vector;
import co.devrandom.vc.controller.KeyPress;
import co.devrandom.vc.view.TextureAttributes;
import co.devrandom.vc.view.TextureList;
import co.devrandom.vc.view.fonts.FontList;

public class ViewController implements Runnable {
	Model model;

	Vector cameraLocation;
	float cameraZoom; // 1 standard pixel = cameraZoom pixels at current scale.
						// Thus, smaller number means farther away.

	/*
	 * FPS stuff
	 */
	private long lastFrameTime;
	private float fps;
	private int numFrames;
	private static final int FPS_CHECK_TIME = 1000;
	boolean fpsMeterVisible = true;
	
	private int main_menu;

	public ViewController(Model model) {
		this.model = model;
		cameraLocation = new Vector();
		cameraZoom = .05f;
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

		initGL();
		AudioList.initAudio();
		FontList.initFonts();
		loadTextures();

		main_menu = 0;

		while (!Display.isCloseRequested()) {

			TextureList.newFrame();
			setCamera();

			if (GameState.isMainMenu()) {
				handleMainMenuInput();

				TextureAttributes textures;

				if (main_menu == 0)
					textures = new TextureAttributes(TextureList.MAIN_MENU,
							new Vector(2048f, 2048f));
				else
					textures = new TextureAttributes(TextureList.GRAVY,
							new Vector(2048f, 2048f));

				renderTexture(textures, new Vector(GameState.WINDOW_WIDTH / 2f,
						GameState.WINDOW_HEIGHT / 2f), 0f);

			} else {

				glPushMatrix();

				/*
				 * Anything controlled by the camera goes below.
				 */

				if (GameState.isPaused()) {
					handlePausedInput();
				} else {
					handleGameInput();
				}

				Vector playerPos = model.getPlayer().getPosition();

				cameraLocation.addInPlace(cameraLocation.minus(playerPos.scale(-cameraZoom)).scale(
						-1f));

				// Put all world matrix transforms here.
				glTranslatef((float) GameState.WINDOW_WIDTH / 2 + cameraLocation.x,
						(float) GameState.WINDOW_HEIGHT / 2 + cameraLocation.y, 0);
				glScalef(cameraZoom, cameraZoom, 0);

				/*
				 * Draw all gameObjects;
				 */
				for (PhysicsObject physicsObject : model.getGameObjects()) {
					if (physicsObject.equals(model.getPlayer())) {
						this.renderTexture(physicsObject.getTexAttributes(), playerPos,
								physicsObject.getRotation());
					} else {
						this.renderTexture(physicsObject.getTexAttributes(),
								physicsObject.getPosition(), physicsObject.getRotation());
					}
				}

				Vector origin = playerPos.scale(1.0f / (GameState.SCALE));
				Vector ray = this.getMousePositionInGame().minus(playerPos);

				Ray collision = RayCaster.getClosestIntersect(origin, ray, model.getPlayer(),
						model.getGameObjects());

				if (collision != null) {
					this.renderLine(playerPos, collision.getEnd().scale(GameState.SCALE));
					/*
					 * Pushing and pulling on other objects with the gun.
					 */
					if (GameState.isModelRunning()) {
						Vector collisionDir = collision.getEnd().minus(collision.getOrigin())
								.norm();
						if (Mouse.isButtonDown(0)) {
							collision.getDest().applyForce(collisionDir.scale(1),
									collision.getEnd());
							model.getPlayer().applyForce(collisionDir.scale(-1),
									collision.getOrigin());
							model.getPlayer().friction(true);
						} else if (Mouse.isButtonDown(1)) {
							collision.getDest().applyForce(collisionDir.scale(-1),
									collision.getEnd());
							model.getPlayer().applyForce(collisionDir.scale(1),
									collision.getOrigin());
							model.getPlayer().friction(true);
						} else {
							model.getPlayer().friction(false);
						}
					}
				} else {
					this.renderLine(playerPos, ray.scale(1000));
				}

				glPopMatrix();

				/*
				 * Anything not controlled by the camera goes below.
				 */

				if (fpsMeterVisible) {
					long currentTime = System.currentTimeMillis();
					long timeDif = currentTime - lastFrameTime;

					if (timeDif > FPS_CHECK_TIME) {
						fps = (float) numFrames / timeDif * 1000;
						lastFrameTime = currentTime;
						numFrames = 0;
					}

					numFrames++;
					FontList.HEADER.getFont().drawString(10, 10, String.format("FPS: %.1f", fps),
							Color.black);
				}

				if (GameState.isPaused()) {
					renderTexture(new TextureAttributes(TextureList.PAUSE, new Vector(64, 64)),
							new Vector(GameState.WINDOW_WIDTH - 42, 42), 0);
				}

				if (GameState.isPaused())
					renderTexture(new TextureAttributes(TextureList.PAUSE, new Vector(64, 64)),
							new Vector(GameState.WINDOW_WIDTH - 42, 42), 0);

				Display.sync(GameState.FPS);
			}

			SoundStore.get().poll(0);

			Display.update();
		}

		Display.destroy();
		System.exit(0);
	}

	private void renderTexture(TextureAttributes texAttr, Vector position, float rotation) {
		if (texAttr != null) {
			glEnable(GL_TEXTURE_2D);
			texAttr.checkAnimation();
			texAttr.textures[texAttr.currentFrame].bindTexture();
			glColor4f(texAttr.r, texAttr.g, texAttr.b, texAttr.a);
			glPushMatrix();
			{
				glTranslatef((float) position.x, (float) position.y, 0);
				glRotatef(rotation, 0, 0, 1);

				glScalef(texAttr.dim.x, texAttr.dim.y, 0);

				Vector start = texAttr.getStartTexPosition();
				Vector end = texAttr.getEndTexPosition();

				glBegin(GL_QUADS);
				{
					glTexCoord2f((float) start.x, (float) start.y);
					glVertex2f(-.5f, -.5f);
					glTexCoord2f((float) end.x, (float) start.y);
					glVertex2f(.5f, -.5f);
					glTexCoord2f((float) end.x, (float) end.y);
					glVertex2f(.5f, .5f);
					glTexCoord2f((float) start.x, (float) end.y);
					glVertex2f(-.5f, .5f);
				}
				glEnd();
			}
			glPopMatrix();
			glDisable(GL_TEXTURE_2D);
		}
	}

	private void renderLine(Vector start, Vector end) {
		// start.scaleInPlace(GameState.SCALE);
		// end.scaleInPlace(GameState.SCALE);
		GL11.glColor3f(0, 0, 0);
		glBegin(GL_LINE_LOOP);
		glVertex2f(start.x, start.y);
		glVertex2f(end.x, end.y);
		glEnd();
	}

	private void handleMainMenuInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == KeyPress.START.getKeyID()) {
					if (main_menu == 0)
						main_menu = 1;
					else
						GameState.startGame();
				}
			}
		}
	}

	private void handleGameInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == KeyPress.PAUSE.getKeyID()) {
					GameState.pauseUnpause();
				}
			}
		}

		if (KeyPress.FORWARD.isDown()) {
			model.getPlayer().moveForward();
		}
		if (KeyPress.BACKWARD.isDown()) {
			model.getPlayer().moveBackward();
		}
		if (KeyPress.LEFT.isDown()) {
			model.getPlayer().moveLeft();
		}
		if (KeyPress.RIGHT.isDown()) {
			model.getPlayer().moveRight();
		}
	}

	private void handlePausedInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == KeyPress.PAUSE.getKeyID()) {
					GameState.pauseUnpause();
				}
			}
		}
	}

	private void initGL() {
		GL11.glViewport(0, 0, GameState.WINDOW_WIDTH, GameState.WINDOW_HEIGHT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
		GL11.glClearColor(0.99f, 0.99f, 0.99f, 1f);
		GL11.glLineWidth(3);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_LINEAR_MIPMAP_LINEAR);
	}

	private void loadTextures() {
		for (TextureList texture : TextureList.values()) {
			texture.initializeTexture();
		}
	}

	/**
	 * Initialize the camera for the next frame of OpenGL. Positions the center
	 * of the camera at controller.getCameraPosition().
	 */
	private void setCamera() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0f, GameState.WINDOW_WIDTH, GameState.WINDOW_HEIGHT, 0.0f, 0.0f, 1.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	private Vector getMousePositionInGame() {
		float x = Mouse.getX();
		float y = Mouse.getY();

		return cameraLocation
				.scale(-1)
				.plus(new Vector(x, -y).minus(new Vector(GameState.WINDOW_WIDTH / 2,
						-GameState.WINDOW_HEIGHT / 2))).scale(1 / this.cameraZoom);
	}
}
