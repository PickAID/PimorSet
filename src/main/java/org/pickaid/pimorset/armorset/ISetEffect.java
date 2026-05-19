package org.pickaid.pimorset.armorset;

import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import org.pickaid.pikey.api.PiInputIntent;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;
import org.pickaid.pimorset.armorset.integration.CuriosIntegration;

import java.util.Map;
import java.util.Set;

public interface ISetEffect {
    /**
     * For item hurt effect
     * @param player
     * @param item
     * @return boolean
     */
    default boolean isSetItem(Player player, ItemStack item) {
        ArmorSet activeSet = ArmorSetManager.getActiveArmorSet(player);
        boolean isSetItem = false;
        for (Map.Entry<EquipmentSlot, Set<Item>> entry : activeSet.getEquipmentItems().entrySet()) {
            if (entry.getValue().contains(item.getItem())) {
                isSetItem = true;
                break;
            }
        }
        if (!isSetItem && CuriosIntegration.isCuriosLoaded) {
            isSetItem = activeSet.getCurioItems().containsKey(item.getItem());
        }
        return isSetItem;
    }

    /**
     * For the user to set up a default skill of this kind of ArmorSet.
     *
     * @param entity
     */
    default void skillEffect(LivingEntity entity) {}

    default void releaseEffect(LivingEntity entity) {}

    default ItemHurtEffectResult itemHurtEffect(LivingEntity entity, ItemStack item, int originalDamage) {
        return ItemHurtEffectResult.unmodified();
    }

    default void startSprintingEffect(LivingEntity entity) {}

    default void stopSprintingEffect(LivingEntity entity) {}

    default void sprintingEffect(LivingEntity entity) {}

    default void normalTickingEffect(LivingEntity entity) {}

    default void jumpEffect(LivingEntity entity) {}

    default void landEffect(LivingEntity entity, double distance, BlockState landingBlock, BlockPos pos) {}

    default void sprintingJumpEffect(LivingEntity entity) {}

    default void onTargetedEffect(LivingEntity entity, LivingChangeTargetEvent changer) {}

    default void blockingEffect(LivingEntity entity) {}

    default void crouchingEffect(LivingEntity entity) {}

    default void onStandOnFluidEffect(LivingEntity entity, StandOnFluidEvent event) {}

    default void stackedOnOther(LivingEntity entity, ItemStackedOnOtherEvent event) {}

    /**
     * Trigger when the entity press skill key.
     *
     * @param player
     * @param intent
     */
    default void onSkillPress(ServerPlayer player, PiInputIntent intent) {}

    /**
     * Trigger when the entity is charging key.
     * @param player
     * @param intent
     */
    default void onSkillCharging(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the entity holds key.
     * @param player
     * @param intent
     */
    default void onSkillHeld(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the entity release skill key.
     * @param player
     * @param intent
     */
    default void onSkillRelease(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the entity finish releasing skill key.
     * @param player
     * @param intent
     */
    default void onSkillFinish(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the skill key time out.
     * @param player
     * @param intent
     */
    default void onSkillTimeOut(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the skill key rapid clicks
     * @param player
     * @param intent
     */
    default void onSkillRapidClick(ServerPlayer player, PiInputIntent intent) {};

    /**
     * Trigger when the skill key rapid clicks finish
     * @param player
     * @param intent
     */
    default void onSkillRapidClickFinish(ServerPlayer player, PiInputIntent intent) {};

    default void applyEffect(LivingEntity entity) {}

    default void removeEffect(LivingEntity entity) {}

    default String getIdentifier() {
        return "set_effect";
    }

    default boolean shouldApply() {
        return true;
    }

    default Component getDescription(Player player, ItemStack stack) {
        return Component.translatable("tooltip.pibrary.armorset." + getIdentifier() + ".description");
    };

    default boolean checkDescription(Player player, ItemStack stack) {
        return false;
    }
}
