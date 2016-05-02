package nl.uglyduckling.hello.responder;

import java.io.StringWriter;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tapestry5.util.TextStreamResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import nl.uglyduckling.hello.services.DateService;

public class XmlResponder extends ResponderBase {

	
	public XmlResponder(String version, DateService dateService) {
		super(version, dateService);
	}

	@Override
	public TextStreamResponse Render(String userName) 
	{
		try
		{
			String xml = getXmlDocumentResult(userName);
			return new TextStreamResponse("application/xml", xml); 
		}
		catch (Exception e) {
			return new TextStreamResponse("plain/text", "Internal error " + e.getMessage()); 
		}
	}
	
	private String getXmlDocumentResult(String userName) 
			throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException 
	{
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;

		icBuilder = icFactory.newDocumentBuilder();
		Document doc = icBuilder.newDocument();
		Element mainRootElement = doc.createElementNS("http://uglyduckling.nl/CreateXMLDOM", "HelloWorld");
		doc.appendChild(mainRootElement);

		mainRootElement.appendChild(buildXmlNode("result", "Hello " + userName, doc));
		mainRootElement.appendChild(buildXmlNode("version", version, doc));
		mainRootElement.appendChild(buildXmlNode("timestamp", dateService.CurrentDate(), doc));

		String finalstring = documentToString(doc);

		return finalstring;
	}

	private String documentToString(Document doc)
			throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		DOMSource source = new DOMSource(doc);
		StringWriter outWriter = new StringWriter();
		StreamResult result = new StreamResult( outWriter );
		transformer.transform(source, result);
		StringBuffer sb = outWriter.getBuffer(); 
		String finalstring = sb.toString();
		return finalstring;
	}

	private Node buildXmlNode(String name, Date date, Document dom)
	{
		return buildXmlNode(name, date.toString(), dom);
	}
	
	private Node buildXmlNode(String name, String value, Document dom)
	{
		Element node = dom.createElement(name);
		node.appendChild(dom.createTextNode(value));
		return node;
	}
}
