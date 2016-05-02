package nl.uglyduckling.hello.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;


/**
 * Start page of application hello.
 */
public class Index
{
  @Inject
  private Logger logger;

  @Property
  private String nameOfUser;
  
  @Property
  private Boolean result;
  
  @Component(id = "nameOfUser")
  private TextField nameOfUserField;

  @Component(id = "inputform")
  private Form inputform;

 @Log
 void onActivate(String nameOfUser)
 {
	logger.info(nameOfUser);
	this.nameOfUser = nameOfUser;
	result = isNameResult(nameOfUser);
 }
 
 void setupRender()
 {
	 if (nameOfUser != null)
	 {
		 result &= hasOnlyCharacters(nameOfUser);
		 result &= !isReservedName(nameOfUser);
	 }
 }

  @Log
  Object[] onPassivate() {
	  logger.info(nameOfUser);
      return new String[] { nameOfUser };
  }

  @Log
  void onValidate()
  {
	  logger.info("validating user input form : " + nameOfUser);
	  inputform.clearErrors();
	  Boolean hasOnlyCharacters = hasOnlyCharacters(nameOfUser);
	  
	  if(!hasOnlyCharacters)
		  inputform.recordError("A name should consist of characters only.");

	  if(isReservedName(nameOfUser))
	    inputform.recordError("This is a reserverd word that can't be used as name.");
  }
  
  private Boolean isNameResult(String nameToCheck)
  {
	  return nameToCheck != null && nameToCheck.length() > 0;
  }
  
  private Boolean hasOnlyCharacters(String stringToCheck)
  {
	  return stringToCheck != null && stringToCheck.matches("[a-zA-Z]+");
  }
  
  private Boolean isReservedName(String stringToCheck)
  {
	  String[] pages = {"Index", "Api", "Error404"};
	  
	  if(stringToCheck == null)
		return false;
	  
	  Boolean isPageName = false;
	  for(String page : pages)
	  {
		  if (page.compareToIgnoreCase(stringToCheck) == 0)
			  isPageName = true;
	  }
	  
	  return isPageName;
  }
}
