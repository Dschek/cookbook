package ru.vimeo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

/**
 * Pre-processor для кривой Vimeo OpenAPI:
 * 1) переносит enum с уровня массива внутрь items;
 * 2) все array-ответы в responses → content → schema
 *    заворачивает в объект-страницу:
 *    { total, page, per_page, paging, data: [items] }.
 */
public class OpenApiFixer {

    private static final String VIMEO_API_SPEC       = "./vimeo_openapi.json";
    private static final String VIMEO_API_SPEC_FIXED = "./vimeo_openapi_fixed.json";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        File input  = new File(VIMEO_API_SPEC);
        File output = new File(VIMEO_API_SPEC_FIXED);

        JsonNode root = MAPPER.readTree(input);
        if (!(root instanceof ObjectNode rootObj)) {
            throw new IllegalStateException("Root of OpenAPI spec must be an object");
        }

        // 1. Чиним enum у массивов
        fixEnumsOnArray(rootObj);

        // 2. Оборачиваем все array-ответы в объект-страницу
        fixPagedResponses(rootObj);

        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output, rootObj);
        System.out.println("Spec fixed: " + output.getAbsolutePath());
    }

    /**
     * Рекурсивно ищет объекты вида:
     *  { "type": "array", "enum": [...], "items": {...} }
     * и переносит enum в items.enum.
     */
    private static void fixEnumsOnArray(JsonNode node) {
        if (node instanceof ObjectNode obj) {

            if (obj.has("type")
                    && "array".equals(obj.get("type").asText())
                    && obj.has("enum")
                    && obj.has("items")
                    && obj.get("items").isObject()) {

                ObjectNode items = (ObjectNode) obj.get("items");
                // переносим enum внутрь items
                items.set("enum", obj.get("enum"));
                obj.remove("enum");
            }

            obj.fields().forEachRemaining(e -> fixEnumsOnArray(e.getValue()));

        } else if (node.isArray()) {
            node.forEach(OpenApiFixer::fixEnumsOnArray);
        }
    }

    /**
     * Проходит по paths → methods → responses → content → schema
     * и все схемы с type=array заворачивает в объект:
     *
     *  {
     *    "type": "object",
     *    "properties": {
     *      "total":    integer,
     *      "page":     integer,
     *      "per_page": integer,
     *      "paging":   { next, previous, first, last },
     *      "data":     { type: "array", items: <старый items> }
     *    }
     *  }
     */
    private static void fixPagedResponses(ObjectNode root) {
        JsonNode pathsNode = root.get("paths");
        if (!(pathsNode instanceof ObjectNode paths)) {
            return;
        }

        paths.fields().forEachRemaining(pathEntry -> {
            ObjectNode pathItem = asObject(pathEntry.getValue());
            if (pathItem == null) return;

            pathItem.fields().forEachRemaining(methodEntry -> {
                String method = methodEntry.getKey();
                if (!isHttpMethod(method)) return;

                ObjectNode operation = asObject(methodEntry.getValue());
                if (operation == null) return;

                ObjectNode responses = asObject(operation.get("responses"));
                if (responses == null) return;

                responses.fields().forEachRemaining(respEntry -> {
                    ObjectNode response = asObject(respEntry.getValue());
                    if (response == null) return;

                    ObjectNode content = asObject(response.get("content"));
                    if (content == null) return;

                    content.fields().forEachRemaining(ctEntry -> {
                        ObjectNode mediaType = asObject(ctEntry.getValue());
                        if (mediaType == null) return;

                        ObjectNode schema = asObject(mediaType.get("schema"));
                        if (schema == null) return;

                        // интересуют только чистые массивы: type=array + items
                        if (!"array".equals(schema.path("type").asText())) return;
                        JsonNode itemsNode = schema.get("items");
                        if (itemsNode == null || itemsNode.isMissingNode()) return;

                        // Строим новую схему страницы, сохранив старый items
                        ObjectNode pagedSchema = buildPagedSchema(itemsNode);
                        mediaType.set("schema", pagedSchema);
                    });
                });
            });
        });
    }

    private static boolean isHttpMethod(String name) {
        return switch (name.toLowerCase()) {
            case "get", "post", "put", "patch", "delete", "options", "head" -> true;
            default -> false;
        };
    }

    private static ObjectNode asObject(JsonNode node) {
        return (node instanceof ObjectNode obj) ? obj : null;
    }

    /**
     * Генерирует generic-страницу:
     *
     *  {
     *    "type":"object",
     *    "properties":{
     *      "total":    { "type":"integer","format":"int64" },
     *      "page":     { "type":"integer" },
     *      "per_page": { "type":"integer" },
     *      "paging":   { ... },
     *      "data": {
     *        "type":"array",
     *        "items": <items>
     *      }
     *    },
     *    "additionalProperties": false
     *  }
     */
    private static ObjectNode buildPagedSchema(JsonNode itemsNode) {
        ObjectNode schema = MAPPER.createObjectNode();
        schema.put("type", "object");

        ObjectNode props = schema.putObject("properties");

        props.putObject("total")
                .put("type", "integer")
                .put("format", "int64");

        props.putObject("page")
                .put("type", "integer");

        props.putObject("per_page")
                .put("type", "integer");

        ObjectNode paging = props.putObject("paging");
        paging.put("type", "object");
        ObjectNode pagingProps = paging.putObject("properties");
        pagingProps.putObject("next").put("type", "string").put("nullable", true);
        pagingProps.putObject("previous").put("type", "string").put("nullable", true);
        pagingProps.putObject("first").put("type", "string").put("nullable", true);
        pagingProps.putObject("last").put("type", "string").put("nullable", true);
        paging.put("additionalProperties", false);

        ObjectNode data = props.putObject("data");
        data.put("type", "array");
        // копируем оригинальный items (там может быть $ref или сложная схема)
        data.set("items", itemsNode.deepCopy());

        schema.put("additionalProperties", false);

        return schema;
    }
}
