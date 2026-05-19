package org.pickaid.pimorset.runtime;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.pidatagraph.result.PiActionResult;
import org.pickaid.pidamage.api.request.PiDamageRequest;
import org.pickaid.pihud.PiAbilityHudState;
import org.pickaid.pikey.api.PiInputPhase;
import org.pickaid.pikey.network.PiInputIntentPacket;
import org.pickaid.pimorset.runtime.slice.PimorSetRuntimeSlice;
import org.pickaid.pinet.api.scope.PiNetScopeKind;
import org.pickaid.pirenderruntime.api.PiSceneFrame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class PimorSetRuntimeSliceTest {
    @Test
    void migrationSliceCarriesInputEngineGraphDamageCueAndHudState() {
        PimorSetRuntimeSlice slice = PimorSetRuntimeSlice.playerActiveSkill(id("pimorset:storm_set"), 40);

        PiInputIntentPacket packet = slice.intentPacket(12L, 3L, PiInputPhase.PERFORMED, 1.0D);
        PiDamageRequest damage = slice.damageRequest(7.0F);
        PiActionResult graphAction = slice.graphAction("damage_request");
        PiSceneFrame frame = slice.sceneFrame(120L, 2L);
        PiAbilityHudState hud = slice.hudState(0.5F);

        assertEquals(id("pimorset:storm_set"), slice.actor().id());
        assertEquals(id("pimorset:active_skill"), packet.actionId());
        assertEquals(id("pimorset:set_burst"), damage.damageType());
        assertEquals(id("pimorset:damage_request"), graphAction.type());
        assertEquals(PiNetScopeKind.TRACKING_ENTITY, slice.syncScope(42).kind());
        assertFalse(frame.cues().isEmpty());
        assertEquals(40, hud.cooldownTicks());
    }

    private static ResourceLocation id(String value) {
        return ResourceLocation.tryParse(value);
    }
}
