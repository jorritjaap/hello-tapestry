package nl.uglyduckling.hello.pages;


import nl.uglyduckling.hello.responder.ApiResponder;
import nl.uglyduckling.hello.responder.JsonResponder;
import nl.uglyduckling.hello.responder.XmlResponder;
import nl.uglyduckling.hello.services.DateService;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.util.TextStreamResponse;
import org.slf4j.Logger;

public class Api {
	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String version;

	@Inject
	private Logger logger;
	
	@Inject
	private DateService dateService;
	
	private final String TYPE_JSON = "JSON";
	private final String TYPE_XML = "XML";

	StreamResponse onActivate(String type, String userName)
	{
		ApiResponder apiResponder;
		
		if (type.compareToIgnoreCase(TYPE_JSON) == 0)
		{
			apiResponder = new JsonResponder(version, dateService);
		}
		else if(type.compareToIgnoreCase(TYPE_XML) == 0)
		{
			apiResponder = new XmlResponder(version, dateService);
		}
		else
		{
			return new TextStreamResponse("plain/text", "first parameter must be XML or JSON. "
					+ "Check http:\\\\localhost:8080\\hello\\api for the online documentation."); 
		}

		return apiResponder.Render(userName);
	}
}


