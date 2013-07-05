package co.devrandom.vc;

public class TextureAttributes {
	private static final float DEFAULT_WIDTH = 128, DEFAULT_HEIGHT = 128;
	
	public TextureAttributes(TextureList[] textures, float w, float h) {
		this.textures = textures;
		this.width = w;
		this.height = h;
	}
	
	public TextureAttributes(TextureList texture, float w, float h) {
		this (new TextureList[] { texture }, w, h);
	}
	
	public TextureAttributes(TextureList texture){
		this(texture, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public TextureList[] textures;
	public int currentFrame;
	public int frameDuration;
	public int lastFrameUpdate;
	public float r = 0, g = 0, b = 0, a = 1;
	public float width, height;
}
