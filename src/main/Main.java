package main;

public class Main {
	
	private static Model model;
	private static ViewController viewController;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		model = new Model();
		viewController = new ViewController(model);
		
		new Thread(model).start();
		new Thread(viewController).start();
	}

}
