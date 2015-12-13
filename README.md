# JavaObjectNotation

## Loading
```java
		JONEntity testEntity = new JONEntity("test");
		
		try(JONReader in = new JONReader(new BufferedInputStream(
		    Example.class.getResourceAsStream("test.jon")))) {
			
			testEntity = in.readEntity();
		}
```
## Saving
``` java
		try(JONWriter out = new JONWriter(new BufferedOutputStream(
		  new FileOutputStream(new File("some/folder/result.jon"))))) {
			
			out.writeEntity(testEntity, <some formatter class>);
		}
```

## Syntax
```
# some comment #

test{

	empty_child{

	}

	string_value:s(teststring)

	float_value:f(3.456)

	filled_child{

		char_value:c(e)

	}

	int_value:i(592)

}
```
