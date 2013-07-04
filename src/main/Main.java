package main;

public class Main {
	
	private static Model model;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		model = new Model();
		new Thread(model).start();
	}

}
