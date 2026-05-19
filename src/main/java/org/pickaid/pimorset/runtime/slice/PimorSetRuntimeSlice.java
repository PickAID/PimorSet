package org.pickaid.pimorset.runtime.slice;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.picontent.api.block.PiBlockBuilder;
import org.pickaid.picontent.api.block.PiBlockLoot;
import org.pickaid.picontent.api.block.PiBlockModels;
import org.pickaid.picontent.api.block.PiBlockPlacement;
import org.pickaid.picontent.api.block.PiBlockPresets;
import org.pickaid.picontent.api.block.PiBlockShapes;
import org.pickaid.picontent.api.blockentity.PiBlockEntityBuilder;
import org.pickaid.picontent.api.blockentity.PiContentRenderers;
import org.pickaid.picontent.api.blockentity.PiContentSync;
import org.pickaid.picontent.api.datagen.PiContentDatagenBundle;
import org.pickaid.picontent.api.item.PiItemBuilder;
import org.pickaid.picontent.api.item.PiItemModels;
import org.pickaid.picontent.api.item.PiItemPresets;
import org.pickaid.pidatagraph.result.PiActionResult;
import org.pickaid.pidamage.api.request.PiDamageRequest;
import org.pickaid.piavatar.api.PiAvatarAnchor;
import org.pickaid.piavatar.api.PiAvatarAnchorResolution;
import org.pickaid.piavatar.api.PiAvatarAnchorResolver;
import org.pickaid.piavatar.api.PiAvatarCue;
import org.pickaid.piavatar.api.PiAvatarMask;
import org.pickaid.piavatar.api.PiFirstPersonMode;
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
import org.pickaid.pirig.api.PiRigAnchor;
import org.pickaid.pirig.api.PiRigSocket;
import org.pickaid.pirig.api.PiRigSocketResolution;
import org.pickaid.pirig.api.PiRigSocketResolver;
import org.pickaid.pirenderruntime.api.PiCueType;
import org.pickaid.pirenderruntime.api.PiSceneFrame;

import java.util.Objects;

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
    private static final ResourceLocation GRAVITY_LIFT_ID = id("gravity_lift");

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

    public GravityLiftAbility gravityLiftAbility() {
        return new GravityLiftAbility(
                GRAVITY_LIFT_ID,
                80,
                "storm_charge",
                GRAVITY_LIFT_ID,
                damageRequest(7.0F),
                sceneFrame(0L, 0L),
                hudStateForCooldown(80)
        );
    }

    public PiRigSocketResolution castRigSocket() {
        return PiRigSocketResolver.empty()
                .withServerSocket("right_hand", PiRigAnchor.bodyOffset(0.0D, 1.4D, -0.4D))
                .resolve(PiRigSocket.named("right_hand"));
    }

    public PiAvatarCue avatarCue() {
        return PiAvatarCue.animation("set_burst")
                .mask(PiAvatarMask.UPPER_BODY)
                .firstPerson(PiFirstPersonMode.VANILLA)
                .build();
    }

    public PiAvatarAnchorResolution avatarAnchor() {
        PiAvatarAnchor anchor = PiAvatarAnchor.named("right_hand");
        return PiAvatarAnchorResolver.empty()
                .withBodyOffset(anchor, 0.0D, 1.4D, -0.4D)
                .resolve(anchor);
    }

    public PiContentDatagenBundle contentBundle() {
        return PiContentDatagenBundle.empty()
                .item(PiItemBuilder.named("storm_staff")
                        .section("pimorset")
                        .properties(PiItemPresets.staff())
                        .itemTag("pimorset:staves")
                        .lang("Storm Staff")
                        .model(PiItemModels.handheld("item/storm_staff"))
                        .plan())
                .block(PiBlockBuilder.named("storm_altar")
                        .section("pimorset")
                        .properties(PiBlockPresets.stoneMachine().lightLevel(4).noOcclusion())
                        .blockTag("pimorset:altars")
                        .itemTag("pimorset:altar_items")
                        .shape(PiBlockShapes.box16(1, 0, 1, 15, 12, 15))
                        .placement(PiBlockPlacement.horizontal())
                        .loot(PiBlockLoot.self())
                        .blockstate(PiBlockModels.horizontal("block/storm_altar"))
                        .simpleItem()
                        .creative("pimorset")
                        .plan())
                .blockEntity(PiBlockEntityBuilder.named("storm_altar")
                        .validBlock("pimorset:storm_altar")
                        .dirtySync(PiContentSync.tracking("storm_altar_state"))
                        .renderer(PiContentRenderers.blockEntity("storm_altar"))
                        .plan());
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation("pimorset", path);
    }

    private static PiAbilityHudState hudStateForCooldown(int cooldownTicks) {
        return PiAbilityHudState.builder()
                .cooldownTicks(cooldownTicks)
                .build();
    }

    public record GravityLiftAbility(
            ResourceLocation abilityId,
            int cooldownTicks,
            String resourceKey,
            ResourceLocation graphId,
            PiDamageRequest damage,
            PiSceneFrame sceneFrame,
            PiAbilityHudState hudState
    ) {
        public GravityLiftAbility {
            Objects.requireNonNull(abilityId, "abilityId");
            if (cooldownTicks < 0) {
                throw new IllegalArgumentException("cooldownTicks must be non-negative");
            }
            resourceKey = Objects.requireNonNull(resourceKey, "resourceKey");
            graphId = Objects.requireNonNull(graphId, "graphId");
            damage = Objects.requireNonNull(damage, "damage");
            sceneFrame = Objects.requireNonNull(sceneFrame, "sceneFrame");
            hudState = Objects.requireNonNull(hudState, "hudState");
        }
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
