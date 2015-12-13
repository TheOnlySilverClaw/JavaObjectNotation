package jon.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Stack;

import jon.exceptions.JONReadException;
import jon.model.JONEntity;
import jon.model.JONValue;

import static jon.JONSymbols.*;

public class JONReader extends InputStreamReader {

	protected final Stack<JONEntity> entityStack = new Stack<>();
	protected final Stack<String> nameStack = new Stack<>();
	protected final Stack<String> typeStack = new Stack<>();
	
	protected boolean ignoring = false;
	
	public JONReader(InputStream in, Charset cs) {
		super(in, cs);
	}

	public JONReader(InputStream in, CharsetDecoder dec) {
		super(in, dec);
	}

	public JONReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
		super(in, charsetName);
	}

	public JONReader(InputStream in) {
		super(in);
	}

	public JONEntity readEntity() throws IOException {
		
		int last = -1;
		JONEntity entity = null;
		StringBuilder buffer = new StringBuilder(1024);
		do {
			last = read();
			// fuck whitespace
			if(Character.isWhitespace(last)) continue;
			
			char lastChar = (char) last;
			
			if(lastChar == COMMENT) {
				ignoring = !ignoring;
				continue;
			}
			
			if(ignoring) continue;
			
			switch(lastChar) {
			
			case ENTITY_START:
				
				entityStack.push(new JONEntity(buffer.toString()));
				clear(buffer);
				break;
			case ENTITY_END:
				
				JONEntity lastEntity = entityStack.pop();
				if(entityStack.empty()) {
					entity = lastEntity;
					break;
				}
				entityStack.peek().addChild(lastEntity);
				break;
				
			case VALUE_TAGGER:
				
				nameStack.push(buffer.toString());
				clear(buffer);
				break;
			
			case VALUE_START:
				
				typeStack.push(buffer.toString());
				clear(buffer);
				break;
			case VALUE_END:
				
				entityStack.peek().addChild(new JONValue(nameStack.pop(), typeStack.pop(), buffer.toString()));
				clear(buffer);
				break;
				
			default:
				buffer.append(lastChar);
				//System.out.println("buffer: " + buffer);
				break;
			}
		} while(last != -1);
		
		if(entity == null) throw new JONReadException("No entity found!");
		return entity;
	}
	
	public JONValue readValue() throws IOException {
		
		int last = -1;
		
		StringBuilder buffer = new StringBuilder(64);
		String name = null;
		String type = null;
		
		do {
			last = read();
			
			char lastChar = (char) last;
			
			switch(lastChar) {
				
			case VALUE_TAGGER:
				
				name = buffer.toString();
				clear(buffer);
				break;
			
			case VALUE_START:
				
				type = buffer.toString();
				clear(buffer);
				break;
				
			case VALUE_END:
				
				if(name == null) throw new JONReadException("Name was null!");
				if(type == null) throw new JONReadException("Type was null");
				if(name.isEmpty()) throw new JONReadException("Name was empty!");
				if(type.isEmpty()) throw new JONReadException("Type was empty!");
				return new JONValue(name, type, buffer.toString());
			default:
				buffer.append(lastChar);
				break;
			}
		} while(last != -1);
		
		return null;
	}
	
	// if anyone knows a better way to reuse a StringBuilder, tell me
	private static void clear(StringBuilder buffer) {
		
		buffer.setLength(0);
	}
}
