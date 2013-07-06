package co.devrandom.vc.view;

import co.devrandom.util.Vector;

public class TextureAttributes {
	private static final Vector DEFAULT_DIM = new Vector(100, 100);
	private static final long DEFAULT_FRAME_DURATION = 50l;
	
	public TextureList[] textures;
	public int currentFrame;
	public long frameDuration;
	public long lastFrameUpdate;
	public float r = 0, g = 0, b = 0, a = 1;
	public Vector dim;
	
	public TextureAttributes(TextureList[] textures, Vector dim, long frameDuration) {
		this.textures = textures;
		this.dim = dim;
		this.frameDuration = frameDuration;
		lastFrameUpdate = System.currentTimeMillis();
	}
	
	public TextureAttributes clone() {
		return new TextureAttributes(textures, dim, frameDuration);
	}
	
	public TextureAttributes(TextureList texture, Vector dim) {
		this (new TextureList[] { texture }, dim, Long.MAX_VALUE);
	}
	
	public TextureAttributes(TextureList texture){
		this(texture, DEFAULT_DIM);
	}
	
	public TextureAttributes(TextureList[] textures){
		this(textures, DEFAULT_DIM, DEFAULT_FRAME_DURATION);
	}
	
	public TextureAttributes(TextureList[] textures, Vector dim){
		this(textures, dim, DEFAULT_FRAME_DURATION);
	}
	
	public Vector getStartTexPosition() {
		return textures[currentFrame].getStartTexPosition();
	}
	
	public Vector getEndTexPosition() {
		return textures[currentFrame].getEndTexPosition();
	}
	
	public void setSize(Vector size) {
		dim = size;
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
