package in.bushansirgur.restapi.model;

import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ValidationModel {
	
	public static final String SEQUENCE_NAME = "employees_sequence"; 
	
	private Pattern bicCode;
	private Pattern accountValidationRegex;
	private String drCode;
	private String bankName;
	
	
}
