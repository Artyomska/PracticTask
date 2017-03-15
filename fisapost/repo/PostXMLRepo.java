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

public class PostXMLRepo extends Repository<Post, Integer> {
    private String fileName;
	public PostXMLRepo(Validator<Post> v, String fileName) {
    	super(v);
    	this.fileName = fileName;
    	loadf();
    }
	@Override
	public Post save(Post e) {
		Post ret = super.save(e);
		savef();
		return ret;
	}
	@Override
	public void delete(Integer id) {
		super.delete(id);
		savef();
	}

	@Override
	public Post update(Post e) {
		Post ret = super.update(e);
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
                Post s = createPost(element);
                super.map.put(s.getId(), s);
            }
        }
    }
    public void savef() 
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Posts");
            doc.appendChild(rootElement);
            for (Post x:super.findAll())
            {
                Element Post = doc.createElement("Post");
                rootElement.appendChild(Post);
                Post.setAttribute("id", x.getId().toString());
                appendPostElement(doc,"nume",x.getNume(),"tip",x.getTip(),Post);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) 
        {
            pce.printStackTrace();
        }
    }
    
    private static void appendPostElement(Document doc, String tagName, String textNode,String tagName2,String textNode2, Element PostNode)
    {
    	Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textNode));
        Element element2 = doc.createElement(tagName2);
        element2.appendChild(doc.createTextNode(textNode2));
        PostNode.appendChild(element);
        PostNode.appendChild(element2);
    }
    private Post createPost(Element element) 
    {
        String id = element.getAttributeNode("id").getValue();
        String nume = element.getElementsByTagName("nume").item(0).getTextContent();
        String tip = element.getElementsByTagName("tip").item(0).getTextContent();
        return new Post(Integer.parseInt(id), nume,tip);
    }

    Document loadDocument() 
    {
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
