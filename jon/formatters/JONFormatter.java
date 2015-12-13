package jon.formatters;

public interface JONFormatter {

	String beforeEntity(int level);
	
	String entityStart();
	
	String afterEntity();
	
	String beforeValue(int level);
	
	String afterValue();
	
	String afterEntry();
}
