package co.devrandom.model.objects.util;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import co.devrandom.util.Vector;

public class PolygonShapeBuilder {
	
	private ArrayList<Vec2> vertices;
	
	private Vector c1, c2;
	
	public PolygonShapeBuilder() {
		vertices = new ArrayList<Vec2>();
	}

	public PolygonShapeBuilder v(Vec2 v) {
		vertices.add(v);
		
		if (c1 == null){
			c1 = new Vector(v.x, v.y);
			c2 = new Vector(c1);
		} else {
			c1.x = Math.min(v.x, c1.x);
			c1.y = Math.min(v.y, c1.y);
			c2.x = Math.max(v.x, c2.x);
			c2.y = Math.max(v.y, c2.y);
		}
		
		return this;
	}
	
	public PolygonShapeBuilder center(Vector center) {
		return this;
	}

	public PolygonShapeBuilder v(float x, float y) {
		return v(new Vec2(x, y));
	}

	public PolygonShapeBuilder v(Vector v) {
		return v(v.x, v.y);
	}
	
	public PolygonShapeBuilder v(float[] coords) {
		int size = coords.length;
		
		if (coords.length % 2 != 0)
			size -= 1;
		
		for (int i = 0; i < size; i+= 2){
			v(coords[i], coords[i+1]);
		}
		
		return this;
	}
	
	public PolygonShapeBuilder v(Vec2[] vertices) {
		for (Vec2 v : vertices)
			v(v);
		return this;
	}

	public PolygonShape build() {
		PolygonShape shape = new PolygonShape();
		
		Vector center = c2.plus(c1).scale(.5f);
		
		Vec2[] finalVerts = new Vec2[vertices.size()];
		
		for (int i = 0; i < vertices.size(); i++){
			Vec2 v = vertices.get(i);
			finalVerts[i] = new Vec2(v.x, v.y);
		}
		
		shape.set(finalVerts, finalVerts.length);
		
		return shape;
	}
}
