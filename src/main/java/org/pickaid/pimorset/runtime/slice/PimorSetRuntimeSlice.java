package org.pickaid.pimorset.runtime.slice;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pidatagraph.result.PiActionResult;
import org.pickaid.pidamage.api.request.PiDamageRequest;
import org.pickaid.pihud.PiAbilityHudState;
import org.pickaid.pikey.api.PiInputContext;
import org.pickaid.pikey.api.PiInputPhase;
import org.pickaid.pikey.network.PiInputIntentPacket;
import org.pickaid.piengine.api.ability.PiAbilityType;
import org.pickaid.piengine.api.actor.PiActor;
import org.pickaid.piengine.api.actor.PiActorType;
import org.pickaid.piengine.api.effect.PiEffectHookGroup;
import org.pickaid.piengine.api.effect.PiEffectHookShape;
import org.pickaid.piengine.api.effect.PiEffectType;
import org.pickaid.pinet.api.scope.PiNetScope;
import org.pickaid.pinet.api.scope.PiPacketBudget;
import org.pickaid.pirenderruntime.api.PiCueType;
import org.pickaid.pirenderruntime.api.PiSceneFrame;

public record PimorSetRuntimeSlice(
        ResourceLocation setId,
        PiActor actor,
        PiAbilityType<Integer> activeSkill,
        PiEffectType<Integer> passiveEffect,
        int cooldownTicks
) {
    private static final ResourceLocation ACTIVE_SKILL_ID = id("active_skill");
    private static final ResourceLocation HOLD_INTERACTION_ID = new ResourceLocation("pikey", "hold");
    private static final ResourceLocation DAMAGE_TYPE = id("set_burst");
    private static final PiCueType<String> CAST_CUE = PiCueType.create(id("cast_cue"), String.class);

    public static PimorSetRuntimeSlice playerActiveSkill(ResourceLocation setId, int cooldownTicks) {
        PiActorType actorType = new PiActorType(id("armor_set_actor"));
        PiActor actor = new PiActor(setId, actorType);
        return new PimorSetRuntimeSlice(
                setId,
                actor,
                new SetAbilityType(ACTIVE_SKILL_ID),
                new SetPassiveEffectType(id("passive_effect")),
                cooldownTicks
        );
    }

    public PiInputIntentPacket intentPacket(long clientTick, long sequence, PiInputPhase phase, double progress) {
        return new PiInputIntentPacket(
                ACTIVE_SKILL_ID,
                HOLD_INTERACTION_ID,
                phase,
                clientTick,
                sequence,
                Math.max(0, (int) Math.round(progress * cooldownTicks)),
                progress,
                1,
                0,
                PiInputContext.GAMEPLAY,
                setId
        );
    }

    public PiDamageRequest damageRequest(float amount) {
        return new PiDamageRequest(DAMAGE_TYPE, amount, null, null);
    }

    public PiActionResult graphAction(String payloadKey) {
        return PiActionResult.request(id(payloadKey), payloadKey);
    }

    public PiNetScope syncScope(int entityId) {
        return PiNetScope.trackingEntity(entityId, PiPacketBudget.normal());
    }

    public PiSceneFrame sceneFrame(long gameTick, long sequence) {
        return PiSceneFrame.create(gameTick, sequence, setId).add(PiSceneFrame.Cue.of(CAST_CUE, setId.toString()));
    }

    public PiAbilityHudState hudState(float captureEscapeProgress) {
        return PiAbilityHudState.builder()
                .cooldownTicks(cooldownTicks)
                .captureEscapeProgress(captureEscapeProgress)
                .build();
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation("pimorset", path);
    }

    private static final class SetAbilityType extends PiAbilityType<Integer> {
        private SetAbilityType(ResourceLocation id) {
            super(id, Codec.INT, () -> 0);
        }
    }

    private static final class SetPassiveEffectType extends PiEffectType<Integer> {
        private SetPassiveEffectType(ResourceLocation id) {
            super(id, Codec.INT, () -> 0);
        }

        @Override
        public PiEffectHookShape hookShape() {
            return PiEffectHookShape.of(PiEffectHookGroup.LIFECYCLE, PiEffectHookGroup.TICK, PiEffectHookGroup.COMBAT);
        }
    }
}
