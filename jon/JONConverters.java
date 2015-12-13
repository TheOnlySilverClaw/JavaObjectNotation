package jon;

import java.util.HashMap;
import java.util.Map;

import jon.exceptions.ConverterDuplicateException;
import jon.exceptions.ConverterMissingException;

public class JONConverters {
	
	private final static HashMap<String, JONConverter> customConverters = new HashMap<>();
	
	public final static JONConverter get(String key) {
		
		switch(key) {
		case "i" : return Integer::parseInt;
		case "s" : return value -> value;
		case "d" : return Double::parseDouble;
		case "l" : return Long::parseLong;
		case "f" : return Float::parseFloat;
		case "b" : return Boolean::parseBoolean;
		case "y" : return Byte::parseByte;
		case "c" : return value -> value.charAt(0);
		default:
			JONConverter c = customConverters.get(key);
			if(c == null) throw new ConverterMissingException(key);
			return c;
		}
	}
	
	public void install(String key, JONConverter converter) 
		throws ConverterDuplicateException {
		
		if(customConverters.containsKey(key)) {
			throw new ConverterDuplicateException(key);
		}
		customConverters.put(key, converter);
	}
	
	public void installAll(Map<String, JONConverter> converters)
		throws ConverterDuplicateException {
		
		converters.forEach(this::install);
	}
}
