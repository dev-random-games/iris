package co.devrandom.model.events;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionHandler implements ContactListener{
	//private class Collision
	
	@Override
	public void beginContact(Contact arg0) {
		//System.out.println("Collision");
	}

	@Override
	public void endContact(Contact arg0) {

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		
	}

}
