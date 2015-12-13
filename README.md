# JavaObjectNotation

## Loading
```java
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

JON files can be written with lots of nice whitespace, so eveything looks neat and structured.
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
But it does not care for whitespace and you can leave it out save some bytes.

```
test{empty_child{}string_value:s(teststring)float_value:f(3.456)filled_child{char_value:c(e)}int_value:i(592)}
```

## Using the values

``` java
char testchar = testEntity.getEntity("filled_child").getValue("char_value").converted();
```
