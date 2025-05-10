package org.pickaid.pimorset.armorset;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.pickaid.pibrary.api.effect.IPlayerEffect;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;
import org.pickaid.pimorset.armorset.integration.CuriosIntegration;
import org.pickaid.pibrary.content.key.KeyData;

import java.util.Map;
import java.util.Set;

public interface ISetEffect extends IPlayerEffect {
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

    /**
     * Trigger when the entity press skill key.
     *
     * @param player
     * @param keyData
     */
    default void onSkillPress(ServerPlayer player, KeyData keyData) {}

    /**
     * Trigger when the entity is charging skill.
     * @param player
     * @param power
     */
    default void onSkillCharging(ServerPlayer player, KeyData power) {};

    /**
     * Trigger when the entity release skill.
     * @param player
     * @param power
     */
    default void onSkillRelease(ServerPlayer player, KeyData power) {};

    default Component getDescription(Player player, ItemStack stack) {
        return Component.translatable("tooltip.pibrary.armorset." + getIdentifier() + ".description");
    };

    default boolean checkDescription(Player player, ItemStack stack) {
        return false;
    }
}