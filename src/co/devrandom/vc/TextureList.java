package co.devrandom.vc;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import co.devrandom.main.GameState;
import co.devrandom.util.Vector;

public enum TextureList {
	/*
	 * Put all textures needed in game right here!
	 */
	EVIL_SMILEY("evil-smiley.png", false),
	DOT("dot.png", false),
	SPARKLE("sparkle.png", false),
	SMILEY_MOUTH_1("smiley-mouth.png", false, new Vector(0, 0), new Vector(16, 16)),
	SMILEY_MOUTH_2("smiley-mouth.png", false, new Vector(16, 0), new Vector(32, 16)),
	SMILEY_MOUTH_3("smiley-mouth.png", false, new Vector(0, 16), new Vector(16, 32)),
	SMILEY_MOUTH_4("smiley-mouth.png", false, new Vector(16, 16), new Vector(32, 32));

	private String filePath;
	private Texture texture;
	private boolean smooth;
	private Vector start, end;
	private Vector startParam, endParam;
	private Vector dim;

	private static Texture currentlyBound = null;
	private static final HashMap<String, Texture> textureCache = new HashMap<>();

	private TextureList(String filePath, boolean smoothTexture, Vector start, Vector end) {
		this.filePath = filePath;
		this.smooth = smoothTexture;
		this.start = start;
		this.end = end;
	}

	private TextureList(String filePath, boolean smoothTexture) {
		this(filePath, smoothTexture, new Vector(), new Vector());
		startParam = new Vector(0, 0);
		endParam = new Vector(1, 1);
	}

	/**
	 * Call each time a new frame begins, as the graphics card has cleared the
	 * texture buffer.
	 */
	public static void newFrame() {
		currentlyBound = null;
	}

	public void initializeTexture() {
		// Make sure the texture hasn't been loaded before. If it has, reuse.
		if (textureCache.containsKey(filePath)) {
			texture = textureCache.get(filePath);
		} else {
			try {
				texture = TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream(GameState.IMG_PATH + filePath));
				textureCache.put(filePath, texture);
			} catch (IOException e) {
				System.err.println("Failed to load image with error:");
				e.printStackTrace();
				System.err.println("Exiting.");
				System.exit(0);
			}
		}
		if (smooth) {
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		} else {
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}
		dim = new Vector(texture.getImageWidth(), texture.getImageHeight());
		if (endParam == null) {
			startParam = new Vector((float) start.x / dim.x, (float) start.y / dim.y);
			endParam = new Vector((float) end.x / dim.x, (float) end.y / dim.y);
		}
	}

	public Vector getStartTexPosition() {
		return startParam;
	}

	public Vector getEndTexPosition() {
		return endParam;
	}

	public void bindTexture() {
		if (texture != currentlyBound) {
			texture.bind();
			currentlyBound = texture;
		}
	}
}
