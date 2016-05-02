package nl.uglyduckling.hello.services;

import java.util.Date;

public class DateServiceImpl implements DateService {

	@Override
	public Date CurrentDate() {
		Date date = new Date();
		return date;
	}
}
