package nl.uglyduckling.hello.responder;

import nl.uglyduckling.hello.services.DateService;

public abstract class ResponderBase implements ApiResponder{

	protected final String version;
	protected final DateService dateService;

	public ResponderBase(String version, DateService dateService) {
		this.version = version;
		this.dateService = dateService;
	}

}