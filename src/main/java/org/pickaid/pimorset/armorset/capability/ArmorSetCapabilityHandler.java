package org.pickaid.pimorset.armorset.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.pickaid.pimorset.api.registry.ArmorSetRegistry;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.network.PimorSetNetworkHandler;

import java.util.*;

public class ArmorSetCapabilityHandler implements IArmorSetCapability {
    private ArmorSet activeSet;

    public ArmorSetCapabilityHandler() {
        this.activeSet = ArmorSetRegistry.EMPTY_SET.get();
    }

    @Override
    public ArmorSet getActiveSet() {
        return activeSet != null ? activeSet : ArmorSetRegistry.EMPTY_SET.get();
    }

    @Override
    public ArmorSet.State getState() {
        return activeSet.getState();
    }

    @Override
    public ArmorSet.SkillState getSkillState() {
        return activeSet.getSkillProperties().getState();
    }

    @Override
    public void setProperties(ArmorSet.SkillProperties properties) {
        this.activeSet.setSkillProperties(properties);
    }

    @Override
    public ArmorSet.SkillProperties getProperties() {
        return activeSet.getSkillProperties();
    }

    @Override
    public void setActiveSet(ArmorSet set) {
        this.activeSet = set != null ? set : ArmorSetRegistry.EMPTY_SET.get();
    }

    @Override
    public void setState(ArmorSet.State state) {
        activeSet.setState(state);
    }

    @Override
    public void setSkillState(ArmorSet.SkillState state) {
        activeSet.getSkillProperties().setState(state);
    }

    @Override
    public List<Component> getAdditionalTooltip(ItemStack itemStack) {
        List<Component> tooltip = new ArrayList<>();
        if (activeSet != null) {

            addEffectsToTooltip(tooltip);
            addAttributesToTooltip(tooltip);
        }
        return tooltip;
    }

    private void addEffectsToTooltip(List<Component> tooltip) {
        for (Map.Entry<MobEffect, Integer> entry : activeSet.getEffects().entrySet()) {
            tooltip.add(Component.translatable("tooltip.armorset.effect",
                    Component.translatable(entry.getKey().getDescriptionId()),
                    entry.getValue() + 1));
        }
    }

    private void addAttributesToTooltip(List<Component> tooltip) {
        for (Map.Entry<Attribute, AttributeModifier> entry : activeSet.getAttributes().entries()) {
            AttributeModifier modifier = entry.getValue();
            double amount = modifier.getAmount();
            String operation = modifier.getOperation() == AttributeModifier.Operation.ADDITION ? "+" : "×";
            tooltip.add(Component.translatable("tooltip.armorset.attribute",
                    Component.translatable(entry.getKey().getDescriptionId()),
                    operation + String.format("%.2f", amount)));
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (activeSet != null) {
            ResourceLocation id = ArmorSetRegistry.getRegistry().getKey(activeSet);
            if (id != null) {
                nbt.putString("activeSet", id.toString());
                nbt.putString("state", activeSet.getState().name());
                nbt.putString("skillState", activeSet.getSkillProperties().getState().name());
            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("activeSet")) {
            try {
                ResourceLocation id = ResourceLocation.parse(nbt.getString("activeSet"));
                this.activeSet = ArmorSetRegistry.getRegistry().getValue(id);

                if (this.activeSet != null) {
                    try {
                        this.activeSet.setState(ArmorSet.State.valueOf(nbt.getString("state")));
                    } catch (IllegalArgumentException e) {
                        this.activeSet.setState(ArmorSet.State.NORMAL);
                    }
                    this.activeSet.getSkillProperties().setState(ArmorSet.SkillState.valueOf(nbt.getString("skillState")));
                }
            } catch (Exception e) {
                this.activeSet = ArmorSetRegistry.EMPTY_SET.get();
            }
        } else {
            this.activeSet = ArmorSetRegistry.EMPTY_SET.get();
        }
    }

    @Override
    public void syncToClient(Player player) {
        if (!player.level().isClientSide() && player instanceof ServerPlayer) {
            PimorSetNetworkHandler.sendArmorSetSync((ServerPlayer) player, this);
        }
    }
}
