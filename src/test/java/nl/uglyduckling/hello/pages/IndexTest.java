package nl.uglyduckling.hello.pages;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.junit.Test;

public class IndexTest {
	private final String PAGE_NAME = "Index";
	private final String FORM_NAME = "inputform";
	
	private PageTester tester;

	public IndexTest() {
		String appPackage = "nl.uglyduckling.hello";
		String appName = "app";
		String context = "src/main/webapp";
		tester = new PageTester(appPackage, appName, context);
	}
	
	@Test
	public void confirmIndexIsLoaded() {
		Document document = tester.renderPage(PAGE_NAME);

		String markup = document.toString();
		String expectedWelcome = "Name :";
		assertTrue(markup.contains(expectedWelcome));
	}
	
	@Test
	public void checkFooter() {
		Document document = tester.renderPage(PAGE_NAME);

		String markup = document.toString();
		String copywrite = "\u00a9";
		String expectedWelcome = copywrite + " Ugly Duckling 2016";
		assertTrue(markup.contains(expectedWelcome));
	}
	
	@Test
	public void helloFred() {
		String name = "Fred";
		String nameOfInputFieldInForm = "nameOfUser";
		
		Document document = tester.renderPage(PAGE_NAME);
		Element form = document.getElementById(FORM_NAME);
		Map<String, String> formValues = new HashMap<String, String>();
		formValues.put(nameOfInputFieldInForm, name);
		
		Document documentWithFormData = tester.submitForm(form, formValues);
						
		String markup = documentWithFormData.toString();
		String expectedHello = "Hello ....";
		assertTrue(markup.contains(expectedHello));
		assertTrue(markup.contains(name));
	}
}
