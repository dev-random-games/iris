package co.devrandom.model.load;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.devrandom.main.GameState;
import co.devrandom.model.Model;
import co.devrandom.model.objects.Wall;
import co.devrandom.util.Vector;

public class LevelLoader {
	private Model model;
	private String levelName;

	public LevelLoader(Model model, String levelName) {
		this.model = model;
		this.levelName = levelName;
	}

	public String loadPhysics() {
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
					
//					System.out.println( new Vector(x + w / 2f, y + h / 2).toString());
					System.out.println(new Vector(w, h));
					
					Wall wall = new Wall(model, new Vector(x + w, y + h).scale(GameState.LEVEL_SCALE),
							new Vector(w, h).scale(GameState.LEVEL_SCALE));

					model.addPhysicsObject(wall);
					
//					System.out.println(extractElement(attrs.getNamedItem("style").getNodeValue(), "fill"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

//	private static String extractElement(String list, String key) {
//		String[] elements = list.split(";");
//		
//		System.out.println(list);
//		
////		Pattern extractor = Pattern.compile(key + ":([^:;]*?);");
//		Pattern extractor = Pattern.compile("(" + key + ")");
//		System.out.println(extractor.toString());
//		System.out.println(extractor.matcher(list).group());
//		
////		System.out.println(elements);
//		
//		return "";
//	}
	
	private static File load(String fileName) {
		return new File(GameState.LEVEL_PATH + fileName);
	}
}
