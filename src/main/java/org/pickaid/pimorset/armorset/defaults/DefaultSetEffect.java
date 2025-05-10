package org.pickaid.pimorset.armorset.defaults;

import net.minecraft.world.entity.LivingEntity;
import org.pickaid.pimorset.armorset.ISetEffect;

public class DefaultSetEffect implements ISetEffect {
    public DefaultSetEffect() {
    }

    @Override
    public void applyEffect(LivingEntity entity) {

    }

    @Override
    public void removeEffect(LivingEntity entity) {

    }

    @Override
    public String getIdentifier() {
        return "defaultSetEffect";
    }

    @Override
    public boolean shouldApply() {
        return true;
    }
}
