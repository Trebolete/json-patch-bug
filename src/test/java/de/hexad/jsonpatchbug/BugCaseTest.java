package de.hexad.jsonpatchbug;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BugCaseTest {

    private ObjectMapper mapper = new ObjectMapper();
    private BugCase systemUnderTest = new BugCase();

    @Test
    void GIVEN_arrayDeletionOperationsInAscendingOrder_WHEN_applyingJsonPatch_THEN_itThrowsJsonPatchException() throws IOException {
        String operationsString = "[{\"op\": \"remove\",\"path\":\"/array/0\"}, {\"op\":\"remove\",\"path\":\"/array/1\"}]";
        String sourceString = "{\"array\":[\"firstElement\",\"secondElement\"]}";
        JsonNode operations = mapper.readTree(operationsString);
        JsonNode source = mapper.readTree(sourceString);

        Assertions.assertThrows(JsonPatchException.class, () -> systemUnderTest.apply(operations, source));
    }

    @Test
    void GIVEN_arrayDeletionOperationsInDescendingOrder_WHEN_applyingJsonPatch_THEN_itShouldReturnEmptyObject() throws IOException, JsonPatchException {
        String operationsString = "[{\"op\": \"remove\",\"path\":\"/array/1\"}, {\"op\":\"remove\",\"path\":\"/array/0\"}]";
        String sourceString = "{\"array\":[\"firstElement\",\"secondElement\"]}";
        String expectedString = "{\"array\" : []}";
        JsonNode operations = mapper.readTree(operationsString);
        JsonNode source = mapper.readTree(sourceString);
        JsonNode expected = mapper.readTree(expectedString);

        JsonNode actual = systemUnderTest.apply(operations, source);

        Assertions.assertEquals(expected, actual);
    }

}
