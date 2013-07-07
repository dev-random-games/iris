package co.devrandom.model.load;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.devrandom.main.GameState;

public class LevelLoader {
	private String levelName;

	public LevelLoader(String levelName) {
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
					
					System.out.println(attrs.getNamedItem("x").getNodeValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private static File load(String fileName) {
		return new File(GameState.LEVEL_PATH + fileName);
	}
}
