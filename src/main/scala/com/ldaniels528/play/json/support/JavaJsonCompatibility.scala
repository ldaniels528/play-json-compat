package com.ldaniels528.play.json.support

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import play.api.libs.json.Json.{obj => J}
import play.api.libs.json.{JsArray, JsBoolean, JsNull, JsNumber, JsString, JsValue}

import scala.collection.JavaConversions._

/**
 * Java JSON Compatibility
 * @author Lawrence Daniels <lawrence.daniels@gmail.com>
 */
object JavaJsonCompatibility {
  private lazy val logger = LoggerFactory.getLogger(getClass)

  /**
   * JSON Java [[JsonNode]] to [[JsValue]] Conversion Utility
   * @param jsonNode the host [[JsonNode]]
   */
  implicit class JsonJavaToScala(val jsonNode: JsonNode) extends AnyVal {

    /**
     * Converts a [[JsonNode]] to a [[JsValue]]
     * @return a [[JsonNode]] to a [[JsValue]]
     */
    def toScala: JsValue = unfold(jsonNode)

    /**
     * Converts the given [[JsonNode]] to a [[JsValue]]
     * @param node the given [[JsonNode]]
     * @return a [[JsValue]]
     */
    private def unfold(node: JsonNode): JsValue = {
      node match {
        case jn if jn.isArray => JsArray(jn.elements().toSeq map unfold)
        case jn if jn.isBoolean => JsBoolean(jn.asBoolean())
        case jn if jn.isNull => JsNull
        case jn if jn.isDouble => JsNumber(jn.asDouble())
        case jn if jn.isLong => JsNumber(jn.asLong())
        case jn if jn.isInt => JsNumber(jn.asInt())
        case jn if jn.isNumber => JsNumber(jn.asDouble())
        case jn if jn.isTextual => JsString(jn.asText())
        case jn if jn.isObject => jn.fields.toSeq.foldLeft(J())((js, e) => js ++ J(e.getKey -> unfold(e.getValue)))
        case jn =>
          logger.error(s"JavaJsonCompability: Dropping unknown object: $jn (${jn.getNodeType})")
          JsNull
      }
    }

  }

}
