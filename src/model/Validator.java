package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	private String textRE="[0-9a-zA-Z ,.\\-!?]{1,150}";
	private String numbersRE="[0-9]{1,10}";
	private String isbnRE = "[0-9-]{13,17}";
	private Pattern stringP;
	private Pattern numberP;
	private Pattern isbnP;
	
	public Validator() {
		stringP=Pattern.compile(textRE);
		numberP=Pattern.compile(numbersRE);
		isbnP = Pattern.compile(isbnRE);
		
	}
	
	public boolean validateString(String text) {
		Matcher m = stringP.matcher(text);
		return m.matches();
	}
	
	public boolean validateID(String text) {
		Matcher m = numberP.matcher(text);
		return m.matches();
	}
	
	public boolean validateISBN(String text) {
		Matcher m = isbnP.matcher(text);
		return m.matches();
	}
}
