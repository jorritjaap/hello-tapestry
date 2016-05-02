package nl.uglyduckling.hello.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateServiceMock 
	implements DateService
	{

	@Override
	public Date CurrentDate() {
		Calendar calendar = GregorianCalendar.getInstance();

		int year = 1900;
		int month = 3;
		int date = 4;
		int hourOfDay = 10;
		int minute = 45;
		int second = 9;
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar.getTime();
	}
}
