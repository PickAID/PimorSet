package org.pickaid.pimorset.runtime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PimorSetGravityLiftGraphTest {
    @Test
    void gravityLiftGraphKeepsGameplaySequenceInDatapackData() {
        JsonObject graph = readGraph("data/pimorset/action/gravity_lift.json");
        JsonArray steps = graph.getAsJsonArray("steps");

        assertEquals("pimorset:gravity_lift", graph.get("ability").getAsString());
        assertEquals("right_hand", graph.get("capture_socket").getAsString());
        assertEquals(46, graph.get("throw_after_ticks").getAsInt());
        assertEquals(List.of(
                "select_nearby_living_cone",
                "filter_not_self",
                "filter_capturable",
                "capture_nearest",
                "delay",
                "throw_forward",
                "request_impact_damage",
                "emit_beam_cue",
                "emit_camera_impulse"
        ), steps.asList().stream()
                .map(step -> step.getAsJsonObject().get("action").getAsString())
                .toList());
        assertTrue(graph.getAsJsonObject("impact_damage").has("damage_type"));
        assertTrue(graph.getAsJsonArray("cues").asList().stream()
                .map(cue -> cue.getAsJsonObject().get("type").getAsString())
                .toList()
                .containsAll(List.of("beam", "camera_impulse")));
    }

    private static JsonObject readGraph(String path) {
        ClassLoader loader = PimorSetGravityLiftGraphTest.class.getClassLoader();
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(loader.getResourceAsStream(path), path),
                StandardCharsets.UTF_8
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception error) {
            throw new AssertionError("failed to read " + path, error);
        }
    }
}
