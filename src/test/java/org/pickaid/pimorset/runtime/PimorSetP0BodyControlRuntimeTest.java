package org.pickaid.pimorset.runtime;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.piengine.api.actor.PiActor;
import org.pickaid.piengine.api.actor.PiActorType;
import org.pickaid.piengine.api.body.PiBodyControlRuntime;
import org.pickaid.piengine.api.body.PiCaptureAnchor;
import org.pickaid.piengine.api.body.PiCaptureMode;
import org.pickaid.piengine.api.body.PiCaptureRequest;
import org.pickaid.piengine.api.body.PiCaptureRuntimeFacts;
import org.pickaid.piengine.api.body.PiCaptureState;
import org.pickaid.piengine.api.body.PiThrowRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class PimorSetP0BodyControlRuntimeTest {
    @Test
    void gravityLiftConsumesPiEngineBodyControlRuntime() {
        PiActor owner = actor("player");
        PiActor target = actor("target");
        PiBodyControlRuntime runtime = PiBodyControlRuntime.create();
        PiCaptureRequest request = PiCaptureRequest.builder(owner, target)
                .mode(PiCaptureMode.RIDE_BASED)
                .anchor(PiCaptureAnchor.socket("right_hand").fallback(PiCaptureAnchor.bodyOffset(0.0, 1.4, -2.0)))
                .durationTicks(46)
                .build();

        PiCaptureState started = runtime.startCapture(request);
        PiCaptureState ticked = runtime.tickCapture(target, PiCaptureRuntimeFacts.active()).orElseThrow();
        PiThrowRequest thrown = runtime.throwCapture(target, 0.0, 0.4, 1.35);

        assertEquals(owner, started.owner());
        assertEquals(target, ticked.target());
        assertEquals("right_hand", started.anchor().id());
        assertEquals(1, ticked.elapsedTicks());
        assertEquals(1.35, thrown.velocityZ());
        assertFalse(runtime.capture(target).isPresent());
    }

    private static PiActor actor(String path) {
        return new PiActor(new ResourceLocation("pimorset", path), new PiActorType(new ResourceLocation("pimorset", "actor")));
    }
}
