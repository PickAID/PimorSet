package org.pickaid.pimorset.handlers.server;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.pickaid.pibrary.Pibrary;
import org.pickaid.pibrary.api.common.ServerKeyManager;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.armorset.ISetEffect;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;
import org.pickaid.pibrary.content.key.KeyData;

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
            var keyData = ServerKeyManager.getKeyData(player, new ResourceLocation(Pibrary.MOD_ID, "armor_set_skill"));
            if (keyData.state == KeyData.KeyState.CHARGING) {
                set.getEffect().onSkillCharging(player, keyData);
            }
        }
    }
}