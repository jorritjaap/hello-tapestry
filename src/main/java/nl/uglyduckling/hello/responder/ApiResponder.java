package nl.uglyduckling.hello.responder;

import org.apache.tapestry5.util.TextStreamResponse;

public interface ApiResponder {
	TextStreamResponse Render(String userName);
}
