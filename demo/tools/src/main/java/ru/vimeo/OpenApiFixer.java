package ru.vimeo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class OpenApiFixer {

    private static final String VIMEO_API_SPEC = "./vimeo_openapi.json";
    private static final String VIMEO_API_SPEC_FIXED = "./vimeo_openapi_fixed.json";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        File input = new File(VIMEO_API_SPEC);
        File output = new File(VIMEO_API_SPEC_FIXED);

        JsonNode root = MAPPER.readTree(input);

        fixNode(root);

        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output, root);

        System.out.println("Spec fixed: " + output.getAbsolutePath());
    }

    private static void fixNode(JsonNode node) {
        if (node instanceof ObjectNode obj) {

            // Our target: array + enum at same level
            if (obj.has("type") &&
                    obj.get("type").asText().equals("array") &&
                    obj.has("enum") &&
                    obj.has("items") &&
                    obj.get("items").isObject()) {

                // Move enum into items
                ((ObjectNode) obj.get("items")).set("enum", obj.get("enum"));
                obj.remove("enum");
            }

            // Recursively process children
            obj.fields().forEachRemaining(e -> fixNode(e.getValue()));
        }
        else if (node.isArray()) {
            node.forEach(OpenApiFixer::fixNode);
        }
    }
}
