package co.devrandom.model.load;

public enum ColorList {
	WALL("000000"),
	BOX("00ff00"),
	PLAYER("0000ff");
	
	private String color;
	
	private ColorList(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
}
