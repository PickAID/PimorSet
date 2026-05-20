package org.pickaid.pimorset.runtime;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.piengine.api.ability.PiAbilityIntent;
import org.pickaid.piengine.api.ability.PiAbilityIntentValidator;
import org.pickaid.piengine.api.ability.PiAbilitySpec;
import org.pickaid.piengine.api.ability.PiAbilityValidationContext;
import org.pickaid.piengine.api.ability.PiAbilityValidationDecision;
import org.pickaid.piengine.api.ability.PiAbilityValidationResult;
import org.pickaid.piengine.api.actor.PiActor;
import org.pickaid.piengine.api.actor.PiActorType;
import org.pickaid.piengine.api.bridge.PiInputRuntimeAccess;
import org.pickaid.pimorset.runtime.ability.PimorSetP0Abilities;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PimorSetP0AbilityIntentValidationTest {
    @Test
    void gravityLiftConsumesPiEngineIntentValidation() {
        PiActor actor = actor("player");
        PiAbilitySpec spec = PimorSetP0Abilities.registrationPlan().ability("gravity_lift");
        PiInputRuntimeAccess input = PiInputRuntimeAccess.create();
        PiAbilityIntent intent = input.abilityIntent(actor, id("active_skill"));

        PiAbilityValidationResult accepted = PiAbilityIntentValidator.validate(
                spec,
                intent,
                new PiAbilityValidationContext(actor, Optional.of(id("storm_staff")), true, true)
        );
        PiAbilityValidationResult rejected = PiAbilityIntentValidator.validate(
                spec,
                intent,
                new PiAbilityValidationContext(actor, Optional.empty(), true, true)
        );

        assertTrue(accepted.accepted());
        assertEquals(id("gravity_lift"), accepted.graph());
        assertEquals(id("impact"), accepted.damageRequest().orElseThrow());
        assertEquals(PiAbilityValidationDecision.ITEM_MISMATCH, rejected.decision());
    }

    private static PiActor actor(String path) {
        return new PiActor(id(path), new PiActorType(id("player_actor")));
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation("pimorset", path);
    }
}
