package jon.formatters;

/**
 * This formatter is only for testing and will be removed soon
 * 
 * @author joel
 *
 */
public class JONInefficientFormatter implements JONFormatter {

	@Override
	public String entityStart() {
		
		return System.lineSeparator() + System.lineSeparator();
	}
	
	@Override
	public String entityEnd(int nestingLevels) {
		
		String indent = "";
		for(int i = 0; i < nestingLevels; i++) {
			indent += "\t";
		}
		return indent;
	}
	
	@Override
	public String beforeValue(int nestingLevels) {
		
		String indent = "";
		for(int i = 0; i < nestingLevels; i++) {
			indent += "\t";
		}
		return indent;
	}
	
	@Override
	public String afterValue() {
		
		return "";
	}
	
	@Override
	public String beforeEntity(int nestingLevels) {
		
		String indent = "";
		for(int i = 0; i < nestingLevels; i++) {
			indent += "\t";
		}
		return indent;
	}
	
	@Override
	public String afterEntity() {
		
		return "";
	}
	
	@Override
	public String afterEntry() {
		
		return System.lineSeparator() + System.lineSeparator();
	}
}