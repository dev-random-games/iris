package co.devrandom.model;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

public class FixtureDefBuilder {
	private static final float DEFAULT_DENSITY = 0.5f;
	private static final float DEFAULT_FRICTION = 0.3f;
	private static final float DEFAULT_RESTITUTION = 0.5f;

	FixtureDef fd;

	public FixtureDefBuilder() {
		this.fd = new FixtureDef();

		density(DEFAULT_DENSITY)
		.friction(DEFAULT_FRICTION)
		.restitution(DEFAULT_RESTITUTION);
	}

	public FixtureDef build() {
		return fd;
	}

	public FixtureDefBuilder shape(Shape shape) {
		fd.shape = shape;
		return this;
	}

	public FixtureDefBuilder density(float density) {
		fd.density = density;
		return this;
	}

	public FixtureDefBuilder friction(float friction) {
		fd.friction = friction;
		return this;
	}

	public FixtureDefBuilder restitution(float restitution) {
		fd.restitution = restitution;
		return this;
	}
}
