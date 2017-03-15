package fisapost.repo;

import fisapost.entities.*;
import fisapost.validator.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SarcinaXMLRepo extends Repository<Sarcina, Integer> {
    private String fileName;
	public SarcinaXMLRepo(Validator<Sarcina> v, String fileName) {
    	super(v);
    	this.fileName = fileName;
    	loadf();
    }
	
	
	@Override
	public Sarcina save(Sarcina e) {
		Sarcina ret = super.save(e);
		savef();
		return ret;
	}
	@Override
	public void delete(Integer id) {
		super.delete(id);
		savef();
	}

	@Override
	public Sarcina update(Sarcina e) {
		Sarcina ret = super.update(e);
		savef();
		return ret;
	}
    public void loadf()  {
        Document document = loadDocument();
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Sarcina s = createSarcina(element);
                super.map.put(s.getId(), s);
            }
        }
    }
    public void savef() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("sarcinas");
            doc.appendChild(rootElement);
            for (Sarcina x:super.findAll())
            {
                Element sarcina = doc.createElement("sarcina");
                rootElement.appendChild(sarcina);
                sarcina.setAttribute("id", x.getId().toString());
                appendSarcinaElement(doc,"desc",x.getDesc(),sarcina);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }
    
    private static void appendSarcinaElement(Document doc, String tagName, String textNode, Element sarcinaNode)
    {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textNode));
        sarcinaNode.appendChild(element);
    }
    private Sarcina createSarcina(Element element) {
        String id = element.getAttributeNode("id").getValue();
        String desc = element.getElementsByTagName("desc").item(0).getTextContent();
        return new Sarcina(Integer.parseInt(id), desc);
    }

    Document loadDocument() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            Document doc = null;
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(new FileInputStream(this.fileName));
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void saveDocument(Document doc) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(this.fileName));
        
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
