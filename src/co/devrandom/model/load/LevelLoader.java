package co.devrandom.model.load;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.devrandom.main.GameState;
import co.devrandom.model.Model;
import co.devrandom.model.objects.Block;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;
import co.devrandom.model.objects.Wall;
import co.devrandom.util.Vector;

public class LevelLoader {
	private Model model;
	private String levelName;

	public LevelLoader(Model model, String levelName) {
		this.model = model;
		this.levelName = levelName;
	}

	public void loadObjects() {
		File levelFile = load(levelName);
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(levelFile);
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("rect");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap attrs = node.getAttributes();
					
					float x = Float.parseFloat(attrs.getNamedItem("x").getNodeValue());
					float y = Float.parseFloat(attrs.getNamedItem("y").getNodeValue());
					
					float w = Float.parseFloat(attrs.getNamedItem("width").getNodeValue());
					float h = Float.parseFloat(attrs.getNamedItem("height").getNodeValue());
					
					String color = extractElement("fill", attrs.getNamedItem("style").getNodeValue()).substring(1);
					
					PhysicsObject object = null;
					
					if (color.equals(ColorList.WALL.getColor())) {
						object = new Wall(model, new Vector(x + w / 2, y + h / 2).scale(GameState.LEVEL_SCALE * 2),
								new Vector(w, h).scale(GameState.LEVEL_SCALE));	
					} else if (color.equals(ColorList.BOX.getColor())) {
						object = new Block(model, new Vector(x + w / 2, y + h / 2).scale(GameState.LEVEL_SCALE * 2),
								new Vector(w, h).scale(GameState.LEVEL_SCALE));
					} else if (color.equals(ColorList.PLAYER.getColor())) {
						Player player = new Player(model, new Vector(x + w / 2, y + h / 2).scale(GameState.LEVEL_SCALE * 2),
								new Vector(w, h).scale(GameState.LEVEL_SCALE));
						object = player;
						
						model.setPlayer(player);
						
						System.out.println("hello");
					}

					model.addPhysicsObject(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String extractElement(String key, String list) {
		Pattern extractor = Pattern.compile(key + ":([^:;]*)");
		Matcher matcher = extractor.matcher(list);
		
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			System.err.println("Level loader encountered a problem");
			System.exit(1);
			return null;
		}
	}
	
	private static File load(String fileName) {
		return new File(GameState.LEVEL_PATH + fileName);
	}
}
