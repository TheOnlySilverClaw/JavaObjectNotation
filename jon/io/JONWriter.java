package jon.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import jon.formatters.JONFormatter;
import jon.formatters.JONInefficientFormatter;
import jon.model.JONEntity;
import jon.model.JONModel;
import jon.model.JONValue;

import static jon.JONSymbols.*;

public class JONWriter extends OutputStreamWriter {


		
	public JONWriter(OutputStream out, Charset cs) {
		super(out, cs);
	}

	public JONWriter(OutputStream out, CharsetEncoder enc) {
		super(out, enc);
	}

	public JONWriter(OutputStream out, String charsetName) throws UnsupportedEncodingException {
		super(out, charsetName);
	}

	public JONWriter(OutputStream out) {
		super(out);
	}

	public void writeEntity(JONEntity entity) throws IOException {
		
		writeEntity(entity, new JONInefficientFormatter());
	}
	
	public void writeEntity(JONEntity entity, JONFormatter formatter) throws IOException {
				
		writeNested(entity, formatter, 0);
	}
	
	protected void writeNested(JONEntity entity, JONFormatter formatter, int level) throws IOException {
		
		write(formatter.beforeEntity(level));
		write(entity.getName());
		write(ENTITY_START);
		write(formatter.entityStart());
		for(JONModel child : entity.children()) {
			if(child instanceof JONValue) {
				write(formatter.beforeValue(level +1));
				writeValue((JONValue) child);
				write(formatter.afterValue());
			} else {
				writeNested((JONEntity) child, formatter, level + 1);
			}
			write(formatter.afterEntry());
		}
		write(formatter.entityEnd(level));
		write(ENTITY_END);
		write(formatter.afterEntity());
	}
	
	public void writeValue(JONValue value) throws IOException {
		
		write(value.getName());
		write(VALUE_TAGGER);
		write(value.getType());
		write(VALUE_START);
		write(value.getRaw());
		write(VALUE_END);
	}
}
