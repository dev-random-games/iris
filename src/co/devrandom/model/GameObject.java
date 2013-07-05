package co.devrandom.model;

import co.devrandom.util.Vector;
import co.devrandom.vc.TextureAttributes;
import co.devrandom.vc.TextureList;

public class GameObject {
	
	private TextureAttributes texAttributes;
	private Vector position;
	private float rotation;
	
	public GameObject() {
		texAttributes = new TextureAttributes(TextureList.EVIL_SMILEY);
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
