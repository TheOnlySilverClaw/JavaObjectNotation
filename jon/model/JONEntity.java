package jon.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jon.JONSymbols;
import jon.exceptions.JONMissingEntityException;
import jon.exceptions.JONMissingValueException;

public class JONEntity extends JONModel {


	private final Map<String, JONModel> children;
	
	public JONEntity(String name) {
		super(name);
		this.children = new HashMap<>();
	}

	public Collection<JONModel> children() {
	
		return children.values();
	}
	
	public JONEntity getEntity(String name)
			throws JONMissingEntityException, ClassCastException {
		
		JONEntity entity = (JONEntity) children.get(name);
		if(entity == null) throw new JONMissingEntityException(name);
		return entity;
	}

	public JONValue getValue(String name)
			throws JONMissingValueException, ClassCastException {
		
		JONValue value = (JONValue) children.get(name);
		if(value == null) throw new JONMissingValueException(name);
		return value;
	}
	
	public boolean hasChild(String key) {
		
		return children.containsKey(key);
	}
	
	public boolean addChild(JONModel child) {
		
		boolean replaced = children.containsKey(child.name);
		children.put(child.name, child);
		return replaced;
	}

	@Override
	public String toJON() {
		
		StringBuilder builder = new StringBuilder(children.size() * 32);
		builder.append(name);
		builder.append(JONSymbols.ENTITY_START);
		children.values().forEach(child -> builder.append(child.toJON()));
		builder.append(JONSymbols.ENTITY_END);
		return builder.toString();
	}
}
