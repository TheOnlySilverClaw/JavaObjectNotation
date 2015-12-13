package jon.model;

import java.util.Objects;

public abstract class JONModel {

	protected final String name;
	
	protected JONModel(String name) {
		
		this.name = Objects.requireNonNull(name, "Name for " 
				+ getClass().getSimpleName() + " be null!");
	}

	public final String getName() {
		
		return name;
	}
	
	public abstract String toJON();
}
