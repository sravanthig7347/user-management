package com.neosoft.user.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

	public boolean isValidPincode(String pinCode) {
		
		String regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
		Pattern p = Pattern.compile(regex);
		if (pinCode == null) {
			return false;
		}
		Matcher m = p.matcher(pinCode);
        return m.matches();

	}
	
}
