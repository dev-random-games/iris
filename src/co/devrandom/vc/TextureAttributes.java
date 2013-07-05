package co.devrandom.vc;

import co.devrandom.util.Vector;

public class TextureAttributes {
	private static final float DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 100;
	private static final long DEFAULT_FRAME_DURATION = 250l;
	
	public TextureList[] textures;
	public int currentFrame;
	public long frameDuration;
	public long lastFrameUpdate;
	public float r = 0, g = 0, b = 0, a = 1;
	public float width, height;
	
	public TextureAttributes(TextureList[] textures, float w, float h, long frameDuration) {
		this.textures = textures;
		this.width = w;
		this.height = h;
		this.frameDuration = frameDuration;
		lastFrameUpdate = System.currentTimeMillis();
	}
	
	public TextureAttributes(TextureList texture, float w, float h) {
		this (new TextureList[] { texture }, w, h, Long.MAX_VALUE);
	}
	
	public TextureAttributes(TextureList texture){
		this(texture, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public TextureAttributes(TextureList[] textures){
		this(textures, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FRAME_DURATION);
	}
	
	public Vector getStartTexPosition() {
		return textures[currentFrame].getStartTexPosition();
	}
	
	public Vector getEndTexPosition() {
		return textures[currentFrame].getEndTexPosition();
	}
	
	public void checkAnimation() {
		if (textures.length > 1) {
			long time = System.currentTimeMillis();
			long duration = time - lastFrameUpdate;
			if (duration > frameDuration) {
				currentFrame = (currentFrame + 1) % textures.length;
				lastFrameUpdate = time;
			}
		}
	}
}
