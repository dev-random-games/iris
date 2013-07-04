package co.devrandom.vc;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public enum TextureList {
	EVIL_SMILEY("assets/img/evil-smiley.png");
	
	private String filePath;
	private Texture texture;
	
	private TextureList(String filePath){
		try {
			this.filePath = filePath;			
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void bindTexture(){
		texture.bind();
	}
}
