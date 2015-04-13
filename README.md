# Play JSON (Java to Scala compatibility)
==========================

Inter-mix the Play Frameworks' awesome JsonNode class with Scala code

## Motivations

The Play Framework offers fantastic support for converter Java Beans (with getters and setters) to JSON. However,
these classes differ from their Scala JSON support and are curiously incompatibility. This project allows you to
seamlessly convert Play's Java-centric JsonNode to its Scala-centric JsValue.

## Features

* Seamlessly convert Play's Java-based JsonNode to its Scala-based JsValue

## Development

Example:

```scala
import play.libs.{Json => JavaJson}

def test = Action {
    val items: java.util.List[ItemBeans] = MyItemDAO.getItems()
    Ok(JavaJson.toJson(items).toScala)
}
```

Above I'm reading a Java List from a Data Access Object (e.g. `MyItemDAO`) and am using Play's Java-based JSON support
to convert the list of items into a JsonNode. Immediately after, I convert the JsonNode (which is not a compatible return
type for Scala) to a JsValue.

### Building the code

```bash
sbt package
```
