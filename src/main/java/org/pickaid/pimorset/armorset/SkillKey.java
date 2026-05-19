package org.pickaid.pimorset.armorset;

import net.minecraft.resources.ResourceLocation;
import org.pickaid.pikey.api.PiInputAction;
import org.pickaid.pikey.api.PiInputActionSpec;
import org.pickaid.pikey.api.PiInputPhase;
import org.pickaid.pikey.api.PiInputContext;
import org.pickaid.pikey.api.PiInputIntent;
import org.pickaid.pikey.api.PiIntentPolicy;
import org.pickaid.pikey.api.PiInteractions;
import org.pickaid.pimorset.PimorSet;

public class SkillKey {
    public static final ResourceLocation SKILL_ACTION_ID = new ResourceLocation(PimorSet.MOD_ID, "active_skill");
    public static PiInputAction SKILL_KEY;

    public static void init() {
        PiInputActionSpec spec = PiInputActionSpec.gameplay()
                .keyMapping(key -> key.translation("key.pimorset.active_skill")
                        .category("key.categories.pimorset")
                        .defaultKeyboard("key.keyboard.g"))
                .interaction(PiInteractions.hold(20))
                .serverIntent(PiIntentPolicy.sendToServer())
                .build();
        SKILL_KEY = new PiInputAction(SKILL_ACTION_ID, spec.context(), spec.keyMapping(), spec.interaction(), spec.serverIntent());
    }

    public static PiInputIntent intent(PiInputPhase phase, long clientTick, long sequence, int elapsedTicks, double progress) {
        return new PiInputIntent(
                SKILL_ACTION_ID,
                SKILL_KEY == null ? PiInteractions.hold(20).id() : SKILL_KEY.interaction().id(),
                phase,
                clientTick,
                sequence,
                elapsedTicks,
                progress,
                1,
                0,
                PiInputContext.GAMEPLAY,
                null
        );
    }
}
