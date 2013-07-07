package co.devrandom.model.objects.util;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.util.Vector;

public class BodyDefBuilder {
	private BodyDef bd;
	
	public BodyDefBuilder() {
		bd = new BodyDef();
	}
	
	public BodyDef build() {
		return bd;
	}
	
	public BodyDefBuilder position(Vector v) {
		bd.position.set(v.x, v.y);
		return this;
	}
	
	public BodyDefBuilder type(BodyType type) {
		bd.type = type;
		return this;
	}
	
	public BodyDefBuilder gravityScale(float gravityScale) {
		bd.gravityScale = gravityScale;
		return this;
	}
	
	public BodyDefBuilder canRotate(boolean r) {
		bd.fixedRotation = !r;
		return this;
	}
	
	public BodyDefBuilder bullet(boolean bullet){
		bd.bullet = bullet;
		return this;
	}
	
	public BodyDefBuilder linearDamping(float linearDamping) {
		bd.linearDamping = linearDamping;
		return this;
	}
	
	public static BodyDef setPosition(BodyDef bd, Vector position) {
		bd.position = position.toVec2();
		return bd;
	}
}