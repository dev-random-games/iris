package co.devrandom.model.objects.util;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import co.devrandom.util.Vector;

public class FixtureListBuilder {
	private ArrayList<Vector> allVertices;
	private ArrayList<Vector[]> shapes;
	
	private Vector size = new Vector(100, 100);

	public FixtureListBuilder() {
		allVertices = new ArrayList<Vector>();
		shapes = new ArrayList<Vector[]>();
	}
	
	public FixtureListBuilder shape(Vector[] vertices){
		shapes.add(vertices);
		for (Vector v : vertices){
			allVertices.add(v);
		}
		return this;
	}
	
	public FixtureListBuilder shape(float[] vertices){
		Vector[] vList = new Vector[vertices.length / 2];
		for (int i = 0; i < vertices.length; i+= 2){
			vList[i/2] = new Vector(vertices[i], vertices[i + 1]);
		}
		return shape(vList);
	}
	
	public FixtureListBuilder size(Vector size) {
		this.size = size;
		return this;
	}
	
	public FixtureDef[] build() {
		FixtureDef[] fixtures = new FixtureDef[shapes.size()];
		
		Vector c1 = new Vector(allVertices.get(0));
		Vector c2 = new Vector(c1);
		
		for (Vector v : allVertices) {
			c1.x = Math.min(v.x, c1.x);
			c1.y = Math.min(v.y, c1.y);
			c2.x = Math.max(v.x, c2.x);
			c2.y = Math.max(v.y, c2.y);
		}
		
		Vector dimension = c2.minus(c1);
		float xScale = size.x / dimension.x;
		float yScale = size.y / dimension.y;
		
		Vector center = c2.plus(c1).scale(.5f);
		
		for (int i = 0; i < shapes.size(); i++){
			Vector[] vertList = shapes.get(i);
			Vec2[] finalVerts = new Vec2[vertList.length];
			
			for (int j = 0; j < vertList.length; j++){
				Vec2 v = new Vec2(vertList[j].x, vertList[j].y);
				v = v.sub(new Vec2(center.x, center.y)).add(new Vec2(center.x, center.y));
				finalVerts[j] = new Vec2(v.x * xScale, v.y * yScale);
			}
			
			PolygonShape shape = new PolygonShapeBuilder().v(finalVerts).build();
			FixtureDef fixture = new FixtureDefBuilder().shape(shape).build();
			fixtures[i] = fixture;
		}
		
		return fixtures;
	}
}
