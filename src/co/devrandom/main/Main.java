package co.devrandom.main;

import co.devrandom.model.Model;
import co.devrandom.vc.ViewController;

public class Main {
	
	private static Model model;
	private static ViewController viewController;

	public static void main(String[] args) {
		model = new Model();
		viewController = new ViewController(model);
		
		new Thread(model).start();
		new Thread(viewController).start();
	}
}
