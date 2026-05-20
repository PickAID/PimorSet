package org.pickaid.pimorset.runtime;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.piengine.api.ability.PiAbilityRegistrationPlan;
import org.pickaid.piengine.api.ability.PiAbilitySpec;
import org.pickaid.pimorset.runtime.ability.PimorSetP0Abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class PimorSetP0AbilityRegistrationTest {
    @Test
    void gravityLiftDeclaresActiveAbilityThroughPiEngineSpec() {
        PiAbilityRegistrationPlan plan = PimorSetP0Abilities.registrationPlan();
        PiAbilitySpec spec = plan.ability("gravity_lift");

        assertEquals("pimorset", plan.modId());
        assertEquals(new ResourceLocation("pimorset", "gravity_lift"), spec.id());
        assertEquals(80, spec.cooldownTicks());
        assertEquals(new ResourceLocation("pimorset", "storm_charge"), spec.resourceCheck().orElseThrow());
        assertEquals(new ResourceLocation("pimorset", "active_skill"), spec.inputAction());
        assertEquals(new ResourceLocation("pimorset", "gravity_lift"), spec.graph());
        assertEquals(new ResourceLocation("pimorset", "impact"), spec.damageRequest().orElseThrow());
        assertEquals(2, spec.cues().size());
        assertEquals(2, spec.hudOutputs().size());
        assertFalse(plan.abilities().isEmpty());
    }
}
