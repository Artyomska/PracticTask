package fisapost.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fisapost.entities.Elementfisa;
import fisapost.service.PostService;
import fisapost.service.SarcinaService;
import fisapost.validator.Validator;
import fisapost.validator.ValidatorException;

public class EFXMLRepo extends Repository<Elementfisa, Integer> 
{

    SarcinaService sservice;
    PostService pservice;
    
    private String fileName;
	public EFXMLRepo(Validator<Elementfisa> v, String fileName, PostService pservice2, SarcinaService sservice2) throws NumberFormatException, ValidatorException 
	{
    	super(v);
        this.sservice = sservice2;
        this.pservice = pservice2;
    	this.fileName = fileName;
    	loadf();
    }
	@Override
	public Elementfisa save(Elementfisa e) {
		Elementfisa ret = super.save(e);
		savef();
		return ret;
	}
	@Override
	public void delete(Integer id) {
		super.delete(id);
		savef();
	}

	@Override
	public Elementfisa update(Elementfisa e) {
		Elementfisa ret = super.update(e);
		savef();
		return ret;
	}
    public void loadf() throws NumberFormatException, ValidatorException  {
        Document document = loadDocument();
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Elementfisa s = createEF(element);
                super.map.put(s.getId(), s);
            }
        }
    }
    public void savef() 
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Posts");
            doc.appendChild(rootElement);
            for (Elementfisa x:super.findAll())
            {
                Element Elementfisa = doc.createElement("Elementfisa");
                rootElement.appendChild(Elementfisa);
                Elementfisa.setAttribute("id", x.getId().toString());
                appendPostElement(doc,"post",x.getPost().getId(),"sarcina",x.getPost().getId(),Elementfisa);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) 
        {
            pce.printStackTrace();
        }
    }
    
    private static void appendPostElement(Document doc, String tagName, Integer integer,String tagName2,Integer integer2, Element PostNode)
    {
    	Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(Integer.toString(integer)));
        Element element2 = doc.createElement(tagName2);
        element2.appendChild(doc.createTextNode(Integer.toString(integer2)));
        PostNode.appendChild(element);
        PostNode.appendChild(element2);
    }
    private Elementfisa createEF(Element element) throws NumberFormatException, ValidatorException 
    {
        String id = element.getAttributeNode("id").getValue();
        String post = element.getElementsByTagName("post").item(0).getTextContent();
        String sarcina = element.getElementsByTagName("sarcina").item(0).getTextContent();
        return new Elementfisa(Integer.parseInt(id), pservice.findOne(Integer.parseInt(post)),sservice.findOne(Integer.parseInt(sarcina)));
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