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
		
		int last = 0;
		JONEntity entity = null;
		StringBuilder buffer = new StringBuilder();
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
	
	private static void clear(StringBuilder buffer) {
		
		buffer.setLength(0);
	}
}
