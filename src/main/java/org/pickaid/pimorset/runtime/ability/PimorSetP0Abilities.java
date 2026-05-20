package org.pickaid.pimorset.runtime.ability;

import net.minecraft.resources.ResourceLocation;
import org.pickaid.piengine.api.ability.PiAbilityRegistrationPlan;
import org.pickaid.piengine.api.ability.PiAbilitySpec;
import org.pickaid.pimorset.PimorSet;

public final class PimorSetP0Abilities {
    private static final PiAbilityRegistrationPlan REGISTRATION_PLAN = PiAbilityRegistrationPlan.create(PimorSet.MOD_ID)
            .ability(PiAbilitySpec.named("gravity_lift")
                    .cooldownTicks(80)
                    .requiredItem(id("storm_staff"))
                    .resourceCheck(id("storm_charge"))
                    .inputAction(id("active_skill"))
                    .graph(id("gravity_lift"))
                    .damageRequest(id("impact"))
                    .cue(id("beam"))
                    .cue(id("camera_impulse"))
                    .hudOutput(id("cooldown"))
                    .hudOutput(id("failure_reason"))
                    .build(id("gravity_lift")));

    private PimorSetP0Abilities() {
    }

    public static PiAbilityRegistrationPlan registrationPlan() {
        return REGISTRATION_PLAN;
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(PimorSet.MOD_ID, path);
    }
}
