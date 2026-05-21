package org.pickaid.pimorset.runtime;

import org.junit.jupiter.api.Test;
import org.pickaid.pidatagraph.result.PiActionResult;
import org.pickaid.pidatagraph.result.PiGraphResults;
import org.pickaid.pidatagraph.result.PiPresentationResult;
import org.pickaid.piengine.api.bridge.PiGraphPendingRequest;
import org.pickaid.piengine.api.bridge.PiGraphRequestKind;
import org.pickaid.piengine.api.bridge.PiGraphRuntimeAccess;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PimorSetP0GraphResultBridgeTest {
    @Test
    void gravityLiftGraphResultsRouteThroughPiEngineOwnerAdapters() {
        PiGraphRuntimeAccess graph = PiGraphRuntimeAccess.create();
        PiActionResult capture = PiGraphResults.captureRequest("capture.target");
        PiActionResult throwTarget = PiGraphResults.throwRequest("capture.throw");
        PiActionResult damage = PiGraphResults.damageRequest("impact_damage");
        PiPresentationResult cue = PiGraphResults.cue("beam");

        assertEquals(PiGraphRequestKind.CAPTURE, graph.requestKind(capture));
        assertEquals(PiGraphRequestKind.THROW, graph.requestKind(throwTarget));
        assertEquals(PiGraphRequestKind.DAMAGE, graph.requestKind(damage));
        assertEquals(PiGraphRequestKind.CUE, graph.requestKind(cue));

        PiGraphPendingRequest captureRequest = graph.pendingRequest(capture).orElseThrow();
        PiGraphPendingRequest cueRequest = graph.pendingRequest(cue).orElseThrow();

        assertEquals(PiGraphRequestKind.CAPTURE, captureRequest.kind());
        assertEquals("capture.target", captureRequest.payloadKey());
        assertEquals(PiGraphRequestKind.CUE, cueRequest.kind());
        assertEquals("beam", cueRequest.payloadKey());
    }
}
