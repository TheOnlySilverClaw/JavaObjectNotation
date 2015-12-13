package jon.model;

import java.util.Objects;

import jon.JONConverter;
import jon.JONConverters;
import jon.JONSymbols;

public class JONValue extends JONModel {

	protected final String typeKey;
	protected final String raw;
	
	public JONValue(String name, String typeKey, String raw) {
		
		super(name);
		this.typeKey = Objects.requireNonNull(typeKey);
		this.raw = Objects.requireNonNull(raw);
	}
	
	@Override
	public String toJON() {
		
		StringBuilder builder = new StringBuilder(32);
		builder.append(name);
		builder.append(JONSymbols.VALUE_TAGGER);
		builder.append(typeKey);
		builder.append(JONSymbols.VALUE_START);
		builder.append(raw);
		builder.append(JONSymbols.VALUE_END);
		return builder.toString();
	}
	
	public String getType() {
		
		return typeKey;
	}
	
	public String getRaw() {
		
		return raw;
	}
	
	public final <C> C converted() throws ClassCastException {
		
		return (C) JONConverters.get(typeKey).apply(raw);
	}
	
	public final <C> C converted(JONConverter converter) {
		
		return (C) converter.apply(raw);
	}
}
