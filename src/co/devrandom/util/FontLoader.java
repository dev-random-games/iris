package co.devrandom.util;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import co.devrandom.main.GameState;

public class FontLoader {
	private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
	
	public static TrueTypeFont loadTTF(String fontName, float size) {
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream(GameState.FONT_PATH + fontName);
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(size);
			TrueTypeFont ttf = new TrueTypeFont(awtFont, GameState.ANTIALIAS);
			
			return ttf;
		} catch (Exception e) {
			e.printStackTrace();
			
			return new TrueTypeFont(DEFAULT_FONT, GameState.ANTIALIAS);
		}
	}
}
