package de.hexad.jsonpatchbug;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.io.IOException;

public class BugCase {

    public JsonNode apply(JsonNode operations, JsonNode source) throws IOException, JsonPatchException {
        JsonPatch jsonPatch = JsonPatch.fromJson(operations);
        return jsonPatch.apply(source);
    }

}