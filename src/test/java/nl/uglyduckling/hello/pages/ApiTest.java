package nl.uglyduckling.hello.pages;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.apache.tapestry5.test.TapestryRunnerConstants;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApiTest {

	private final String PAGE_NAME = "Api";
	private final String URL = "http://localhost:8080/api/xml/fred";
	private final String WEBAPP_FOLDER = "src/main/webapp";

	private Server jettyServer;

	@Before
	public void setup() throws Exception 
	{
		String baseFolder = TapestryRunnerConstants.MODULE_BASE_DIR.getAbsolutePath();
		String fullFolder = baseFolder + "/" + WEBAPP_FOLDER;

		// Create Server
		jettyServer = new Server(8080);
		ServletHolder defaultServ = new ServletHolder("default", DefaultServlet.class);
		defaultServ.setInitParameter("resourceBase", fullFolder);
		defaultServ.setInitParameter("dirAllowed","true");

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(fullFolder);
		webapp.setDescriptor(fullFolder + "/" + "WEB-INF/web-test.xml");
		jettyServer.setHandler(webapp);

		jettyServer.start();
	}

	@After
	public void tearDown() throws Exception
	{
		jettyServer.stop();
	}

	@Test
	public void confirmPlaceholderIsLoaded() {
		String appPackage = "nl.uglyduckling.hello";
		String appName = "app";
		String context = WEBAPP_FOLDER;
		PageTester tester = new PageTester(appPackage, appName, context);
		Document document = tester.renderPage(PAGE_NAME);

		String markup = document.toString();
		String expectedWelcome = "placeholder";
		assertTrue(markup.contains(expectedWelcome));
	}

	@Test()
	public void confirmXmlResponse() throws Exception 
	{
		HttpURLConnection httpConnection = (HttpURLConnection)new URL(URL).openConnection();

		InputStream in = httpConnection.getInputStream();
		String encoding = httpConnection.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);

		String expected = "<timestamp>Wed Apr 04 10:45:09 CET 1900</timestamp>";

		assertEquals("Response Code", httpConnection.getResponseCode(), HttpStatus.OK_200);

		assertTrue("Expected " +expected + ", but was " + body, body.contains(expected));
	}

	@Test()
	public void confirmXmlResponseContainsFred() throws Exception 
	{
		HttpURLConnection httpConnection = (HttpURLConnection)new URL(URL).openConnection();

		InputStream in = httpConnection.getInputStream();
		String encoding = httpConnection.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);

		String expected = "fred";

		assertEquals("Response Code", httpConnection.getResponseCode(), HttpStatus.OK_200);

		assertTrue("Expected " +expected + ", but was " + body, body.contains(expected));
	}

	@Test()
	public void validateJettySetup() throws Exception 
	{

		HttpURLConnection httpConnection = (HttpURLConnection)new URL(URL).openConnection();

		assertEquals("Response Code", httpConnection.getResponseCode(), HttpStatus.OK_200);
	}
}
