package co.devrandom.assets.level;

import co.devrandom.model.objects.Block;
import co.devrandom.model.objects.Player;
import co.devrandom.model.objects.Wall;

public enum ColorList {
	WALL("000000", Wall.class),
	BLOCK("00ff00", Block.class),
	PLAYER("0000ff", Player.class);

	private String color;
	private Class<?> aClass;

	private ColorList(String color, Class<?> aClass) {
		this.color = color;
		this.aClass = aClass;
	}

	public String getColor() {
		return color;
	}
	
	public static Class<?> getClassForColor(String color) {
		for (ColorList e : ColorList.class.getEnumConstants()) {
			if (e.getColor().equals(color)) {
				return e.aClass;
			}
		}
		
		throw new IllegalArgumentException("No object associated with color: " + color);
	}
}
