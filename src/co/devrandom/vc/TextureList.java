package co.devrandom.vc;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import co.devrandom.main.GameState;
import co.devrandom.util.Vector;

public enum TextureList {
	/*
	 * Put all textures needed in game right here!
	 */
	EVIL_SMILEY("evil-smiley.png", false, new Vector(), new Vector(12, 12));

	private String filePath;
	private Texture texture;
	private boolean smooth;
	private Vector start, end;
	private Vector startParam, endParam;
	private Vector dim;

	private TextureList(String filePath, boolean smoothTexture, Vector start, Vector end){
		this.filePath = filePath;
		smooth = smoothTexture;
		this.start = start;
		this.end = end;
	}
	
	private TextureList(String filePath, boolean smoothTexture) {
		this(filePath, smoothTexture, new Vector(), new Vector());
		startParam = new Vector(0, 0);
		endParam = new Vector(1, 1);
	}
	
	public void initializeTexture() {
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(GameState.IMG_PATH + filePath));
			dim = new Vector(texture.getImageWidth(), texture.getImageHeight());
			if (endParam == null){
				startParam = new Vector((float) start.x / dim.x, (float) start.y / dim.y);
				endParam = new Vector((float) end.x / dim.x, (float) end.y / dim.y);
			}
		} catch (IOException e) {
			System.err.println("Failed to load image with error:");
			e.printStackTrace();
			System.err.println("Exiting.");
			System.exit(0);
		}
	}
	
	public Vector getStartTexPosition() {
		return startParam;
	}
	
	public Vector getEndTexPosition() {
		return endParam;
	}

	public void bindTexture() {
		if (smooth) {
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		} else {
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}
		texture.bind();
	}
}
