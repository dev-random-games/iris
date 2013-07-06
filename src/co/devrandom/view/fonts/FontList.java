package co.devrandom.view.fonts;

import org.newdawn.slick.TrueTypeFont;

public enum FontList {
	BODY("iceland.ttf", 14),
	HEADER("iceland.ttf", 22);
	
	private TrueTypeFont font;
	
	private FontList(String fileName, float size) {
		this.font = FontLoader.loadTTF(fileName, size);
	}
	
	public TrueTypeFont getFont() {
		return font;
	}
}

