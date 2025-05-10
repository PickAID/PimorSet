package org.pickaid.pimorset.handlers.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.armorset.ISetEffect;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;

public class SetEffectHandler {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player) {
            ArmorSet set = ArmorSetManager.getActiveArmorSet(player);
            ISetEffect oldEffect = set.getEffect();
            ISetEffect newEffect = set.checkEffect(player);
            if (newEffect!= null &&!newEffect.equals(oldEffect)) {
                oldEffect.removeEffect(player);
                set.setEffect(newEffect);
            }
        }
    }
}