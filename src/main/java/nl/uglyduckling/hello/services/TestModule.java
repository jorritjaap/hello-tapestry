package nl.uglyduckling.hello.services;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class TestModule extends AppModule
{
	
	public static void bind(ServiceBinder binder)
	{
		binder.bind(DateService.class, DateServiceMock.class);
	}
}
