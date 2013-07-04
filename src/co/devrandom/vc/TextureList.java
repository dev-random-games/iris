package co.devrandom.vc;

import org.newdawn.slick.opengl.Texture;

public enum TextureList {
	EVIL_SMILEY("assets/img/evil-smiley.png");
	
	private String filePath;
	private Texture texture;
	
	private TextureList(String filePath){
		this.filePath = filePath;
		//texture = 
	}
}
