package org.pickaid.pimorset.armorset.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.pickaid.pimorset.armorset.ArmorSet;

import java.util.List;

public interface IArmorSetCapability extends INBTSerializable<CompoundTag> {
    /**
     *
     * @return {@link ArmorSet}
     */
    ArmorSet getActiveSet();
    ArmorSet.State getState();
    void setSkillState(ArmorSet.SkillState state);
    ArmorSet.SkillState getSkillState();
    void setProperties(ArmorSet.SkillProperties properties);
    ArmorSet.SkillProperties getProperties();
    void setActiveSet(ArmorSet set);
    void setState(ArmorSet.State state);
    List<Component> getAdditionalTooltip(ItemStack itemStack);
    void syncToClient(Player player);
}