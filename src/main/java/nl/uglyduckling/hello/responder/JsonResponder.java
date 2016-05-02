package nl.uglyduckling.hello.responder;


import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;

import nl.uglyduckling.hello.services.DateService;

public class JsonResponder extends ResponderBase {

	public JsonResponder(String version, DateService dateService) {
		super(version, dateService);
	}

	public TextStreamResponse Render(String userName) {
		JSONObject json = getJsonResult(userName);

		return new TextStreamResponse("application/json", json.toCompactString()); 
	}

	private JSONObject getJsonResult(String userName) {
		JSONObject json = new JSONObject();

		json.put("result", "Hello " + userName);
		json.put("version", version);
		json.put("timestamp", dateService.CurrentDate().toString());
		return json;
	}

}
