package co.devrandom.model;

import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class GameObject {
	
	private TextureAttributes texAttributes;
	private Vector position;
	private float rotation;
	
	public GameObject() {
		texAttributes = new TextureAttributes(new TextureList[] {TextureList.SMILEY_MOUTH_1,
																 TextureList.SMILEY_MOUTH_2,
																 TextureList.SMILEY_MOUTH_3,
																 TextureList.SMILEY_MOUTH_4,
																 TextureList.SMILEY_MOUTH_4,
																 TextureList.SMILEY_MOUTH_3,
																 TextureList.SMILEY_MOUTH_2,
																 TextureList.SMILEY_MOUTH_1});
		position = new Vector();
	}

	public TextureAttributes getTexAttributes() {
		return texAttributes;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float newRot) {
		rotation = newRot;
	}
}
